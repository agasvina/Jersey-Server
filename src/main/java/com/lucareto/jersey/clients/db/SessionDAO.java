/*
 * IBM Confidential
 *
 * OCO Source Materials
 *
 * © Copyright IBM Corp. 2015, 2016
 *
 * The source code for this program is not published or otherwise
 * divested of its trade secrets, irrespective of what has been
 * deposited with the U.S. Copyright Office.
 */
package com.lucareto.jersey.clients.db;

import java.security.SecureRandom;
import java.util.Objects;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;

import org.apache.commons.codec.binary.Base64;

public class SessionDAO {
    private static final String DB_NAME = "sessions";
    
    private final DBCollection sessionsCollection;
    
    public SessionDAO(final DB blogDatabase) {
        sessionsCollection = blogDatabase.getCollection(DB_NAME);
    }
    
    public String findUserNameBySessionId(final String sessionID) {
        DBObject session = getSession(sessionID);
        if(Objects.nonNull(session)) 
            return session.get("username").toString();
        return null;
    }

    //Make the session as the token
    //TODO: Use JWT
    public String startSession(final String username) {
        String sessionID = Base64.encodeBase64String(generate32RandomBytes());
        BasicDBObject session = new BasicDBObject("username", username);
        session.append("_id", sessionID);
        sessionsCollection.insert(session);
        return sessionID;
    }
    
    private DBObject getSession(final String sessionID) {
        return sessionsCollection.findOne(new BasicDBObject("_id", sessionID));
    }
    
    public static byte [] generate32RandomBytes() {
        SecureRandom generator = new SecureRandom();
        byte [] randomBytes = new byte[32];
        generator.nextBytes(randomBytes);
        return randomBytes;
    }
    
}

