package com.mp.showandtell.business.reader

import java.io.{BufferedReader, FileReader, IOException}
import java.nio.charset.StandardCharsets
import java.nio.file.Path
import java.util

import com.mp.showandtell.business.ReaderInterface
import com.mp.showandtell.domain.{CreditInput, CreditMain}
import com.typesafe.scalalogging.Logger
import org.springframework.stereotype.Component

import scala.jdk.CollectionConverters.CollectionHasAsScala
import scala.util.control.Breaks.{break, breakable}

@Component
class PRNFileReader extends ReaderInterface {
  val logger = Logger(classOf[PRNFileReader])
  val STRING_DELIM: String = ";"

  def processPNRInputFile(tempFile: Path): util.ArrayList[CreditInput] = {
    val prnList = new util.ArrayList[CreditInput]()
    try {
      val br = new BufferedReader(new FileReader(tempFile.toString, StandardCharsets.UTF_8))
      try { // skip the header of the csv
        var line1: String = null

        while (br.readLine() != null) {
          line1 = br.readLine();
          breakable {
            if (line1.trim() == ("")) {
              break // break out of the 'breakable', continue the outside loop
            }
          }
          val sb = new StringBuilder()
          val parts: Array[String] = splitStringToChunks(line1, 16, 22, 9, 14, 13, 8)
          for (str <- parts) {
            if (str.toString().equals("")) {
              sb.append(str)
            } else {
              sb.append("; " + str)
            }
          }
          prnList.add(mapToCredit(sb.toString()))
        }
      }
      catch {
        case e: IOException =>
          logger.error("File handling exception{}", e)
      } finally if (br != null) br.close()
    } catch {
      case e: IOException => logger.error("File handling exception{}", e)

    }
    prnList
  }

  override def readFile(tempFile: Path): util.ArrayList[CreditInput] = {
    processPNRInputFile(tempFile)
  }

  def splitStringToChunks(inputString: String,
                          chunkSizes: Int*): Array[String] = {
    val list = new util.ArrayList[String]
    var chunkStart: Int = 0
    var chunkEnd: Int = 0
    for (length <- chunkSizes) {
      chunkStart = chunkEnd
      chunkEnd = chunkStart + length
      val dataChunk: String = inputString.substring(chunkStart, chunkEnd)
      list.add(dataChunk.trim())
    }
    list.asScala.toArray[String]
  }

  def mapToCredit(splitV: String): CreditInput = {
    val p = splitV.split(STRING_DELIM).map(_.trim)
    CreditMain.main(p)
  }

}

