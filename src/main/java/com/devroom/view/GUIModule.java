package com.devroom.view;


import com.devroom.DevBans;
import com.devroom.view.buttons.Button;
import lombok.Getter;
import me.lucko.helper.Events;
import me.lucko.helper.terminable.TerminableConsumer;
import me.lucko.helper.terminable.module.TerminableModule;
import org.bukkit.entity.Player;
import org.bukkit.event.EventPriority;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;

import javax.annotation.Nonnull;

@Getter
public class GUIModule implements TerminableModule {

    DevBans plugin;
    GUIManager guiManager;
    GUIUpdateTask guiUpdateTask;

    public GUIModule(DevBans plugin) {
        this.plugin = plugin;
        guiManager = new GUIManager();
        guiUpdateTask = new GUIUpdateTask();
        guiUpdateTask.runTaskTimer(plugin, 0, 20L);
    }

    @Override
    public void setup(@Nonnull TerminableConsumer consumer) {
        Events.subscribe(InventoryClickEvent.class, EventPriority.MONITOR)
                .filter(event -> event.getClickedInventory() != null)
                .filter(event -> this.guiManager.getGUI(event.getInventory()) != null)
                .filter(event -> event.getCurrentItem() != null)
                .handler(event -> {
                    for (GUI gui : plugin.getGuiModule().getGuiManager().loadedGuis) {
                        if (gui.getInventory().equals(event.getInventory())) {
                            for (Button button : gui.getButtons()) {
                                if (event.getCurrentItem().equals(button.getItemStack())) {
                                    button.onClick((Player) event.getWhoClicked(), event);
                                    event.setCancelled(true);
                                    DevBans.getInstance().getGuiModule().getGuiManager().rebuildGUIs();
                                    return;
                                }
                            }
                        }
                    }

                }).bindWith(consumer);

        Events.subscribe(InventoryCloseEvent.class, EventPriority.MONITOR)
                .filter(event -> event.getInventory() != null)
                .filter(event -> this.guiManager.getGUI(event.getInventory()) != null)
                .handler(event -> {
                    this.getGuiManager().getGUI(event.getInventory()).onClose((Player) event.getPlayer());

                }).bindWith(consumer);


    }

}
