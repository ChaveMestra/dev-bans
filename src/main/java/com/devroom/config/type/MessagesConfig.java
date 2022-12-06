package com.devroom.config.type;

import lombok.Getter;
import lombok.ToString;
import me.lucko.helper.config.objectmapping.Setting;
import me.lucko.helper.config.objectmapping.serialize.ConfigSerializable;

@ConfigSerializable
@Getter
@ToString
public class MessagesConfig {

    @Setting("punishment-message")
    public String punishmentMessage = "&cYou are [past-punishment]! Get in touch to review your [type]. Expires in: &f[duration] Reason: [reason]";

    @Setting("punishment-broadcast")
    public String punishmentBroadcast = "&cThe player &f[player]&c has been [past-punishment] for [reason], duration: [duration]";

    @Setting("punishment-broadcast-enabled")
    public boolean punishmentBroadcastEnabled = true;
}
