package mpoegel.locomotion.proxy

import mpoegel.locomotion.ModBlocks
import mpoegel.locomotion.blocks.BlockCool
import net.minecraft.block.Block
import net.minecraft.item.{Item, ItemBlock}
import net.minecraftforge.event.RegistryEvent
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.event._
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent

/**
  * Created by poegem on 7/17/2017.
  */
@Mod.EventBusSubscriber
object CommonProxy {
  @SubscribeEvent
  def registerBlocks(event: RegistryEvent.Register[Block]): Unit =
  {
    val blockCool = new BlockCool()
    event.getRegistry.register(blockCool)
    ModBlocks.blockCool = blockCool
  }

  @SubscribeEvent
  def registerItems(event: RegistryEvent.Register[Item]): Unit =
  {
    event.getRegistry.register(new ItemBlock(ModBlocks.blockCool).setRegistryName(ModBlocks.blockCool.getRegistryName))
  }
}

@Mod.EventBusSubscriber
class CommonProxy {
  def preInit(event: FMLPreInitializationEvent): Unit =
  {
    return;
  }

  def init(event: FMLInitializationEvent): Unit =
  {
    return
  }

  def postInit(event: FMLPostInitializationEvent): Unit =
  {
    return
  }

  def serverStarting(event: FMLServerStartingEvent): Unit =
  {
    return
  }

  def serverStopping(event: FMLServerStoppingEvent): Unit =
  {
    return
  }
}
