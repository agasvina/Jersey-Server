package com.lucareto.jersey.services;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.OPTIONS;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.lucareto.jersey.clients.db.MongoClientHolder;
import com.lucareto.jersey.clients.db.collections.SessionDAO;
import com.lucareto.jersey.clients.db.collections.UserDAO;
import com.lucareto.jersey.clients.model.User;
import com.lucareto.jersey.exception.UserAuthorizationException;
import com.lucareto.jersey.util.Utils;

@Path("/user")
public class AuthorizationService {
    
    private UserDAO userDAO = MongoClientHolder.getUserDAO();
    private SessionDAO sessionDAO = MongoClientHolder.getSessionDAO();
    
    @Context ContainerRequestContext request;
    
    @OPTIONS
    @Path("/*")
    public Response freeFlight() {
        return Response.ok().build();
    }
    
    @POST
    @Path("/signup")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, Object> createUser(User user) throws UserAuthorizationException {
        Map<String, Object> validateResult = new HashMap<>();
        if(Utils.validateSignup(user, validateResult)) {
            if(!userDAO.addUser(user)) {
                validateResult.put("reason", "Username is already in use");
                throw new UserAuthorizationException(validateResult);
            } else {
                //TODO: USING JWT TOKEN
                String sessionID = sessionDAO.startSession(user.getUsername());
                validateResult.put("token", sessionID);
                return null;
            }
        } else throw new UserAuthorizationException(validateResult);

    }

    @POST
    @Path("/signin")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, Object> signIn(User user) throws NotFoundException{
        Map<String, Object> response = new HashMap<>();
        if(userDAO.validateLogin(user)) {
            response.put("token", sessionDAO.startSession(user.getUsername()));
            return response;
        }
        throw new NotFoundException("Invalid user");
    }
}