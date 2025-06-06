package com.catface.cfhearts.core.hearts.item.power;

import com.catface.cfhearts.CFHearts;
import com.catface.cfhearts.core.hearts.EnumHearts;
import com.catface.cfhearts.core.heartbar.HeartBar;
import com.catface.cfhearts.core.heartbar.IHeartBar;
import com.catface.cfhearts.core.hearts.entity.HeartBase;
import com.catface.cfhearts.registries.ItemLoader;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

public class HeartPowerBase extends Item {

    public HeartPowerBase(String name){
        this.setRegistryName(name);
        this.setUnlocalizedName(name);
        ItemLoader.ITEM_LIST.add(this);
        this.setCreativeTab(CFHearts.tabHearts);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {

        ItemStack stack = playerIn.getHeldItem(handIn);
        IHeartBar bar = HeartBar.getHeartBar(playerIn);

        if(stack.getItem() instanceof HeartPowerBase && bar != null){

            EnumHearts heart = EnumHearts.getLinkedPower((HeartPowerBase) stack.getItem());

            if(!bar.getCustomHeartList().contains(heart)){
                playerIn.sendMessage(new TextComponentString("You don't have the power of "+heart+" in your hearts!"));
                return ActionResult.newResult(EnumActionResult.FAIL,playerIn.getHeldItem(handIn));
            }

            if(!worldIn.isRemote) {
                HeartBase.spawnHeart(worldIn,heart,playerIn);
            }
            return ActionResult.newResult(EnumActionResult.SUCCESS,playerIn.getHeldItem(handIn));
        }


        return super.onItemRightClick(worldIn, playerIn, handIn);
    }
}
