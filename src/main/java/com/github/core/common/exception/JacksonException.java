package com.github.core.common.exception;

/**
 * @author yayee
 */
public class JacksonException extends RuntimeException {

    public JacksonException(String message, Exception e) {
        super(message, e);
    }
}