package com.devroom.config.type;

import lombok.Getter;
import lombok.ToString;
import me.lucko.helper.config.objectmapping.Setting;
import me.lucko.helper.config.objectmapping.serialize.ConfigSerializable;

@ConfigSerializable
@Getter
public class StorageConfig {

    @Setting("mysql-host")
    public String mysqlHost = "127.0.0.1";

    @Setting("mysql-port")
    public String mysqlPort = "3306";

    @Setting("mysql-user")
    public String mysqlUser = "root";

    @Setting("mysql-pwd")
    public String mysqlPassword = "123456";

    @Setting("mysql-databasename")
    public String mysqlDbName = "network_production_db";
}
