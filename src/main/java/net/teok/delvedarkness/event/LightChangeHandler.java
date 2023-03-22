package net.teok.delvedarkness.event;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.LightType;
import net.minecraft.world.World;
import net.teok.delvedarkness.networking.ModMessages;
import net.teok.delvedarkness.util.DarknessData;
import net.teok.delvedarkness.util.IEntityDataSaver;


public class LightChangeHandler {
    public static int darkTick = 19; //should be 1 tick less than config doDamageInSeconds
    public static int timeUntilDamage = 100; //5 seconds (100 ticks) default time until damage starts ticking
    public static int tick = 0;

    public static void registerLightChange()
    {

        ClientTickEvents.START_CLIENT_TICK.register(client -> {
            if (client.player != null) {
                //
                PacketByteBuf packet = PacketByteBufs.create();
                packet.writeInt(darkTick);
                if (client.player.isCreative() || client.player.isSpectator() || client.isPaused()) return;
                if (isDark(client.world, client.player.getBlockPos())) {
                    DarknessData.removeDarknessImmunity(((IEntityDataSaver) client.player), 1); //darkness immunity tick
                    //spawn particles
                    //client.world.addParticle(ParticleTypes.ASH, client.player.getX(),client.player.getY(),client.player.getZ(),0,0,0);
                    if (((IEntityDataSaver) client.player).getPersistentData().getInt("darknessImmunity") <= 0)
                    {
                        darkTick ++;
                    }
                    ClientPlayNetworking.send(ModMessages.DARKNESS_ID, packet);
                } else {
                    DarknessData.addDarknessImmunity(((IEntityDataSaver) client.player), 5); //number should be configurable for speed
                    darkTick = 19;
                    ClientPlayNetworking.send(ModMessages.LIGHTNESS_ID, packet);
                }
                //client.player.sendMessage(Text.of("Immunity: " + ((IEntityDataSaver)client.player).getPersistentData().getInt("darknessImmunity")),true);
            }
        });
    }
    public static boolean isDark(World world, BlockPos pos)
    {
        int light = isDay(world, pos);
        light += world.getLightLevel(LightType.BLOCK, pos);
        return light <= 2;//light level for darkness, should be controlled by config
    }

    public static int isDay(World world, BlockPos pos)
    {
        int i = world.getLightLevel(LightType.SKY, pos) - world.getAmbientDarkness();
        float f = world.getSkyAngleRadians(1.0f);
        float g = f < (float)Math.PI ? 0.0f : (float)Math.PI * 2;
        f += (g - f) * 0.2f;
        i = Math.round((float)i * MathHelper.cos(f));

        i = MathHelper.clamp(i, 0, 15);
        return i;
    }
    public static void register(){
        registerLightChange();
    }
}
