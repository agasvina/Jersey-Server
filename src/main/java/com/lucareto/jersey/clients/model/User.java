package com.lucareto.jersey.clients.model;

import java.io.Serializable;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public class User implements Serializable, MongoDBObject{

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
    @Override
    public DBObject createDBObject() {
        BasicDBObject mongoObject = new BasicDBObject();
        mongoObject.append("_id", username);
        mongoObject.append("password", password);
        mongoObject.append("email", email);
        return mongoObject;
    }
}
