package com.mp.showandtell.business

import java.nio.file.Path
import java.util

import com.mp.showandtell.domain.CreditInput


trait ReaderInterface {

  def readFile(tempFile: Path): util.ArrayList[CreditInput]

}
