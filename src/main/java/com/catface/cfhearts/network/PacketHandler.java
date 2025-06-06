package com.catface.cfhearts.network;


import com.catface.cfhearts.CFHearts;
import com.catface.cfhearts.network.packets.PacketSyncHeartBar;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class PacketHandler {

    private static int packetClientID = 0;
    private static int packetServerID = 0;

    public SimpleNetworkWrapper network;

    public PacketHandler(){
        network = new SimpleNetworkWrapper(CFHearts.MODID);
    }

    public void registerMessagesClient(){
        network.registerMessage(PacketSyncHeartBar.HandlerClient.class, PacketSyncHeartBar.class,packetClientID++,Side.CLIENT);
    }

    public void registerMessagesServer(){
        network.registerMessage(PacketSyncHeartBar.HandlerServer.class, PacketSyncHeartBar.class,packetServerID++,Side.SERVER);
    }


}
