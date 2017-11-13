package services.minte.merge

import org.apache.jena.rdf.model.Model

sealed trait MergeResponse
case class MergeError(errorMessage: String) extends MergeResponse
case class MergeSuccess(model: Model) extends MergeResponse
case class NothingToMerge(message: String) extends MergeResponse

trait FusionTrait {

  def initialize(model_1 :String, model_2 : String)

  def merge(uri_1: String, uri_2: String, fusionPolicy: String) : MergeResponse

  def isInitialized() : Boolean

}