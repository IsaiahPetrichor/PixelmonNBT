package org.fallen.pixelmonnbt.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

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
            // save NBT
            reply(sender, "not implemented yet");
        } else if (args[0].equalsIgnoreCase("load")) {
            // load NBT
            reply(sender, "not implemented yet");
        }

        return true;
    }
}
