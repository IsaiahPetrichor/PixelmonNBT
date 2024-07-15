package org.fallen.pixelmonnbt.utils;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static org.fallen.pixelmonnbt.PixelmonNBT.GlobalLogger;

public class sendCommandReply {
    public static void reply(CommandSender sender, String message) {
        if (sender instanceof Player p) {
            p.sendMessage(message);
        } else {
            GlobalLogger.info(message);
        }
    }
}
