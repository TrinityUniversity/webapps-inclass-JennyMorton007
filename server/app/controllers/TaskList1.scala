package controllers

import javax.inject._

import shared.SharedMessages
import play.api.mvc._

@Singleton 
class TaskList1 @Inject()(cc: ControllerComponents) extends AbstractController(cc){
  def logIn = Action {
    Ok(views.html.login1())
  } //first item occurs on the page first (maybe?)
  
  def validateLoginGet(username:String, password:String) = Action {
    Ok(s"$username logged in with $password.")
  }

  def validateLoginPost = Action { request =>
    val postVals = request.body.asFormUrlEncoded    //the "argument" names in login1.scala.html are the keys in this map
    postVals.map {args =>                           //the values are the inputs to the form
      Ok(args("userName").head+" logged in with "+args("passWord").head+".")
    }.getOrElse(Ok("Oops"))
    
  }


  def taskList1 = Action {
    val tasks=List("thing1","thing2","thing3")
    Ok(views.html.taskList1(tasks))
  }
}
