package db;

import org.bson.types.ObjectId;
import org.mongodb.morphia.dao.DAO;

import java.util.List;

public interface GameDAO extends DAO<Game, ObjectId> {

    public List<Game> getAllGames();

    public List<Game> getGameByTag(String tag);

    public Game getGameByName(String name);

    public void addGame(Game game);

    public void deleteGameByName(String name);

    public void deleteGameById(String id);

    public void updateGame(Game game);
}
