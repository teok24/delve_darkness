package net.teok.delvedarkness.event;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.block.BlockState;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

public class KeyInputHandler {
    public static final String KEY_CATEGORY_DELVEDARKNESS = "key.category.delvedarkness.delvedarkness";
    public static final String KEY_GET_LIGHT = "key.delvedarkness.get_light";

    public static KeyBinding lightKey;

    public static void registerKeyInputs()
    {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (lightKey.wasPressed() && client.player != null)
            {
                BlockState state = client.player.getSteppingBlockState();
                //do shit

                client.player.sendMessage(Text.of("i = " + LightChangeHandler.isDay(client.world, client.player.getBlockPos())));
            }
        });
    }

    public static void register(){
        lightKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                KEY_GET_LIGHT,
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_O,
                KEY_CATEGORY_DELVEDARKNESS
        ));
        registerKeyInputs();
    }


}
