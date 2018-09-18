import scala.collection.mutable.ListBuffer
import scala.util.Random.shuffle

import org.joda.time.DateTime

import SqlUtil._

object Random {
  def pick(n: String): Unit = getConnection.foreach(conn => {
    val query = "SELECT board, articleId, postTime from ARTICLES"
    val time = "(\\d{4}).(\\d{2}).(\\d{2}) (\\d{2}):(\\d{2}):(\\d{2})".r
    val curr = new DateTime
    val limit = curr.minusYears(1)

    val stmt = conn.createStatement
    val rs = stmt.executeQuery(query)
    val buf = ListBuffer[String]()
    while (rs.next) {
      val board = rs.getString("board")
      val id = rs.getString("articleId")
      if (rs.getString("postTime") match {
        case time(y, m, d, h, mi, s) =>
          new DateTime(
            y.toInt, m.toInt, d.toInt, h.toInt, mi.toInt, s.toInt
          ) isAfter limit
        case _ => false
      }) buf += s"https://portal.kaist.ac.kr/ennotice/$board/$id"
    }

    println(shuffle(buf).take(n.toInt).mkString("\n"))
  })
}
