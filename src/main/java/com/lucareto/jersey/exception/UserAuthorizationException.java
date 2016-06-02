package com.lucareto.jersey.exception;

import java.util.Map;

public class UserAuthorizationException extends RuntimeException {

    private static final long serialVersionUID = 4456199814696749966L;

    
    private Map<String, Object> reasons;
    
    public UserAuthorizationException(Map<String, Object> reasons) {
        super();
        this.reasons = reasons;
    }
    
    public Map<String, Object> getReasons() {
        return this.reasons;
    }

}
