package Part2

import akka.actor.{Actor, ActorSystem, Props}

object ActorsIntro extends App{
  val system = ActorSystem("CounterActorSystem")
/*
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
*/

  class BankAccount extends Actor{
    var amount=0
    override def receive = {
      case Deposit(n) =>  amount += n

      case Withdraw(n) => amount -= n

      case Statement => println("This is the amount: " + amount)
    }
  }

  case class Deposit(x: Int)
  case class Withdraw(x: Int)
  case class Statement()

  val bankAccount = system.actorOf(Props[BankAccount], "BankAccountActor")

  bankAccount ! Deposit(1000)
  bankAccount ! Deposit(4000)
  bankAccount ! Withdraw(2000)
  bankAccount ! Statement
  bankAccount ! Deposit(1000)
  bankAccount ! Withdraw(2000)
  bankAccount ! Statement
  bankAccount ! Deposit(500)
  bankAccount ! Statement

}
