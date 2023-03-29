package net.teok.delvedarkness.event;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.sound.MovingSoundInstance;
import net.minecraft.client.sound.SoundInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.LightType;
import net.minecraft.world.World;
import net.teok.delvedarkness.DelveDarkness;
import net.teok.delvedarkness.networking.ModMessages;
import net.teok.delvedarkness.util.DarknessData;
import net.teok.delvedarkness.util.DarknessSound;
import net.teok.delvedarkness.util.IEntityDataSaver;


public class LightChangeHandler {
    public static int darkTick = 19; //should be 1 tick less than config doDamageInSeconds
    public static int grace = DelveDarkness.config.gracePeriod()*20;
    public static float darkness_master;
    public static float darkness_ambient;
    public static boolean darkness_start;
    public static DarknessLoop loop;

    public static void registerLightChange()
    {

        ClientTickEvents.START_CLIENT_TICK.register(client -> {
            if (client.player == null)
            {
                darkness_start = false;
            }
            if (client.player != null) {
                if (!darkness_start)
                {
                    loop = null;
                    darkness_start = true;
                }


                PacketByteBuf packet = PacketByteBufs.create();
                packet.writeInt(darkTick);
                if (client.player.isCreative() || client.player.isSpectator() || client.isPaused()) return;
                if (client.player.isAlive()) //confusing messy code, refactor later
                {
                    grace--; //grace period ticks down

                } else {
                    grace = DelveDarkness.config.gracePeriod() * 20; //if player dies, set grace period to max
                }
                if (grace > 0) { //if grace greater than 0, max darkness immunity out
                    if (((IEntityDataSaver) client.player).getPersistentData().getInt("darknessImmunity") != DelveDarkness.config.darknessImmunity() * 20) {
                        DarknessData.addDarknessImmunity(((IEntityDataSaver) client.player), DelveDarkness.config.darknessImmunity() * 20);
                    }
                    return;
                }
                if (loop == null) {
                    loop = new DarknessLoop(DarknessSound.DARKNESS_SOUND_EVENT, client);
                    client.getSoundManager().play(loop);
                    darkness_master = client.options.getSoundVolume(SoundCategory.MASTER); //set volume to what it currently is in game, if this changes
                    darkness_ambient = client.options.getSoundVolume(SoundCategory.AMBIENT);
                }
                if (client.options.getSoundVolume(SoundCategory.MASTER) <= 0 || client.options.getSoundVolume(SoundCategory.AMBIENT) <= 0){
                    client.getSoundManager().stop(loop);
                    loop = null;
                }
                if (client.options.getSoundVolume(SoundCategory.MASTER) != darkness_master || client.options.getSoundVolume(SoundCategory.AMBIENT) != darkness_ambient)
                {
                    //if volume differs, reset loop
                    client.getSoundManager().stop(loop);
                    loop = null;
                }
                //loop.tick();
                if (isDark(client.world, client.player.getBlockPos(), client)) {


                    DarknessData.removeDarknessImmunity(((IEntityDataSaver) client.player), 1); //darkness immunity tick, THIS is the reason you can't sync darkness immunity because it's not server only

                    //spawn particles
                    //client.world.addParticle(ParticleTypes.ASH, client.player.getX(),client.player.getY(),client.player.getZ(),0,0,0);
                    if (((IEntityDataSaver) client.player).getPersistentData().getInt("darknessImmunity") <= 0)
                    {
                        darkTick ++;
                    }
                    ClientPlayNetworking.send(ModMessages.DARKNESS_ID, packet);
                } else {
                    DarknessData.addDarknessImmunity(((IEntityDataSaver) client.player), DelveDarkness.config.darknessImmunity()); //darkness immunity will increase at 10% of it's maximum per tick
                    darkTick = 19;
                    ClientPlayNetworking.send(ModMessages.LIGHTNESS_ID, packet);
                }
                //client.player.sendMessage(Text.of("Immunity: " + ((IEntityDataSaver)client.player).getPersistentData().getInt("darknessImmunity")),true);
            }
        });
    }


    public static boolean isDark(World world, BlockPos pos, MinecraftClient client)
    {
        int light = isDay(world, pos);
        int mod = 0;
        light += world.getLightLevel(LightType.BLOCK, pos);
        if (client.player!= null && client.player.hasStatusEffect(StatusEffects.DARKNESS) && DelveDarkness.config.darknessEffectModifier())
        {
            mod = 8; //

        }
        return light <= mod + DelveDarkness.config.lightLevel();//light level for darkness, controlled by config
    }

    public static int isDay(World world, BlockPos pos) //got this code directly from the daylight sensor block. I have no idea what it really does and at this point I'm too afraid to ask
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

    public static class DarknessLoop extends MovingSoundInstance{
        private final MinecraftClient client;
        public DarknessLoop(SoundEvent sound, MinecraftClient client)
        {
            super(sound, SoundCategory.AMBIENT, SoundInstance.createRandom());
            this.repeat = true;
            this.repeatDelay = 0;
            this.volume = 1f;
            this.relative = true;
            this.client = client;
        }

        @Override
        public void tick() {

            float darkImm = 0;
            if (client.player != null) {
                darkImm = ((IEntityDataSaver) client.player).getPersistentData().getInt("darknessImmunity");
            }
            //this.strength = darkVol;
            float strength = (1 - ((darkImm / 20) / DelveDarkness.config.darknessImmunity())) * ((float)DelveDarkness.config.darknessSoundLevel() / 100);
            //this.pitch = 1;
            this.volume = MathHelper.clamp(strength, 0.0f, 1.0f);

        }
    }
}
