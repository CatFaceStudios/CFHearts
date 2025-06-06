package com.catface.cfhearts.core.heartbar;
import com.catface.cfhearts.client.gui.RenderHeartBar;
import net.minecraft.client.Minecraft;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Mod.EventBusSubscriber
public class HeartBarEventHandler {

    public static final ResourceLocation HEART_BAR_ID = new ResourceLocation("cfhearts", "heart_bar");

    @SubscribeEvent
    public static void onAttachCapabilitiesPlayer(AttachCapabilitiesEvent event) {
        if (event.getObject() instanceof EntityPlayer) {
            event.addCapability(HEART_BAR_ID, new HeartBarProvider());
        }
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public static void onClientTick(TickEvent.WorldTickEvent event){
        if(event.phase == TickEvent.Phase.START && event.side == Side.CLIENT) {
            EntityPlayer player = Minecraft.getMinecraft().player;
            IHeartBar bar = HeartBar.getHeartBar(player);
            if(bar != null && bar.hasWorm()){
                bar.tickAnimation();
            }

            if(!Minecraft.getMinecraft().isGamePaused()) {
                RenderHeartBar.updateCounter++;
            }
        }
    }
}
