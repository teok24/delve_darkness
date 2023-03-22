package net.teok.delvedarkness.networking.packet;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.PacketByteBuf;
import net.teok.delvedarkness.util.IEntityDataSaver;

public class DarknessSyncDataS2CPacket {
    public static void receive(MinecraftClient client, ClientPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender)
    {
        if (client.player != null)
            ((IEntityDataSaver) client.player).getPersistentData().putInt("darkness", buf.readInt());

    }
}
