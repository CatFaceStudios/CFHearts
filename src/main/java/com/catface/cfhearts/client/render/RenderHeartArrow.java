package com.catface.cfhearts.client.render;

import com.catface.cfhearts.core.hearts.item.bow.HeartArrowEntity;
import net.minecraft.client.renderer.entity.RenderArrow;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;

public class RenderHeartArrow extends RenderArrow<HeartArrowEntity> {

    public static final ResourceLocation RES_ARROW = new ResourceLocation("textures/entity/projectiles/arrow.png");
    public RenderHeartArrow(RenderManager renderManagerIn) {
        super(renderManagerIn);
    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(HeartArrowEntity entity) {
        return RES_ARROW;
    }
}
