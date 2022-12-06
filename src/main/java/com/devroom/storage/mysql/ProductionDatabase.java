package com.devroom.storage.mysql;

import com.devroom.DevBans;
import com.devroom.utils.logs.Log;
import com.devroom.utils.logs.LogType;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class ProductionDatabase implements Database {

  private static final AtomicInteger POOL_COUNTER = new AtomicInteger(0);

  private static final String DATA_SOURCE_CLASS = "org.mariadb.jdbc.MySQLDataSource";

  private static final int MAXIMUM_POOL_SIZE = (Runtime.getRuntime().availableProcessors() * 2) + 10;
  private static final int MINIMUM_IDLE = Math.min(MAXIMUM_POOL_SIZE, 10);
  private static final long MAX_LIFETIME = TimeUnit.MINUTES.toMillis(30);
  private static final long CONNECTION_TIMEOUT = TimeUnit.SECONDS.toMillis(35);
  private static final long LEAK_DETECTION_THRESHOLD = TimeUnit.SECONDS.toMillis(35);

  public HikariDataSource source;



  public ProductionDatabase() {
    setupRemoteDatabase();
  }

  public void closeRemoteConnection() {
      source.close();
  }


  @Override
  public Connection getConnection(){
    try {
      return source.getConnection();
    } catch (SQLException e) {
      Log.print(LogType.ERROR, "MySQL connection failed! Error:");
      e.printStackTrace();
    }
    return null;
  }

  private void setupRemoteDatabase() {
    final HikariConfig hikari = new HikariConfig();
    hikari.setPoolName("devbans-production-hikaripool-" + POOL_COUNTER.getAndIncrement());

    hikari.setDataSourceClassName(DATA_SOURCE_CLASS);
    hikari.addDataSourceProperty("serverName", DevBans.getInstance().getStorageConfig().getMysqlHost());
    hikari.addDataSourceProperty("port", DevBans.getInstance().getStorageConfig().getMysqlPort());
    hikari.addDataSourceProperty("databaseName", DevBans.getInstance().getStorageConfig().getMysqlDbName());
    hikari.addDataSourceProperty("user",  DevBans.getInstance().getStorageConfig().getMysqlUser());
    hikari.addDataSourceProperty("password",  DevBans.getInstance().getStorageConfig().getMysqlPassword());
    hikari.addDataSourceProperty("properties", "useUnicode=true;characterEncoding=utf8");



    hikari.setMaximumPoolSize(MAXIMUM_POOL_SIZE);
    hikari.setMinimumIdle(MINIMUM_IDLE);
    hikari.setMaxLifetime(MAX_LIFETIME);
    hikari.setConnectionTimeout(CONNECTION_TIMEOUT);
    hikari.setLeakDetectionThreshold(LEAK_DETECTION_THRESHOLD);



    this.source = new HikariDataSource(hikari);
  }
}
