case class Neo4jDbConfig(username: String, password: String, url: String)

object Neo4jDbConfig {
  val default: Neo4jDbConfig = Neo4jDbConfig("neo4j", "hilltops-webs-worms", "bolt://100.25.160.229:33019")
}