package com.mp.showandtell.exception;

public class JsonParseOrProcessingException extends RuntimeException {
    public JsonParseOrProcessingException(String message) {
        super("Exception : " + message);
    }
}
