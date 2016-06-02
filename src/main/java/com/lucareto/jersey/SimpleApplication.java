package com.lucareto.jersey;

import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import com.lucareto.jersey.clients.db.MongoClientHolder;
import com.lucareto.jersey.clients.db.collections.ArticleDAO;
import com.lucareto.jersey.clients.db.collections.SessionDAO;
import com.lucareto.jersey.clients.db.collections.UserDAO;

public class SimpleApplication extends ResourceConfig {

    public SimpleApplication(){
        
        JacksonJsonProvider json = new JacksonJsonProvider()
                .configure(SerializationFeature.WRITE_DATE_KEYS_AS_TIMESTAMPS, false)
                .configure(SerializationFeature.INDENT_OUTPUT, true);
        
        register(new AbstractBinder() {
            @Override
            protected void configure() {
                bind(MongoClientHolder.getArticleDAO()).to(ArticleDAO.class);
                bind(MongoClientHolder.getSessionDAO()).to(SessionDAO.class);
                bind(MongoClientHolder.getUserDAO()).to(UserDAO.class);
            }
        });
        
        packages("com.lucareto.jersey");
        register(json);
        property(ServerProperties.BV_SEND_ERROR_IN_RESPONSE, true);
    }
    
    
    
}
