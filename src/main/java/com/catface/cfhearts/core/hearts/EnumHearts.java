package com.catface.cfhearts.core.hearts;

import com.catface.cfhearts.CFHearts;
import com.catface.cfhearts.core.heartbar.HeartBar;
import com.catface.cfhearts.core.heartbar.IHeartBar;
import com.catface.cfhearts.core.hearts.entity.*;
import com.catface.cfhearts.core.hearts.item.heart.HeartItemBase;
import com.catface.cfhearts.core.hearts.item.power.HeartPowerBase;
import com.catface.cfhearts.network.packets.PacketSyncHeartBar;
import com.catface.cfhearts.registries.ItemLoader;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.GuiContainerEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber
public enum EnumHearts {
    NORMAL(new ResourceLocation(CFHearts.MODID,"textures/gui/hearts/filled_heart.png"), new ResourceLocation(CFHearts.MODID,"heart_normal"), HeartNormal.class, ItemLoader.NORMAL_HEART_ITEM,ItemLoader.NORMAL_HEART_POWER),
    CREEPER(new ResourceLocation(CFHearts.MODID,"textures/gui/hearts/creeper_heart.png"), new ResourceLocation(CFHearts.MODID,"heart_creeper"), HeartCreeper.class,ItemLoader.CREEPER_HEART_ITEM,ItemLoader.CREEPER_HEART_POWER),
    BAN(new ResourceLocation(CFHearts.MODID,"textures/gui/hearts/ban_heart.png"), new ResourceLocation(CFHearts.MODID,"heart_ban"), HeartBan.class,ItemLoader.BAN_HEART_ITEM,ItemLoader.BAN_HEART_POWER),
    BEE(new ResourceLocation(CFHearts.MODID,"textures/gui/hearts/bee_heart.png"), new ResourceLocation(CFHearts.MODID,"heart_bee"), HeartBee.class,ItemLoader.BEE_HEART_ITEM,ItemLoader.BEE_HEART_POWER),
    CLOCK(new ResourceLocation(CFHearts.MODID,"textures/gui/hearts/clock_heart.png"), new ResourceLocation(CFHearts.MODID,"heart_clock"), HeartClock.class,ItemLoader.CLOCK_HEART_ITEM,ItemLoader.CLOCK_HEART_POWER),
    GHAST(new ResourceLocation(CFHearts.MODID,"textures/gui/hearts/ghast_heart.png"), new ResourceLocation(CFHearts.MODID,"heart_ghast"), HeartGhast.class,ItemLoader.GHAST_HEART_ITEM,ItemLoader.GHAST_HEART_POWER),
    GOLD(new ResourceLocation(CFHearts.MODID,"textures/gui/hearts/gold_heart.png"), new ResourceLocation(CFHearts.MODID,"heart_gold"), HeartGold.class,ItemLoader.GOLD_HEART_ITEM,ItemLoader.GOLD_HEART_POWER),
    OCELOT(new ResourceLocation(CFHearts.MODID,"textures/gui/hearts/ocelot_heart.png"), new ResourceLocation(CFHearts.MODID,"heart_ocelot"), HeartOcelot.class,ItemLoader.OCELOT_HEART_ITEM,ItemLoader.OCELOT_HEART_POWER),
    POTION(new ResourceLocation(CFHearts.MODID,"textures/gui/hearts/potion_heart.png"), new ResourceLocation(CFHearts.MODID,"heart_potion"), HeartPotion.class,ItemLoader.POTION_HEART_ITEM,ItemLoader.POTION_HEART_POWER),
    SHEEP(new ResourceLocation(CFHearts.MODID,"textures/gui/hearts/sheep_heart.png"), new ResourceLocation(CFHearts.MODID,"heart_sheep"), HeartSheep.class,ItemLoader.SHEEP_HEART_ITEM,ItemLoader.SHEEP_HEART_POWER),
    SLIME(new ResourceLocation(CFHearts.MODID,"textures/gui/hearts/slime_heart.png"), new ResourceLocation(CFHearts.MODID,"heart_slime"), HeartSlime.class,ItemLoader.SLIME_HEART_ITEM,ItemLoader.SLIME_HEART_POWER),
    WITHER(new ResourceLocation(CFHearts.MODID,"textures/gui/hearts/wither_heart.png"), new ResourceLocation(CFHearts.MODID,"heart_wither"), HeartWither.class,ItemLoader.WITHER_HEART_ITEM,ItemLoader.WITHER_HEART_POWER);

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

            }
        }
//        else if (stack.getItem() instanceof HeartItemBase){
//            heart = EnumHearts.getLinkedItem((HeartItemBase) stack.getItem());
//            if(heart == NORMAL){
//                stack.setCount(0);
//                bar.setMaxHealth(bar.getMaxHealth()+2);
//                if(player instanceof EntityPlayerMP){
//                    CFHearts.packetHandler.network.sendTo((IMessage) new PacketSyncHeartBar(bar), (EntityPlayerMP) player);
//                }
//            }
//        }

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
