package org.karane.akka.streams

import akka.actor.ActorSystem
import akka.stream.scaladsl.{Sink, Source}

object StreamExample extends App {
  implicit val system: ActorSystem = ActorSystem("StreamSystem")

  val source = Source(1 to 10)
  val sink = Sink.foreach[Int](println)

  source.runWith(sink).onComplete(_ => system.terminate())(system.dispatcher)
}
