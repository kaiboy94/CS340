package server.db.mongodb;

import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoDatabase;
import server.db.IGameDAO;
import server.db.IUserDAO;
import server.plugin.IPlugin;
import server.plugin.PersistencePlugin;

import java.util.Map;

/**
 * Created by kevin on 12/7/16.
 */
public class MongoProvider extends PersistencePlugin {

    private MongoUserDAO userDAO;
    private MongoGameDAO gameDAO;
    private MongoClient client;
    private MongoDatabase database;

    public MongoProvider(Map params) {
        super(params);
    }

    @Override
    public IPlugin start() {
        client = new MongoClient(new ServerAddress("localhost", 27017));
        database = client.getDatabase("catan");
        return this;
    }

    @Override
    public String getName() {
        return "mongo";
    }

    @Override
    public boolean createDB() {
        database.createCollection("users");
        database.createCollection("games");
        database.createCollection("commands");
        return true;
    }

    @Override
    public boolean startTransaction() {
        // No transactions in MongoDB
        return true;
    }

    @Override
    public boolean finishTransaction() {
        // No transactions in MongoDB
        return true;
    }

    @Override
    public boolean clearDB() {
        return false;
    }

    @Override
    public IUserDAO getUserDAO() {
        if (userDAO == null) {
            userDAO = new MongoUserDAO(database);
        }
        return userDAO;
    }

    @Override
    public IGameDAO getGameDAO() {
        if (gameDAO == null) {
            gameDAO = new MongoGameDAO(database);
        }
        return gameDAO;
    }
}
