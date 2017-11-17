package controllers

import javax.inject._
import akka.actor.ActorSystem
import org.apache.jena.rdf.model.ModelFactory
import org.apache.jena.riot.Lang
import play.Logger
import play.api.libs.json._
import play.api.mvc._
import services.minte.merge.MergeSuccess
import utils.dataintegration.{MergeOperator, RDFUtil}
import scala.concurrent.{ExecutionContext}

/**
 * This controller creates an `Action` that demonstrates how to write
 * simple asynchronous code in a controller. It uses a timer to
 * asynchronously delay sending a response for 1 second.
 *
 * @param cc standard controller components
 * @param actorSystem We need the `ActorSystem`'s `Scheduler` to
 * run code after a delay.
 * @param exec We need an `ExecutionContext` to execute our
 * asynchronous code.  When rendering content, you should use Play's
 * default execution context, which is dependency injected.  If you are
 * using blocking operations, such as database or network access, then you should
 * use a different custom execution context that has a thread pool configured for
 * a blocking API.
 */
@Singleton
class FusionController @Inject()(cc: ControllerComponents, actorSystem: ActorSystem)(implicit exec: ExecutionContext) extends AbstractController(cc) {

  implicit val mergeTaskReader = Json.reads[MergeTask]
  implicit val mergeTaskWrites = Json.writes[MergeTask]

  implicit val initTaskReader = Json.reads[InitTask]
  implicit val initTaskWrites = Json.writes[InitTask]
  
  /**
   * Creates an Action that returns a plain text message after a delay
   * of 1 second.
   *
   * The configuration in the `routes` file means that this method
   * will be called when the application receives a `GET` request with
   * a path of `/message`.
   */
  def merge (policy: String) = Action { request =>
    Logger.info(s"Policy Sent: $policy")

    val json = request.body.asJson
    json match {
      case Some(value) =>
        val list = (value \ "tasks").as[List[MergeTask]]
        val responseModel = ModelFactory.createDefaultModel
        list.map{ item =>
          MergeOperator.merge(item.uri1.get,item.uri2.get,"union") match {
            case s: MergeSuccess => responseModel.add(s.model)
            case _ => Logger.info(s"Failed to merge: ${item.uri1} and ${item.uri2}")
          }
        }
        Ok(RDFUtil.modelToTripleString(responseModel,Lang.NTRIPLES))
      case None => BadRequest("No Json Sent!!!")
    }
  }

  def set () = Action { request =>
    Logger.info(s"Set new locations")
    // LOADING MODELSSSSSS
    val json = request.body.asJson

    json match {
      case Some(value) =>
        val elem = value.as[InitTask]
        MergeOperator.initialize(elem.location1,elem.location2) 
      case None => BadRequest("No json")
    }

    //MergeOperator.initialize(location1,location2)
    //Logger.info("asdadsasdasdasd")

    Ok("Setting location")
  }

}

case class MergeTask (uri1: Option[String], uri2: Option[String])

case class InitTask (location1: String, location2: String)