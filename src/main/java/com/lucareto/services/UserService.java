package com.lucareto.services;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lucareto.db.UserDB;
import com.lucareto.db.model.User;
 
@Path("/user")
public class UserService {
    
    private UserDB userDB = new UserDB();
    private Gson gson = new GsonBuilder().serializeNulls().create();
    
    @GET
    @Path("/retrieve")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUser(@QueryParam("id") String id) {
        return buildJson(userDB.getUser(id));
    }
    
    @GET
    @Path("/retrieve/all")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUsers() {
        return buildJson(userDB.getAllUsers());
    }
    
    @POST
    @Path("/create")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createUser(String userData) {
        User toBeCreated = gson.fromJson(userData, User.class);
        return buildJson(userDB.addUser(toBeCreated));
    }
    
    @DELETE
    @Path("/delete")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteUser(@QueryParam("id") String id) {
        return buildJson(userDB.deleteUser(id));
    }
    
    private Response buildJson(Object object) {
        return Response.ok(gson.toJson(object),MediaType.APPLICATION_JSON).build();
    }
}