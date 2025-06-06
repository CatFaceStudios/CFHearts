package com.catface.cfhearts.client.render;

import com.catface.cfhearts.core.hearts.entity.HeartBase;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;

public class RenderHeartBase extends Render<HeartBase> {
    public RenderHeartBase(RenderManager renderManager) {
        super(renderManager);
    }

    @Override
    public void doRender(HeartBase entity, double x, double y, double z, float entityYaw, float partialTicks) {

    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(HeartBase entity) {
        return null;
    }
}
