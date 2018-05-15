package drivers;

import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;

import com.mongodb.client.model.Filters;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MongoDriver {

    private MongoClient mongo;
    private MongoCredential credential;
    private MongoDatabase database;

    private final String GAMES = "games";
    private final String USERS = "user";
    private final String DB = "Tommy";

    public MongoDriver() {
        mongo = new MongoClient("localhost",27017);
        database = mongo.getDatabase(DB);
    }

    public MongoCollection<Document> getGames() {
        return database.getCollection(GAMES);
    }
    public MongoCollection<Document> getUsers() {
        return database.getCollection(USERS);
    }

    public List<Document> getGameByTag(String tag) {
        MongoCollection<Document> col = this.getGames();
        return col.find(Filters.regex("tags",tag)).into(new ArrayList<Document>());
    }

    public void removeGame(String name) {
        try {
            MongoCollection<Document> col = this.getGames();
            col.deleteOne(Filters.eq("name",name));
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void insertGame(Document doc)
    {
        try {
            MongoCollection<Document> col = this.getGames();
            col.insertOne(doc);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void main(String[] args) {
        MongoDriver mydb = new MongoDriver();
        Scanner myS = new Scanner(System.in);

        while (true) {
            System.out.print("Enter a tag (Exit to quit): ");
            String tag = myS.next();

            if (tag.equalsIgnoreCase("Exit")) break;

            List<Document> mylist = mydb.getGameByTag(tag);

            System.out.println(mylist.size() + " games found:");
            for (Document d : mylist) {
                System.out.println(d);
            }
        }
    }
}
