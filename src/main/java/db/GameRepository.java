package db;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class GameRepository implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<Game> gameList = new ArrayList<Game>();

    public List<Game> getGames() {
        return gameList;
    }

    public void setGames(List<Game> gameList) {
        this.gameList = gameList;
    }
}
