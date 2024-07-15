package org.fallen.pixelmonnbt.commands;

import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.block.BlockState;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.fallen.pixelmonnbt.commands.core.loadNBT;
import org.fallen.pixelmonnbt.commands.core.saveNBT;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static org.fallen.pixelmonnbt.utils.sendCommandReply.reply;

public class pixelmonnbtRouterCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String[] subCommandsArr = {"help", "save", "load"};
        List<String> subCommands = Arrays.stream(subCommandsArr).toList();

        boolean invalidSubCommand = args.length > 0 && !subCommands.contains(args[0]);

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
            String commandSyntax = "/pixelmonnbt save <filename> <overwrite: true || false> <x?> <y?> <z?>";

            try {

                int argCount = args.length;
                if (argCount < 2) {
                    reply(sender, commandSyntax);
                }

                // Get filename
                String filename = "";
                if (argCount >= 2) {
                    filename = args[1];
                }

                // Get overwrite
                boolean overwrite = false;
                if (argCount >= 3) {
                    if (Objects.equals(args[2], "true") || Objects.equals(args[2], "false")) {

                        overwrite = Boolean.parseBoolean(args[2]);
                    } else {
                        throw new IllegalArgumentException();
                    }
                }

                // require location for server
                if (argCount < 4 && sender instanceof Server) {
                    reply(
                            sender,
                            "To run this from console, please provide NBT data location and world name.\nCommand syntax: " + commandSyntax);
                }

                BlockState blockState = null;

                if (argCount >= 6) {
                    int x;
                    int y;
                    int z;
                    try {
                        x = Integer.parseInt(args[3]);
                        y = Integer.parseInt(args[4]);
                        z = Integer.parseInt(args[5]);

                        blockState = sender.getServer().getWorlds().get(0).getBlockAt(x, y, z).getState();
                    } catch (Exception e) {
                        reply(sender, String.valueOf(e));
                    }
                } else {
                    try {
                        Player player = (Player) sender;
                        blockState = Objects.requireNonNull(player.getTargetBlockExact(30)).getState();
                    } catch (NullPointerException e) {
                        reply(sender, "Couldn't get target block");
                    }
                }

                new saveNBT(sender, blockState, filename, overwrite);

            } catch (IllegalArgumentException e) {
                reply(sender, ChatColor.YELLOW + "Command Syntax: " + commandSyntax);
            }
        } else if (args[0].equalsIgnoreCase("load")) {
            String commandSyntax = "/pixelmonnbt load <filename>";

            int argCount = args.length;
            if (argCount < 2) {
                reply(sender, commandSyntax);
            }

            // Get filename
            String filename = "";
            if (argCount >= 2) {
                filename = args[1];
            }

            new loadNBT(sender, filename);
        }

        return true;
    }
}
