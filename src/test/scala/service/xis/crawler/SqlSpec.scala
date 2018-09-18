import org.scalatest._

import java.nio.file.{Paths, Files}

import SqlUtil._

class SqlSpec extends FlatSpec with Matchers {

  "Login Config" should "exists" in {
    Files.exists(Paths.get(LoginUtil.confPath)) shouldEqual true
  }

  "MySQL Config" should "exists" in {
    Files.exists(Paths.get(confPath)) shouldEqual true
  }

  val connection = getConnection

  "MySQL" should "be connected" in {
    connection.size shouldEqual 1
  }

  "MySQL" should "be prepared" in {
    connection.get.createStatement.execute("USE CRAWL")
  }
}
