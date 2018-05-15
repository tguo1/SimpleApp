package drivers;

import db.Game;
import db.GameDAO;
import db.GameDAOImpl;
import db.MorphiaService;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class MongoMorphiaDriver {
    public static void main(String[] args) {
        MorphiaService ms = new MorphiaService();

        Game myGame = new Game();
        myGame.setName("Super Mario 64");
        myGame.setAuthor("Nintendo");
        myGame.setRelease_date("01/01/1997");

        List<String> tags = new ArrayList<String>();
        tags.add("single player");
        tags.add("third person");
        tags.add("fantasy");
        tags.add("platformer");
        myGame.setTags(tags);

        GameDAO gameDAO = new GameDAOImpl(Game.class, ms.getDatastore());
        //gameDAO.save(myGame);

        while (true) {
            List<Game> retrievedGames = new ArrayList<Game>();
            String input;

            try {
                System.out.print("Enter a search (X Y): ");
                BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                input = br.readLine();
            } catch (Exception e) {
                System.out.println(e);
                continue;
            }

            if (input.equalsIgnoreCase("Exit")) break;

            String[] inputs = input.split(":");

            if (inputs.length < 2) {
                System.out.println("Invalid input. Requires at least 2 arguments. Format: 'command:tag'");
                continue;
            }

            String command = inputs[0];
            String tag = inputs[1];

            if (command.equalsIgnoreCase("tag")) {
                retrievedGames = gameDAO.getGameByTag(tag);
            } else if (command.equalsIgnoreCase("name")) {
                retrievedGames = new ArrayList<Game>();
                Game tempGame = gameDAO.getGameByName(tag);
                if (tempGame != null) retrievedGames.add(tempGame);
            } else {
                System.out.println("Invalid command '" + command + "'. Valid commands: (tag, name) ");
            }

            System.out.println("================================================================================");
            System.out.println("Search results found (" + retrievedGames.size() + " games): ");
            for (Game g : retrievedGames) {
                System.out.println(g.toString());
            }
            System.out.println("================================================================================");
        }
    }
}
