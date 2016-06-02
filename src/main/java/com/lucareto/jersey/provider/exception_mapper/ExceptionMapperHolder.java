package com.lucareto.jersey.provider.exception_mapper;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.lucareto.jersey.exception.UserAuthorizationException;

public class ExceptionMapperHolder {
    
    @Provider
    public static class UserAuthorizaton implements ExceptionMapper<UserAuthorizationException> {
        public Response toResponse(UserAuthorizationException ex) {
            return Response.status(401).entity(ex.getReasons()).type(MediaType.APPLICATION_JSON).build();
        }
    }
    
    @Provider
    public static class NotFound implements ExceptionMapper<NotFoundException> {
        public Response toResponse(NotFoundException ex) {
            return Response.status(401).entity(ex.getMessage()).type(MediaType.TEXT_PLAIN).build();
        }
    }
    
    @Provider
    public static class RunTime implements ExceptionMapper<RuntimeException> {
        public Response toResponse(RuntimeException ex) {
            return Response.status(500).entity(ex.getMessage()).type(MediaType.TEXT_PLAIN).build();
        }
    }
    
}

