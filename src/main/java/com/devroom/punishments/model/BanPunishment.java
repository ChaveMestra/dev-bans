package com.devroom.punishments.model;

import com.devroom.punishments.Punishment;
import com.devroom.punishments.PunishmentType;

import javax.annotation.Nullable;
import java.time.Duration;
import java.util.UUID;

public class BanPunishment extends Punishment {

    public BanPunishment(PunishmentType punishmentType, UUID punishedUuid, String operator, long creationTimeStamp, @Nullable Duration duration, @Nullable String reason) {
        super(punishmentType, punishedUuid, operator, creationTimeStamp, duration, reason);
    }
}
