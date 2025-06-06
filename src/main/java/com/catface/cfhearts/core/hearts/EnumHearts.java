package com.catface.cfhearts.core.hearts;

import com.catface.cfhearts.CFHearts;
import com.catface.cfhearts.core.heartbar.HeartBar;
import com.catface.cfhearts.core.heartbar.IHeartBar;
import com.catface.cfhearts.core.hearts.entity.HeartBase;
import com.catface.cfhearts.core.hearts.entity.HeartCreeper;
import com.catface.cfhearts.core.hearts.entity.HeartNormal;
import com.catface.cfhearts.core.hearts.item.heart.HeartItemBase;
import com.catface.cfhearts.core.hearts.item.power.HeartPowerBase;
import com.catface.cfhearts.registries.ItemLoader;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.GuiContainerEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber
public enum EnumHearts {
    NORMAL(new ResourceLocation(CFHearts.MODID,"textures/gui/hearts/filled_heart.png"), new ResourceLocation(CFHearts.MODID,"heart_normal"), HeartNormal.class, ItemLoader.NORMAL_HEART_ITEM,ItemLoader.NORMAL_HEART_POWER),
    CREEPER(new ResourceLocation(CFHearts.MODID,"textures/gui/hearts/creeper_heart.png"), new ResourceLocation(CFHearts.MODID,"heart_creeper"), HeartCreeper.class,ItemLoader.CREEPER_HEART_ITEM,ItemLoader.CREEPER_HEART_POWER);

    public ResourceLocation texture;
    public ResourceLocation entityLocation;
    public Class<? extends HeartBase> entityClass;
    private HeartItemBase linkedItem;
    private HeartPowerBase linkedPower;


    private EnumHearts(ResourceLocation texture, ResourceLocation entityLocation, Class<? extends HeartBase> entityClass,HeartItemBase item,HeartPowerBase power){
        this.texture = texture;
        this.entityLocation = entityLocation;
        this.entityClass = entityClass;
        this.linkedItem = item;
        this.linkedPower = power;
    }

    public HeartItemBase getLinkedItem() {
        return linkedItem;
    }

    public HeartPowerBase getLinkedPower() {
        return linkedPower;
    }

    @SubscribeEvent
    public static void onItemPickup(PlayerEvent.ItemPickupEvent event){
        ItemStack stack = event.getStack();
        EntityItem ent = event.getOriginalEntity();
        EntityPlayer player = event.player;
        IHeartBar bar = HeartBar.getHeartBar(player);
        if(bar == null)return;
        EnumHearts heart;
        if(stack.getItem() instanceof HeartPowerBase){
            heart = EnumHearts.getLinkedPower((HeartPowerBase) stack.getItem());
            if(!bar.getCustomHeartList().contains(heart)){
                event.setCanceled(true);
            }
        } else if (stack.getItem() instanceof HeartItemBase){
            heart = EnumHearts.getLinkedItem((HeartItemBase) stack.getItem());
            if(heart == NORMAL){
                stack.setCount(0);
                bar.setMaxHealth(bar.getMaxHealth()+1);
            }
        }

    }


    public static void onEntityDamage(LivingDamageEvent event){

    }

    public static List<String> names(){
        List<String> list = new ArrayList<>();
        for(EnumHearts heart : EnumHearts.values()){
            list.add(heart.name());
        }
        return list;
    }

    public static EnumHearts getLinkedItem(HeartItemBase item){
        for(EnumHearts heart: EnumHearts.values()){
            if(heart.linkedItem == item){
                return heart;
            }
        }

        return null;
    }

    public static EnumHearts getLinkedPower(HeartPowerBase item){
        for(EnumHearts heart: EnumHearts.values()){
            if(heart.linkedPower == item){
                return heart;
            }
        }

        return null;
    }

    public static EnumHearts findHeart(String name){
        for(EnumHearts heart: EnumHearts.values()){
            if(heart.name().equalsIgnoreCase(name)){
                return heart;
            }
        }

        return null;
    }
}
