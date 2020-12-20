import cats.effect.{IO, Resource}
import org.neo4j.driver.v1.{AuthTokens, Config, Driver, GraphDatabase, Session, StatementResult}
import org.neo4j.driver.v1.Values.parameters

import scala.collection.mutable.ListBuffer
import scala.util.Try

trait Client {
  def runQuery(query: String): IO[StatementResult]
}

object Client {
  private def sessionResource(driver: Driver): Resource[IO, Session] = Resource.make(
    IO.fromTry(Try(driver.session()))
  )(s => IO(s.close()))

  private def run(session: Session, query: String): IO[StatementResult] = IO.fromTry(
    Try(session.run(query, parameters()))
  )

  private def driver(config: Neo4jDbConfig): Driver = {
    val noSSL: Config = Config.build.withEncryptionLevel(Config.EncryptionLevel.NONE).toConfig
    GraphDatabase.driver(config.url, AuthTokens.basic(config.username, config.password), noSSL)
  }

  def apply(config: Neo4jDbConfig): Resource[IO, Client] = {
    sessionResource(driver(config)).map{ session =>
      new Client {
        override def runQuery(query: String): IO[StatementResult] = run(session, query)
      }
    }
  }


  def extractValues(result: StatementResult, key: String): List[String] = {
    var listBuffer: ListBuffer[String] = ListBuffer[String]()
    while (result.hasNext) {
      listBuffer += (result.next.get(key).toString)

    }
    listBuffer.toList
  }

}