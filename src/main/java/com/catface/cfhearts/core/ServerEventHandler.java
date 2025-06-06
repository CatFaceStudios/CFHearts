package com.catface.cfhearts.core;

import com.catface.cfhearts.CFHearts;
import com.catface.cfhearts.core.heartbar.HeartBar;
import com.catface.cfhearts.core.heartbar.IHeartBar;
import com.catface.cfhearts.network.packets.PacketSyncHeartBar;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

@Mod.EventBusSubscriber
public class ServerEventHandler {

    @SubscribeEvent
    public static void onPlayerJoin(PlayerEvent.PlayerLoggedInEvent event){
        EntityPlayer player = event.player;
        IHeartBar bar = HeartBar.getHeartBar(player);
        if(bar != null && player instanceof EntityPlayerMP){
            CFHearts.packetHandler.network.sendTo((IMessage) new PacketSyncHeartBar(bar), (EntityPlayerMP) player);
        }
    }
}
