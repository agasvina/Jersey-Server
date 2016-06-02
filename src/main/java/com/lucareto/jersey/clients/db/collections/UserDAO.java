package com.lucareto.jersey.clients.db.collections;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Objects;
import java.util.Random;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lucareto.jersey.clients.model.User;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoException;


public class UserDAO {
    private static final Logger logger = LoggerFactory.getLogger(UserDAO.class);
    
    private static final String COLLECTION_NAME = "users";
    
    private final DBCollection usersCollection;
    private Random random = new SecureRandom();


    public UserDAO(final DB blogDatabase) {
        usersCollection = blogDatabase.getCollection(COLLECTION_NAME);
    }
    
    public boolean addUser(User user) {
        String passwordHash = makePasswordHash(user.getPassword(),Integer.toString(random.nextInt()));
        user.setPassword(passwordHash);
        
        try {
            usersCollection.insert(user.createDBObject());
            return true;
        } catch (MongoException.DuplicateKey e) {
            logger.warn("Username is no available" + user.getUsername());
        }
        return false;
        
    }
    
    public boolean validateLogin(final User user) {
        DBObject userObject = usersCollection.findOne(new BasicDBObject("_id", user.getUsername()));
        if(Objects.nonNull(userObject)) {
            String hashedAndSalted = userObject.get("password").toString();
            String salt = hashedAndSalted.split(",")[1];
            if(hashedAndSalted.equals(makePasswordHash(user.getPassword(), salt)))
                return true;
        }
        return false;
    }
    

    //TODO: IMPLEMENT BETTER ENCRYPTION
    private String makePasswordHash(String password, String salt) {
        try {
            String saltedAndHashed = password + "," + salt;
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(saltedAndHashed.getBytes());
            byte [] hashedBytes = (new String(digest.digest(), StandardCharsets.UTF_8.name()).getBytes());
            return Base64.encodeBase64String(hashedBytes) + "," + salt;
        } catch (NoSuchAlgorithmException e) {
            logger.error("MD5 algorithm is not available", e);
            throw new RuntimeException("MD5 algorithm is not available", e);
        } catch (UnsupportedEncodingException e) {
            logger.error("MD5 algorithm is not available", e);
            throw new RuntimeException("UTF_8 is not available", e);
        }
    }

}
