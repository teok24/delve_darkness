package net.teok.delvedarkness.util;

import net.minecraft.entity.damage.DamageSource;
import net.teok.delvedarkness.DelveDarkness;

public class DarknessDamageSource{
    public static final DamageSource DARKNESS = new DamageSource(DelveDarkness.MOD_ID + "_darkness").setBypassesArmor().setUnblockable();

}
