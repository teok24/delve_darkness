package net.teok.delvedarkness.config;

import io.wispforest.owo.config.Option;
import io.wispforest.owo.config.annotation.*;
import net.teok.delvedarkness.DelveDarkness;

@Modmenu(modId = DelveDarkness.MOD_ID)

@Config(name = "delvedarknessconfig", wrapperName =  "DelveDarknessConfig")
public class DelveDarknessConfigModel {

    @SectionHeader("darknessOptions")
    @Sync(Option.SyncMode.OVERRIDE_CLIENT)
    public DamageTypeSetting damageTypeSetting = DamageTypeSetting.FLAT;
    @Nest
    @Sync(Option.SyncMode.OVERRIDE_CLIENT)
    public FlatDarknessSettings flat = new FlatDarknessSettings();
    @Nest
    @Sync(Option.SyncMode.OVERRIDE_CLIENT)
    public LevelsDarknessSettings levels = new LevelsDarknessSettings();
    @RangeConstraint(min = 1, max = 60)
    @Sync(Option.SyncMode.OVERRIDE_CLIENT)
    public int darknessImmunity = 5;
    @RangeConstraint(min = 0, max = 14)
    @Sync(Option.SyncMode.OVERRIDE_CLIENT)
    public int lightLevel = 2;
    @RangeConstraint(min = 0, max = 60)
    @Sync(Option.SyncMode.OVERRIDE_CLIENT)
    public int gracePeriod = 15;

    @Sync(Option.SyncMode.OVERRIDE_CLIENT)
    public boolean darknessEffectModifier = true;
    @RangeConstraint(min = 0, max = 100)
    public int darknessSoundLevel = 100;

    public static class LevelsDarknessSettings{
        @RangeConstraint(min = 0, max = 10)
        public int minDarknessLevels = 0;
        @RangeConstraint(min = 0, max = 20 )
        public int maxDarknessLevels = 8;
        @RangeConstraint(min = 1, max = 10)
        public int darknessMultiplier = 1;
    }
    public static class FlatDarknessSettings{
        @RangeConstraint(min = 1, max = 20)
        public int darknessDamage = 4;
    }

    public enum DamageTypeSetting{
        FLAT,
        LEVELS
    }
}
