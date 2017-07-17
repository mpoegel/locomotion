package com.mymod;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import java.util.logging.Logger;

/**
 * Created by poegem on 7/13/2017.
 */
@Mod(
        modid = MyMod.MODID,
        name = MyMod.MODNAME,
        version = MyMod.VERSION
)
public class MyMod {
    public static final String MODID = "mymod";
    public static final String MODNAME = "MyMod";
    public static final String VERSION = "0.0.1";

    @Mod.Instance
    public static MyMod instance;

    public static final Logger LOGGER = Logger.getLogger(MyMod.MODID);

    @SidedProxy(clientSide = "com.mymod.ClientProxy", serverSide = "com.mymod.ServerProxy")
    public static CommonProxy proxy;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        proxy.preInit(event);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event)
    {
        proxy.init(event);
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
        proxy.postInit(event);
    }
}
