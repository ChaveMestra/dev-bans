package com.devroom.punishments;

import com.devroom.punishments.model.BanPunishment;
import com.devroom.punishments.model.UnbanPunishment;

import javax.annotation.Nullable;
import java.time.Duration;
import java.util.UUID;

public class PunishmentFactory {

    public static Punishment createPunishment(PunishmentType punishmentType ,UUID punishedUuid,  String operator, long issueTimeStamp ,long lenght, @Nullable String reason) {
        switch (punishmentType) {
            case WARN: {
                //todo;
            }
            case UNBAN: {
                return new UnbanPunishment(punishedUuid, operator, issueTimeStamp);
            }

            default:
            case BAN: {
                return new BanPunishment(punishmentType, punishedUuid, operator, issueTimeStamp, Duration.ofSeconds(lenght), reason);
            }
        }
    }
}
