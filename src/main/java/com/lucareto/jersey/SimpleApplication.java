package com.lucareto.jersey;

import org.glassfish.jersey.server.ResourceConfig;

import com.lucareto.jersey.clients.db.MongoClientHolder;
import com.lucareto.jersey.provider.AuthenticationFilter;
import com.lucareto.jersey.provider.CORSFilter;
import com.lucareto.jersey.provider.GsonMessageBodyHandler;

public class SimpleApplication extends ResourceConfig {

    public SimpleApplication() {
        MongoClientHolder.load();
        packages("com.lucareto.jersey");
        register(org.glassfish.jersey.filter.LoggingFilter.class);
        register(GsonMessageBodyHandler.class);
        register(AuthenticationFilter.class);
        register(CORSFilter.class);
    }
}
