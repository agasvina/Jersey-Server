package com.lucareto.jersey.services;

import static com.lucareto.jersey.util.Utils.buildJson;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lucareto.jersey.clients.db.MongoDBClient;
import com.lucareto.jersey.clients.db.SessionDAO;
import com.lucareto.jersey.clients.db.UserDAO;
import com.lucareto.jersey.db.model.User;
import com.lucareto.jersey.util.Utils;

@Path("/user")
public class AuthorizationService {
    
    //TODO: put in utils (duplication detected)
    private Gson gson = new GsonBuilder().setPrettyPrinting()
            .serializeNulls().setDateFormat("yyyy-MM-dd'T'HH:mm:ssz").create();
    private MongoDBClient mongoDBClient = new MongoDBClient();
    
    private UserDAO userDAO = mongoDBClient.getUserDAO();
    private SessionDAO sessionDAO = mongoDBClient.getSessionDAO();
    
    @POST
    @Path("/signup")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createUser(String userValue) {
        User user = gson.fromJson(userValue, User.class);
        Map<String, Object> validateResult = new HashMap<>();
        if(Utils.validateSignup(user.getUsername(), user.getPassword(), user.getEmail(), validateResult)) {
            if(!userDAO.addUser(user.getUsername(), user.getPassword(), user.getEmail())) {
                validateResult.put("success", false);
                validateResult.put("reason", "Username is already in use");
                return Response.status(Response.Status.UNAUTHORIZED)
                    .entity(validateResult).type(MediaType.APPLICATION_JSON).build();
            } else {
                //TODO: implement better tokens...
                String sessionID = sessionDAO.startSession(user.getUsername());
                validateResult.put("success", true);
                validateResult.put("token", sessionID);
                return buildJson(gson.toJson(validateResult));
            }
        } else {
           return Response.status(Response.Status.BAD_REQUEST)
            .entity(validateResult).type(MediaType.APPLICATION_JSON).build();
        }
    }
    
    
    

}
