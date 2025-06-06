package com.catface.cfhearts;

import com.catface.cfhearts.command.CommandHeartAbility;
import com.catface.cfhearts.command.CommandHeartBar;
import com.catface.cfhearts.core.CommonProxy;
import com.catface.cfhearts.core.CreativeTabHearts;
import com.catface.cfhearts.network.PacketHandler;
import net.minecraft.init.Blocks;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import org.apache.logging.log4j.Logger;

@Mod(modid = CFHearts.MODID, name = CFHearts.NAME, version = CFHearts.VERSION)
public class CFHearts
{
    public static final String MODID = "cfhearts";
    public static final String NAME = "CF Hearts";
    public static final String VERSION = "1.0";

    @SidedProxy(clientSide = "com.catface.cfhearts.client.ClientProxy",serverSide = "com.catface.cfhearts.core.CommonProxy")
    public static CommonProxy proxy;

    @Mod.Instance
    public static CFHearts instance;

    public static PacketHandler packetHandler;

    public static CreativeTabHearts tabHearts = new CreativeTabHearts("tabHearts");

    private static Logger logger;

    public static Logger getLogger() {
        return logger;
    }

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        logger = event.getModLog();
        packetHandler = new PacketHandler();
        proxy.preInit(event);

    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        proxy.init(event);
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event){
        proxy.postInit(event);
    }

    @EventHandler
    public void onServerStart(FMLServerStartingEvent event){
        event.registerServerCommand(new CommandHeartBar());
        event.registerServerCommand(new CommandHeartAbility());
    }
}
