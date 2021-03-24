package controllers

import java.io.{BufferedReader, File, FileReader}
import java.nio.charset.StandardCharsets
import java.nio.file.{Files, Path}
import java.util

import akka.stream.IOResult
import akka.stream.scaladsl._
import akka.util.ByteString
import javax.inject._
import models.{ CreditUser, SampleCase}
import play.api._
import play.api.data.Form
import play.api.data.Forms._
import play.api.libs.json.{JsObject, JsString, JsValue}
import play.api.libs.streams._
import play.api.mvc.MultipartFormData.FilePart
import play.api.mvc._
import play.core.parsers.Multipart.FileInfo

import scala.concurrent.{ExecutionContext, Future}


case class FormData(name: String)

/**
 * This controller handles a file upload.
 */
@Singleton
class HomeController @Inject()(cc: MessagesControllerComponents)
                              (implicit executionContext: ExecutionContext)
  extends MessagesAbstractController(cc) {

  private val logger = Logger(this.getClass)

  val form = Form(
    mapping(
      "name" -> text
    )(FormData.apply)(FormData.unapply)
  )

  /**
   * Renders a start page.
   */
  def index = Action { implicit request =>
    Ok(views.html.index(form))
  }

  type FilePartHandler[A] = FileInfo => Accumulator[ByteString, FilePart[A]]

  /**
   * Uses a custom FilePartHandler to return a type of "File" rather than
   * using Play's TemporaryFile class.  Deletion must happen explicitly on
   * completion, rather than TemporaryFile (which uses finalization to
   * delete temporary files).
   *
   * @return
   */
  private def handleFilePartAsFile: FilePartHandler[File] = {
    case FileInfo(partName, filename, contentType, _) =>
      val path: Path = Files.createTempFile("multipartBody", "tempFile")
      val fileSink: Sink[ByteString, Future[IOResult]] = FileIO.toPath(path)
      val accumulator: Accumulator[ByteString, IOResult] = Accumulator(fileSink)
      accumulator.map {
        case IOResult(count, status) =>
          logger.info(s"count = $count, status = $status")
          FilePart(partName, filename, contentType, path.toFile)
      }
  }

  /**
   * A generic operation on the temporary file that deletes the temp file after completion.
   */
  private def operateOnTempFile(file: File) = {
    var inputList = new util.ArrayList[CreditUser]()
    val size = Files.size(file.toPath)
    val user = new util.ArrayList[JsValue]
    val br = new BufferedReader(new FileReader(file.getAbsolutePath, StandardCharsets.ISO_8859_1))
    while (br.readLine() != null) {
      inputList = new util.ArrayList[CreditUser]()
      val line1 = br.readLine();
      val splitArr = line1.split(",")
//      user = new CreditUser(splitArr)
      user.add(writes(splitArr))
      val credObj = CreditUser(splitArr.apply(0)+splitArr.apply(1),splitArr.apply(2),splitArr.apply(3),splitArr.apply(4),splitArr.apply(5),splitArr.apply(6))
      inputList.add(credObj)

    }
    logger.info(s"size = ${size}")
    logger.info(s"size = ${inputList}")
    Files.deleteIfExists(file.toPath)

    user
//    size
  }

  def writes(usr: Array[String]): JsValue = {
    //  tweetSeq == Seq[(String, play.api.libs.json.JsString)]
    val usrSeq = Seq(
      "name" -> JsString(usr.apply(0) + usr.apply(1)),
      "address" -> JsString(usr.apply(2)),
      "postCode" -> JsString(usr.apply(3)),
      "phoneNumber" -> JsString(usr.apply(4)),
      "creditLimit" -> JsString(usr.apply(5)),
      "birthDate" -> JsString(usr.apply(6))
    )
    JsObject(usrSeq)
  }

  /**
   * Uploads a multipart file as a POST request.
   *
   * @return
   */
  def upload = Action(parse.multipartFormData(handleFilePartAsFile)) { implicit request =>
    val fileOption = request.body.file("name").map {

      case FilePart(key, filename, contentType, file, fileSize, dispositionType) =>
        logger.info(s"key = $key, filename = $filename, contentType = $contentType, file = $file, fileSize = $fileSize, dispositionType = $dispositionType")
        val data = operateOnTempFile(file)
        data
    }

    Ok(s"file = ${fileOption.getOrElse("no file")}")
  }

}


/*import java.nio.file.Paths

import io.jsonwebtoken.lang.Strings.replace
import javax.inject._
import play.api.mvc._

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class HomeController @Inject()(val controllerComponents: ControllerComponents) extends BaseController {

  /**
   * Create an Action to render an HTML page.
   *
   * The configuration in the `routes` file means that this method
   * will be called when the application receives a `GET` request with
   * a path of `/`.
   */
  def index() = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.index())
  }

  def view() = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.view())
  }

  /*def upload() = Action { request =>
    //     var input = request.asMultipartFormData.get
    //    val filename = request.body.asText.toList
    request.body.asMultipartFormData
    request.body.file("Workbook2").map { picture =>
      val filename = Paths.get(picture.filename).getFileName
      picture.ref.moveTo(Paths.get(s"/$filename"), replace = true)

      Ok("Test Post method");

    }
  }*/

  def upload = Action(parse.multipartFormData) { request =>
    request.body
      .file("filedata")
      .map { picture =>
        // only get the last part of the filename
        // otherwise someone can send a path like ../../home/foo/bar.txt to write to other files on the system
        val filename = Paths.get(picture.filename).getFileName
        println(filename)

        picture.ref.moveTo(Paths.get(s"/tmp/$filename"), replace = true)

      }
      /*.getOrElse {
        Redirect(index())
        //      }
        //        Redirect(index()).flashing("error" -> "Missing file")
      }*/
    Ok("File uploaded")
  }
}*/
