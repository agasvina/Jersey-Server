package com.lucareto.jersey.clients.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public class Article implements Serializable, Node, MongoDBObject {

    private static final long serialVersionUID = 3667424756208005409L;
    public static final String NID = "article:";

    private String id;
    private String title;
    
    //TODO: implements Draft.JS (contentState)
    private Object body;
    
    private String authorId;
    private int nominated;
   
    private List<String> tags = new ArrayList<>();
    
    private Date createdDate;
    
    
    @Override
    public String getId() {
        return id;
    }
    
    public void setId(final String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public Object getBody() {
        return body;
    }

    public void setBody(final Object body) {
        this.body = body;
    }

    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(final String authorId) {
        this.authorId = authorId;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(final Date date) {
        this.createdDate = date;
    }
    
    public int getNominated() {
        return nominated;
    }
    
    public void setNominated(final int nominated) {
        this.nominated = nominated;
    }
    
    public List<String> getTags() {
        return tags;
    }
    
    public void setTags(final List<String> tags){
        this.tags = tags;
    }

    @Override
    public DBObject createDBObject() {
        BasicDBObject mongoArticle = new BasicDBObject();
        mongoArticle.append("_id", id);
        mongoArticle.append("title", title);
        mongoArticle.append("body", body);
        mongoArticle.append("authorId", authorId);
        mongoArticle.append("nominated", nominated);
        mongoArticle.append("tags", tags);
        mongoArticle.append("createdDate", createdDate);
        return mongoArticle;
    }
}
