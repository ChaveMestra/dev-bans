package com.devroom.view.buttons.type;


import com.devroom.DevBans;
import com.devroom.view.GUI;
import com.devroom.view.buttons.Button;
import com.devroom.view.buttons.ButtonMechanics;
import me.lucko.helper.item.ItemStackBuilder;
import me.lucko.helper.text.Text;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;


public class PageButton extends Button implements ButtonMechanics {

  GUI gui;


  public PageButton(int slot, GUI gui) {
    super(ItemStackBuilder.of(Material.ARROW)
                    .name(Text.colorize("&f "))
            .lore(Text.colorize("&7Click to go to page #"+gui.getPage())).build(),
            slot);
    this.gui = gui;

  }

  @Override
  public void onClick(Player player, InventoryClickEvent inventoryClickEvent) {
    DevBans.getInstance().getGuiModule().getGuiManager().initializeGUI(gui, player);

  }
}

