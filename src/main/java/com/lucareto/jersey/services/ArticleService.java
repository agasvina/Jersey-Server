package com.lucareto.jersey.services;


import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.OPTIONS;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.server.ManagedAsync;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.lucareto.jersey.clients.db.collections.ArticleDAO;
import com.lucareto.jersey.clients.model.Article;
import com.mongodb.DBObject;

@Path("/article")
public class ArticleService {

    @Context ArticleDAO articleDAO;
    
    @POST
    @RolesAllowed("BLOGGER")
    @Path("/create")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @ManagedAsync 
    public void createUser(@Valid @NotNull Article article, @HeaderParam("username") String username, @Suspended final AsyncResponse response) {
        article.setAuthorId(username);
        ListenableFuture<DBObject> future = articleDAO.createArticleAsync(article);
        Futures.addCallback(future, new FutureCallback<DBObject>() {

            @Override
            public void onFailure(Throwable thrown) {
                response.resume(thrown);
                
            }

            @Override
            public void onSuccess(DBObject article) {
                response.resume(article);
                
            }
            
        });
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @ManagedAsync
    //TODO: ADD PAGINATION 
    public void getArticles(String article, @QueryParam("limit") Integer limit, @Suspended final AsyncResponse response) {
        ListenableFuture<List<DBObject>> future =  (articleDAO.findByDateDescendingAsync(limit));
        Futures.addCallback(future, new FutureCallback<List<DBObject>>() {

            @Override
            public void onFailure(Throwable thrown) {
                response.resume(thrown);
                
            }

            @Override
            public void onSuccess(List<DBObject> article) {
                response.resume(article);
                
            }
            
        });
    }
    
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @ManagedAsync
    public void getArticle(@PathParam("id") String id, @Suspended final AsyncResponse response) {
        ListenableFuture<DBObject> future = articleDAO.findByIdAsync(id);
        Futures.addCallback(future, new FutureCallback<DBObject>() {

            @Override
            public void onFailure(Throwable thrown) {
                response.resume(thrown);
                
            }

            @Override
            public void onSuccess(DBObject article) {
                response.resume(article);
                
            }
            
        });
    }
    
    @OPTIONS
    @Path("/create")
    public Response freeFlight() {
        return Response.ok().build();
    }
}
