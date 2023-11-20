import org.scalatestplus.play._
import models.TaskListInMemoryModel
class TaskListInMemoryModel extends PlaySpec{
  "TaskListInMemoryModel" must{
    "do valid login for default user" in{
        TaskListInMemoryModel.validateUser("someone","something") mustBe(true)
        TaskListInMemoryModel.validateUser("Jenny","blue") mustBe(true)
    }
    "get correct default tasks" in {
      TaskListInMemoryModel.getTasks("Jenny") mustBe (List("test","complete videos"))
    }   //tasks complete in order, so this goes at the top
    //this suite of tests goes after TaskList1Spec
    "reject login with wrong password" in{
        TaskListInMemoryModel.validateUser("someone","somethinh") mustBe(false)
    }
    "reject login with wrong username" in{
        TaskListInMemoryModel.validateUser("somrone","something") mustBe(false)
    }
    "reject login with wrong username and password" in{
        TaskListInMemoryModel.validateUser("somrone","some") mustBe(false)
    }
    "create new user with no tasks" in{
        TaskListInMemoryModel.createUser("somrone","some") mustBe(true)
        TaskListInMemoryModel.getTasks("somrone") mustBe(Nil)
    }
    "create new user with existing name" in{
        TaskListInMemoryModel.createUser("Jenny","what") mustBe(false)
    }
    "add new task for default user" in{
        TaskListInMemoryModel.addTask("Jenny","testing")
        TaskListInMemoryModel.getTasks("Jenny") must contain ("testing")
    }
    "add new task for new user" in {
      TaskListInMemoryModel.addTask("Mike", "testing1")
      TaskListInMemoryModel.getTasks("Mike") must contain ("testing1")
    }
    "remove task from default user" in {
      TaskListInMemoryModel.removeTask("Jenny", TaskListInMemoryModel.getTasks("Jenny").indexOf("stay alive"))
      TaskListInMemoryModel.getTasks("Jenny") must not contain ("stay alive")
    }
  }
}
