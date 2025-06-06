package com.catface.cfhearts.client.gui;

import com.catface.cfhearts.core.hearts.EnumHearts;
import com.catface.cfhearts.core.heartbar.HeartBar;
import com.catface.cfhearts.core.heartbar.IHeartBar;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.util.FoodStats;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;
import java.util.Random;

import static net.minecraft.client.gui.Gui.ICONS;

@SideOnly(Side.CLIENT)
public class RenderHeartBar {


        private static final ResourceLocation EMPTY_HEART = new ResourceLocation("cfhearts", "textures/gui/hearts/empty_heart.png");
        private static final ResourceLocation FILLED_HEART = new ResourceLocation("cfhearts", "textures/gui/hearts/filled_heart.png");

        // Vanilla HUD heart size (9x9 px), 1 pixel gap
        private static final int HEART_SIZE = 9;
        private static final int HEART_GAP = 0;
        private static final int TEXTURE_SIZE = 18;

        private Minecraft mc = Minecraft.getMinecraft();
        private Random rand = new Random();

        public static int updateCounter = 0;

        @SubscribeEvent
        public void onRenderHealthBar(RenderGameOverlayEvent.Pre event) {
            if (event.getType() == RenderGameOverlayEvent.ElementType.HEALTH && Minecraft.getMinecraft().playerController.shouldDrawHUD()) {
                event.setCanceled(true);
                renderCustomHearts();
                renderVanillaFoodBar(event.getResolution(), event.getPartialTicks());
            }

        }

        private void renderCustomHearts() {
            Minecraft mc = Minecraft.getMinecraft();
            if (mc.player == null || mc.world == null) return;

            IHeartBar heartBar = mc.player.getCapability(HeartBar.HEART_BAR_CAPABILITY, null);
            if (heartBar == null) return;

            int maxHealth = Math.max(2, heartBar.getMaxHealth());
            int hearts = maxHealth / 2;

            float currentHealth = mc.player.getHealth();
            if(currentHealth>maxHealth){
                currentHealth = maxHealth;
            }
            int fullHearts = (int) currentHealth / 2;
            boolean hasHalfHeart = currentHealth % 2 != 0;

            ScaledResolution res = new ScaledResolution(mc);
            int screenWidth = res.getScaledWidth();
            int screenHeight = res.getScaledHeight();
            float scaleFactor = Minecraft.getMinecraft().gameSettings.guiScale; // same as ScaledResolution uses
            float targetHeartSize = (HEART_SIZE/3.0f) * scaleFactor; // Vanilla size adjusted by scale

            float scale = targetHeartSize / (TEXTURE_SIZE*1.0f); // 32 is your original heart texture size

            int totalWidth = (int) ((10 * (TEXTURE_SIZE + HEART_GAP)));
            int startX = (int) (screenWidth / 2 - 91);
            int startY = screenHeight - (39); // Matches vanilla height (above hotbar)

            GlStateManager.pushMatrix();
            GlStateManager.translate(startX,startY,0);
            GlStateManager.scale(scale, scale, 1); // Scale from 32px to ~16px

            // Render empty hearts
            mc.getTextureManager().bindTexture(EMPTY_HEART);
            GlStateManager.color(1f, 1f, 1f, 1f);
            GlStateManager.enableBlend();
            GlStateManager.disableDepth();
            GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA,
                    GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA,
                    GlStateManager.SourceFactor.ONE,
                    GlStateManager.DestFactor.ZERO);

            for (int i = 0; i < hearts; i++) {
                double row = Math.floor(i/10.0);
                double column = i%10;
                int y = (int) ((TEXTURE_SIZE+HEART_GAP)*row);
                int x = (int) ((TEXTURE_SIZE+HEART_GAP)*column);
                drawTexturedModalRect(x, -y, 0, 0, TEXTURE_SIZE, TEXTURE_SIZE, TEXTURE_SIZE, TEXTURE_SIZE);
            }

            // Render full hearts

            List<EnumHearts> heartList = heartBar.getCustomHeartList();
            for (int i = 0; i < fullHearts; i++) {
                EnumHearts currentHeart = EnumHearts.NORMAL;
                if(heartList.size() > i){
                    currentHeart = heartList.get(i);
                }
                mc.getTextureManager().bindTexture(currentHeart.texture);
                double row = Math.floor(i/10.0);
                double column = i%10;
                int y = (int) ((TEXTURE_SIZE+HEART_GAP)*row);
                int x = (int) ((TEXTURE_SIZE+HEART_GAP)*column);
                drawTexturedModalRect(x, -y, 0, 0, TEXTURE_SIZE, TEXTURE_SIZE, TEXTURE_SIZE, TEXTURE_SIZE);
            }

            // Render half heart (left half only)
            if (hasHalfHeart && fullHearts < hearts) {
                EnumHearts currentHeart = EnumHearts.NORMAL;
                if(heartList.size() > fullHearts){
                    currentHeart = heartList.get(fullHearts);
                }
                mc.getTextureManager().bindTexture(currentHeart.texture);
                double row = Math.floor(fullHearts/10.0);
                double column = fullHearts%10;
                int y = (int) ((TEXTURE_SIZE+HEART_GAP)*row);
                int x = (int) ((TEXTURE_SIZE+HEART_GAP)*column);
                drawTexturedModalRect(x, -y,
                        0, 0, TEXTURE_SIZE / 2, TEXTURE_SIZE, TEXTURE_SIZE, TEXTURE_SIZE);
            }

