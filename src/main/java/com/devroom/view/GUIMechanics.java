package com.devroom.view;

import org.bukkit.entity.Player;

public  interface GUIMechanics {
  void open(Player player);

  void onClose(Player player);

  void build();

  void setDefaultSetup();

}
