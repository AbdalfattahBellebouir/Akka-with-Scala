package Part2

import akka.actor.{Actor, ActorRef}

object ActorBehavior extends App{
  case class Vote(candidate: String)
  case object VoteStatusRequest
  case class VoteStatusReply(cadidate: Option[String])
  class Citizen extends Actor{
    override def receive: Receive = {
      ???
    }
  }

  case class AggregatorVotes(citizens: Set[ActorRef])
  class VoteAggregator extends Actor{
    override def receive: Receive = {
      ???
    }
  }





}
