package com.lucareto.jersey;

import java.net.UnknownHostException;

import org.glassfish.jersey.server.ResourceConfig;

import com.lucareto.jersey.clients.db.MongoClientHolder;
import com.lucareto.jersey.provider.AuthenticationFilter;
import com.lucareto.jersey.provider.CORSFilter;
import com.lucareto.jersey.provider.GsonMessageBodyHandler;

public class SimpleApplication extends ResourceConfig {

    public SimpleApplication() throws UnknownHostException {
        MongoClientHolder.load();
        packages("com.lucareto.jersey");
        register(org.glassfish.jersey.filter.LoggingFilter.class);
        register(GsonMessageBodyHandler.class);
        register(AuthenticationFilter.class);
        register(CORSFilter.class);
    }
}
