package com.mp.showandtell.service

import java.io.IOException
import java.nio.file.{Files, Path}

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.scala.{DefaultScalaModule, ScalaObjectMapper}
import com.mp.showandtell.business.ReaderInterface
import com.mp.showandtell.business.reader.{CSVFileReader, PRNFileReader}
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

@Service
class FileService {

  val mapper = new ObjectMapper() with ScalaObjectMapper
  mapper.registerModule(DefaultScalaModule)

  /**
   * Simple method which decides based on input
   * file format which class will read the file
   *
   * @param file multipart file
   * @return List of CreditInput
   * @throws IOException
   */
  def loadFile(file: MultipartFile): String = {
    val fileName: String = file.getOriginalFilename
    val fileExtn: String = fileName.split("\\.")(1)
    val tempFile: Path = Files.createTempFile(fileName, fileExtn)
    // write a line
    Files.write(tempFile, file.getBytes)
    try {
      val fileReader: ReaderInterface = getClassAsString(fileExtn)
      val result = fileReader.readFile(tempFile)
      mapper.writeValueAsString(result)
    } catch {
      case ioe: IOException =>
        throw new Exception(
          "File could not be parsed or mapped to object" + ioe)
      case e: Exception =>
        print("Some exception ", e)
        throw new Exception(
          "File could not be parsed or mapped to object" + e)
    } finally Files.delete(tempFile)
  }

  def getClassAsString(x: String): ReaderInterface = x match {
    case "csv" => new CSVFileReader
    case "prn" => new PRNFileReader
    case _ => null
  }
}


