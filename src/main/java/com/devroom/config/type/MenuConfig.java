package com.devroom.config.type;

import lombok.Getter;
import lombok.ToString;
import me.lucko.helper.config.objectmapping.Setting;
import me.lucko.helper.config.objectmapping.serialize.ConfigSerializable;

@ConfigSerializable
@Getter
@ToString
public class MenuConfig {

    @Setting("punishment-item-material")
    public String punishItemMaterial = "NAME_TAG";

    @Setting("unban-item-material")
    public String unbanItemMaterial = "BARRIER";

    @Setting("gui-edges-glass-color-byte")
    public int guiEdgeGlassByte = 7;

    @Setting("gui-fill-glass-color-byte")
    public int guiFillGlassByte = 15;
}
