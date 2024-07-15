package org.fallen.pixelmonnbt.utils;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static org.fallen.pixelmonnbt.PixelmonNBT.GlobalLogger;

public class sendCommandReply {
    public static void reply(CommandSender sender, String message) {
        String PluginPrefix = ChatColor.WHITE + "[" + ChatColor.AQUA + "PixelmonNBT" + ChatColor.WHITE + "]: ";

        if (sender instanceof Player p) {
            p.sendMessage(PluginPrefix + message);
        } else {
            GlobalLogger.info(message);
        }
    }
}
