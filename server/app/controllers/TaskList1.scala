package controllers

import javax.inject._

import shared.SharedMessages
import play.api.mvc._

@Singleton 
class TaskList1 @Inject()(cc: ControllerComponents) extends AbstractController(cc){
  def taskList2 = Action {
    val tasks=List("thing1","thing2","thing3")
    Ok(views.html.taskList1(tasks))
  }
}
