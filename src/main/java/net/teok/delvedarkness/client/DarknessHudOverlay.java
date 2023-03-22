package net.teok.delvedarkness.client;

import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.teok.delvedarkness.DelveDarkness;
import net.teok.delvedarkness.event.LightChangeHandler;
import net.teok.delvedarkness.util.IEntityDataSaver;

public class DarknessHudOverlay implements HudRenderCallback {
    private static final Identifier EYE_CLOSED = new Identifier(DelveDarkness.MOD_ID, "textures/darkness/eye_closed.png");
    private static final Identifier[] EYE_QUARTER_OPENED = {
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
    public void onHudRender(MatrixStack matrixStack, float tickDelta) {
        int x = 0, y = 0;
        MinecraftClient client = MinecraftClient.getInstance();
        if (client != null)
        {
            int width = client.getWindow().getScaledWidth(), height = client.getWindow().getScaledHeight(); //make ints for width and height
            x = width/2;
            y = height;
        }
        if (client.player == null || client.player.isCreative() || client.player.isSpectator())
        {
            return;
        }
        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
        RenderSystem.setShaderColor(1,1,1,1);

        var darknessLevel = ((IEntityDataSaver) client.player).getPersistentData().getInt("darkness");

        var darknessTick = LightChangeHandler.tick;
        Identifier texture = EYE_CLOSED;

        if (darknessTick < 20){
            texture = EYE_CLOSED;
        } else if (darknessTick >=20 && darknessTick < 40){
            texture = getAnimatedTextureSlice(EYE_QUARTER_OPENED, darknessTick, 4);
        } else if (darknessTick >=40 && darknessTick < 60){
            texture = getAnimatedTextureSlice(EYE_HALF_OPENED,darknessTick,3);
        } else if (darknessTick >=60 && darknessTick < 80){
            texture = getAnimatedTextureSlice(EYE_THREE_QUARTERS_OPENED, darknessTick, 2);
        } else if (darknessTick >=80 && darknessTick < 100){
            texture = getAnimatedTextureSlice(EYE_OPENED, darknessTick, 1.5f);
        } else if (darknessTick >=100){
            texture = getAnimatedTextureSlice(EYE_EVIL, darknessTick, 1);
        }

        int yMod = client.player.experienceLevel > 0 ? 52 : 46; //does the player have a level number?

        RenderSystem.setShaderTexture(0,texture);
        DrawableHelper.drawTexture(matrixStack, x-9,y-yMod,0,0,18,18,18,18);
    }

    public Identifier getAnimatedTextureSlice(Identifier[] frames, int tick, float frameSpeed)
    {
        int modulo = (tick / (int)frameSpeed) % frames.length; //frame number
        return frames[modulo];
    }
}
