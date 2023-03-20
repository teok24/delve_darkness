package net.teok.delvedarkness.event;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.LightType;
import net.minecraft.world.World;
import net.teok.delvedarkness.networking.ModMessages;


public class LightChangeHandler {
    public static int monsterSpawnBlockLightLimit;
    public static int darknessTick;

    public static void registerLightChange()
    {

        ClientTickEvents.START_CLIENT_TICK.register(client -> {
            if (client.player != null) {
                //
                if (darknessTick % 20 == 0)
                {
                    if (client.player.isCreative() || client.player.isSpectator()) return;
                    if (isDark(client.world, client.player.getBlockPos())) {
                        ClientPlayNetworking.send(ModMessages.DARKNESS_ID, PacketByteBufs.create());
                    } else {
                        ClientPlayNetworking.send(ModMessages.LIGHTNESS_ID, PacketByteBufs.create());
                    }
                }

                darknessTick++;
            }
        });
    }
    public static boolean isDark(World world, BlockPos pos)
    {
        int light = isDay(world, pos);
        light += world.getLightLevel(LightType.BLOCK, pos);
        if (light <= 4)
        {
            return true;
        }
        return false;
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
