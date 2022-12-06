package com.devroom.view.type;


import com.devroom.DevBans;
import com.devroom.player.model.PlayerCache;
import com.devroom.punishments.Punishment;
import com.devroom.punishments.PunishmentType;
import com.devroom.view.GUI;
import com.devroom.view.GUIMechanics;
import com.devroom.view.buttons.type.PageButton;
import com.devroom.view.buttons.type.PunishmentButton;
import com.devroom.view.buttons.type.UnbanButton;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class HistoryGUI extends GUI implements GUIMechanics {


    PlayerCache playerCache;


    public HistoryGUI(int page, PlayerCache playerCache) {
        super(Bukkit.createInventory(null, 54, "History #" + page),
                page,
                52,
                new ArrayList<>());

        this.playerCache = playerCache;
    }

    @Override
    public void open(Player player) {
        player.openInventory(this.getInventory());
    }


    @Override
    public void build() {
        getInventory().clear();
        this.getButtons().forEach(button -> getInventory().setItem(button.getSlot(), button.getItemStack()));
    }

    @Override
    public void setDefaultSetup() {
        this.fillEdgesWithGlass((byte) DevBans.getInstance().getMenuConfig().getGuiEdgeGlassByte());
        int initialIndex, maxIndex = 0;
        initialIndex = this.getMaxButtons() * (this.getPage() - 1);
        maxIndex = initialIndex + this.getMaxButtons() + 1;
        List<Punishment> sorted = playerCache.getPunishmentList() != null ? new ArrayList<>(playerCache.getPunishmentList()) : new ArrayList<>();
        Collections.sort(sorted);
        for (int i = initialIndex; i < maxIndex; i++) {
            if (sorted.size() > i) {
                Punishment punishment = sorted.get(i);
                if (punishment != null) {
                    if (punishment.getPunishmentType() == PunishmentType.UNBAN) {
                        registerButton(new UnbanButton(getFirstAvailableSlot(), punishment));

                    } else {
                        registerButton(new PunishmentButton(getFirstAvailableSlot(), punishment));
                    }
                }
            }
        }

        this.fillWithGlass((byte) DevBans.getInstance().getMenuConfig().getGuiFillGlassByte());

        if (this.getPage() > 1) {
            registerButton(new PageButton(45, new HistoryGUI(this.getPage() - 1, this.playerCache)));
        }
        registerButton(new PageButton(53, new HistoryGUI(this.getPage() + 1, this.playerCache)));
    }
}
