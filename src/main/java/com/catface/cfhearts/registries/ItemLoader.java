package com.catface.cfhearts.registries;

import com.catface.cfhearts.core.hearts.item.bow.HeartArrowItem;
import com.catface.cfhearts.core.hearts.item.bow.HeartBowItem;
import com.catface.cfhearts.core.hearts.item.heart.HeartItemBase;
import com.catface.cfhearts.core.hearts.item.power.HeartPowerBase;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
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


    public static final HeartItemBase NORMAL_HEART_ITEM = new HeartItemBase("normal_heart_item") {
        @Override
        public void addHeartInfo(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
            tooltip.add("Adds 1 normal heart to your heart bar");
        }
    };

    public static final HeartItemBase BEE_HEART_ITEM = new HeartItemBase("bee_heart_item") {
        @Override
        public void addHeartInfo(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
            tooltip.add("Equip Bee Wings");
        }
    };

    public static final HeartItemBase CREEPER_HEART_ITEM = new HeartItemBase("creeper_heart_item") {
        @Override
        public void addHeartInfo(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
            tooltip.add("TNT Projectile");
        }
    };

    public static final HeartItemBase GHAST_HEART_ITEM = new HeartItemBase("ghast_heart_item") {
        @Override
        public void addHeartInfo(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {

        }
    };

    public static final HeartItemBase GOLD_HEART_ITEM = new HeartItemBase("gold_heart_item") {
        @Override
        public void addHeartInfo(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
            tooltip.add("Turn Players to Gold");
        }
    };

    public static final HeartItemBase OCELOT_HEART_ITEM = new HeartItemBase("ocelot_heart_item") {
        @Override
        public void addHeartInfo(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
            tooltip.add("Speed Boost!");
        }
    };

    public static final HeartItemBase POTION_HEART_ITEM = new HeartItemBase("potion_heart_item") {
        @Override
        public void addHeartInfo(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {

        }
    };

    public static final HeartItemBase SHEEP_HEART_ITEM = new HeartItemBase("sheep_heart_item") {
        @Override
        public void addHeartInfo(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
            tooltip.add("Fluffy Defense!");
        }
    };

    public static final HeartItemBase SLIME_HEART_ITEM = new HeartItemBase("slime_heart_item") {
        @Override
        public void addHeartInfo(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {

        }
    };

    public static final HeartItemBase WITHER_HEART_ITEM = new HeartItemBase("wither_heart_item") {
        @Override
        public void addHeartInfo(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
            tooltip.add("Summon a Wither!");
        }
    };

    public static final HeartItemBase CLOCK_HEART_ITEM = new HeartItemBase("clock_heart_item") {
        @Override
        public void addHeartInfo(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
            tooltip.add("Mesmerize other players!");
        }
    };

    public static final HeartItemBase BAN_HEART_ITEM = new HeartItemBase("ban_heart_item") {
        @Override
        public void addHeartInfo(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
            tooltip.add("Ban other players!");
        }
    };

    public static final HeartPowerBase NORMAL_HEART_POWER = new HeartPowerBase("normal_heart_power") {
        @Override
        public void addHeartInfo(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {

        }
    };

    public static final HeartPowerBase BEE_HEART_POWER = new HeartPowerBase("bee_heart_power") {
        @Override
        public void addHeartInfo(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
            tooltip.add("Equip Bee Wings");
        }
    };

    public static final HeartPowerBase CREEPER_HEART_POWER = new HeartPowerBase("creeper_heart_power"){
        @Override
        public void addHeartInfo(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
            tooltip.add("TNT Projectile");
        }
    };

    public static final HeartPowerBase GHAST_HEART_POWER = new HeartPowerBase("ghast_heart_power"){
        @Override
        public void addHeartInfo(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {

        }
    };

    public static final HeartPowerBase GOLD_HEART_POWER = new HeartPowerBase("gold_heart_power"){
        @Override
        public void addHeartInfo(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
            tooltip.add("Turn Players to Gold");
        }
    };

    public static final HeartPowerBase OCELOT_HEART_POWER = new HeartPowerBase("ocelot_heart_power"){
        @Override
        public void addHeartInfo(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
            tooltip.add("Speed Boost!");
        }
    };

    public static final HeartPowerBase POTION_HEART_POWER = new HeartPowerBase("potion_heart_power"){
        @Override
        public void addHeartInfo(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {

        }
    };

    public static final HeartPowerBase SHEEP_HEART_POWER = new HeartPowerBase("sheep_heart_power"){
        @Override
        public void addHeartInfo(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
            tooltip.add("Fluffy Defense!");
        }
    };

    public static final HeartPowerBase SLIME_HEART_POWER = new HeartPowerBase("slime_heart_power"){
        @Override
        public void addHeartInfo(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {

        }
    };

    public static final HeartPowerBase WITHER_HEART_POWER = new HeartPowerBase("wither_heart_power"){
        @Override
        public void addHeartInfo(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
            tooltip.add("Summon a Wither!");
        }
    };

    public static final HeartPowerBase CLOCK_HEART_POWER = new HeartPowerBase("clock_heart_power"){
        @Override
        public void addHeartInfo(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
            tooltip.add("Mesmerize other players!");
        }
    };

    public static final HeartPowerBase BAN_HEART_POWER = new HeartPowerBase("ban_heart_power"){
        @Override
        public void addHeartInfo(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
            tooltip.add("Ban other players!");
        }
    };

    public static final HeartArrowItem HEART_ARROW = new HeartArrowItem("heart_arrow");
    public static final HeartBowItem HEART_BOW = new HeartBowItem("heart_bow");
}
