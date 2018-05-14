package controller;

import db.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class GameController {
    private MorphiaService ms;
    private GameDAO gameDAO;
    private GameRepository gameRepository;

    public GameController() {
        ms = new MorphiaService();
        gameDAO = new GameDAOImpl(Game.class, ms.getDatastore());
        gameRepository = new GameRepository();
    }

    @RequestMapping(value = "/games", produces = MediaType.APPLICATION_JSON_VALUE,  method = RequestMethod.GET)
    public ResponseEntity<GameRepository> getAllGames() {
        gameRepository.setGames(gameDAO.getAllGames());
        return new ResponseEntity<GameRepository>(gameRepository, HttpStatus.OK);
    }

    @RequestMapping(value = "/games/{tag}", produces = MediaType.APPLICATION_JSON_VALUE,  method = RequestMethod.GET)
    public ResponseEntity<GameRepository> getGamesByTag(@PathVariable String tag) {
        gameRepository.setGames(gameDAO.getGameByTag(tag.replace('_',' ')));
        return new ResponseEntity<GameRepository>(gameRepository, HttpStatus.OK);
    }

    @RequestMapping(value = "/games", method = RequestMethod.POST)
    public void addGame(Game game) {
        gameDAO.addGame(game);
    }
}
