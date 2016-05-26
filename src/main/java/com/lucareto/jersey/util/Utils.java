package com.lucareto.jersey.util;

import java.util.UUID;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class Utils {

    public static String generateUrn(String NID) {
        return NID + UUID.randomUUID().toString();
    }
    
    public static Response buildJson(String jsonObject) {
        return Response.ok(jsonObject,MediaType.APPLICATION_JSON).build();
    }
}
