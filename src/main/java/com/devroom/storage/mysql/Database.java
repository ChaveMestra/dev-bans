package com.devroom.storage.mysql;

import java.sql.Connection;

public interface Database {


  Connection getConnection();
}
