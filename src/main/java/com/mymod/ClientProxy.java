package com.mymod;

import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.obj.OBJLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.*;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

/**
 * Created by poegem on 7/13/2017.
 */
@Mod.EventBusSubscriber(Side.CLIENT)
public class ClientProxy extends CommonProxy {
    @Override
    public void preInit(FMLPreInitializationEvent event)
    {
        super.preInit(event);
        OBJLoader.INSTANCE.addDomain(MyMod.MODID);
    }

    @Override
    public void init(FMLInitializationEvent event)
    {
        super.init(event);

    }

    @Override
    public void postInit(FMLPostInitializationEvent event)
    {
        super.postInit(event);
    }

    @SubscribeEvent
    public static void registerModels(ModelRegistryEvent event)
    {
        ModBlocks.initModel();
    }

    @Override
    public void serverStarting(FMLServerStartingEvent event)
    {
        super.serverStarting(event);
    }

    @Override
    public void serverStopping(FMLServerStoppingEvent event)
    {
        super.serverStopping(event);
    }
}
