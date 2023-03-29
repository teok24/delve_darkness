package net.teok.delvedarkness.util;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

public class DarknessSound {
    public static final Identifier DARKNESS_SOUND = new Identifier("delvedarkness:darkness_sound");
    public static SoundEvent DARKNESS_SOUND_EVENT = SoundEvent.of(DARKNESS_SOUND);
    public static void register()
    {
        Registry.register(Registries.SOUND_EVENT,DARKNESS_SOUND, DARKNESS_SOUND_EVENT);
    }
}
