package org.karane.akka

import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.AskPattern.*
import akka.util.Timeout
import org.karane.akka.actors.SimpleActor

import scala.concurrent.duration.*
import scala.util.{Failure, Success}
import scala.concurrent.ExecutionContextExecutor

object Main extends App {
  val system: ActorSystem[SimpleActor.Command] =
    ActorSystem(SimpleActor(), "SimpleSystem")

  // ExecutionContext for futures
  implicit val ec: ExecutionContextExecutor = system.executionContext

  // Timeout for ask
  implicit val timeout: Timeout = 3.seconds

  // 👇 Add this line to provide the scheduler
  implicit val scheduler: akka.actor.typed.Scheduler = system.scheduler

  val future = system.ask(replyTo => SimpleActor.Greet("Karane", replyTo))

  future.onComplete {
    case Success(value) =>
      println(s"Received reply: $value")
      system.terminate()
    case Failure(exception) =>
      println(s"Failed: $exception")
      system.terminate()
  }
}
