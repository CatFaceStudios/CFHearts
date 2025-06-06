package com.catface.cfhearts.core;

import com.catface.cfhearts.registries.ItemLoader;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class CreativeTabHearts extends CreativeTabs {
    public CreativeTabHearts(String label) {
        super(label);
    }

    @Override
    public ItemStack getTabIconItem() {
        return new ItemStack(ItemLoader.NORMAL_HEART_ITEM);
    }


}
