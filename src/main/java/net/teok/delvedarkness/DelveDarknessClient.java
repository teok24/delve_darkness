package net.teok.delvedarkness;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.teok.delvedarkness.client.DarknessHudOverlay;
import net.teok.delvedarkness.event.LightChangeHandler;
import net.teok.delvedarkness.networking.ModMessages;
import net.teok.delvedarkness.util.DarknessSound;

public class DelveDarknessClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        LightChangeHandler.register();
        ModMessages.registerS2CPackets();
        HudRenderCallback.EVENT.register(new DarknessHudOverlay());
        DarknessSound.register();
    }
}
