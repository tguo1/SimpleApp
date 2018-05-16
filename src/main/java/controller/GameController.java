package controller;

import db.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:63342")
@Controller
public class GameController {
    private MorphiaService ms;
    private GameDAO gameDAO;

    public GameController() {
        ms = new MorphiaService();
        gameDAO = new GameDAOImpl(Game.class, ms.getDatastore());
    }

    @RequestMapping(value = "/games", produces = MediaType.APPLICATION_JSON_VALUE,  method = RequestMethod.GET)
    public ResponseEntity getAllGames() {
        List<Game> gameList = gameDAO.getAllGames();
        return ResponseEntity.ok(gameList);
    }

    @RequestMapping(value = "/games/{tag}", produces = MediaType.APPLICATION_JSON_VALUE,  method = RequestMethod.GET)
    public ResponseEntity getGamesByTag(@PathVariable("tag") String tag) {
        List<Game> gameList = gameDAO.getGameByTag(tag.replace('_',' '));
        return ResponseEntity.ok(gameList);
    }

    @RequestMapping(value = "/games", consumes = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
    public ResponseEntity<Game> addGame(@RequestBody Game game) {
        gameDAO.addGame(game);
        return getAllGames();
    }
    
    @RequestMapping(value = "/games", consumes = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.DELETE)
    public ResponseEntity<Game>  deleteGame(@RequestBody Game game) {
        gameDAO.deleteGameByName(game.getName());
        return getAllGames();
    }

    @RequestMapping(value = "/games", consumes = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.PUT)
    public ResponseEntity<Game>  updateGame(@RequestBody Game game) {
        gameDAO.updateGame(game);
        return getAllGames();
    }
}
