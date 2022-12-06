package com.devroom.player;

import com.devroom.player.model.PlayerCache;
import me.lucko.helper.Events;
import me.lucko.helper.terminable.TerminableConsumer;
import me.lucko.helper.terminable.module.TerminableModule;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.UUID;

public class PlayerController implements TerminableModule {

    public HashMap<UUID, PlayerCache> cacheMap = new HashMap<>();

    @Override
    public void setup(@Nonnull TerminableConsumer consumer) {

        Events.subscribe(PlayerJoinEvent.class)
                .handler(event -> {
                   PlayerCache playerCache = retrievePlayerCache(event.getPlayer().getUniqueId());
                   if (playerCache.isBanned()) {
                       playerCache.kickPlayer();
                   }
                }).bindWith(consumer);

        Events.subscribe(PlayerQuitEvent.class)
                .handler(event -> {
                    invalidatePlayerCache(event.getPlayer().getUniqueId());
                }).bindWith(consumer);

    }


    public PlayerCache retrievePlayerCache(UUID uuid) {
        if (cacheMap.containsKey(uuid)) return cacheMap.get(uuid);
        PlayerCache playerCache = new PlayerCache(uuid);
        cacheMap.put(uuid, playerCache);
        return playerCache;
    }

    private void invalidatePlayerCache(UUID uuid) {
        cacheMap.remove(uuid);
    }
}
