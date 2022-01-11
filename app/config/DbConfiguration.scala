package config

object DbConfiguration {
  val HOST_NAME: String = "localhost"
  val DB_NAME: String = "testdb"
  val DB_PORT: String = "3306"
  val DB_USER_NAME: String = "root"
  val DB_USER_PASSWORD: String = "example"
  val DB_DRIVER: String = "com.mysql.cj.jdbc.Driver"
  val DB_MIN_COLLECTION: Int = 1
  val DB_MAX_COLLECTION: Int = 6
  val CONNECTION_URL: String =
    s"jdbc:mysql://$HOST_NAME:$DB_PORT/$DB_NAME?useSSL=false"
}
