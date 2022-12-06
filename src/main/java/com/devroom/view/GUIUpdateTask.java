package com.devroom.view;


import com.devroom.DevBans;
import org.bukkit.scheduler.BukkitRunnable;

public class GUIUpdateTask extends BukkitRunnable {
  @Override
  public void run() {
    DevBans.getInstance().getGuiModule().getGuiManager().autoRebuildGUIs();
  }
}
