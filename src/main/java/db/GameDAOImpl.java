package db;

import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.dao.BasicDAO;
import org.mongodb.morphia.query.UpdateOperations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.management.Query;
import java.util.List;

@Repository
public class GameDAOImpl extends BasicDAO<Game, ObjectId> implements GameDAO{

    @Autowired
    public GameDAOImpl(Class<Game> entityclass, Datastore ds) {
        super(entityclass, ds);
    }

    public List<Game> getAllGames() { return find().asList(); }

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

    public void updateGame(Game game) {
        UpdateOperations<Game> ops = createUpdateOperations().set("name",game.getName())
                                                             .set("author",game.getAuthor())
                                                             .set("release_date",game.getRelease_date())
                                                             .set("tags",game.getTags())
                                                             .set("imgs",game.getImgs());
        update(createQuery().field("_id").equal(game.getId()), ops);
    }
}
