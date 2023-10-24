package models
import collection.mutable

object TaskListInMemoryModel{   //if the server goes down in any way, all of the changes/information is lost, so this is not ideal
    private val users = mutable.Map[String,String]("Jenny"->"bye bitch")

    def validateUser(username:String,password:String):Boolean = {
        users.get(username).map(_ ==password).getOrElse(false)  //check type signatures if confused
    }

    def createUser(username:String,password:String):Boolean = ???   //returns false if username already exists

    def getTasks(username:String):Seq[String] = ???

    def addTask(username:String,task:String):Unit = ???

    def removeTask(username:String,ind:Int):Boolean = ???    //returns false if invalid index 
}