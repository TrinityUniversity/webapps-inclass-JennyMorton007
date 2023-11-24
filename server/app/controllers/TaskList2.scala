package controllers

import javax.inject._
import models.TaskListInMemoryModel
import shared.SharedMessages
import play.api.mvc._
import play.api.i18n._  //don't think I need

import views.html.defaultpages.badRequest

@Singleton
class TaskList2 @Inject()(cc: ControllerComponents) extends AbstractController(cc){
  def load = Action{ implicit request =>
    val usernameOpt = request.session.get("username")
    usernameOpt.map{ username =>
        Ok(views.html.version2Main(routes.TaskList2.taskList.toString()))
    }.getOrElse(Ok(views.html.version2Main(routes.TaskList2.login.toString)))
  }

  def login = Action{implicit request =>
    Ok(views.html.login2())
  }

  def taskList = Action{implicit request =>
    val usernameOpt = request.session.get("username")
    usernameOpt.map{ username =>
        Ok(views.html.tasklist2(TaskListInMemoryModel.getTasks(username)))
    }.getOrElse(Ok(views.html.login2()))
  }

  def validate = Action{implicit request =>
    val postVals = request.body.asFormUrlEncoded    //the "argument" names in login1.scala.html are the keys in this map
    postVals.map {args =>                           //the values are the inputs to the form
      val username = args("username").head
      val password = args("password").head
      if(TaskListInMemoryModel.validateUser(username,password)){
        Ok(views.html.tasklist2(TaskListInMemoryModel.getTasks(username))).withSession("username"->username)
      } else Ok(views.html.login2())
    }.getOrElse(Ok(views.html.login2()))
  }

  def create = Action{implicit request =>
      //println("creating the user")
      val postVals = request.body.asFormUrlEncoded    //the "argument" names in login1.scala.html are the keys in this map
    postVals.map {args =>                           //the values are the inputs to the form
      val username = args("username").head
      val password = args("password").head
      if(TaskListInMemoryModel.createUser(username,password)){
        Ok(views.html.tasklist2(TaskListInMemoryModel.getTasks(username))).withSession("username"->username)
      } else Ok(views.html.login2())
    }.getOrElse(Ok(views.html.login2()))
  }

  def delete(index:Int) = Action{implicit request =>
    val usernameOpt = request.session.get("username")
    usernameOpt.map{ username =>
    if(TaskListInMemoryModel.removeTask(username,index)){
          Ok(views.html.tasklist2(TaskListInMemoryModel.getTasks(username)))
        } else Ok(views.html.tasklist2(TaskListInMemoryModel.getTasks(username)))
    }.getOrElse(Ok(views.html.login2()))
    }

    def add(task:String)=Action{implicit request =>
        val usernameOpt = request.session.get("username")
        println(task)
        usernameOpt.map{ username =>
            TaskListInMemoryModel.addTask(username,task)
            Ok(views.html.tasklist2(TaskListInMemoryModel.getTasks(username)))
        }.getOrElse(Ok(views.html.login2()))
    }

    def logout=Action{
        Redirect(routes.TaskList2.load).withNewSession
    }
}
