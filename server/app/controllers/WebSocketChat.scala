package controllers

import javax.inject._
import models._
import play.api.mvc._
import play.api.i18n._  
import play.api.libs.json._
import play.api.libs.streams.ActorFlow
import akka.actor.ActorSystem
import akka.stream.Materializer
import actors._
import akka.actor.Props

@Singleton
class WebSocketChat @Inject()(cc: ControllerComponents)(implicit system:ActorSystem,mat:Materializer) extends AbstractController(cc){
    val manager = system.actorOf(Props[ChatManager](),"Manager")

    def index = Action{implicit request=>
        Ok(views.html.chatPage())
    }
    
    def socket = WebSocket.accept[String,String]{request=>
        //println("Getting Socket")
        ActorFlow.actorRef{out=>
            ChatActor.props(out,manager)
        }
    }
}