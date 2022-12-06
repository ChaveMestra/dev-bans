package com.devroom.config;

import com.devroom.DevBans;
import com.devroom.config.type.MenuConfig;
import com.devroom.config.type.MessagesConfig;
import com.devroom.config.type.StorageConfig;
import com.devroom.utils.logs.Log;
import com.devroom.utils.logs.LogType;

public class ConfigController {
    private final DevBans plugin;

    public ConfigController(DevBans plugin) {
        this.plugin = plugin;
        setupConfig();
    }

    public ConfigController setupConfig() {
        //configs
        plugin.setMenuConfig(plugin.setupConfig("menu.yml", new MenuConfig()));
        plugin.setMessagesConfig(plugin.setupConfig("messages.yml", new MessagesConfig()));
        plugin.setStorageConfig(plugin.setupConfig("storage.yml", new StorageConfig()));

        printConfigs();
        return this;
    }

    public void reloadConfigs() {
        setupConfig();
    }


    public void printConfigs() {
        Log.print(LogType.INFO, "Config loaded:");
        Log.print(LogType.INFO, plugin.getMenuConfig().toString());
        Log.print(LogType.INFO, plugin.getMessagesConfig().toString());
    }
}
