object Main {
  def main(args: Array[String]) = args.toList match {
    case "-c" :: Nil => Crawler run
    case "-r" :: n :: Nil => Random pick n
    case _ =>
  }
}
