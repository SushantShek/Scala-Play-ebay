package com.mp.showandtell.controller

import java.io.IOException
import java.util

import com.mp.showandtell.domain.CreditInput
import com.mp.showandtell.exception.FileProcessingException
import com.mp.showandtell.service.FileService
import com.typesafe.scalalogging.Logger
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.{HttpStatus, ResponseEntity}
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.{PostMapping, RequestMapping, RequestPart, ResponseBody}
import org.springframework.web.multipart.MultipartFile

@Controller
@RequestMapping(path = Array("/api"))
class FileReaderController @Autowired()(fileService: FileService) {

  val logger = Logger(classOf[FileReaderController])

  var listInputs: util.ArrayList[CreditInput] = _

  @PostMapping(path = Array("/upload"))
  @ResponseBody
  def loadInventory(@RequestPart("file") file: MultipartFile): String = {
    if (null == file.getOriginalFilename) {
      throw new FileProcessingException("File not found")
    }
    try {
      return fileService.loadFile(file)
    }
    catch {
      case e: IOException => {
        logger.error("Exception in loadInventory " + e)
        throw new FileProcessingException(
          "Some exception while saving the data")
      }

    }
  }

}

