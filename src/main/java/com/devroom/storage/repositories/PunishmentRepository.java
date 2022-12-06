package com.devroom.storage.repositories;

import com.devroom.DevBans;
import com.devroom.player.PlayerCacheProvider;
import com.devroom.player.model.PlayerCache;
import com.devroom.punishments.Punishment;
import com.devroom.punishments.PunishmentFactory;
import com.devroom.punishments.PunishmentType;
import com.devroom.storage.mysql.ConnectionProvider;
import com.devroom.utils.logs.Log;
import com.devroom.utils.logs.LogType;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.NonNull;
import me.lucko.helper.Schedulers;
import me.lucko.helper.promise.Promise;
import org.bukkit.Bukkit;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class PunishmentRepository {

    public static String PUNISHMENTS_TABLE = "devroom_punishments";

    private static final String PUNISHMENTS_SCHEME = "CREATE TABLE IF NOT EXISTS " + PUNISHMENTS_TABLE +
            " (punishmentUUID varchar(36)" +
            ", punishedUUID varchar(36)" +
            ", punishmentType varchar(12)" +
            ", operator varchar(32)" +
            ", issueTimeStamp long" +
            ", duration long" +
            ", reason varchar(32)" +
            ", PRIMARY KEY(punishmentUUID))";

    private static final String PUNISHMENTS_INSERT = "INSERT INTO " + PUNISHMENTS_TABLE +
            " (punishmentUUID" +
            ", punishedUUID" +
            ", punishmentType" +
            ", operator" +
            ", issueTimeStamp" +
            ", duration" +
            ", reason)" +
            " VALUES (? , ? , ? , ? , ? , ? , ?)";

    private static final String PUNISHMENTS_SELECT = "SELECT * FROM " + PUNISHMENTS_TABLE +
            " WHERE punishedUUID=?";

    private static final Cache<UUID, Punishment> punishmentCache = Caffeine.newBuilder()
            .maximumSize(20_000)
            .expireAfterAccess(6, TimeUnit.HOURS)
            .build();


    public DevBans plugin;

    public PunishmentRepository(DevBans plugin) {
        this.plugin = plugin;
        createPunishmentsDatabase();
    }

    public void createPunishmentsDatabase() {
        Log.print(LogType.INFO, "Creating punishments table..");
        Schedulers.async().run(() -> {
            try (Connection connection = ConnectionProvider.getProductionConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(PUNISHMENTS_SCHEME)) {
                preparedStatement.execute();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }



    public static Promise<List<Punishment>> requestPunishmentList(@NonNull UUID playerUuid) {

        return Schedulers.async().supply(() -> {
            List<Punishment> punishments = new ArrayList<>();

            try (Connection connection = ConnectionProvider.getProductionConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(PUNISHMENTS_SELECT)) {
                preparedStatement.setString(1, playerUuid.toString());
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    String punishmentType = resultSet.getString("punishmentType");
                    UUID punishmentUUID = UUID.fromString(resultSet.getString("punishmentUUID"));
                    UUID punishedUUID = UUID.fromString(resultSet.getString("punishedUUID"));
                    String operator = resultSet.getString("operator");
                    long issueTimeStamp = resultSet.getLong("issueTimeStamp");
                    long duration = resultSet.getLong("duration");
                    String reason = resultSet.getString("reason");
                    Punishment punishment = PunishmentFactory.createPunishment(PunishmentType.valueOf(punishmentType),
                            punishedUUID, operator, issueTimeStamp ,duration, reason);
                    punishments.add(punishment);

                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return punishments;
        });
    }



   public static void insertPunishment(Punishment punishment) {
        Schedulers.async().run(() -> {
        try (Connection connection = ConnectionProvider.getProductionConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(PUNISHMENTS_INSERT)) {
            UUID generatedUuid = UUID.randomUUID();
            preparedStatement.setString(1, generatedUuid.toString());
            preparedStatement.setString(2, punishment.getPunishedUuid().toString());
            preparedStatement.setString(3, punishment.getPunishmentType().name());
            preparedStatement.setString(4, punishment.getOperator());
            preparedStatement.setLong(5, punishment.getCreationTimeStamp());
            preparedStatement.setLong(6, punishment.getDuration() == null ? -1 : punishment.getDuration().getSeconds());
            preparedStatement.setString(7, punishment.getReason());
            preparedStatement.execute();
            punishmentCache.put(generatedUuid, punishment);
            PlayerCache playerCache = PlayerCacheProvider.getPlayerCache(punishment.getPunishedUuid());

            playerCache.getPunishmentList().add(punishment);
            Schedulers.sync().run(playerCache::updateBanStatus);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        });
    }


}
