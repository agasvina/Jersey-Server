package com.lucareto.jersey.filters;
import java.io.IOException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.ext.Provider;

@Provider
@PreMatching
public class CORSFilter implements ContainerResponseFilter {

    @Override
    public void filter(final ContainerRequestContext requestContext, final ContainerResponseContext responseContext)
            throws IOException {
        responseContext.getHeaders().add("Access-Control-Allow-Origin", requestContext.getHeaderString("origin"));
        responseContext.getHeaders().add("Access-Control-Expose-Headers",
                "Origin, X-Requested-With, Content-Type, Cookie, Accept, jwt, Authorization");
        responseContext.getHeaders().add("Access-Control-Max-Age", "6000");
        responseContext.getHeaders().add("Access-Control-Allow-Credentials", "true");
        responseContext.getHeaders().add("Access-Control-Allow-Methods", "['POST', 'GET', 'DELETE', 'OPTIONS']");
        responseContext.getHeaders().add("Access-Control-Allow-Headers",
                "Origin, X-Requested-With, Content-Type, Cookie, Accept, jwt, Authorization");
    }
}
