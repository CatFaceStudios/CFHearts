package com.catface.cfhearts.core.hearts.item.bow;

import com.catface.cfhearts.CFHearts;
import com.catface.cfhearts.registries.ItemLoader;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityTippedArrow;
import net.minecraft.item.ItemArrow;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class HeartArrowItem extends ItemArrow {

    public HeartArrowItem(String name){
        this.setRegistryName(name);
        this.setUnlocalizedName(name);
        ItemLoader.ITEM_LIST.add(this);
        this.setCreativeTab(CFHearts.tabHearts);
    }

    public EntityArrow createArrow(World worldIn, ItemStack stack, EntityLivingBase shooter)
    {
        HeartArrowEntity heartArrow = new HeartArrowEntity(worldIn, shooter);
        return heartArrow;
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        //tooltip.add("Steal Hearts Mod");
        tooltip.add("Steal Hearts on Hit!");
    }
}
