package com.lucareto.jersey.clients.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Issue implements Serializable, Node {

    private static final long serialVersionUID = 8793143031874692721L;
    public static final String NID = "issue:";
    
    private String id;
    private String title;
    private String section;
    private List<String> articleIds = new ArrayList<>();
    private boolean published;
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(final String title) {
        this.title = title;
    }
    
    public String getSection() {
        return section;
    }

    public void setSection(final String section) {
        this.section = section;
    }

    public List<String> getArticleIds() {
        return articleIds;
    }

    public void setArticleIds(final List<String> articleIds) {
        this.articleIds = articleIds;
    }

    public Boolean getPublished() {
        return published;
    }

    public void setPublished(final Boolean published) {
        this.published = published;
    }

    public void setId(final String id) {
        this.id = id;
    }

    @Override
    public String getId() {
        return id;
    }
}
