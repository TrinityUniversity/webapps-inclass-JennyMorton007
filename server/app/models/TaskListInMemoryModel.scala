package models
import collection.mutable

object TaskListInMemoryModel{   //if the server goes down in any way, all of the changes/information is lost, so this is not ideal
    private val users = mutable.Map[String,String]("Jenny"->"blue","someone"->"something")
    private val tasks = mutable.Map[String,List[String]]("Jenny"->List("complete videos","stay alive"))

    def validateUser(username:String,password:String):Boolean = {
        users.get(username).map(_ ==password).getOrElse(false)  //check type signatures if confused
    }

    def createUser(username:String,password:String):Boolean = { 
        if(users.contains(username)) false else {//currently does not work (idk why)
            users(username) = password
            true
        } 
    }   //returns false if username already exists

    def getTasks(username:String):Seq[String] = {
        tasks.get(username).getOrElse(Nil)      //if the user doesnt have any tasks, return empty list
    }

    def addTask(username:String,task:String):Unit = {
        tasks(username) = task :: tasks.get(username).getOrElse(Nil)
    }

    def removeTask(username:String,ind:Int):Boolean = {
        if(ind<tasks(username).length && ind>=0 && tasks.get(username).nonEmpty){
            tasks(username) = tasks(username).take(ind).concat(tasks(username).drop(ind+1))
            //tasks(username)= tasks(username).patch(ind,Nil,1)     //is equivalent to above
            true
        } else false
    }    //returns false if invalid index 
}