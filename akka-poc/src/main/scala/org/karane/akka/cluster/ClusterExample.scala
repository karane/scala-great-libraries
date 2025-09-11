package org.karane.akka.cluster

import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors
import akka.cluster.typed.{Cluster, Join}

object ClusterExample extends App {
  val system = ActorSystem[Nothing](Behaviors.empty, "ClusterSystem")
  val cluster = Cluster(system)

  // Join the cluster (for demonstration purposes, it joins itself)
  cluster.manager ! Join(cluster.selfMember.address)

  println(s"Node joined: ${cluster.selfMember.address}")
}
