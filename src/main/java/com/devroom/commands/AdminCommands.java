package com.devroom.commands;

import com.devroom.DevBans;
import me.lucko.helper.Commands;
import me.lucko.helper.terminable.TerminableConsumer;
import me.lucko.helper.terminable.module.TerminableModule;
import me.lucko.helper.text3.Text;

import javax.annotation.Nonnull;

public class AdminCommands implements TerminableModule {
    @Override
    public void setup(@Nonnull TerminableConsumer consumer) {
        Commands.create()
                .assertPermission("devbans.admin")
                .handler(cmd -> {

                    if (cmd.args().size() == 0) {
                        cmd.reply(Text.colorize("&6&lDEVROOM BANS COMMANDS"));
                        cmd.reply(Text.colorize("&8◆ &7/devbans reload &8&o(Reloads config)"));
                        cmd.reply(Text.colorize("&8◆ &7/ban <player> [duration] [reason] &8&o(Ban player)"));
                        cmd.reply(Text.colorize("&8◆ &7/unban <player> &8&o(Unban player)"));
                        cmd.reply(Text.colorize("&8◆ &7/history <player> &8&o(Shows history)"));

                        return;
                    }

                    String arg = cmd.arg(0).parseOrFail(String.class);

                    if (arg.equalsIgnoreCase("reload")) {
                        DevBans.getInstance().getConfigController().reloadConfigs();
                        return;
                    }

                }).registerAndBind(consumer, "devbans");
    }
}
