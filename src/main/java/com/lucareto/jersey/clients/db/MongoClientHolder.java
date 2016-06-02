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
    
    private static MongoClientHolder getInstance() {
        if (Objects.isNull(instance)) {
            synchronized(MongoClientHolder.class) {
                try {
                    instance = new MongoClientHolder(MONGO_CLIENT_URI, DB_NAME);
                } catch (UnknownHostException e) {
                    throw new RuntimeException("Unable to connect the host", e);
                }
            }
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
