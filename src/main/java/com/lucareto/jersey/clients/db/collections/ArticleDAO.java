package com.lucareto.jersey.clients.db.collections;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;

import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import com.lucareto.jersey.clients.model.Article;
import com.lucareto.jersey.util.Utils;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

public class ArticleDAO {
    private static final String COLLECTION_NAME = "articles";
    
    private DBCollection postsCollection;
    private ListeningExecutorService service;
    
    public ArticleDAO(final DB blogDatabase) {
        postsCollection = blogDatabase.getCollection(COLLECTION_NAME);
        service = MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(10));

    }
    
    public DBObject createArticle(final Article article){
        article.setNominated(0);
        article.setId(Utils.generateUrn(Article.NID));
        article.setCreatedDate(new Date());
        BasicDBObject dbo= article.createDBObject();
        postsCollection.insert(dbo);
        return findById(dbo.get("_id").toString());
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
    
    public ListenableFuture<DBObject> createArticleAsync(final Article article) {
        return service.submit(new Callable<DBObject>() {
                    @Override
                     public DBObject call() throws Exception {
                         return createArticle(article);
                     }
                 });
    }
    
    public ListenableFuture<DBObject> findByIdAsync(final String id) {
        return service.submit(new Callable<DBObject>() {
                    @Override
                     public DBObject call() throws Exception {
                         return findById(id);
                     }
                 });
    }
    
    public ListenableFuture<List<DBObject>> findByDateDescendingAsync(final Integer limit) {
        return service.submit(new Callable<List<DBObject>>() {
                    @Override
                     public List<DBObject> call() throws Exception {
                         return findByDateDescending(limit);
                     }
                 });
    }
     

}
