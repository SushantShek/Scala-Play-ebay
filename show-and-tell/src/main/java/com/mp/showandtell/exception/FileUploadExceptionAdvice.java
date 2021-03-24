package com.mp.showandtell.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class FileUploadExceptionAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<String> handleMaxSizeException(MaxUploadSizeExceededException exc) {
        return new ResponseEntity<>("Crossed Max File size threshold to upload", HttpStatus.EXPECTATION_FAILED);
    }

    @ExceptionHandler(FileProcessingException.class)
    public ResponseEntity<String> handleMaxSizeException(FileProcessingException exc) {
        return new ResponseEntity<>(exc.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(JsonParseOrProcessingException.class)
    public ResponseEntity<String> handleMaxSizeException(JsonParseOrProcessingException exc) {
        return new ResponseEntity<>(exc.getMessage(), HttpStatus.EXPECTATION_FAILED);
    }
}