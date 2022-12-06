package com.devroom.player.model;

import com.devroom.DevBans;
import com.devroom.punishments.Punishment;
import com.devroom.punishments.PunishmentType;
import com.devroom.storage.repositories.PunishmentRepository;
import lombok.Getter;
import me.lucko.helper.text3.Text;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
@Getter
public class PlayerCache {

    private UUID uuid;
    private List<Punishment> punishmentList = new ArrayList<>();
    boolean banned;
    private Punishment activeBanPunishment;

    public PlayerCache(UUID uuid, List<Punishment> punishmentList) {
        this.uuid = uuid;
        this.punishmentList = punishmentList;
    }

    public PlayerCache(UUID uuid) {
        this.uuid = uuid;
        PunishmentRepository.requestPunishmentList(uuid).thenAcceptSync(list -> {
            this.punishmentList = list;
            updateBanStatus();
        });
    }

    public void updateBanStatus() {
        if (this.punishmentList == null || this.punishmentList.size() == 0) return;
        List<Punishment> sorted = new ArrayList<>(this.getPunishmentList());
        Collections.sort(sorted);
        for (int i = 0; i < sorted.size();i++) {
            Punishment currentPunishment = sorted.get(i);
            if (currentPunishment.getPunishmentType() == PunishmentType.UNBAN && i == 0) {
                //unbanned
                return;
            }

            if (currentPunishment.getPunishmentType() == PunishmentType.BAN && !currentPunishment.isExpired()) {
                banned = true;
                activeBanPunishment = currentPunishment;
                kickPlayer();
                return;
            }
        }
    }

    public void kickPlayer() {
        Player player = Bukkit.getPlayer(this.getUuid());
        if (player == null) return;
        player.kickPlayer(Text.colorize(DevBans.getInstance().getMessagesConfig().getPunishmentMessage()
                .replace("[past-punishment]", activeBanPunishment.getPunishmentType().getPastReference())
                .replace("[type]", activeBanPunishment.getPunishmentType().getReference())
                .replace("[duration]", activeBanPunishment.getTimeLeftDisplay())
                .replace("[reason]", activeBanPunishment.getReason())));
    }
}
