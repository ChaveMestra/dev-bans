package com.devroom.view.buttons;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.inventory.ItemStack;

@AllArgsConstructor
@Getter
public abstract class Button implements ButtonMechanics {

  @Setter
  private ItemStack itemStack;
  int slot;

}