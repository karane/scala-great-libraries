package example

import zio._
import zio.test._
import zio.test.Assertion._

object MainSpec extends ZIOSpecDefault {

  // A simple in-memory logger for testing
  class TestLogger(ref: Ref[List[String]]) extends Logger:
    def log(msg: String): UIO[Unit] = ref.update(msg :: _)

  def spec = suite("Main Tests")(

    test("Logger should log messages") {
      for
        ref <- Ref.make[List[String]](Nil)
        logger = new TestLogger(ref)
        _ <- logger.log("Test log")
        logs <- ref.get
      yield assert(logs)(contains("Test log"))
    },

    test("Safe divide should succeed") {
      for
        result <- Main.safeDivide
      yield assert(result)(equalTo(2))
    },

    test("Parallel work should return correct tuple") {
      for
        both <- Main.parallelWork
      yield assert(both)(equalTo((42, 99)))
    },

    test("Stream example should multiply numbers by 10") {
      for
        numbers <- Main.streamExample
      yield assert(numbers)(equalTo(List(10, 20, 30, 40, 50)))
    }
  )
}
