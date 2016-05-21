package com.lucareto.jersey.db.model;

import java.io.Serializable;

public class User  implements Serializable{
    
    private static final long serialVersionUID = 833345861967299144L;

    private String id;
    
    private String name;
    
    private String email;
    
    private String title;
    
    public User() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
