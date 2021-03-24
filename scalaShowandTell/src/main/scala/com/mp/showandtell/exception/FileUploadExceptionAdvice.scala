package com.mp.showandtell.exception

import org.springframework.http.{HttpStatus, ResponseEntity}
import org.springframework.web.bind.annotation.{ControllerAdvice, ExceptionHandler}
import org.springframework.web.multipart.MaxUploadSizeExceededException
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@ControllerAdvice
class FileUploadExceptionAdvice extends ResponseEntityExceptionHandler {

 /* @ExceptionHandler(class[MaxUploadSizeExceededException])
  def handleMaxSizeException(exc: MaxUploadSizeExceededException): ResponseEntity[String] =
    new ResponseEntity("Crossed Max File size threshold to upload",
      HttpStatus.EXPECTATION_FAILED)

  @ExceptionHandler(classOf[FileProcessingException])
  def handleMaxSizeException(
                              exc: FileProcessingException): ResponseEntity[String] =
    new ResponseEntity(exc.getMessage, HttpStatus.BAD_REQUEST)

  @ExceptionHandler(classOf[JsonParseOrProcessingException])
  def handleMaxSizeException(
                              exc: JsonParseOrProcessingException): ResponseEntity[String] =
    new ResponseEntity(exc.getMessage, HttpStatus.EXPECTATION_FAILED)*/

}
