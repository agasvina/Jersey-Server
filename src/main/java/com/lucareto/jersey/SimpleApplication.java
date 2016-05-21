package com.lucareto.jersey;

import org.glassfish.jersey.server.ResourceConfig;

import com.lucareto.jersey.provider.AuthenticationFilter;
import com.lucareto.jersey.provider.GsonMessageBodyHandler;

public class SimpleApplication extends ResourceConfig {

    public SimpleApplication() {
        packages("com.lucareto.jersey");
        register(org.glassfish.jersey.filter.LoggingFilter.class);
        register(GsonMessageBodyHandler.class);
        register(AuthenticationFilter.class);
    }
}
