package com.catface.cfhearts.client;

import com.catface.cfhearts.CFHearts;
import com.catface.cfhearts.client.gui.RenderHeartBar;
import com.catface.cfhearts.core.CommonProxy;
import com.catface.cfhearts.registries.EntityLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy extends CommonProxy {

    @Override
    public void preInit(FMLPreInitializationEvent event) {
        super.preInit(event);
        CFHearts.packetHandler.registerMessagesClient();
        MinecraftForge.EVENT_BUS.register(new RenderHeartBar());
        EntityLoader.loadModels();
    }

    @Override
    public void init(FMLInitializationEvent event) {
        super.init(event);
    }

    @Override
    public void postInit(FMLPostInitializationEvent event) {
        super.postInit(event);
    }
}