            GlStateManager.enableDepth();
            GlStateManager.disableBlend();
            GlStateManager.popMatrix();
        }

        private void renderVanillaFoodBar(ScaledResolution resolution, float partialTicks) {
            Minecraft mc = Minecraft.getMinecraft();
            GuiIngame gui = mc.ingameGUI;
            GlStateManager.pushMatrix();
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            this.mc.getTextureManager().bindTexture(ICONS);
            renderPlayerStats(resolution,gui);
            GlStateManager.disableBlend();
            GlStateManager.popMatrix();
        }

        /**
         * Renders a portion of a 32x32 texture.
         */
        private void drawTexturedModalRect(int x, int y, int textureX, int textureY, int width, int height, int texWidth, int texHeight) {
            float u = 1.0f / texWidth;
            float v = 1.0f / texHeight;

            Tessellator tess = Tessellator.getInstance();
            BufferBuilder buffer = tess.getBuffer();
            buffer.begin(7, DefaultVertexFormats.POSITION_TEX);
            buffer.pos(x,         y + height, 0).tex((textureX) * u,        (textureY + height) * v).endVertex();
            buffer.pos(x + width, y + height, 0).tex((textureX + width) * u, (textureY + height) * v).endVertex();
            buffer.pos(x + width, y,          0).tex((textureX + width) * u, (textureY) * v).endVertex();
            buffer.pos(x,         y,          0).tex((textureX) * u,        (textureY) * v).endVertex();
            tess.draw();
        }

    protected void renderPlayerStats(ScaledResolution scaledRes,GuiIngame gui)
    {
        if (this.mc.getRenderViewEntity() instanceof EntityPlayer)
        {
            EntityPlayer entityplayer = (EntityPlayer)this.mc.getRenderViewEntity();
            int i = MathHelper.ceil(entityplayer.getHealth());

            this.rand.setSeed((long)(updateCounter * 312871));
            FoodStats foodstats = entityplayer.getFoodStats();
            int k = foodstats.getFoodLevel();
            IAttributeInstance iattributeinstance = entityplayer.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH);

            int i1 = scaledRes.getScaledWidth() / 2 + 91;
            int j1 = scaledRes.getScaledHeight() - 39;
            float f = (float)iattributeinstance.getAttributeValue();
            int k1 = MathHelper.ceil(entityplayer.getAbsorptionAmount());
            int l1 = MathHelper.ceil((f + (float)k1) / 2.0F / 10.0F);
            // BS rand usage to match vanilla HUB
            for (int j5 = MathHelper.ceil((f + (float)k1) / 2.0F) - 1; j5 >= 0; --j5) {
                int l4 = this.rand.nextInt(2);
            }




            Entity entity = entityplayer.getRidingEntity();

            if (entity == null || !(entity instanceof EntityLivingBase))
            {

                for (int l5 = 0; l5 < 10; ++l5)
                {
                    int j6 = j1;
                    int l6 = 16;
                    int j7 = 0;

                    if (entityplayer.isPotionActive(MobEffects.HUNGER))
                    {
                        l6 += 36;
                        j7 = 13;
                    }

//                    if (entityplayer.getFoodStats().getSaturationLevel() <= 0.0F && updateCounter % (k * 3 + 1) == 0)
//                    {
//                        j6 = j1 + (this.rand.nextInt(3) - 1);
//                    }

                    int l7 = i1 - l5 * 8 - 9;
                    this.drawTexturedModalRect(l7, j6, 16 + j7 * 9, 27, 9, 9);

                    if (l5 * 2 + 1 < k)
                    {
                        this.drawTexturedModalRect(l7, j6, l6 + 36, 27, 9, 9);
                    }

                    if (l5 * 2 + 1 == k)
                    {
                        this.drawTexturedModalRect(l7, j6, l6 + 45, 27, 9, 9);
                    }
                }
            }

        }
    }

    public void drawTexturedModalRect(int x, int y, int textureX, int textureY, int width, int height)
    {
        float f = 0.00390625F;
        float f1 = 0.00390625F;
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
        bufferbuilder.pos((double)(x + 0), (double)(y + height), 0.0).tex((double)((float)(textureX + 0) * 0.00390625F), (double)((float)(textureY + height) * 0.00390625F)).endVertex();
        bufferbuilder.pos((double)(x + width), (double)(y + height), 0.0).tex((double)((float)(textureX + width) * 0.00390625F), (double)((float)(textureY + height) * 0.00390625F)).endVertex();
        bufferbuilder.pos((double)(x + width), (double)(y + 0), 0.0).tex((double)((float)(textureX + width) * 0.00390625F), (double)((float)(textureY + 0) * 0.00390625F)).endVertex();
        bufferbuilder.pos((double)(x + 0), (double)(y + 0), 0.0).tex((double)((float)(textureX + 0) * 0.00390625F), (double)((float)(textureY + 0) * 0.00390625F)).endVertex();
        tessellator.draw();
    }
}
