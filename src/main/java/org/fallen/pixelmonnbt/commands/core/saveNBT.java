package org.fallen.pixelmonnbt.commands.core;

import de.tr7zw.nbtapi.NBT;
import de.tr7zw.nbtapi.NBTFile;
import org.bukkit.block.BlockState;

import java.io.File;
import java.io.IOException;

import static org.fallen.pixelmonnbt.PixelmonNBT.GlobalLogger;

public class saveNBT {
    public saveNBT(BlockState blockState, String filename, boolean overwrite) {
        String nbtData = NBT.get(blockState, nbt -> {
            try {
                NBTFile file = new NBTFile(new File("plugins/pixelmon-nbt", "spawnerArea_spawnerDetails.nbt"));
                file.mergeCompound(nbt);
                file.save();
                GlobalLogger.info("File saved.");
            } catch (IOException e) {
                GlobalLogger.warning("Failed to save NBT Data to file...");
            }
            return nbt.toString();
        });
        GlobalLogger.info(nbtData);
    }
}
