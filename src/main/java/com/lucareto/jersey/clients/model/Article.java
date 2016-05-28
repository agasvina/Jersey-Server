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
    private String section;
    private boolean published;
    private boolean nominated;
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
    
    public String getSection() {
        return section;
    }
    
    public void setSection(final String section) {
        this.section = section;
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

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(final Date date) {
        this.createdDate = date;
    }
}
