package com.devroom.punishments;

import com.avaje.ebean.validation.NotNull;
import com.devroom.utils.time.TimeUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.annotation.Nullable;
import java.time.Duration;
import java.util.Date;
import java.util.UUID;

@AllArgsConstructor
@Getter
public abstract class Punishment implements Comparable<Punishment> {

    private PunishmentType punishmentType;
    private UUID punishedUuid;
    private String operator;
    private long creationTimeStamp;
    private Duration duration;
    @Nullable
    private String reason;

    public boolean isPermanent() {
        return duration.isNegative() || duration.isZero();
    }

    public boolean isExpired() {
        return !isPermanent() && creationTimeStamp + duration.toMillis() < System.currentTimeMillis();
    }

    public String getDurationDisplay() {
        return TimeUtils.rawTimeLeft(new Date(creationTimeStamp), new Date(creationTimeStamp + duration.toMillis()));
    }

    public String getTimeLeftDisplay() {
        return TimeUtils.rawTimeLeft(new Date(System.currentTimeMillis()), new Date(creationTimeStamp + duration.toMillis()));
    }

    @Override
    public int compareTo(Punishment punishment) {
        return Long.compare(punishment.getCreationTimeStamp(), this.getCreationTimeStamp());
    }

    public String getReason() {
       return this.reason == null ? "N/A" : this.reason;
    }



}
