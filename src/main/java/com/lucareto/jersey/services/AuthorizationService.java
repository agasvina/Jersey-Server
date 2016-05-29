package com.lucareto.jersey.services;

import static com.lucareto.jersey.util.Utils.buildJson;
import static com.lucareto.jersey.util.Utils.gson;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.lucareto.jersey.clients.db.MongoClientHolder;
import com.lucareto.jersey.clients.db.collections.SessionDAO;
import com.lucareto.jersey.clients.db.collections.UserDAO;
import com.lucareto.jersey.clients.model.User;
import com.lucareto.jersey.util.Utils;

@Path("/user")
public class AuthorizationService {
    
    private UserDAO userDAO = MongoClientHolder.getUserDAO();
    private SessionDAO sessionDAO = MongoClientHolder.getSessionDAO();
    
    @POST
    @Path("/signup")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createUser(String userValue) {
        User user = gson.fromJson(userValue, User.class);
        Map<String, Object> validateResult = new HashMap<>();
        if(Utils.validateSignup(user, validateResult)) {
            if(!userDAO.addUser(user)) {
                validateResult.put("success", false);
                validateResult.put("reason", "Username is already in use");
                return Response.status(Response.Status.UNAUTHORIZED)
                    .entity(validateResult).type(MediaType.APPLICATION_JSON).build();
            } else {
                //TODO: USING JWT TOKEN
                String sessionID = sessionDAO.startSession(user.getUsername());
                validateResult.put("success", true);
                validateResult.put("token", sessionID);
                return buildJson(validateResult);
            }
        } else {
           return Response.status(Response.Status.BAD_REQUEST)
            .entity(validateResult).type(MediaType.APPLICATION_JSON).build();
        }
    }
    
    
    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response signIn(String userValue) {
        User user = gson.fromJson(userValue, User.class);
        Map<String, Object> response = new HashMap<>();
        if(userDAO.validateLogin(user)) {
            response.put("success", true);
            response.put("token", sessionDAO.startSession(user.getUsername()));
            return buildJson(response);
        }
        response.put("reason", "Invalid username and password");
        return Response.status(Response.Status.NOT_FOUND)
                .entity(response).type(MediaType.APPLICATION_JSON).build();
    }
}
