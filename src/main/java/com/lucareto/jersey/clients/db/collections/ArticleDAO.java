package com.lucareto.jersey.clients.db.collections;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lucareto.jersey.clients.model.Article;
import com.lucareto.jersey.util.Utils;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

public class ArticleDAO {
    private static final Logger logger = LoggerFactory.getLogger(ArticleDAO.class);

    private static final String COLLECTION_NAME = "articles";
    
    private DBCollection postsCollection;
    
    public ArticleDAO(final DB blogDatabase) {
        postsCollection = blogDatabase.getCollection(COLLECTION_NAME);
    }
    
    public String createArticle(final Article article) {
        article.setNominated(0);
        article.setId(Utils.generateUrn(Article.NID));
        article.setCreatedDate(new Date());
        try {
            postsCollection.insert(article.createDBObject());
            return article.getId();
        } catch (Exception e) {
            logger.error("Error inserting posts", e);
        }
        return null;
    }
    
    
    public DBObject findById(String id) {
        return postsCollection.findOne(new BasicDBObject("_id", id));
    }

    public List<DBObject> findByDateDescending(Integer limit) {
        List<DBObject> posts = new ArrayList<>();
        DBCursor cursor;
        if(Objects.nonNull(limit)) 
            cursor = postsCollection.find().sort(new BasicDBObject().append("createdDate", -1)).limit(limit);
        else cursor = postsCollection.find().sort(new BasicDBObject().append("createdDate", -1));
        try {
            posts = cursor.toArray();
        } finally {
            cursor.close();
        }
        return posts;
    } 
    

}
