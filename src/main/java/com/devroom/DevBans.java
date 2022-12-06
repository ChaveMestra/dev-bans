package com.devroom;

import com.devroom.commands.AdminCommands;
import com.devroom.commands.BanCommand;
import com.devroom.commands.HistoryCommand;
import com.devroom.commands.UnbanCommand;
import com.devroom.config.ConfigController;
import com.devroom.config.type.MenuConfig;
import com.devroom.config.type.MessagesConfig;
import com.devroom.config.type.StorageConfig;
import com.devroom.player.PlayerController;
import com.devroom.storage.StorageController;
import com.devroom.view.GUIModule;
import lombok.Getter;
import lombok.Setter;
import me.lucko.helper.plugin.ExtendedJavaPlugin;
@Getter
public class DevBans extends ExtendedJavaPlugin {

    @Getter
    public static DevBans instance;

    //configs
    private ConfigController configController;
    @Setter
    private MenuConfig menuConfig;
    @Setter
    private MessagesConfig messagesConfig;
    @Setter
    private StorageConfig storageConfig;

    //storage
    private StorageController storageController;

    //cache
    private PlayerController playerController;

    //view
    private GUIModule guiModule;


    protected void enable() {
        instance = this;
        this.configController = new ConfigController(this).setupConfig();

        this.storageController = new StorageController(this);
        this.storageController.initializeStorage(this);

        this.playerController = new PlayerController();
        this.guiModule = new GUIModule(this);

        bindModule(this.playerController);
        bindModule(this.guiModule);
        bindModule(new AdminCommands());
        bindModule(new BanCommand());
        bindModule(new HistoryCommand());
        bindModule(new UnbanCommand());
    }

    protected void disable() {

    }


}
