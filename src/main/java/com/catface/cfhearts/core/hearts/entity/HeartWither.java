package com.catface.cfhearts.core.hearts.entity;

import net.minecraft.block.Block;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class HeartWither extends HeartArrow{
    public HeartWither(World worldIn) {
        super(worldIn);
    }

    @Override
    public void onActivation() {

    }

    @Override
    public void onAbilityUpdate() {
        super.onAbilityUpdate();
        if(this.world instanceof WorldServer){
            double d0 = 0.0; //red
            double d1 = 0.0; //green
            double d2 = 0.0; //blue
            ((WorldServer)this.world).spawnParticle(EnumParticleTypes.SPELL_MOB,false,this.posX,this.posY,this.posZ,5,this.width/2,this.height/2,this.width/2,0.0,0);
        }
    }

    @Override
    public void onEnd() {

    }

    @Override
    public void onImpact(RayTraceResult result) {
        if(!this.world.isRemote) {
            Vec3d vec = result.hitVec;
            EntityWither wither = new EntityWither(this.world);
            wither.setPosition(vec.x,vec.y,vec.z);
            this.world.spawnEntity(wither);
        }
        this.setDead();
    }
}
