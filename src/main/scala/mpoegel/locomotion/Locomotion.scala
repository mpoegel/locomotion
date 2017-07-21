package mpoegel.locomotion

import java.util.logging.Logger

import mpoegel.locomotion.guis.GuiHandler
import mpoegel.locomotion.proxy.{ClientProxy, CommonProxy}
import mpoegel.locomotion.tabs.TabLocomotion
import net.minecraftforge.fml.common.Mod.Instance
import net.minecraftforge.fml.common.event.{FMLInitializationEvent, FMLPostInitializationEvent, FMLPreInitializationEvent}
import net.minecraftforge.fml.common.network.NetworkRegistry
import net.minecraftforge.fml.common.{Mod, SidedProxy}

/**
  * Created by poegem on 7/17/2017.
  */
object Locomotion {
  final val MODID = "locomotion"
  final val MODNAME = "Locomotion"
  final val VERSION = "0.0.1"

  var tabLocomotion: TabLocomotion = _

  @Instance(MODID)
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
  Locomotion.instance = this

  @Mod.EventHandler
  def preInit(event: FMLPreInitializationEvent): Unit =
  {
    Locomotion.tabLocomotion = new TabLocomotion("Locomotion")
    Locomotion.proxy.preInit(event)
  }

  @Mod.EventHandler
  def init(event: FMLInitializationEvent): Unit =
  {
    NetworkRegistry.INSTANCE.registerGuiHandler(this, new GuiHandler())
    Locomotion.proxy.init(event)
  }

  @Mod.EventHandler
  def postInit(event: FMLPostInitializationEvent): Unit =
  {
    Locomotion.proxy.postInit(event)
  }
}
