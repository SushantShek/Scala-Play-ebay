package com.mp.showandtell.exception

class FileProcessingException(message: String)
  extends RuntimeException("Exception : " + message)