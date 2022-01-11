package config

import org.apache.commons.dbcp2.BasicDataSource
import java.sql.Connection

object DbPool {
  private val ds: BasicDataSource = new BasicDataSource

  ds.setDriverClassName(DbConfiguration.DB_DRIVER)
  ds.setUrl(DbConfiguration.CONNECTION_URL)
  ds.setUsername(DbConfiguration.DB_USER_NAME)
  ds.setPassword(DbConfiguration.DB_USER_PASSWORD)
  ds.setMinIdle(DbConfiguration.DB_MIN_COLLECTION)
  ds.setInitialSize(DbConfiguration.DB_MIN_COLLECTION)
  ds.setMaxIdle(DbConfiguration.DB_MAX_COLLECTION)
  ds.setMaxOpenPreparedStatements(100)

  def getConnection: Connection = ds.getConnection()

}
