package com.centralway.contactbook.security.common;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ErrorCode {
    AUTHENTICATION(10), JWT_TOKEN_EXPIRED(11);
    
    private int errorCode;

    ErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    @JsonValue
    public int getErrorCode() {
        return errorCode;
    }
}
