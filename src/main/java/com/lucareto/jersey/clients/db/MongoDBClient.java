package com.lucareto.jersey.clients.db;

import java.io.IOException;
import java.net.UnknownHostException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

public class MongoDBClient {
    private static final Logger logger = LoggerFactory.getLogger(MongoDBClient.class);
    
    //TODO: Use config.yaml;
    private static final String MONGO_CLIENT_URI = "mongodb://localhost:27017";
    private static final String DB_NAME = "newspaper";
    
    private final UserDAO  userDAO;
    private final SessionDAO sessionDAO;
    private final ArticleDAO articleDAO;
    
    //TODO: implement as SINGLETON
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
