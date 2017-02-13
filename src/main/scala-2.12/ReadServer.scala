package voice

import akka.actor.ActorSystem
import akka.persistence.cassandra.query.scaladsl.CassandraReadJournal
import akka.persistence.query.PersistenceQuery
import akka.stream.ActorMaterializer
import read.ReadActor
import read.ReadActor.Start
import user.user.{Address, Person}

import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Await
/**
  * Created by anand on 01/02/17.
  */
object ReadServer extends App{

  implicit val system = ActorSystem("TestSystem")
  implicit val materializer = ActorMaterializer()

  println("started the processing")

  val journalId = akka.persistence.cassandra.query.scaladsl.CassandraReadJournal.Identifier

  val readJournal = PersistenceQuery(system).readJournalFor[CassandraReadJournal](journalId)

  //val source: Source[EventEnvelope, NotUsed] =
  //"akka://voiceActorSystem/user/callSupervisor/service_23823bea-e890-11e6-8273-176861845217", 0, Long.MaxValue)
  val result = //readJournal.allPersistenceIds().
    readJournal.eventsByPersistenceId("akka://voiceActorSystem/user/callSupervisor/service_1a9d24b4-e894-11e6-827d-176861845217",0,Long.MaxValue).
    runForeach(println)

  result.onComplete { _ =>
    materializer.shutdown()
    Await.result(system.terminate(), 50.seconds)
  }

  val vv = Person(name = Some("test"),age = Some(12), addresses = List(Address(street1 = Some("a"),street2 = Some("b"))))


  //val k =

  //val readActor = system.actorOf(ReadActor.props(system,materializer),"ReadActor")

  //readActor ! Start()

}
