import scala.io.Source

import java.sql.{DriverManager, Connection}

object SqlUtil {
  val confPath = "mysql.conf"
  private var driverLoaded = false

  private def loadDriver: Unit =
    try {
      Class.forName("com.mysql.jdbc.Driver").newInstance
      driverLoaded = true
    } catch {
      case _: Exception =>
    }

  def getConnection: Option[Connection] = {
    this.synchronized { if (!driverLoaded) loadDriver }
    try {
      Source.fromFile(confPath, "UTF-8").mkString.split("\n").toList match {
        case host :: port :: db :: username :: pwd :: Nil =>
          Some(DriverManager.getConnection(
        s"jdbc:mysql://$host:$port/$db?useUnicode=true&characterEncoding=UTF-8",
            username, pwd
          ))
        case _ => None
      }
    } catch {
      case e: Exception =>
        e.printStackTrace
        None
    }
  }
} 
