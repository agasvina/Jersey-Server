package com.lucareto.jersey.provider;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import javax.annotation.security.DenyAll;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import com.lucareto.jersey.clients.db.MongoClientHolder;
import com.lucareto.jersey.clients.db.collections.SessionDAO;

public class AuthenticationFilter implements ContainerRequestFilter {
    
    @Context
    private ResourceInfo resourceInfo;
    private static final String AUTHORIZATION_PROPERTY = "Authorization";
    private static final String AUTHENTICATION_SCHEME  = "Bearer";
    
    private static final Response ACCESS_DENIED = Response.status(Response.Status.UNAUTHORIZED).build();
    private static final Response ACCESS_FORBIDDEN = Response.status(Response.Status.FORBIDDEN).build();

    private SessionDAO sessionDAO = MongoClientHolder.getSessionDAO();
    
    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
       Method method = resourceInfo.getResourceMethod();
       if(!method.isAnnotationPresent(PermitAll.class)) {
           if(method.isAnnotationPresent(DenyAll.class)) {
               requestContext.abortWith(ACCESS_FORBIDDEN);
           }
           
           final MultivaluedMap<String, String> headers = requestContext.getHeaders();
           final List<String> authorization = headers.get(AUTHORIZATION_PROPERTY);
           if(method.isAnnotationPresent(RolesAllowed.class)) {
              if(Objects.nonNull(authorization) && !authorization.isEmpty()) {
                  String token = authorization.get(0).replaceFirst(AUTHENTICATION_SCHEME + " ", "");
                  Set<String> roleSet = new HashSet<>(Arrays.asList(method.getAnnotation(RolesAllowed.class).value()));
                  if(!isUserAllowed(token, roleSet)) 
                      requestContext.abortWith(ACCESS_DENIED);
              } else {
                  requestContext.abortWith(ACCESS_FORBIDDEN);
              }
           } 
       }
        
    }
    
    private boolean isUserAllowed(final String token, final Set<String> roleSet) {
        if(Objects.nonNull(sessionDAO.findUserNameBySessionId(token))) {
            return true;
        }
        return false;
    }

}
