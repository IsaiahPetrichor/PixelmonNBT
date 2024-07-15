package org.fallen.pixelmonnbt;

import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginLogger;
import org.bukkit.plugin.java.JavaPlugin;
import org.fallen.pixelmonnbt.commands.pixelmonnbtRouterCommand;

import java.util.logging.Logger;

public final class PixelmonNBT extends JavaPlugin implements Listener {
    // Create Logger
    public static Logger GlobalLogger = PluginLogger.getLogger("PixelmonNBT");

    @Override
    public void onEnable() {
        // Register Commands
        getCommand("pixelmonnbt").setExecutor(new pixelmonnbtRouterCommand());
        // TODO: load command
    }
}
