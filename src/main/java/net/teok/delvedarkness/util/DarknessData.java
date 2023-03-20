package net.teok.delvedarkness.util;

import net.minecraft.nbt.NbtCompound;

public class DarknessData {
    public static float addDarkness(IEntityDataSaver player, float amount)
    {
        NbtCompound nbt = player.getPersistentData();
        float darkness = nbt.getFloat("darkness");
        if (darkness + amount >= 5)
        {
            darkness = 5;
        }
        else
        {
            darkness += amount;
        }
        nbt.putFloat("darkness", darkness);
        //sync data
        return darkness;
    }
    public static float removeDarkness(IEntityDataSaver player, float amount)
    {
        NbtCompound nbt = player.getPersistentData();
        float darkness = nbt.getFloat("darkness");
        if (darkness - amount <= 0)
        {
            darkness = 0;
        }
        else
        {
            darkness -= amount;
        }
        nbt.putFloat("darkness", darkness);
        //sync data
        return darkness;
    }
    public static void resetDarkness(IEntityDataSaver player)
    {
        NbtCompound nbt = player.getPersistentData();
        float darkness = nbt.getFloat("darkness");
        darkness = 0;
        nbt.putFloat("darkness", darkness);
    }
}
