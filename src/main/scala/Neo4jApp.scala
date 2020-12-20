import cats.effect.{ExitCode, IO, IOApp}

object Neo4jApp extends IOApp {

  val cypherQuery: String =
    """
      |MATCH (p:Person)-[:SCORED_GOAL]->(match)-[:IN_TOURNAMENT]->(tourn),
      |      (p)-[:REPRESENTS]->(team)
      |WITH team, p, count(*) AS goals
      |ORDER BY team, goals DESC
      |WITH team, collect({player: p, goals: goals}) AS topScorers
      |RETURN team.name AS team, topScorers[0].player.name AS player, topScorers[0].goals AS goals
      |ORDER BY goals DESC
      |""".stripMargin

  override def run(args: List[String]): IO[ExitCode] = for {
    result <- Client(Neo4jDbConfig.default)
      .use(_.runQuery(cypherQuery))
    _ = println(Client.extractValues(result, key = "player"))
  } yield ExitCode.Success
}
