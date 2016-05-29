package com.lucareto.jersey.clients.db;

import java.net.UnknownHostException;

import com.lucareto.jersey.clients.db.collections.ArticleDAO;
import com.lucareto.jersey.clients.db.collections.SessionDAO;
import com.lucareto.jersey.clients.db.collections.UserDAO;
import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

public class MongoDBClient {
    
    //TODO: DYNAMICALLY SET THE URI FROM CONFIG.YAML;
    private static final String MONGO_CLIENT_URI = "mongodb://localhost:27017";
    private static final String DB_NAME = "newspaper";
    
    private final UserDAO  userDAO;
    private final SessionDAO sessionDAO;
    private final ArticleDAO articleDAO;
    
    public MongoDBClient() {
        DB db = DBInstance.INSTANCE.getDb();
        
        userDAO = new UserDAO(db);
        sessionDAO = new SessionDAO(db);
        articleDAO = new ArticleDAO(db);
    }
    
    private enum DBInstance {
        INSTANCE;
        private DB dbSingleton;
        
        private DBInstance() {
            try {
                MongoClient mongoClient = new MongoClient(new MongoClientURI(MONGO_CLIENT_URI));
                dbSingleton = mongoClient.getDB(DB_NAME);
                
            } catch (UnknownHostException e) {
                throw new RuntimeException("Unable to connect to the server",e);
            } 
        }
        
        private DB getDb() {
            return dbSingleton;
        }
    }
    

    public UserDAO getUserDAO() {
        return userDAO;
    }

    public SessionDAO getSessionDAO() {
        return sessionDAO;
    }

    public ArticleDAO getArticleDAO() {
        return articleDAO;
    }
    
}
