package db;

import com.mongodb.MongoClient;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

public class MorphiaService {
    private final MongoClient mongo;
    private final Morphia morphia;
    private final Datastore datastore;

    public MorphiaService() {
        mongo = new MongoClient("localhost",27017);
        morphia = new Morphia();
        String dbName = "Tommy";
        datastore = morphia.createDatastore(mongo, dbName);
        morphia.mapPackage("db");
    }

    public MongoClient getMongo() {
        return mongo;
    }

    public Morphia getMorphia() {
        return morphia;
    }

    public Datastore getDatastore() {
        return datastore;
    }
}
