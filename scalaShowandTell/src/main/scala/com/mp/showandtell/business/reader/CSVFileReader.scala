package com.mp.showandtell.business.reader

import java.io.{BufferedReader, File, FileReader, IOException}
import java.nio.charset.StandardCharsets
import java.nio.file.Path
import java.util

import com.mp.showandtell.business.ReaderInterface
import com.mp.showandtell.domain.{CreditInput, CreditMain}
import com.typesafe.scalalogging._
import org.springframework.stereotype.Component

import scala.io.Source

class CSVFileReader extends ReaderInterface {
  val logger = Logger(classOf[CSVFileReader])
  val DILIM_CSV: String = ","

  override def readFile(tempFile: Path): util.ArrayList[CreditInput] = {
    val output: util.ArrayList[CreditInput] = processCSVInputFile(tempFile)
    output
  }

  def processCSVInputFile(inputFilePath: Path): util.ArrayList[CreditInput] = {
    val file_Object = new File("abc.txt")
    println(file_Object.getAbsolutePath)
    val inputList = new util.ArrayList[CreditInput]()

    try {
      for (line <- Source.fromFile("Workbook2.csv").getLines) {
        println(line)
      }
    } catch {
      case exception: Exception =>
        println("Exception is Source parsing" + exception)
    }

    try {
      val br = new BufferedReader(new FileReader(inputFilePath.toString, StandardCharsets.ISO_8859_1))
      try { // skip the header of the csv
        var line1: String = null

        while (br.readLine() != null) {
          line1 = br.readLine();
          println("File : " + line1)
          inputList.add(mapToCredit(line1))
        }
      }
      catch {
        case e: IOException =>
          logger.error("File handling exception{}", e)
      } finally if (br != null) br.close()
    } catch {
      case e: IOException => logger.error("File handling exception{}", e)
    }
    inputList
  }

  def mapToCredit(splitV: String): CreditInput = {
    val p: Array[String] = splitV.split(DILIM_CSV)
    val creditObject = CreditMain.main(p)
    creditObject
  }
}


