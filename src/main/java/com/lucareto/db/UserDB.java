package com.lucareto.db;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import com.lucareto.db.model.User;

public class UserDB extends MapDB<User> implements Serializable{

    private static final long serialVersionUID = -9010924243586283602L;
    private static final String DB_NAME = "user_db";

    public UserDB() {
        super(UserDB.DB_NAME);
    }
    
    public User addUser(User user) {
        if(Objects.nonNull(user.getId()))
            put(user.getId(), user);
        else {
            user.setId(generateUrn());
            put(user.getId(), user);
        }
        return get(user.getId());
    }
    
    public User getUser(String id) {
        return get(id);
    }
    
    public Boolean deleteUser(String id) {
        return remove(id);
    }
    
    public List<User> getAllUsers() {
        return getAll();
    }
    
    protected String generateUrn() {
        return "user:" + UUID.randomUUID().toString();
    }
}
