package com.devroom.commands;

import com.devroom.DevBans;
import com.devroom.player.PlayerCacheProvider;
import com.devroom.punishments.Punishment;
import com.devroom.punishments.PunishmentFactory;
import com.devroom.punishments.PunishmentType;
import com.devroom.punishments.model.BanPunishment;
import com.devroom.storage.repositories.PunishmentRepository;
import com.devroom.utils.time.TimeUtils;
import me.lucko.helper.Commands;
import me.lucko.helper.terminable.TerminableConsumer;
import me.lucko.helper.terminable.module.TerminableModule;
import me.lucko.helper.text3.Text;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;
import java.util.Optional;

public class BanCommand implements TerminableModule {
    @Override
    public void setup(@Nonnull TerminableConsumer consumer) {
        Commands.create()
                .assertPermission("devbans.ban")
                .assertUsage("<player> [duration] [reason]")
                .handler(cmd -> {
                    String playerName = cmd.arg(0).parseOrFail(String.class);
                    Player player = Bukkit.getPlayer(playerName);

                    if (player == null) {
                        cmd.reply(Text.colorize("&cPlayer not found!"));
                        return;
                    }

                    Optional<String> durationInput = cmd.arg(1).parse(String.class);
                    Optional<String> reasonInput = cmd.arg(2).parse(String.class);
                    long length = 0;

                    if (durationInput.isPresent()) {
                        length = TimeUtils.getSecondsLengthFromString(durationInput.get());
                        if (length < 0L) {
                            cmd.reply(Text.colorize("&c'" + durationInput.get() + "' is not a valid duration - Accepted examples: 10m, 1h, 5d, 2w, 6mo, etc."));
                            return;
                        }
                    }

                    Punishment punishment = PunishmentFactory.createPunishment(PunishmentType.BAN, player.getUniqueId(), cmd.sender().getName(), System.currentTimeMillis() ,length, reasonInput.orElse(null));
                    PunishmentRepository.insertPunishment(punishment);
                    cmd.reply(Text.colorize("&7The player &f"+player.getName()+" &7has been banned. &7Duration: &c"+punishment.getDurationDisplay()+"&7. Reason: &e"+punishment.getReason()));

                    if (DevBans.getInstance().getMessagesConfig().isPunishmentBroadcastEnabled()) {
                        Bukkit.broadcastMessage(Text.colorize(DevBans.getInstance().getMessagesConfig().getPunishmentMessage()
                                .replace("[player]", player.getName())
                                .replace("[past-punishment]", punishment.getPunishmentType().getPastReference())
                                .replace("[reason]", punishment.getReason())
                                .replace("[duration]", punishment.getDurationDisplay())));
                    }
                    //execute ban



                }).registerAndBind(consumer, "ban", "punish", "tempban");
    }
}
