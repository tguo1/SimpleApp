package db;

import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.dao.BasicDAO;

import java.util.List;

public class GameDAOImpl extends BasicDAO<Game, ObjectId> implements GameDAO{

    public GameDAOImpl(Class<Game> entityclass, Datastore ds) {
        super(entityclass, ds);
    }

    public List<Game> getAllGames() { return createQuery().field("name").contains("").asList();};

    public List<Game> getGameByTag(String tag) {
        return createQuery().field("tags").equalIgnoreCase(tag).asList();
    }

    public Game getGameByName(String name) {
        return createQuery().field("name").containsIgnoreCase(name).get();
    }

    public void addGame(Game game) {
        this.save(game);
    }

    public void deleteGameByName(String name) {
        this.deleteByQuery(createQuery().field("name").equalIgnoreCase(name));
    }

    public void deleteGameById(String id) {
        this.deleteByQuery(createQuery().field("id").equalIgnoreCase(id));
    }
}
