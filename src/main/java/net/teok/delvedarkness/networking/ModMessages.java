package net.teok.delvedarkness.networking;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.util.Identifier;
import net.teok.delvedarkness.DelveDarkness;
import net.teok.delvedarkness.networking.packet.DarknessC2SPacket;
import net.teok.delvedarkness.networking.packet.LightnessC2SPacket;

public class ModMessages {
    public static final Identifier DARKNESS_ID = new Identifier(DelveDarkness.MOD_ID, "darkness");
    public static final Identifier LIGHTNESS_ID = new Identifier(DelveDarkness.MOD_ID, "lightness");
    public static final Identifier DARKNESS_SYNC_ID = new Identifier(DelveDarkness.MOD_ID, "darkness_sync");

    public static void registerC2SPackets(){
        ServerPlayNetworking.registerGlobalReceiver(DARKNESS_ID, DarknessC2SPacket::receive);
        ServerPlayNetworking.registerGlobalReceiver(LIGHTNESS_ID, LightnessC2SPacket::receive);
    }
    public static void registerS2CPackets(){

    }

}
