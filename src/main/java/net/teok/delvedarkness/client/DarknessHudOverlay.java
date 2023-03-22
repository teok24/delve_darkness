package net.teok.delvedarkness.client;

import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.teok.delvedarkness.DelveDarkness;
import net.teok.delvedarkness.util.IEntityDataSaver;

public class DarknessHudOverlay implements HudRenderCallback {
    private static int delveDarkness_eyeTick;
    private static final Identifier EYE_CLOSED = new Identifier(DelveDarkness.MOD_ID, "textures/darkness/eye_closed.png");
    //I have no idea if minecraft has a way of animating HUD elements normally, so I've coded my own below. If someone does know a proper way, please let me know on the fabric discord.
    private static final Identifier[] EYE_QUARTER_OPENED = { //Identifier arrays hold every frame of animation, these are used in getAnimatedTextureSlice() below.
            new Identifier(DelveDarkness.MOD_ID, "textures/darkness/eye_quarter_open/1.png"),
            new Identifier(DelveDarkness.MOD_ID, "textures/darkness/eye_quarter_open/2.png"),
            new Identifier(DelveDarkness.MOD_ID, "textures/darkness/eye_quarter_open/3.png"),
            new Identifier(DelveDarkness.MOD_ID, "textures/darkness/eye_quarter_open/4.png"),
            new Identifier(DelveDarkness.MOD_ID, "textures/darkness/eye_quarter_open/5.png"),
            new Identifier(DelveDarkness.MOD_ID, "textures/darkness/eye_quarter_open/6.png"),
            new Identifier(DelveDarkness.MOD_ID, "textures/darkness/eye_quarter_open/7.png"),
            new Identifier(DelveDarkness.MOD_ID, "textures/darkness/eye_quarter_open/8.png"),
            new Identifier(DelveDarkness.MOD_ID, "textures/darkness/eye_quarter_open/9.png"),
            new Identifier(DelveDarkness.MOD_ID, "textures/darkness/eye_quarter_open/10.png"),
            new Identifier(DelveDarkness.MOD_ID, "textures/darkness/eye_quarter_open/11.png"),
    };
    private static final Identifier[] EYE_HALF_OPENED = {
            new Identifier(DelveDarkness.MOD_ID, "textures/darkness/eye_half_open/1.png"),
            new Identifier(DelveDarkness.MOD_ID, "textures/darkness/eye_half_open/2.png"),
            new Identifier(DelveDarkness.MOD_ID, "textures/darkness/eye_half_open/3.png"),
            new Identifier(DelveDarkness.MOD_ID, "textures/darkness/eye_half_open/4.png"),
            new Identifier(DelveDarkness.MOD_ID, "textures/darkness/eye_half_open/5.png"),
            new Identifier(DelveDarkness.MOD_ID, "textures/darkness/eye_half_open/6.png"),
            new Identifier(DelveDarkness.MOD_ID, "textures/darkness/eye_half_open/7.png"),
            new Identifier(DelveDarkness.MOD_ID, "textures/darkness/eye_half_open/8.png"),
            new Identifier(DelveDarkness.MOD_ID, "textures/darkness/eye_half_open/9.png"),
            new Identifier(DelveDarkness.MOD_ID, "textures/darkness/eye_half_open/10.png"),
            new Identifier(DelveDarkness.MOD_ID, "textures/darkness/eye_half_open/11.png"),
            new Identifier(DelveDarkness.MOD_ID, "textures/darkness/eye_half_open/12.png"),

    };

