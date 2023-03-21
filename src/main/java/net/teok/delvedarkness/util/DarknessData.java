package net.teok.delvedarkness.util;

import net.minecraft.nbt.NbtCompound;

public class DarknessData {
    public static float addDarkness(IEntityDataSaver player, int amount)
    {
        NbtCompound nbt = player.getPersistentData();
        int darkness = nbt.getInt("darkness");
        if (darkness + amount >= 10)
        {
            darkness = 8; //control by config
        }
        else
        {
            darkness += amount;
        }
        nbt.putInt("darkness", darkness);
        //sync data
        return darkness;
    }
    public static float removeDarkness(IEntityDataSaver player, int amount)
    {
        NbtCompound nbt = player.getPersistentData();
        int darkness = nbt.getInt("darkness");
        if (darkness - amount <= 0)
        {
            darkness = 0;
        }
        else
        {
            darkness -= amount;
        }
        nbt.putInt("darkness", darkness);
        //sync data
        return darkness;
    }
    public static void resetDarkness(IEntityDataSaver player)
    {
        NbtCompound nbt = player.getPersistentData();
        int darkness = nbt.getInt("darkness");
        darkness = 0;
        nbt.putInt("darkness", darkness);
    }
}
