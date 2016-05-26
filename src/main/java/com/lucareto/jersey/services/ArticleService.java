package com.lucareto.jersey.services;

import java.util.Date;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lucareto.jersey.clients.model.Article;
import com.lucareto.jersey.util.Utils;

import static com.lucareto.jersey.util.Utils.buildJson;

@Path("/article")
public class ArticleService {

    private Gson gson = new GsonBuilder().setPrettyPrinting()
            .serializeNulls().setDateFormat("yyyy-MM-dd'T'HH:mm:ssz").create();
    
    @POST
    @Path("/create")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createUser(String article) {
        Article createdArticle = gson.fromJson(article, Article.class);
        createdArticle.setDate(new Date());
        createdArticle.setId(Utils.generateUrn(Article.NID));
        //TODO: Use mongodb 
        return buildJson(gson.toJson(createdArticle));
    }
    
    
}
