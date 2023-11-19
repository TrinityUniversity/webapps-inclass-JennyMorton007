import org.scalatestplus.play.PlaySpec
import org.scalatestplus.play.guice.GuiceOneServerPerSuite
import org.scalatestplus.play.OneBrowserPerSuite
import org.scalatestplus.play.HtmlUnitFactory

class TaskList1Spec extends PlaySpec with GuiceOneServerPerSuite with OneBrowserPerSuite with HtmlUnitFactory {
  "Task list 1" must {
    "login and see task list" in {
        println("\tbeginning of login and see task list")
      go to s"http://localhost:$port/login1"        //this is what doesnt work
      println("\tgot to login page (maybe?)")
      pageTitle mustBe "Login"
      println("\ttitle is Login")
      find(cssSelector("h2")).isEmpty mustBe false
      find(cssSelector("h2")).foreach(e => e.text mustBe "Login")
      click on "username-login"
      textField("username-login").value = "Jenny"
      click on "password-login"
      pwdField(id("password-login")).value = "blue"
      submit()
      eventually {
        pageTitle mustBe "Task List"
        find(cssSelector("h2")).isEmpty mustBe false
        find(cssSelector("h2")).foreach(e => e.text mustBe "Task List")
        findAll(cssSelector("li")).toList.map(_.text) mustBe List("complete videos", "stay alive")
      }
    }

    "add task in" in {
      pageTitle mustBe "Task List"
      click on "newTask"
      textField("newTask").value = "test"
      submit()
      eventually {
        pageTitle mustBe "Task List"
        findAll(cssSelector("li")).toList.map(_.text) mustBe List("test", "complete videos", "stay alive")
      }
    }

    "delete task" in {
      pageTitle mustBe "Task List"
      click on "delete-2"
      eventually {
        pageTitle mustBe "Task List"
        findAll(cssSelector("li")).toList.map(_.text) mustBe List("test", "complete videos")
      }
    }

    "logout" in {
      pageTitle mustBe "Task List"
      click on "logout"
      eventually {
        pageTitle mustBe "Login"
      }
    }

    "create user and add two tasks" in {
      pageTitle mustBe "Login"
      click on "username-create"
      textField("username-create").value = "Lewis"
      click on "password-create"
      pwdField(id("password-create")).value = "foo"
      submit()
      eventually {
        pageTitle mustBe "Task List"
        findAll(cssSelector("li")).toList.map(_.text) mustBe Nil
        click on "newTask"
        textField("newTask").value = "test1"
        submit()
        eventually {
          pageTitle mustBe "Task List"
          findAll(cssSelector("li")).toList.map(_.text) mustBe List("test1")
          click on "newTask"
          textField("newTask").value = "test2"
          submit()
          eventually {
            pageTitle mustBe "Task List"
            findAll(cssSelector("li")).toList.map(_.text) mustBe List("test2", "test1")
          }
        }
      }
    }
  }  
}