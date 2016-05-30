package com.lucareto.jersey.clients.db;

import java.net.UnknownHostException;
import java.util.Objects;

import com.lucareto.jersey.clients.db.collections.ArticleDAO;
import com.lucareto.jersey.clients.db.collections.SessionDAO;
import com.lucareto.jersey.clients.db.collections.UserDAO;

public class MongoClientHolder {
    //TODO: DYNAMICALLY SET THE URI FROM CONFIG.YAML;
    private static final String MONGO_CLIENT_URI = "mongodb://localhost:27017";
    private static final String DB_NAME = "newspaper";
    
    
    private final MongoDBClient mongoClient;
    
    private static volatile MongoClientHolder instance = null;
    
    private MongoClientHolder(final String mongoURI, final String dbName) throws UnknownHostException {
        mongoClient = new MongoDBClient(mongoURI, dbName);
    }
    
    private MongoDBClient getMongoDBClient() {
        return mongoClient;
    }
    
    public static void load() throws UnknownHostException {
        if(Objects.isNull(instance)) 
            instance = new MongoClientHolder(MONGO_CLIENT_URI, DB_NAME);
        else throw new IllegalStateException("Instance already initialized");
    }
    
    private static MongoClientHolder getInstance() {
        if (instance == null) {
            throw new IllegalStateException("Instance not initialized");
        }
        return instance;
    }
    
    public static UserDAO getUserDAO() {
        return getInstance().getMongoDBClient().getUserDAO();
    }
    
    public static SessionDAO getSessionDAO() {
        return getInstance().getMongoDBClient().getSessionDAO();
    }
    
    public static ArticleDAO getArticleDAO() {
        return getInstance().getMongoDBClient().getArticleDAO();
    }
}
