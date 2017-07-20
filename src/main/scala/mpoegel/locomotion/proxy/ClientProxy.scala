package mpoegel.locomotion.proxy

import mpoegel.locomotion.{Locomotion, ModBlocks, ModItems}
import net.minecraftforge.client.event.ModelRegistryEvent
import net.minecraftforge.client.model.obj.OBJLoader
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.event._
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.relauncher.Side

/**
  * Created by poegem on 7/17/2017.
  */
@Mod.EventBusSubscriber(Array(Side.CLIENT))
object ClientProxy {
  @SubscribeEvent
  def registerModels(event: ModelRegistryEvent): Unit =
  {
    ModBlocks.initModels()
    ModItems.initModels()
  }
}

@Mod.EventBusSubscriber(Array(Side.CLIENT))
class ClientProxy extends CommonProxy {
  override def preInit(event: FMLPreInitializationEvent): Unit =
  {
    super.preInit(event)
    OBJLoader.INSTANCE.addDomain(Locomotion.MODID)
  }

  override def init(event: FMLInitializationEvent): Unit =
  {
    super.init(event)
  }

  override def postInit(event: FMLPostInitializationEvent): Unit =
  {
    super.postInit(event)
  }

  override def serverStarting(event: FMLServerStartingEvent): Unit =
  {
    super.serverStarting(event)
  }

  override def serverStopping(event: FMLServerStoppingEvent): Unit =
  {
    super.serverStopping(event)
  }
}