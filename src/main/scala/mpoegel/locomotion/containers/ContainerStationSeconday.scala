package mpoegel.locomotion.containers

import mpoegel.locomotion.tiles.TileStation
import net.minecraft.inventory.IInventory
import net.minecraft.util.EnumFacing
import net.minecraftforge.items.{CapabilityItemHandler, IItemHandler, SlotItemHandler}

class ContainerStationSeconday(playerInventory: IInventory, tile: TileStation) extends ContainerStation(playerInventory, tile) {
  override def addOwnSlots(): Unit = {
    val inputItemHandler: IItemHandler = this.tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null)
    val outputItemHandler: IItemHandler = this.tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.DOWN)
    var x = 46
    val y = 23
    this.addSlotToContainer(new SlotItemHandler(inputItemHandler, 0, x, y))
    x = 118
    this.addSlotToContainer(new SlotItemHandler(outputItemHandler, 0, x , y))
  }
}
