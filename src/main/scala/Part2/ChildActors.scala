package Part2

import akka.actor.{Actor, ActorSelection, ActorSystem, Props}

object ChildActors extends App {

  object WordCounterMaster{
    case class Initialize(nChildren: Int)
    case class WordCountTask(text: String)
    case class WordCountReply(message: String, count: Int)
  }
  class WordCounterMaster extends Actor{
    import WordCounterMaster._
    override def receive: Receive = {
      case Initialize(n) => {
        (0 until n).foreach( x => {
          context.actorOf(Props[WordCounterWorker],s"worker${x+1}")
        })
        context.become(hasWorkers(n,0))
      }
      case _ => println("You should initialize workers first.")
    }

    def hasWorkers(numberOfWorkers: Int,currentWorker: Int):Receive= {
      case message:String => {
        val worker = context.actorSelection(s"worker${currentWorker+1}")
        context.become(hasWorkers(numberOfWorkers,(currentWorker+1)%numberOfWorkers))
        worker ! WordCountTask(message)
      }
      case WordCountReply(message,n) => {
        println(s"${sender().path.name} counted $n words in the message: $message")
      }
    }
  }

  class WordCounterWorker extends Actor{
    import WordCounterMaster._

    override def receive: Receive = {
      case WordCountTask(message) =>{
        val count = message.split(" ").length
        sender() ! WordCountReply(message,count)
      }
    }
  }
  import WordCounterMaster._
  val system = ActorSystem("ActorSystem")

  val wordCounterMaster = system.actorOf(Props[WordCounterMaster],"CounterMaster")

  wordCounterMaster ! Initialize(5)

  wordCounterMaster ! "Akka is awesome"
  wordCounterMaster ! "This is really cool"
  wordCounterMaster ! "I made this program with Actors"
  wordCounterMaster ! "Hello World!"
  wordCounterMaster ! "The final message"
  wordCounterMaster ! "Last message"
  wordCounterMaster ! "I did it !"



}
