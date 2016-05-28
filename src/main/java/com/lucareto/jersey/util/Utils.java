package com.lucareto.jersey.util;

import java.util.Map;
import java.util.Objects;
import java.util.UUID;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class Utils {

    public static String generateUrn(String NID) {
        return NID + UUID.randomUUID().toString();
    }
    
    public static boolean validateSignup(String username, String password, String email,
                                  Map<String, Object> errors) {
        String USER_RE = "^[a-zA-Z0-9_-]{3,20}$";
        String PASS_RE = "^.{3,20}$";
        String EMAIL_RE = "^[\\S]+@[\\S]+\\.[\\S]+$";
        
        if (Objects.nonNull(username) && !username.matches(USER_RE)) {
            errors.put("username_error", "invalid username. Use letter and number only ");
        } else if(Objects.isNull(username)) errors.put("username_error", "Please specify a username");
        
        if (Objects.nonNull(password) && !password.matches(PASS_RE)) {
            errors.put("password_error", "invalid password.");
        }  else if(Objects.isNull(password)) errors.put("password_error", "Password can't be null");

        if (Objects.nonNull(email) && !email.isEmpty()) {
            if (!email.matches(EMAIL_RE)) {
                errors.put("email_error", "Invalid Email Address");
            }
        } 
        return errors.isEmpty();
    }
    
    
    public static Response buildJson(String jsonObject) {
        return Response.ok(jsonObject,MediaType.APPLICATION_JSON).build();
    }
    
}
