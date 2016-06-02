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
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.lucareto.jersey.clients.db.MongoClientHolder;
import com.lucareto.jersey.clients.db.collections.ArticleDAO;
import com.lucareto.jersey.clients.model.Article;
import com.mongodb.DBObject;

@Path("/article")
public class ArticleService {

    private ArticleDAO articleDAO = MongoClientHolder.getArticleDAO();
    
    @POST
    @RolesAllowed("BLOGGER")
    @Path("/create")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public DBObject createUser(@Valid @NotNull Article article, @HeaderParam("username") String username) {
        article.setAuthorId(username);
        return articleDAO.createArticle(article);
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    //TODO: ADD PAGINATION 
    public  List<DBObject> getArticles(String article, @QueryParam("limit") Integer limit) {
        return (articleDAO.findByDateDescending(limit));
    }
    
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public DBObject getArticle(@PathParam("id") String id) {
        return articleDAO.findById(id);
    }
    
    @OPTIONS
    @Path("/create")
    public Response freeFlight() {
        return Response.ok().build();
    }
}
