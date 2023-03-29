package net.teok.delvedarkness.util;

import net.minecraft.entity.damage.DamageSource;
import net.teok.delvedarkness.DelveDarkness;

public class DarknessDamageSource extends DamageSource{
    public static final DamageSource DARKNESS = new DarknessDamageSource(DelveDarkness.MOD_ID + "_darkness").setUnblockable();


    protected DarknessDamageSource(String name) {
        super(name);
    }
}
