package mpoegel.locomotion.tiles

import mpoegel.locomotion.ModItems
import net.minecraft.item.Item

/**
  * Created by Matt Poegel on 7/24/2017.
  */
class TilePaperMill extends TileStationSecondary {
  def getItemProduced: Item = ModItems.paper_crate

  def getStorageLimit: Int = 32

  def getItemConsumed: Item = ModItems.lumber_crate

  def getProductionRate: Int = 20

  def getInputOutputRatio: Int = 2
}
