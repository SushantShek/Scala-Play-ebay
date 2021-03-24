package models

case class SampleCase(name: String, address: String)
/*

import akka.http.scaladsl.model.headers.CacheDirectives.public
import play.libs.Files.TemporaryFile
import play.mvc.Http.MultipartFormData.FilePart

public class FormService {


    private FilePart<TemporaryFile> myFile;

  /*  public void setMyFile(final FilePart<TemporaryFile> myFile) {
      this.myFile = myFile;
    }

    public FilePart<TemporaryFile> getMyFile() {
      return this.myFile;
    }*/
//
//  def index = Action {
//    val url = "http://symlink.dk/code/php/submit/"
//
//    val asyncHttpClient:AsyncHttpClient = WS.client.underlying
//    val postBuilder = asyncHttpClient.preparePost("http://symlink.dk/code/php/submit/")
//    val builder = postBuilder.addBodyPart(new StringPart("myField", "abc", "UTF-8"))
//      .addBodyPart(new StringPart("myField1", "abc1", "UTF-8"))
//      .addBodyPart(new StringPart("myField2", "abc2", "UTF-8"))
//      .addBodyPart(new FilePart("myFile", new File("app/controllers/Application.scala")))
//    val response = asyncHttpClient.executeRequest(builder.build()).get();
//    Ok(response.getResponseBody)
//  }

}
*/
