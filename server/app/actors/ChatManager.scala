package actors

import akka.actor._

class ChatManager extends Actor{
    private var chatters = List.empty[ActorRef]
    import ChatManager._
  def receive: Receive = {
    case NewChatter(chatter) => chatters::=chatter
    case Message(msg) => for(c <- chatters) c! ChatActor.SendMessage(msg)
    case m => println("Unhandled message in ChatActor: "+m)
  }
}
object ChatManager{
    case class NewChatter(chatter:ActorRef)
    case class Message(msg:String)
}