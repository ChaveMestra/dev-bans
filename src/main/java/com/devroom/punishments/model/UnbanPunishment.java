package com.devroom.punishments.model;

import com.devroom.punishments.Punishment;
import com.devroom.punishments.PunishmentType;

import javax.annotation.Nullable;
import java.time.Duration;
import java.util.UUID;

public class UnbanPunishment extends Punishment {

    public UnbanPunishment(UUID punishedUuid, String operator, long creationTimeStamp) {
        super(PunishmentType.UNBAN, punishedUuid, operator, creationTimeStamp, Duration.ZERO, null);
    }
}
