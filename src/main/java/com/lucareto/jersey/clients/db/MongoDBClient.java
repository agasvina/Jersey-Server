package com.lucareto.jersey.clients.db;

import java.net.UnknownHostException;

import com.lucareto.jersey.clients.db.collections.ArticleDAO;
import com.lucareto.jersey.clients.db.collections.SessionDAO;
import com.lucareto.jersey.clients.db.collections.UserDAO;
import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

public class MongoDBClient {
    
    private final UserDAO  userDAO;
    private final SessionDAO sessionDAO;
    private final ArticleDAO articleDAO;
    
    public MongoDBClient(final String mongoURI, final String dbName) throws UnknownHostException {
        MongoClient mongoClient = new MongoClient(new MongoClientURI(mongoURI));
        DB db = mongoClient.getDB(dbName);
        
        userDAO = new UserDAO(db);
        sessionDAO = new SessionDAO(db);
        articleDAO = new ArticleDAO(db);
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
