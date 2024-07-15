package org.fallen.pixelmonnbt.commands.core;

import de.tr7zw.nbtapi.NBT;
import de.tr7zw.nbtapi.NBTFile;
import org.bukkit.block.BlockState;
import org.bukkit.command.CommandSender;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static org.fallen.pixelmonnbt.utils.sendCommandReply.reply;

public class saveNBT {
    public saveNBT(CommandSender sender, BlockState blockState, String filename, boolean overwrite) {
        if (blockState == null) {
            reply(sender, "Could not save NBT: null block state");
        } else if (!blockState.getType().toString().equalsIgnoreCase("PIXELMON_PIXELMON_SPAWNER")) {
            reply(sender, "Failed to save NBT: Not a Pixelmon Spawner");
        } else {
            String fullFilePath = "plugins\\pixelmon-nbt\\" + filename + ".nbt";

            NBT.modify(blockState, nbt -> {
                try {
                    NBTFile file = new NBTFile(new File(fullFilePath));

                    if (overwrite) {
                        file.clearNBT();
                    }

                    ArrayList<Integer> xLocations = new ArrayList<>();
                    ArrayList<Integer> yLocations = new ArrayList<>();
                    ArrayList<Integer> zLocations = new ArrayList<>();

                    if (!file.getKeys().isEmpty()) {
                        int[] xLocationsInFile = GetLocationsFromFile(file, "x");
                        int[] yLocationsInFile = GetLocationsFromFile(file, "y");
                        int[] zLocationsInFile = GetLocationsFromFile(file, "z");

                        for (int i : xLocationsInFile) {
                            xLocations.add(i);
                        }
                        for (int i : yLocationsInFile) {
                            yLocations.add(i);
                        }
                        for (int i : zLocationsInFile) {
                            zLocations.add(i);
                        }
                    }

                    xLocations.add(blockState.getX());
                    yLocations.add(blockState.getY());
                    zLocations.add(blockState.getZ());

                    nbt.setIntArray("x", xLocations.stream().mapToInt(i -> i).toArray());
                    nbt.setIntArray("y", yLocations.stream().mapToInt(i -> i).toArray());
                    nbt.setIntArray("z", zLocations.stream().mapToInt(i -> i).toArray());

                    file.mergeCompound(nbt);
                    file.save();
                    reply(sender, "File saved to " + sender.getServer().getWorldContainer().getPath() + fullFilePath);
                } catch (IOException e) {
                    reply(sender, "Failed to save NBT Data to file...\n" + e);
                }
            });
        }
    }

    public int[] GetLocationsFromFile(NBTFile file, String key) {
        int[] locations = file.getIntArray(key);

        return locations;
    }
}
