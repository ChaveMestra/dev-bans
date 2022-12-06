package com.devroom.view;



import com.devroom.DevBans;
import com.devroom.view.buttons.Button;
import com.devroom.view.buttons.type.GlassButton;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.List;

@AllArgsConstructor
@Getter
public abstract class GUI implements GUIMechanics {

    @Setter
    Inventory inventory;
    int page;
    int maxButtons;
    List<Button> buttons;

    public void registerButton(Button button) {
        this.buttons.add(button);
        build();
    }

    public void unregisterButton(Button button) {
        this.buttons.remove(button);
        build();
    }

    public int getFirstAvailableSlot() {
        for (int i = 0; i < this.inventory.getSize(); i++) {
            boolean isOccupied = false;
            for (Button button : this.buttons) {
                if (button.getSlot() == i) {
                    isOccupied = true;
                    break;
                }
            }
            if (!isOccupied) {
                return i;
            }
        }

        return -1;
    }

    public void fillEdgesWithGlass(byte data) {
        if (this.getInventory().getSize() == 9 || this.getInventory().getSize() == 18) return;
        for (int i = 0; i < 9; i++) {
            registerButton(new GlassButton(i, data));
        }
        for (int i = this.getInventory().getSize(); i > this.getInventory().getSize() - 9; i--) {
            registerButton(new GlassButton(i - 1, data));
        }
        if (this.getInventory().getSize() >= 27) {
            registerButton(new GlassButton(9, data));
            registerButton(new GlassButton(9 + 8, data));
        }
        if (this.getInventory().getSize() >= 36) {
            registerButton(new GlassButton(18, data));
            registerButton(new GlassButton(18 + 8, data));
        }
        if (this.getInventory().getSize() >= 45) {
            registerButton(new GlassButton(27, data));
            registerButton(new GlassButton(27 + 8, data));
        }
        if (this.getInventory().getSize() >= 54) {
            registerButton(new GlassButton(36, data));
            registerButton(new GlassButton(36 + 8, data));
        }

    }

    public void fillWithGlass(byte data) {
        while (getFirstAvailableSlot() != -1) {
            registerButton(new GlassButton(getFirstAvailableSlot(), data));
        }
    }

    public void onClose(Player player) {
        DevBans.getInstance().getGuiModule().getGuiManager().unregisterGui(this);
    }

    public void unregisterButton(int slot) {
        buttons.removeIf(button -> button.getSlot() == slot);
    }

    public void clearButtons() {
        this.inventory.clear();

        this.buttons.clear();
    }

    public void registerButtonCoppies(List<Button> buttons) {
        buttons.forEach(this::registerButton);
    }




}