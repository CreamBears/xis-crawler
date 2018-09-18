import scala.collection.mutable.{Map => MMap}

import java.sql.Connection

import ConnectUtil._
import LoginUtil._
import CrawlUtil._
import SqlUtil._

object Crawler {
  private val boards = List("it_newsletter", "notice", "International",
    "student_notice", "lecture_academic_paper",
    "leadership_intern_counseling", "dormitory_notice",
    "dormitory_scholarship_welfare", "parttime_scholarship",
    "academic_courses", "recruiting", "gsc_usc_notice", "student_club", 
    "researcher_on_military_duty", "classified")

  def run: Unit = getConnection.foreach(conn => {
     implicit val cookies: Cookie = MMap()
     login

     for (
       board <- boards;
       max   <- getMax(board);
       index <- 1 to max;
       id    <- getIds(board, index);
       artic <- getArticle(board, id)
     ) {
       println(s"${artic.board}/${artic.id}")
       insert(conn, artic)
     }
  })

  private def insert(conn: Connection, article: Article): Unit = article match {
    case Article(b, i, t, a, d, tm, h, f, l, c, im) =>
      val stmt = conn.prepareStatement(
        "INSERT INTO ARTICLES VALUES (?, ?, ?, ?, ?, ?, ?, ?)"
      )
      stmt.setString(1, b)
      stmt.setString(2, i)
      stmt.setString(3, t.filter(_ <= 0xd800))
      stmt.setString(4, a)
      stmt.setString(5, d)
      stmt.setString(6, tm)
      stmt.setInt(7, h)
      stmt.setString(8, c.filter(_ <= 0xd800))
      stmt.executeUpdate()
      stmt.close()

      (f zip l).foreach{ case (n, l) => {
        val stmt = conn.prepareStatement(
          "INSERT INTO ATTACHEDFILES (articleId, name, url) VALUES (?, ?, ?)"
        )
        stmt.setString(1, i)
        stmt.setString(2, n)
        stmt.setString(3, l)
        stmt.executeUpdate()
        stmt.close()
      }}

      im.foreach(l => {
        val stmt = conn.prepareStatement(
          "INSERT INTO IMAGES (articleId, url) VALUES (?, ?)"
        )
        stmt.setString(1, i)
        stmt.setString(2, l)
        stmt.executeUpdate()
        stmt.close()
      })
  }
}
