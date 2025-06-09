package com.catface.cfhearts.registries;

import com.catface.cfhearts.CFHearts;
import com.catface.cfhearts.client.render.RenderHeartArrow;
import com.catface.cfhearts.client.render.RenderHeartBase;
import com.catface.cfhearts.core.hearts.EnumHearts;
import com.catface.cfhearts.core.hearts.entity.HeartBase;
import com.catface.cfhearts.core.hearts.item.bow.HeartArrowEntity;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityLoader {

    public static int entityID=0;

    public static void loadEntities(){

        for(EnumHearts heart : EnumHearts.values()){
            registerEggless(heart.entityLocation.getResourcePath(), heart.entityClass);
        }

        registerEggless("heart_arrow", HeartArrowEntity.class);
    }

    private static void registerEntity(final String name, final Class<? extends Entity> entityClass,
                                       final int eggPrimary, final int eggSecondary) {
        EntityRegistry.registerModEntity(new ResourceLocation(CFHearts.MODID, name), entityClass, name,
                ++entityID, CFHearts.instance, 64, 1, true, eggPrimary, eggSecondary);
    }

    private static void registerEggless(final String name, final Class<? extends Entity> entityClass) {
        EntityRegistry.registerModEntity(new ResourceLocation(CFHearts.MODID, name), entityClass, name,
                ++entityID, CFHearts.instance, 64, 1, true);
    }

    @SideOnly(Side.CLIENT)
    public static void loadModels(){
        RenderingRegistry.registerEntityRenderingHandler(HeartBase.class, RenderHeartBase::new);
        RenderingRegistry.registerEntityRenderingHandler(HeartArrowEntity.class, RenderHeartArrow::new);
    }
}
