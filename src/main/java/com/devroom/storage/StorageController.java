package com.devroom.storage;

import com.devroom.DevBans;
import com.devroom.storage.mysql.ProductionDatabase;
import com.devroom.storage.repositories.PunishmentRepository;
import com.devroom.utils.logs.Log;
import com.devroom.utils.logs.LogType;
import lombok.Getter;

public class StorageController {

    @Getter
    private final ProductionDatabase productionDatabase;
    private PunishmentRepository punishmentRepository;


    public StorageController(DevBans plugin) {
        productionDatabase = new ProductionDatabase();
        productionDatabase.getConnection();
        Log.print(LogType.INFO, "Connection successful");
    }

    public void initializeStorage(DevBans plugin) {
        punishmentRepository = new PunishmentRepository(plugin);
    }


}
