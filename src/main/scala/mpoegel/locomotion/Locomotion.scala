package mpoegel.locomotion

import java.util.logging.Logger

import mpoegel.locomotion.proxy.{ClientProxy, CommonProxy}
import net.minecraftforge.fml.common.event.{FMLInitializationEvent, FMLPostInitializationEvent, FMLPreInitializationEvent}
import net.minecraftforge.fml.common.{Mod, SidedProxy}

/**
  * Created by poegem on 7/17/2017.
  */
object Locomotion {
  final val MODID = "locomotion"
  final val MODNAME = "Locomotion"
  final val VERSION = "0.0.1"

  @Mod.Instance
  var instance: Locomotion = _

  val LOGGER: Logger = Logger.getLogger(Locomotion.MODID)

  @SidedProxy(clientSide = "mpoegel.locomotion.proxy.ClientProxy",
              serverSide = "mpoegel.locomotion.proxy.ServerProxy")
  var proxy: CommonProxy = new ClientProxy()
}

@Mod(
  modid = Locomotion.MODID,
  name = Locomotion.MODNAME,
  version = Locomotion.VERSION
)
class Locomotion {
  @Mod.EventHandler
  def preInit(event: FMLPreInitializationEvent): Unit =
  {
    Locomotion.proxy.preInit(event)
  }

  @Mod.EventHandler
  def init(event: FMLInitializationEvent): Unit =
  {
    Locomotion.proxy.init(event)
  }

  @Mod.EventHandler
  def postInit(event: FMLPostInitializationEvent): Unit =
  {
    Locomotion.proxy.postInit(event)
  }
}
