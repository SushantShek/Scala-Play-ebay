package com.mp.showandtell.exception;

public class FileProcessingException extends RuntimeException {
    public FileProcessingException(String message) {
        super("Exception : " + message);
    }
}
