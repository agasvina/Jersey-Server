package com.lucareto.jersey.db.model;

import java.io.Serializable;

public class User implements Serializable{

    private static final long serialVersionUID = -8041797624979735132L;
    
    private String username;
    private String email;
    private String password;
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
}
