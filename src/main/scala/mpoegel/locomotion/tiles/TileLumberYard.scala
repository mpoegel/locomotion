package mpoegel.locomotion.tiles

import mpoegel.locomotion.ModItems
import net.minecraft.item.Item


class TileLumberYard extends TileStationPrimary {
  def getItemProduced: Item = ModItems.lumber_crate

  def getStorageLimit: Int = 32

  def getProductionRate: Int = 20
}
