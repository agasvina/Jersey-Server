package com.lucareto.jersey.clients.model;

import java.io.Serializable;
import java.util.Date;

public class Article implements Serializable, Node{

    private static final long serialVersionUID = 3667424756208005409L;
    public static final String NID = "article:";

    private String id;
    private String title;
    private String body;
    private String authorId;
    private boolean published;
    private boolean nominated;
    private Date date;
    
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

    public String getBody() {
        return body;
    }

    public void setBody(final String body) {
        this.body = body;
    }

    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(final String authorId) {
        this.authorId = authorId;
    }

    public boolean getPublished() {
        return published;
    }

    public void setPublished(boolean published) {
        this.published = published;
    }

    public boolean getNominated() {
        return nominated;
    }

    public void setNominated(boolean nominated) {
        this.nominated = nominated;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(final Date date) {
        this.date = date;
    }
}
