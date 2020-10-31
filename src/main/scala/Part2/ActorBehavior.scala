package Part2

import akka.actor.{Actor, ActorRef, ActorSystem, Props}

import scala.Option

object ActorBehavior extends App{

  val system = ActorSystem("ActorBehavior")

  case class Vote(candidate: String)
  case object VoteStatusRequest
  case class VoteStatusReply(candidate: Option[String])

  class Citizen extends Actor{
    override def receive: Receive = vote("")

    def vote(candidate:String):Receive={
      case Vote(s) => {
        context.become(vote(s))
        println(s"[Citizen ${self.path.name}] voted for $s")
      }
      case VoteStatusRequest => {
        println(s"[Citizen ${self.path.name}] sending vote status to ${sender().path.name}")
        sender() ! VoteStatusReply(Some(candidate))
      }
    }
  }

  case class AggregatorVotes(citizens: Set[ActorRef])

  class VoteAggregator extends Actor{
    override def receive: Receive = agg(Map[String,Int]())


    

    def agg(m: Map[String,Int]):Receive ={
      case AggregatorVotes(s) => {
        print("Start")
        val r = s.foreach(x => x ! VoteStatusRequest)
      }
    }
  }

  val alice = system.actorOf(Props[Citizen],"alice")
  val bob = system.actorOf(Props[Citizen],"bob")
  val charlie = system.actorOf(Props[Citizen],"charlie")
  val daniel = system.actorOf(Props[Citizen],"daniel")
  val voteAggregator = system.actorOf(Props[Citizen],"voteAggregator")
  alice ! Vote("Martin")
  bob ! Vote("Jonas")
  charlie ! Vote("Ronald")
  daniel ! Vote("Ronald")


  voteAggregator ! AggregatorVotes(Set(alice,bob,charlie,daniel))

}
