package com.devroom.player;

import com.devroom.DevBans;
import com.devroom.player.model.PlayerCache;

import java.util.UUID;

public class PlayerCacheProvider {

    public static PlayerCache getPlayerCache(UUID uuid) {
        return DevBans.getInstance().getPlayerController().retrievePlayerCache(uuid);
    }
}
