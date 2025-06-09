package com.catface.cfhearts.core.hearts.entity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class HeartBee extends HeartBase{
    public HeartBee(World worldIn) {
        super(worldIn);
        this.maxTime = 5;
    }

    @Override
    public void onActivation() {

    }

    @Override
    public void onAbilityUpdate() {

    }

    @Override
    public void onEnd() {
        if(this.getOwner() instanceof EntityPlayer && !this.world.isRemote){
            EntityPlayer player = (EntityPlayer) this.getOwner();
            Item wings = Item.getByNameOrId("wings:slime_wings");
            if(wings != null){
                player.addItemStackToInventory(new ItemStack(wings,1));
            }
        }
    }
}
