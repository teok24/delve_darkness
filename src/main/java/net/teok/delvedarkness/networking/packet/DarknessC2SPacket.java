package net.teok.delvedarkness.networking.packet;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.teok.delvedarkness.DelveDarkness;
import net.teok.delvedarkness.config.DelveDarknessConfigModel;
import net.teok.delvedarkness.util.DarknessData;
import net.teok.delvedarkness.util.IEntityDataSaver;
import net.teok.delvedarkness.util.ModDamageSources;


public class DarknessC2SPacket {

    public static void receive(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender)
    {

        var darknessTick = buf.readInt();
        if (darknessTick % 20 != 0) return; // %20 is 1 second, should be controlled by config
        DarknessData.addDarkness(((IEntityDataSaver) player),1);
        if (DelveDarkness.config.damageTypeSetting() == DelveDarknessConfigModel.DamageTypeSetting.FLAT)
        {
            player.damage(ModDamageSources.of(player.world, ModDamageSources.DARKNESS), DelveDarkness.config.flat.darknessDamage()); //if player is using flat damage, do this
        } else {
            player.damage(ModDamageSources.of(player.world, ModDamageSources.DARKNESS), ((IEntityDataSaver) player).getPersistentData().getInt("darkness") * DelveDarkness.config.levels.darknessMultiplier()); //if player is using darkness stat for damage
        }


    }
}
