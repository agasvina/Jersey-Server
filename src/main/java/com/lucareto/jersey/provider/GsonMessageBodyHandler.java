package com.lucareto.jersey.provider;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Objects;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.MessageBodyWriter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GsonMessageBodyHandler implements MessageBodyWriter<Object>, MessageBodyReader<Object>{
    
    private static final String UTF_8 = "UTF-8";
    private static final Logger logger = LoggerFactory.getLogger(GsonMessageBodyHandler.class);
    private Gson gson;
    
    private Gson getGson() {
        if(Objects.isNull(gson)) {
            gson = new GsonBuilder().disableHtmlEscaping()
                       .setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)
                       .setPrettyPrinting()
                       .serializeNulls()
                       .create();
        }
        return gson;
    }
    
    @Override
    public boolean isReadable(Class<?> type, Type genericType, Annotation [] annotations, MediaType mediatType) {
        return true;
    }
    
    @Override
    public Object readFrom(Class<Object> type, Type genericType, Annotation[] annotations, MediaType mediaType,
            MultivaluedMap<String, String> httpHeaders, InputStream entityStream) {
        InputStreamReader streamReader = null;
        try {
            streamReader = new InputStreamReader(entityStream, UTF_8);
        } catch (UnsupportedEncodingException e) {
            logger.warn("Unsupported Encoding", e);
        }
        
        try {
            Type jsonType;
            if(type.equals(genericType)) 
                jsonType = type;
            else 
                jsonType = genericType;
            return getGson().fromJson(streamReader, jsonType);
        } finally {
            try {
                streamReader.close();
            } catch (IOException e) {
                logger.warn("Unable to close the stream reader", e);
            }
        }
    }

    @Override
    public boolean isWriteable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        return true;
    }

    @Override
    public long getSize(Object t, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        return -1;
    }

    @Override
    public void writeTo(Object object, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType,
            MultivaluedMap<String, Object> httpHeaders, OutputStream entityStream)
                    throws IOException, WebApplicationException {
        OutputStreamWriter writer = new OutputStreamWriter(entityStream, UTF_8);
        try {
            Type jsonType;
            if (type.equals(genericType)) {
                jsonType = type;
            } else {
                jsonType = genericType;
            }
            getGson().toJson(object, jsonType, writer);
        } finally {
            writer.close();
        }
        
    }

}
