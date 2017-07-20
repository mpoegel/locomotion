package mpoegel.locomotion

import mpoegel.locomotion.items.ItemLumberCrate
import net.minecraftforge.fml.common.registry.GameRegistry
import net.minecraftforge.fml.relauncher.{Side, SideOnly}

object ModItems {
  @GameRegistry.ObjectHolder("locomotion:lumber_crate")
  var lumber_crate: ItemLumberCrate = _

  @SideOnly(Side.CLIENT)
  def initModels(): Unit =
  {
    lumber_crate.initModel()
  }
}
