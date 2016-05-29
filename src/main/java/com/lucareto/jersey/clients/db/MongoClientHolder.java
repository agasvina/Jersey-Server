package com.lucareto.jersey.clients.db;

import java.util.Objects;

import com.lucareto.jersey.clients.db.collections.ArticleDAO;
import com.lucareto.jersey.clients.db.collections.SessionDAO;
import com.lucareto.jersey.clients.db.collections.UserDAO;

public class MongoClientHolder {

    private final MongoDBClient mongoClient;
    
    private static volatile MongoClientHolder instance = null;
    
    private MongoClientHolder() {
        mongoClient = new MongoDBClient();
    }
    
    private MongoDBClient getMongoDBClient() {
        return mongoClient;
    }
    
    public static void load() {
        if(Objects.isNull(instance)) 
            instance = new MongoClientHolder();
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
