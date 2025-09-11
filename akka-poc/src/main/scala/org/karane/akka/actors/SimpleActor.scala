package org.karane.akka.actors

import akka.actor.typed.{ActorRef, Behavior}
import akka.actor.typed.scaladsl.Behaviors

object SimpleActor {
  sealed trait Command
  case class Greet(name: String, replyTo: ActorRef[String]) extends Command

  def apply(): Behavior[Command] = Behaviors.receive { (context, message) =>
    message match {
      case Greet(name, replyTo) =>
        context.log.info(s"Hello, $name!")
        replyTo ! s"Greeted $name"
        Behaviors.same
    }
  }
}
