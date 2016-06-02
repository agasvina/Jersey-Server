package com.lucareto.jersey;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;

public class SimpleApplication extends ResourceConfig {

    public SimpleApplication(){
        
        JacksonJsonProvider json = new JacksonJsonProvider()
                .configure(SerializationFeature.WRITE_DATE_KEYS_AS_TIMESTAMPS, false)
                .configure(SerializationFeature.INDENT_OUTPUT, true);
        
        packages("com.lucareto.jersey");
        register(json);
        property(ServerProperties.BV_SEND_ERROR_IN_RESPONSE, true);
    }
    
    
    
}
