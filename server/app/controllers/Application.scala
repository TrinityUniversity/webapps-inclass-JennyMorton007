package controllers

import javax.inject._

import shared.SharedMessages
import play.api.mvc._

@Singleton
class Application @Inject()(cc: ControllerComponents) extends AbstractController(cc) {
  //to run the site; "sbt";"run" in the terminal
  //hit enter to stop it; "exit" to escape sbt
  def index = Action {  implicit request =>
    Ok(views.html.index(SharedMessages.itWorks))
  }

  def product(prodType:String, prodNum: Int) = Action{
    Ok(s"Product type is: $prodType, product number is: $prodNum")
  }

  def randomNumber = Action{
    Ok(util.Random.nextInt(100).toString())
  }

  def randomString(length:Int)=Action{
    Ok(util.Random.nextString(length))
  }
}
