package com.catface.cfhearts.core.hearts.entity;

import com.catface.cfhearts.CFHearts;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class HeartCreeper extends HeartArrow{
    public HeartCreeper(World worldIn) {
        super(worldIn);
        this.speed = 0.5f;
        this.maxTime = 200;
    }

    @Override
    public void onActivation() {
        CFHearts.getLogger().info("heart creeper start");
    }

    @Override
    public void onAbilityUpdate() {
        super.onAbilityUpdate();
        if(this.world instanceof WorldServer){
            ((WorldServer)this.world).spawnParticle(EnumParticleTypes.SMOKE_NORMAL,false,this.posX,this.posY,this.posZ,5,this.width/2,this.height/2,this.width/2,0.0);
        }
    }

    @Override
    public void onEnd() {
        CFHearts.getLogger().info("heart creeper end");
        Vec3d vec = this.getPositionVector();
        this.world.createExplosion(this, vec.x,vec.y,vec.z,3.0f,true);

    }

    @Override
    public void onImpact(RayTraceResult result) {
        CFHearts.getLogger().info("heart creeper impact");
        Vec3d vec = result.hitVec;
        if(vec != null) {
            this.world.createExplosion(this, vec.x,vec.y,vec.z,3.0f,true);
        }
        this.setDead();
    }

    @Override
    public void setDead() {
        super.setDead();
        CFHearts.getLogger().info("heart creeper dead");
    }
}
