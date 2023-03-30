package net.teok.delvedarkness.util;

import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import net.teok.delvedarkness.DelveDarkness;

public class ModDamageSources {
    public static final RegistryKey<DamageType> DARKNESS;
    public ModDamageSources(){}
    public static DamageSource of(World world, RegistryKey<DamageType> key){
        return new DamageSource(world.getRegistryManager().get(RegistryKeys.DAMAGE_TYPE).entryOf(key));
    }
    static{
        DARKNESS = RegistryKey.of(RegistryKeys.DAMAGE_TYPE, new Identifier(DelveDarkness.MOD_ID, "darkness"));
    }
}
