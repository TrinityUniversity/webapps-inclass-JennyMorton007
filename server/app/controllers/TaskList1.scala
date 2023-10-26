package controllers 

import javax.inject._
import models.TaskListInMemoryModel
import shared.SharedMessages
import play.api.mvc._
//import play.api.i18n._
import play.api.data._
import play.api.data.Form._

case class LoginData(username:String,password:String)

@Singleton 
class TaskList1 @Inject()(cc: ControllerComponents) extends AbstractController(cc){
  val loginForm = Form(mapping(
    "Username"->text(3,10),
    "Password"->text(8)
  )(LoginData.apply)(LoginData.unapply))

  def logIn = Action {implicit request =>
    Ok(views.html.login1())
  } //first item occurs on the page first (maybe?)
  
  def validateLoginForm = Action{ implicit request =>
    Ok("temp")
  }

  def validateLoginGet(username:String, password:String) = Action {
    Ok(s"$username logged in with $password.")
  }

  def validateLoginPost = Action { implicit request =>
    val postVals = request.body.asFormUrlEncoded    //the "argument" names in login1.scala.html are the keys in this map
    postVals.map {args =>                           //the values are the inputs to the form
      val username = args("userName").head
      val password = args("passWord").head
      if(TaskListInMemoryModel.validateUser(username,password)){
        Redirect(routes.TaskList1.taskList1).withSession("username"->username)
      } else Redirect(routes.TaskList1.logIn).flashing("error"->"Invalid username/password")
    }.getOrElse(Redirect(routes.TaskList1.logIn)) //could use "login1", but what if someone changes the route?
  }//use reverse routing to avoid this problem; if the name has been changed, there will be a compiler error

  def createUser = Action { implicit request =>
    val postVals = request.body.asFormUrlEncoded    //the "argument" names in login1.scala.html are the keys in this map
    postVals.map {args =>                           //the values are the inputs to the form
      val username = args("userName").head
      val password = args("passWord").head
      if(TaskListInMemoryModel.createUser(username,password)){
        Redirect(routes.TaskList1.taskList1).withSession("username"->username)
      } else Redirect(routes.TaskList1.logIn).flashing("error"->"Username taken")
    }.getOrElse(Redirect(routes.TaskList1.logIn)) //could use "login1", but what if someone changes the route?
  }

  def taskList1 = Action { implicit request =>
    val usernameOpt = request.session.get("username")
    usernameOpt.map{ username =>
    val tasks=TaskListInMemoryModel.getTasks(username)
    Ok(views.html.tasklist1(tasks))}.getOrElse(Redirect(routes.TaskList1.logIn))
  }

  def logout = Action{
    Redirect(routes.TaskList1.logIn).withNewSession
  }

  def addTask = Action{implicit request =>
    val usernameOpt = request.session.get("username")
    usernameOpt.map{ username =>
      val postVals = request.body.asFormUrlEncoded    //the "argument" names in login1.scala.html are the keys in this map
      postVals.map {args =>                           //the values are the inputs to the form
        val task = args("task").head
        TaskListInMemoryModel.addTask(username,task)
        Redirect(routes.TaskList1.taskList1).flashing("success"->"Task added")
      }.getOrElse(Redirect(routes.TaskList1.taskList1).flashing("error"->"Invalid form"))
    }.getOrElse(Redirect(routes.TaskList1.logIn).flashing("error"->"How'd you get here?")) //could use "login1", but what if someone changes the route?
  }

  def removeTask = Action{implicit request =>
    val usernameOpt = request.session.get("username")
    usernameOpt.map{ username =>
      val postVals = request.body.asFormUrlEncoded    //the "argument" names in login1.scala.html are the keys in this map
      postVals.map {args =>                           //the values are the inputs to the form
        val index = args("index").head.toInt
        if(TaskListInMemoryModel.removeTask(username,index)){
          Redirect(routes.TaskList1.taskList1).flashing("success"->"Task removed")
        } else Redirect(routes.TaskList1.taskList1).flashing("error"->"Invalid index")
      }.getOrElse(Redirect(routes.TaskList1.taskList1).flashing("error"->"Invalid form"))
    }.getOrElse(Redirect(routes.TaskList1.logIn).flashing("error"->"How'd you get here?")) //could use "login1", but what if someone changes the route?
  }
}
