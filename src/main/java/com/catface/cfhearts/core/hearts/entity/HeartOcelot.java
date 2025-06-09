package com.catface.cfhearts.core.hearts.entity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class HeartOcelot extends HeartBase{
    public HeartOcelot(World worldIn) {
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
            Item boots = Item.getByNameOrId("diamond_boots");
            if(boots != null){
                player.addItemStackToInventory(new ItemStack(boots,1));
            }
        }
    }
}
