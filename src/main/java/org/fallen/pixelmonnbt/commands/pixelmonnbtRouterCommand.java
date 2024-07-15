package org.fallen.pixelmonnbt.commands;

import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;

import static org.fallen.pixelmonnbt.utils.sendCommandReply.reply;

public class pixelmonnbtRouterCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String[] subCommandsArr = {"help", "save", "load"};
        List<String> subCommands = Arrays.stream(subCommandsArr).toList();

        boolean invalidSubCommand = args.length > 0 && !subCommands.contains(args[0].toLowerCase());

        if (args.length < 1) {
            reply(sender, command.getUsage());
        } else if (invalidSubCommand || args[0].equalsIgnoreCase("help")) {
            if (invalidSubCommand) {
                String message = ChatColor.RED + "Invalid sub-command!\n";
                reply(sender, message);
            }
            String header = ChatColor.WHITE + "" + ChatColor.BOLD + "----------------------------------\n" + ChatColor.AQUA + "" + ChatColor.BOLD + "Available sub-commands:\n";
            String content = ChatColor.YELLOW + subCommands.toString();
            String footer = ChatColor.WHITE + "" + ChatColor.BOLD + "\n----------------------------------";

            String info = header + content + footer;

            reply(sender, info);

        } else if (args[0].equalsIgnoreCase("save")) {
            // ex. /pixelmonnbt save Route11_LAND false
            String commandSyntax = "/pixelmonnbt <action: save||load> <filename> <overwrite: true||false> <x?> <y?> <z?>";

            // Get filename
            String filename = "";
            if (!args[1].isEmpty()) {
                filename = args[1];
            }

            // Get overwrite
            boolean overwrite = false;
            if (!args[2].isEmpty()) {
                try {
                    overwrite = Boolean.parseBoolean(args[2]);
                } catch (Exception ex) {
                    reply(sender, "invalid argument, command syntax: " + commandSyntax);
                }
            }

            // require location for server
            if (args[3].isEmpty() && sender instanceof Server) {
                reply(
                        sender,
                        "To run this from console, please provide NBT data location.\nCommand syntax: " + commandSyntax);
            }

            if (!args[3].isEmpty()) {
                int x;
                int y;
                int z;
                try {
                    x = Integer.parseInt(args[3]);
                    y = Integer.parseInt(args[4]);
                    z = Integer.parseInt(args[5]);
                } catch (Exception ex) {
                    reply(sender, String.valueOf(ex));
                }
            } else {
                Player player = (Player) sender;
                reply(sender, player.getEyeLocation().toString());
            }

            // save NBT
            //            new saveNBT();
        } else if (args[0].equalsIgnoreCase("load")) {
            // load NBT
            reply(sender, "not implemented yet");
        }

        return true;
    }
}
