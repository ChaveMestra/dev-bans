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


public class UnbanButton extends Button implements ButtonMechanics {


    public UnbanButton(int slot, Punishment punishment) {
        super(ItemStackBuilder.of(Material.valueOf(DevBans.getInstance().getMenuConfig().getUnbanItemMaterial()))
                .name(Text.colorize("&fPlayer Unban"))
                .lore("",Text.colorize("&7Issue Date: &f"+ TimeUtils.dateStringFromTime(punishment.getCreationTimeStamp())),
                        Text.colorize("&7Operator: &f"+punishment.getOperator()))
                        .build(),
               slot);
    }

    @Override
    public void onClick(Player player, InventoryClickEvent inventoryClickEvent) {
    }
}

