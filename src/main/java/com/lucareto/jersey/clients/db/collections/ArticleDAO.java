package com.lucareto.jersey.clients.db.collections;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lucareto.jersey.clients.model.Article;
import com.lucareto.jersey.util.Utils;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;

public class ArticleDAO {
    private static final Logger logger = LoggerFactory.getLogger(ArticleDAO.class);

    private static final String COLLECTION_NAME = "articles";
    
    private DBCollection postsCollection;
    
    public ArticleDAO(final DB blogDatabase) {
        postsCollection = blogDatabase.getCollection(COLLECTION_NAME);
    }
    
    public String addArticle(final Article article) {
        article.setId(Utils.generateUrn(Article.NID));
        article.setCreatedDate(new Date());
        
        BasicDBObject mongoArticle = new BasicDBObject();
        mongoArticle.append("_id", article.getId());
        mongoArticle.append("title", article.getTitle());
        mongoArticle.append("body", article.getBody());
        mongoArticle.append("authorId", article.getAuthorId());
        //TODO:change into is...
        mongoArticle.append("createdDate", article.getCreatedDate());
        try {
            postsCollection.insert(mongoArticle);
            return article.getId();
        } catch (Exception e) {
            logger.error("Error inserting posts", e);
        }
        return null;
    }
    

}
