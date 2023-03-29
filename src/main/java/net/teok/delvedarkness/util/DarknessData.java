package net.teok.delvedarkness.util;

import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.teok.delvedarkness.DelveDarkness;
import net.teok.delvedarkness.networking.ModMessages;

public class DarknessData {
    public static int addDarknessImmunity(IEntityDataSaver player, int amount)
    {
        NbtCompound nbt = player.getPersistentData();
        int darknessImmunity = nbt.getInt("darknessImmunity");
        if (darknessImmunity + amount >= DelveDarkness.config.darknessImmunity() * 20){ //config is in seconds which we multiply by the number of ticks in a second
            darknessImmunity = DelveDarkness.config.darknessImmunity() * 20;

            nbt.putInt("darknessImmunity", darknessImmunity);
            return darknessImmunity;
        }
        else {
            darknessImmunity += amount;
            nbt.putInt("darknessImmunity", darknessImmunity);
        }
        //syncDarknessImmunity(darknessImmunity,(ServerPlayerEntity)player);
        return darknessImmunity;
    }
     public static int removeDarknessImmunity(IEntityDataSaver player, int amount)
    {
        NbtCompound nbt = player.getPersistentData();
        int darknessImmunity = nbt.getInt("darknessImmunity");
        if (darknessImmunity - amount <= 0){
            darknessImmunity = 0;
            nbt.putInt("darknessImmunity", darknessImmunity);
            return darknessImmunity;
        }
        else {
            darknessImmunity -= amount;
            nbt.putInt("darknessImmunity", darknessImmunity);
        }
        //syncDarknessImmunity(darknessImmunity,((ServerPlayerEntity)player));
        return darknessImmunity;
    }

    public static int addDarkness(IEntityDataSaver player, int amount)
    {
        NbtCompound nbt = player.getPersistentData();
        int darkness = nbt.getInt("darkness");
        if (darkness + amount >= DelveDarkness.config.levels.maxDarknessLevels()) //if darkness is at or greater than the amount, return without sending packets
        {
            darkness = DelveDarkness.config.levels.maxDarknessLevels(); //control by config
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
        if (darkness - amount <= DelveDarkness.config.levels.minDarknessLevels())
        {
            darkness = DelveDarkness.config.levels.minDarknessLevels();
            nbt.putInt("darkness", darkness);
            return darkness;
        }
        else
        {
            darkness -= amount;
            nbt.putInt("darkness", darkness);
        }

        //sync data
        syncDarkness(darkness, ((ServerPlayerEntity) player));
        return darkness;
    }
    public static void resetDarkness(IEntityDataSaver player)
    {
        NbtCompound nbt = player.getPersistentData();
        int darkness = DelveDarkness.config.levels.minDarknessLevels();
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
