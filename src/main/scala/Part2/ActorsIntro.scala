package Part2

import akka.actor.{Actor, ActorSystem, Props}

object ActorsIntro extends App{
  val system = ActorSystem("CounterActorSystem")

  class CounterActor extends Actor{
    var c=0
    override def receive: Receive = {
      case "Increment" =>
        c+=1
        println("counter incremented")

      case "Decrement" =>
        c-=1
        println("counter decremented")
      case "Print" => println("counter now is:" + c)
    }
  }

  val counter=system.actorOf(Props[CounterActor],"FirstCounter")

  counter ! "Increment"
  counter ! "Increment"
  counter ! "Increment"
  counter ! "Print"
  counter ! "Increment"
  counter ! "Increment"
  counter ! "Decrement"
  counter ! "Decrement"
  counter ! "Print"
  counter ! "Decrement"
  counter ! "Decrement"
  counter ! "Decrement"
  counter ! "Increment"
  counter ! "Print"

}
