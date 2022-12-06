package com.devroom.punishments;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum PunishmentType {

    BAN("ban", "banned"), WARN("warn", "warned"), UNBAN("unban", "unbanned");

    private String reference;
    private String pastReference;

}
