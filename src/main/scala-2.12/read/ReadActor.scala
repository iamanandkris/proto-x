package read

import akka.NotUsed
import akka.actor.{Actor, ActorSystem, Props}
import akka.stream.Materializer

import scala.concurrent.Await
//import akka.persistence.cassandra.query.javadsl.CassandraReadJournal
import akka.persistence.query.{EventEnvelope, PersistenceQuery}
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.Source
import read.ReadActor.Start
import akka.persistence.cassandra.query.scaladsl.CassandraReadJournal
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global
/**
  * Created by anand on 01/02/17.
  */

object ReadActor{
  def props(system:ActorSystem,materializer: ActorMaterializer):Props= Props(new ReadActor(system,materializer))

  case class Start()
}


class ReadActor(system:ActorSystem,materializer: ActorMaterializer) extends Actor{

  implicit val materi = materializer

  override def receive: Receive ={

    case x:EventEnvelope => println("Here are the events - " + x.event)
    case x:Start => {

      println("started the processing")

      val journalId = akka.persistence.cassandra.query.scaladsl.CassandraReadJournal.Identifier

      val readJournal = PersistenceQuery(system).readJournalFor[CassandraReadJournal](journalId)

      //val source: Source[EventEnvelope, NotUsed] =
      val result = readJournal.eventsByPersistenceId("akka://voiceActorSystem/user/callSupervisor/service_23823bea-e890-11e6-8273-176861845217", 0, Long.MaxValue).take(3).
        runForeach(println)

      result.onComplete { _ =>
        materializer.shutdown()
        Await.result(system.terminate(), 10.seconds)
      }


      //val k = source.runForeach { self ! _ }

      //source.  //async.runForeach { event => println("Event: " + event)}

      //val k = source.runForeach { event => println("Event: " + event)}

      //Await.result(k,10 seconds)

      println("Awaiting the processing")
    }
    case x:Any => {}
  }
}
