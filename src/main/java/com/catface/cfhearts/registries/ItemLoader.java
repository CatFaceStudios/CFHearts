package com.catface.cfhearts.registries;

import com.catface.cfhearts.core.hearts.item.heart.HeartItemBase;
import com.catface.cfhearts.core.hearts.item.power.HeartPowerBase;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber
public class ItemLoader {

    public static final List<Item> ITEM_LIST = new ArrayList<>();

    @SubscribeEvent
    public static void onItemRegister(RegistryEvent.Register<Item> event){
        for(Item i: ITEM_LIST){
            event.getRegistry().register(i);
        }
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public static void onModelRegister(ModelRegistryEvent event){
        for(Item i:ITEM_LIST) {
            ModelLoader.setCustomModelResourceLocation(i,0,new ModelResourceLocation(i.getRegistryName(),"inventory"));
        }
    }
    public static final HeartItemBase NORMAL_HEART_ITEM = new HeartItemBase("normal_heart_item");
    public static final HeartItemBase BEE_HEART_ITEM = new HeartItemBase("bee_heart_item");
    public static final HeartItemBase CREEPER_HEART_ITEM = new HeartItemBase("creeper_heart_item");
    public static final HeartItemBase GHAST_HEART_ITEM = new HeartItemBase("ghast_heart_item");
    public static final HeartItemBase GOLD_HEART_ITEM = new HeartItemBase("gold_heart_item");
    public static final HeartItemBase OCELOT_HEART_ITEM = new HeartItemBase("ocelot_heart_item");
    public static final HeartItemBase POTION_HEART_ITEM = new HeartItemBase("potion_heart_item");
    public static final HeartItemBase SHEEP_HEART_ITEM = new HeartItemBase("sheep_heart_item");
    public static final HeartItemBase SLIME_HEART_ITEM = new HeartItemBase("slime_heart_item");
    public static final HeartItemBase WITHER_HEART_ITEM = new HeartItemBase("wither_heart_item");

    public static final HeartPowerBase NORMAL_HEART_POWER = new HeartPowerBase("normal_heart_power");
    public static final HeartPowerBase BEE_HEART_POWER = new HeartPowerBase("bee_heart_power");
    public static final HeartPowerBase CREEPER_HEART_POWER = new HeartPowerBase("creeper_heart_power");
    public static final HeartPowerBase GHAST_HEART_POWER = new HeartPowerBase("ghast_heart_power");
    public static final HeartPowerBase GOLD_HEART_POWER = new HeartPowerBase("gold_heart_power");
    public static final HeartPowerBase OCELOT_HEART_POWER = new HeartPowerBase("ocelot_heart_power");
    public static final HeartPowerBase POTION_HEART_POWER = new HeartPowerBase("potion_heart_power");
    public static final HeartPowerBase SHEEP_HEART_POWER = new HeartPowerBase("sheep_heart_power");
    public static final HeartPowerBase SLIME_HEART_POWER = new HeartPowerBase("slime_heart_power");
    public static final HeartPowerBase WITHER_HEART_POWER = new HeartPowerBase("wither_heart_power");
}
