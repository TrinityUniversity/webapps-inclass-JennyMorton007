import org.junit.runner._
import org.specs2.mutable._
import org.specs2.runner._
import play.api.test._

/**
 * add your integration spec here.
 * An integration test will fire up a whole play application in a real (or headless) browser
 */
@RunWith(classOf[JUnitRunner])
class IntegrationSpec extends Specification {

  "Application" should {
    "work from within a browser" in new WithBrowser {
      println("\tbeginning of work within browser")
      browser.goTo("http://localhost:" + port)
      println("\tgot to the index page (maybe?)")
      browser.pageSource must contain("shouts out")
      println("\tpage contains what we want")
    }
  }
}
