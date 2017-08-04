package mpoegel.locomotion.containers

import mpoegel.locomotion.tiles.TileStation
import net.minecraft.inventory.IInventory
import net.minecraftforge.items.{CapabilityItemHandler, IItemHandler, SlotItemHandler}

class ContainerStationPrimary(playerInventory: IInventory, tile: TileStation) extends
  ContainerStation(playerInventory, tile) {

  override def addOwnSlots(): Unit = {
    val itemHandler: IItemHandler = this.tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null)
    val x = 82
    val y = 23
    val slotIndex = 0
    this.addSlotToContainer(new SlotItemHandler(itemHandler, slotIndex, x, y))
  }
}
