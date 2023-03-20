package net.teok.delvedarkness;

import net.fabricmc.api.ClientModInitializer;
import net.teok.delvedarkness.event.KeyInputHandler;
import net.teok.delvedarkness.event.LightChangeHandler;
import net.teok.delvedarkness.networking.ModMessages;

public class DelveDarknessClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        KeyInputHandler.register();
        LightChangeHandler.register();
        ModMessages.registerS2CPackets();
    }
}
