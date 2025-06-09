package com.catface.cfhearts.core.hearts.item.heart;

import com.catface.cfhearts.CFHearts;
import com.catface.cfhearts.core.hearts.EnumHearts;
import com.catface.cfhearts.core.heartbar.HeartBar;
import com.catface.cfhearts.core.heartbar.IHeartBar;
import com.catface.cfhearts.core.hearts.item.power.HeartPowerBase;
import com.catface.cfhearts.registries.ItemLoader;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public abstract class HeartItemBase extends Item {

    public HeartItemBase(String name){
        this.setRegistryName(name);
        this.setUnlocalizedName(name);
        ItemLoader.ITEM_LIST.add(this);
        this.setMaxStackSize(1);
        this.setCreativeTab(CFHearts.tabHearts);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {

        ItemStack stack = playerIn.getHeldItem(handIn);
        IHeartBar bar = HeartBar.getHeartBar(playerIn);

        if(stack.getItem() instanceof HeartItemBase && bar != null){

            EnumHearts heart = EnumHearts.getLinkedItem((HeartItemBase) stack.getItem());

            if(!bar.getCustomHeartList().contains(heart)){
                bar.addCustomHeart(heart);
            }

            if(!worldIn.isRemote && heart != EnumHearts.BEE && heart != EnumHearts.OCELOT) {
                stack.shrink(1);
                HeartPowerBase heartPower = heart.getLinkedPower();
                playerIn.addItemStackToInventory(new ItemStack(heartPower, 1));
            }
            return ActionResult.newResult(EnumActionResult.SUCCESS,playerIn.getHeldItem(handIn));
        }


        return super.onItemRightClick(worldIn, playerIn, handIn);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        //tooltip.add("Steal Hearts Mod");
        addHeartInfo(stack,worldIn,tooltip,flagIn);
    }

    public abstract void addHeartInfo(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn);


}
