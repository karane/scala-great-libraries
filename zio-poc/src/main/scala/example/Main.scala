package example

import zio._
import zio.stream._
import java.io.IOException

// ===== Logger service =====
trait Logger:
  def log(msg: String): UIO[Unit]

object Logger:
  val consoleLogger: ULayer[Logger] = ZLayer.succeed(new Logger:
    def log(msg: String): UIO[Unit] =
      Console.printLine(s"[LOG] $msg").orDie
  )

  def log(msg: String): ZIO[Logger, Nothing, Unit] =
    ZIO.serviceWithZIO[Logger](_.log(msg))

// ===== Main application =====
object Main extends ZIOAppDefault:

  // Console greeting
  val greet: ZIO[Logger & Console, IOException, String] =
    for
      _    <- Logger.log("Starting greeting program")
      _    <- Console.printLine("What is your name?")
      name <- Console.readLine
    yield name

  // Safe division with typed errors
  val safeDivide: ZIO[Any, String, Int] =
    ZIO.fromEither(Either.cond(5 != 0, 10 / 5, "Division by zero!"))

  // Parallel work
  val parallelWork: ZIO[Any, Nothing, (Int, Int)] =
    ZIO.succeed(42) zipPar ZIO.succeed(99)

  // Stream example
  val streamExample: ZIO[Any, Nothing, List[Int]] =
    ZStream.fromIterable(1 to 5).map(_ * 10).runCollect.map(_.toList)

  override def run: ZIO[Any, Any, Any] =
    (
      for
        name    <- greet
        _       <- Console.printLine(s"Hello, $name!")
        result  <- safeDivide.catchAll(err => Console.printLine(s"Error: $err"))
        _       <- Console.printLine(s"Safe divide result: $result")
        both    <- parallelWork
        _       <- Console.printLine(s"Parallel results: $both")
        numbers <- streamExample
        _       <- Console.printLine(s"Stream results: $numbers")
      yield ()
      ).provide(Logger.consoleLogger ++ ZLayer.succeed(Console.ConsoleLive))
