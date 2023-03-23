package net.teok.delvedarkness.networking.packet;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.teok.delvedarkness.util.DarknessDamageSource;
import net.teok.delvedarkness.util.DarknessData;
import net.teok.delvedarkness.util.IEntityDataSaver;


public class DarknessC2SPacket {

    public static void receive(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender)
    {

        var darknessTick = buf.readInt();
        if (darknessTick % 20 != 0) return; // %20 is 1 second, should be controlled by config
        DarknessData.addDarkness(((IEntityDataSaver) player),1);
        var darkness = ((IEntityDataSaver) player).getPersistentData().getInt("darkness");
        //player.sendMessage(Text.of("Darkness: " + darkness), true);
        player.damage(DarknessDamageSource.DARKNESS, 4); //2 should be a multiplier in config

    }
}
