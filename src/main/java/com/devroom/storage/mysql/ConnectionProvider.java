package com.devroom.storage.mysql;

import com.devroom.DevBans;

import java.sql.Connection;

public class ConnectionProvider {

    public static Connection getProductionConnection() {
        return DevBans.getInstance().getStorageController().getProductionDatabase().getConnection();
    }
}
