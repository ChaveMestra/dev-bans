package com.devroom.view;


import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;

public class GUIManager {

    public ArrayList<GUI> loadedGuis;


    public GUIManager() {
        loadedGuis = new ArrayList<>();
        rebuildGUIs();
        //init paginated


    }


    public void registerGUI(GUI gui) {
        loadedGuis.add(gui);
    }


    public void initializeGUI(GUI gui, Player player) {
        registerGUI(gui);
        rebuildGUIs();
        gui.open(player);
    }


    public void unregisterGui(GUI gui) {
        this.loadedGuis.remove(gui);
        rebuildGUIs();
    }

    public void autoRebuildGUIs() {
        for (GUI gui : loadedGuis) {
            gui.clearButtons();
            gui.setDefaultSetup();
        }
    }


    public void rebuildGUIs() {
        for (GUI gui : loadedGuis) {
            gui.clearButtons();
            gui.setDefaultSetup();
        }
    }


    public GUI getGUI(Inventory inventory) {
        for (GUI gui : loadedGuis) {
            if (gui.getInventory().equals(inventory)) {
                return gui;
            }
        }
        return null;
    }

}
