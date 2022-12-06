package com.devroom.commands;

import com.devroom.DevBans;
import com.devroom.player.PlayerCacheProvider;
import com.devroom.player.model.PlayerCache;
import com.devroom.punishments.Punishment;
import com.devroom.punishments.PunishmentFactory;
import com.devroom.punishments.PunishmentType;
import com.devroom.storage.repositories.PunishmentRepository;
import com.devroom.utils.time.TimeUtils;
import com.devroom.view.type.HistoryGUI;
import me.lucko.helper.Commands;
import me.lucko.helper.Schedulers;
import me.lucko.helper.terminable.TerminableConsumer;
import me.lucko.helper.terminable.module.TerminableModule;
import me.lucko.helper.text3.Text;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;
import java.util.Optional;

public class HistoryCommand implements TerminableModule {
    @Override
    public void setup(@Nonnull TerminableConsumer consumer) {
        Commands.create()
                .assertPlayer()
                .assertPermission("devbans.history")
                .assertUsage("<player>")
                .handler(cmd -> {
                    String playerName = cmd.arg(0).parseOrFail(String.class);
                    Schedulers.async().run(() -> {
                        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(playerName);
                        if (offlinePlayer == null) {
                            cmd.reply(Text.colorize("&cPlayer not found!"));
                            return;
                        }
                        PlayerCache playerCache = PlayerCacheProvider.getPlayerCache(offlinePlayer.getUniqueId());
                        Schedulers.sync().run(() -> {
                            DevBans.getInstance().getGuiModule().getGuiManager().initializeGUI(new HistoryGUI(1, playerCache), cmd.sender());
                        });
                    });



                }).registerAndBind(consumer, "history");
    }
}
