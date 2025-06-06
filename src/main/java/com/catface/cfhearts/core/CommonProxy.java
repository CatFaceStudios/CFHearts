package com.catface.cfhearts.core;

import com.catface.cfhearts.CFHearts;
import com.catface.cfhearts.core.heartbar.HeartBar;
import com.catface.cfhearts.core.heartbar.HeartBarProvider;
import com.catface.cfhearts.core.heartbar.HeartBarStorage;
import com.catface.cfhearts.core.heartbar.IHeartBar;

import com.catface.cfhearts.registries.EntityLoader;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class CommonProxy {

    public void preInit(FMLPreInitializationEvent event){
        CapabilityManager.INSTANCE.register(IHeartBar.class, new HeartBarStorage(), HeartBar::new);
        CFHearts.packetHandler.registerMessagesServer();
        EntityLoader.loadEntities();
    }

    public void init(FMLInitializationEvent event){

    }

    public void postInit(FMLPostInitializationEvent event){

    }
}
