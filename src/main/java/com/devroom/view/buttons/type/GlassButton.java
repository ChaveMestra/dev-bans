package com.devroom.view.buttons.type;


import com.devroom.view.buttons.Button;
import com.devroom.view.buttons.ButtonMechanics;
import me.lucko.helper.item.ItemStackBuilder;
import me.lucko.helper.text.Text;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;


public class GlassButton extends Button implements ButtonMechanics {


    public GlassButton(int slot, byte data) {
        super(ItemStackBuilder.of(new ItemStack(Material.STAINED_GLASS_PANE, 1, data))
                .name(Text.colorize("&f ")).build(),
               slot);
    }

    @Override
    public void onClick(Player player, InventoryClickEvent inventoryClickEvent) {
    }
}

