package com.catface.cfhearts.core.hearts.item.bow;

import com.catface.cfhearts.registries.ItemLoader;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class HeartArrowEntity extends EntityArrow {

    public HeartArrowEntity(World worldIn)
    {
        super(worldIn);

    }

    public HeartArrowEntity(World worldIn, double x, double y, double z)
    {
        super(worldIn);
        super.setPosition(x, y, z);
    }

    public HeartArrowEntity(World worldIn, EntityLivingBase shooter) {
        super(worldIn, shooter);
    }

    @Override
    protected void onHit(RayTraceResult raytraceResultIn) {
        super.onHit(raytraceResultIn);
        Entity entity = raytraceResultIn.entityHit;
        if(entity != null && entity != this.shootingEntity && !this.world.isRemote){
            EntityItem heart = new EntityItem(entity.world,entity.posX,entity.posY+entity.height/2,entity.posZ,new ItemStack(ItemLoader.NORMAL_HEART_ITEM));
            heart.setPickupDelay(20);
            Vec3d vec = entity.getLookVec();
            heart.motionX=vec.x;
            heart.motionY=vec.y;
            heart.motionZ=vec.z;
            entity.world.spawnEntity(heart);
        }

    }

    @Override
    protected ItemStack getArrowStack() {
        return new ItemStack(ItemLoader.HEART_ARROW);
    }
}

