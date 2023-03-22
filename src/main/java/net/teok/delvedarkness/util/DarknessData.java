package net.teok.delvedarkness.util;

import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.teok.delvedarkness.networking.ModMessages;

public class DarknessData {
    public static int addDarkness(IEntityDataSaver player, int amount)
    {
        NbtCompound nbt = player.getPersistentData();
        int darkness = nbt.getInt("darkness");
        if (darkness + amount >= 8) //if darkness is at or greater than the amount, return without sending packets
        {
            darkness = 8; //control by config
            nbt.putInt("darkness", darkness);
            return darkness;
        }
        else
        {
            darkness += amount;
            nbt.putInt("darkness", darkness);
        }
        //sync data
        syncDarkness(darkness, ((ServerPlayerEntity) player));
        return darkness;
    }
    public static int removeDarkness(IEntityDataSaver player, int amount)
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
        syncDarkness(darkness, ((ServerPlayerEntity) player));
        return darkness;
    }
    public static void resetDarkness(IEntityDataSaver player)
    {
        NbtCompound nbt = player.getPersistentData();
        int darkness = 0;
        nbt.putInt("darkness", darkness);
        syncDarkness(darkness, ((ServerPlayerEntity) player));
    }
    public static void syncDarkness(int darkness, ServerPlayerEntity player)
    {
        PacketByteBuf packet = PacketByteBufs.create();
        packet.writeInt(darkness);
        ServerPlayNetworking.send(player, ModMessages.DARKNESS_SYNC_ID, packet);
    }
}
