package com.devroom.view.buttons.type;


import com.devroom.DevBans;
import com.devroom.punishments.Punishment;
import com.devroom.utils.time.TimeUtils;
import com.devroom.view.buttons.Button;
import com.devroom.view.buttons.ButtonMechanics;
import me.lucko.helper.item.ItemStackBuilder;
import me.lucko.helper.text.Text;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.Date;


public class PunishmentButton extends Button implements ButtonMechanics {


    public PunishmentButton(int slot, Punishment punishment) {
        super(ItemStackBuilder.of(Material.valueOf(DevBans.getInstance().getMenuConfig().getPunishItemMaterial()))
                .name(Text.colorize("&fPlayer Punishment"))
                .lore("",Text.colorize("&7Issue Date: &f"+ TimeUtils.dateStringFromTime(punishment.getCreationTimeStamp())),
                        Text.colorize("&7Type: &f"+punishment.getPunishmentType().getReference()),
                        Text.colorize("&7Operator: &f"+punishment.getOperator()),
                        Text.colorize("&7Expires in: &f"+(punishment.isExpired() ? "Expired" : punishment.isPermanent() ? "Never" : punishment.getTimeLeftDisplay())),
                        Text.colorize("&7Duration: &f"+(punishment.isPermanent() ? "Permanent" : punishment.getDurationDisplay())),
                        Text.colorize("&7Reason: &f"+punishment.getReason()))
                        .build(),
               slot);
    }

    @Override
    public void onClick(Player player, InventoryClickEvent inventoryClickEvent) {
    }
}

