package com.appdirect.sdk.security.exception;

import java.util.List;

public class RoleMappingException extends RuntimeException {
    public RoleMappingException(List<String> role) {
        super(String.format("Invalid roles: %s. Roles should not include spaces", role));
    }
}