    private static final Identifier[] EYE_THREE_QUARTERS_OPENED = {
            new Identifier(DelveDarkness.MOD_ID, "textures/darkness/eye_three_quarters_open/1.png"),
            new Identifier(DelveDarkness.MOD_ID, "textures/darkness/eye_three_quarters_open/2.png"),
            new Identifier(DelveDarkness.MOD_ID, "textures/darkness/eye_three_quarters_open/3.png"),
            new Identifier(DelveDarkness.MOD_ID, "textures/darkness/eye_three_quarters_open/4.png"),
            new Identifier(DelveDarkness.MOD_ID, "textures/darkness/eye_three_quarters_open/5.png"),
            new Identifier(DelveDarkness.MOD_ID, "textures/darkness/eye_three_quarters_open/6.png"),
            new Identifier(DelveDarkness.MOD_ID, "textures/darkness/eye_three_quarters_open/7.png"),
            new Identifier(DelveDarkness.MOD_ID, "textures/darkness/eye_three_quarters_open/8.png"),
            new Identifier(DelveDarkness.MOD_ID, "textures/darkness/eye_three_quarters_open/9.png"),
            new Identifier(DelveDarkness.MOD_ID, "textures/darkness/eye_three_quarters_open/10.png"),

    };
    private static final Identifier[] EYE_OPENED = {
            new Identifier(DelveDarkness.MOD_ID, "textures/darkness/eye_open/1.png"),
            new Identifier(DelveDarkness.MOD_ID, "textures/darkness/eye_open/2.png"),
            new Identifier(DelveDarkness.MOD_ID, "textures/darkness/eye_open/3.png"),
            new Identifier(DelveDarkness.MOD_ID, "textures/darkness/eye_open/4.png"),
            new Identifier(DelveDarkness.MOD_ID, "textures/darkness/eye_open/5.png"),
            new Identifier(DelveDarkness.MOD_ID, "textures/darkness/eye_open/6.png"),
            new Identifier(DelveDarkness.MOD_ID, "textures/darkness/eye_open/7.png"),
            new Identifier(DelveDarkness.MOD_ID, "textures/darkness/eye_open/8.png"),
            new Identifier(DelveDarkness.MOD_ID, "textures/darkness/eye_open/9.png"),
            new Identifier(DelveDarkness.MOD_ID, "textures/darkness/eye_open/10.png"),
            new Identifier(DelveDarkness.MOD_ID, "textures/darkness/eye_open/11.png"),
            new Identifier(DelveDarkness.MOD_ID, "textures/darkness/eye_open/12.png")
        };
    private static final Identifier[] EYE_EVIL = {
            new Identifier(DelveDarkness.MOD_ID, "textures/darkness/eye_evil/1.png"),
            new Identifier(DelveDarkness.MOD_ID, "textures/darkness/eye_evil/2.png"),
            new Identifier(DelveDarkness.MOD_ID, "textures/darkness/eye_evil/3.png"),
            new Identifier(DelveDarkness.MOD_ID, "textures/darkness/eye_evil/4.png"),
            new Identifier(DelveDarkness.MOD_ID, "textures/darkness/eye_evil/5.png"),
            new Identifier(DelveDarkness.MOD_ID, "textures/darkness/eye_evil/6.png"),
            new Identifier(DelveDarkness.MOD_ID, "textures/darkness/eye_evil/7.png"),
            new Identifier(DelveDarkness.MOD_ID, "textures/darkness/eye_evil/8.png"),
            new Identifier(DelveDarkness.MOD_ID, "textures/darkness/eye_evil/9.png"),
            new Identifier(DelveDarkness.MOD_ID, "textures/darkness/eye_evil/10.png"),
            new Identifier(DelveDarkness.MOD_ID, "textures/darkness/eye_evil/11.png"),
            new Identifier(DelveDarkness.MOD_ID, "textures/darkness/eye_evil/12.png"),
            new Identifier(DelveDarkness.MOD_ID, "textures/darkness/eye_evil/13.png"),
            new Identifier(DelveDarkness.MOD_ID, "textures/darkness/eye_evil/14.png"),
            new Identifier(DelveDarkness.MOD_ID, "textures/darkness/eye_evil/15.png"),
            new Identifier(DelveDarkness.MOD_ID, "textures/darkness/eye_evil/16.png"),
            new Identifier(DelveDarkness.MOD_ID, "textures/darkness/eye_evil/17.png"),

    };
    @Override
    public void onHudRender(MatrixStack matrixStack, float tickDelta) { //Whenever hud is rendered, this will update.
        delveDarkness_eyeTick++; //simply the tick for the animation speed.
        int x = 0, y = 0;
        MinecraftClient client = MinecraftClient.getInstance(); //get client instance
        if (client != null)
        {
            int width = client.getWindow().getScaledWidth(), height = client.getWindow().getScaledHeight(); //make ints for width and height
            x = width/2;
            y = height;
        }
        if (client.player == null || client.player.isCreative() || client.player.isSpectator())
        {
            return; //if player doesn't exist or is in a mode with invulnerability, leave
        }
        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
        RenderSystem.setShaderColor(1,1,1,1);

        var darknessImmunity = ((IEntityDataSaver) client.player).getPersistentData().getInt("darknessImmunity"); //get immunity nbt data
        Identifier texture = EYE_CLOSED;

        if (darknessImmunity <= 0){
            texture = getAnimatedTextureSlice(EYE_EVIL, delveDarkness_eyeTick, 1.5f);
        } else if (darknessImmunity < 20) texture = getAnimatedTextureSlice(EYE_OPENED, delveDarkness_eyeTick, 1.5f);
        else if (darknessImmunity < 40){
            texture = getAnimatedTextureSlice(EYE_THREE_QUARTERS_OPENED,delveDarkness_eyeTick,2);
        } else if (darknessImmunity < 60){
            texture = getAnimatedTextureSlice(EYE_HALF_OPENED, delveDarkness_eyeTick, 3);
        } else if (darknessImmunity < 80)
            texture = getAnimatedTextureSlice(EYE_QUARTER_OPENED, delveDarkness_eyeTick, 4f);

        int yMod = client.player.experienceLevel > 0 ? 52 : 46; //If the player has levels (making the level number appear on hud) modify the y position of the eye, so it's above it

        RenderSystem.setShaderTexture(0,texture); //render the eye using all that logic from before
        DrawableHelper.drawTexture(matrixStack, x-9,y-yMod,0,0,18,18,18,18); //draw the eye at the correct position
    }

    public Identifier getAnimatedTextureSlice(Identifier[] frames, int tick, float frameSpeed)
    {
        int modulo = (tick / (int)frameSpeed) % frames.length; //modulo is the current image number in the array, because it uses modulus the number can never be higher than the array bounds and allows for looping animation, for example 13 % 12 = 1, which would be the SECOND frame due to how arrays are numbered.
        return frames[modulo];
    }
}
