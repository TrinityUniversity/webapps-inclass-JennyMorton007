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
      val username = args("userName").head
      val password = args("passWord").head
      if(TaskListInMemoryModel.validateUser(username,password)){
        Redirect(routes.TaskList1.taskList1)
      } else Redirect(routes.TaskList1.logIn)
    }.getOrElse(Redirect(routes.TaskList1.logIn)) //could use "login1", but what if someone changes the route?
  }//use reverse routing to avoid this problem; if the name has been changed, there will be a compiler error


  def taskList1 = Action {
    val tasks=List("thing1","thing2","thing3")
    Ok(views.html.taskList1(tasks))
  }
}
