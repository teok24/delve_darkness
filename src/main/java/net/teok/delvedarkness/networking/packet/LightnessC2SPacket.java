package net.teok.delvedarkness.networking.packet;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.teok.delvedarkness.util.DarknessData;
import net.teok.delvedarkness.util.IEntityDataSaver;


public class LightnessC2SPacket {
    public static void receive(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender)
    {
        DarknessData.removeDarkness(((IEntityDataSaver) player), 1);
    }
}
