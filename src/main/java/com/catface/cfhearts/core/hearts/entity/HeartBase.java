package com.catface.cfhearts.core.hearts.entity;

import com.catface.cfhearts.core.hearts.EnumHearts;
import com.google.common.base.Optional;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.server.management.PreYggdrasilConverter;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.UUID;

public abstract class HeartBase extends Entity {

    protected static final DataParameter<Optional<UUID>> OWNER_UNIQUE_ID = EntityDataManager.<Optional<UUID>>createKey(HeartBase.class, DataSerializers.OPTIONAL_UNIQUE_ID);
    protected int maxTime;

    public HeartBase(World worldIn) {
        super(worldIn);
        this.setSize(0.1f,0.1f);
        this.maxTime = 100;
        this.setNoGravity(true);
    }

    @Override
    public boolean canBeCollidedWith() {
        return false;
    }

    @Override
    protected void entityInit() {
        this.dataManager.register(OWNER_UNIQUE_ID,Optional.absent());
    }

    @Override
    public void onAddedToWorld() {
        super.onAddedToWorld();
        onActivation();
    }

    @Override
    public void onEntityUpdate() {
        super.onEntityUpdate();
        if(!this.world.isRemote) {
            if (this.ticksExisted < maxTime || this.maxTime<0) {
                onAbilityUpdate();
            } else {
                this.onEnd();
                this.setDead();
            }
        }
    }

    @Override
    protected void readEntityFromNBT(NBTTagCompound compound) {
        if(compound.hasKey("maxTime")){
            this.maxTime = compound.getInteger("maxTime");
        }

        String s;
        if (compound.hasKey("OwnerUUID", 8))
        {
            s = compound.getString("OwnerUUID");
        }
        else
        {
            String s1 = compound.getString("Owner");
            s = PreYggdrasilConverter.convertMobOwnerIfNeeded(this.getServer(), s1);
        }

        if (!s.isEmpty())
        {
            try
            {
                this.setOwnerId(UUID.fromString(s));

            }
            catch (Throwable var4)
            {

            }
        }
    }

    @Override
    protected void writeEntityToNBT(NBTTagCompound compound) {
        compound.setInteger("maxTime",this.maxTime);
        if (this.getOwnerId() == null)
        {
            compound.setString("OwnerUUID", "");
        }
        else
        {
            compound.setString("OwnerUUID", this.getOwnerId().toString());
        }
    }

    @Nullable
    public UUID getOwnerId()
    {
        return (UUID)((Optional)this.dataManager.get(OWNER_UNIQUE_ID)).orNull();
    }

    public void setOwnerId(@Nullable UUID p_184754_1_)
    {
        this.dataManager.set(OWNER_UNIQUE_ID, Optional.fromNullable(p_184754_1_));
    }

    @Nullable
    public EntityLivingBase getOwner()
    {
        try
        {
            UUID uuid = this.getOwnerId();
            return uuid == null ? null : this.world.getPlayerEntityByUUID(uuid);
        }
        catch (IllegalArgumentException var2)
        {
            return null;
        }
    }

    public int getMaxTime(){
        return maxTime;
    }

    public abstract void onActivation();


    public abstract void onAbilityUpdate();


    public abstract void onEnd();

    public static void spawnHeart(World world, EnumHearts heartType, EntityLivingBase owner) {
        try {
            HeartBase heart = heartType.entityClass.getConstructor(World.class).newInstance(world);
            heart.setOwnerId(owner.getUniqueID());
            if(heart instanceof HeartArrow){
                Vec3d look = owner.getLookVec().scale(0.5);
                heart.setPosition(owner.posX+look.x, owner.posY + owner.getEyeHeight()+look.y, owner.posZ+look.z);
                ((HeartArrow) heart).shoot();
            } else{
                heart.setPosition(owner.posX, owner.posY + 1.5, owner.posZ);
            }

            world.spawnEntity(heart);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
