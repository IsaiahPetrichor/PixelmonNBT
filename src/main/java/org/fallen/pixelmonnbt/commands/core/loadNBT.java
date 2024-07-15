package org.fallen.pixelmonnbt.commands.core;

import de.tr7zw.nbtapi.NBT;
import de.tr7zw.nbtapi.NBTFile;
import de.tr7zw.nbtapi.iface.ReadableNBT;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;

import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;

import static org.fallen.pixelmonnbt.utils.sendCommandReply.reply;

public class loadNBT {
    public loadNBT(CommandSender sender, String filename) {
        String fullFilePath = "plugins\\pixelmon-nbt\\" + filename + ".nbt";

        try {
            ReadableNBT nbtData = NBTFile.readFrom(new File(fullFilePath));

            int[] xLocations = nbtData.getIntArray("x");
            int[] yLocations = nbtData.getIntArray("y");
            int[] zLocations = nbtData.getIntArray("z");

            assert xLocations != null;
            assert yLocations != null;
            assert zLocations != null;

            if (xLocations.length != yLocations.length || yLocations.length != zLocations.length) {
                throw new IllegalStateException("location data corrupted, check file and try again...");
            }

            for (int i = 0; i < xLocations.length; i++) {
                Block block = sender.getServer().getWorlds().get(0).getBlockAt(
                        xLocations[i], yLocations[i], zLocations[i]);

                if (block.getType() != Material.getMaterial("PIXELMON_PIXELMON_SPAWNER")) {
                    String message = MessageFormat.format(
                            "Block at x: {0}, y: {1}, z: {2} is not a Pixelmon Spawner, attempting to change block into a spawner",
                            block.getX(), block.getY(), block.getZ());
                    reply(sender, message);

                    try {
                        block.setType(Material.getMaterial("PIXELMON_PIXELMON_SPAWNER"));
                    } catch (Exception e) {
                        reply(sender, "Failed to convert block into Pixelmon Spawner");
                    }
                }

                NBT.modify(block.getState(), nbt -> {
                    // wipe block
                    nbt.clearNBT();

                    // load file data
                    nbt.mergeCompound(nbtData);

                    // ensure location data gets added
                    nbt.setInteger("x", block.getX());
                    nbt.setInteger("y", block.getY());
                    nbt.setInteger("z", block.getZ());
                });

                reply(sender, block.toString());
            }

        } catch (IOException e) {
            reply(sender, "File not found...");
        } catch (AssertionError e) {
            reply(sender, "Failed to parse locations from nbt file...");
        } catch (IllegalStateException e) {
            reply(sender, e.toString());
        }

    }
}
