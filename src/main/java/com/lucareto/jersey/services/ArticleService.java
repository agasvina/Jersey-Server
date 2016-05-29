package com.lucareto.jersey.services;

import static com.lucareto.jersey.provider.AuthenticationFilter.AUTHENTICATION_SCHEME;
import static com.lucareto.jersey.provider.AuthenticationFilter.AUTHORIZATION_PROPERTY;
import static com.lucareto.jersey.util.Utils.buildJson;
import static com.lucareto.jersey.util.Utils.gson;

import java.util.Objects;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.lucareto.jersey.clients.db.MongoClientHolder;
import com.lucareto.jersey.clients.db.collections.ArticleDAO;
import com.lucareto.jersey.clients.db.collections.SessionDAO;
import com.lucareto.jersey.clients.model.Article;

@Path("/article")
public class ArticleService {

    private ArticleDAO articleDAO = MongoClientHolder.getArticleDAO();
    private SessionDAO sessionDAO = MongoClientHolder.getSessionDAO();

    
    @POST
    @RolesAllowed("BLOGGER")
    @Path("/create")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createUser(String article, @HeaderParam(AUTHORIZATION_PROPERTY) String auth) {
        Article createdArticle = gson.fromJson(article, Article.class);
        
        createdArticle.setAuthorId(sessionDAO.findUserNameBySessionId(getToken(auth)));
        String articleId = articleDAO.createArticle(createdArticle);
        
        if(Objects.nonNull(articleId))
            return buildJson(createdArticle);
        return Response.status(Response.Status.BAD_REQUEST).build();
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    //TODO: ADD PAGINATION 
    public Response getArticles(String article, @QueryParam("limit") Integer limit) {
        return buildJson(articleDAO.findByDateDescending(limit));
    }
    
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getArticle(@PathParam("id") String id) {
        return buildJson(articleDAO.findById(id));
    }
    
    public String getToken(String authHeader) {
        return authHeader.replaceFirst(AUTHENTICATION_SCHEME + " ", "");
    }
}
