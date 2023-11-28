package controllers

import javax.inject._
import models._
import play.api.mvc._
import play.api.i18n._  
import play.api.libs.json._


@Singleton
class TaskList4 @Inject()(cc: ControllerComponents) extends AbstractController(cc){
    def load = Action{implicit request=>
        Ok(views.html.version4Main())
    }

}