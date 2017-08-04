package mpoegel.locomotion.tiles

import net.minecraft.item.Item
import net.minecraft.tileentity.TileEntity
import net.minecraft.util.ITickable


abstract class TileStation extends TileEntity with ITickable {
  protected var cycle: Int = 0

  def getItemProduced: Item

  def getStorageLimit: Int

  def getItemConsumed: Item

  def getProductionRate: Int

  def getInputOutputRatio: Int

  def getCount: Int

  override def update(): Unit
}