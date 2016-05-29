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

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoException;


public class UserDAO {
    private static final Logger logger = LoggerFactory.getLogger(UserDAO.class);
    
    private static final String COLLECTION_NAME = "users";
    
    private final DBCollection usersCollection;
    private Random random = new SecureRandom();


    public UserDAO(final DB blogDatabase) {
        usersCollection = blogDatabase.getCollection(COLLECTION_NAME);
    }
    
    public boolean addUser(final String username, final String password, final String email) {
        String passwordHash = makePasswordHash(password,Integer.toString(random.nextInt()));
        
        BasicDBObject user = new BasicDBObject();
        user.append("_id", username).append("password", passwordHash);
        if(Objects.nonNull(email) && !email.isEmpty()) {
            user.append("email", email);
        }
        
        try {
            usersCollection.insert(user);
            return true;
        } catch (MongoException.DuplicateKey e) {
            logger.warn("Username is no available" + username);
        }
        return false;
        
    }

    //TODO: implement better encyption 
    private String makePasswordHash(String password, String salt) {
        try {
            String saltedAndHashed = password + "," + salt;
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(saltedAndHashed.getBytes());
            byte [] hashedBytes = (new String(digest.digest(), StandardCharsets.UTF_8.name()).getBytes());
            return Base64.encodeBase64String(hashedBytes) + "," + salt;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("MD5 algorithm is not available", e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("UTF_8 is not available", e);
        }
    }

}
