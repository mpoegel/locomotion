package com.mymod;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.*;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.io.File;

/**
 * Created by poegem on 7/13/2017.
 */
@Mod.EventBusSubscriber
public class CommonProxy {
    public static Configuration config;

    public void preInit(FMLPreInitializationEvent event)
    {
//        File directory event.getModConfigurationDirectory();
//        config = new Configuration(new File(directory.getPath(), "mymod.cfg"));
//        Config.readConfig();


    }

    public void init(FMLInitializationEvent event)
    {
        return;
    }

    public void postInit(FMLPostInitializationEvent event)
    {
        return;
    }

    public void serverStarting(FMLServerStartingEvent event)
    {
        return;
    }

    public void serverStopping(FMLServerStoppingEvent event)
    {
        return;
    }

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event)
    {
        BlockCool blockCool = new BlockCool();
        event.getRegistry().register(blockCool);
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event)
    {
        event.getRegistry().register(new ItemBlock(ModBlocks.blockCool).setRegistryName(ModBlocks.blockCool.getRegistryName()));
    }
}
