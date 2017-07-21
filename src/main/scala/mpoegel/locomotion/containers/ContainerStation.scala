package mpoegel.locomotion.containers

import mpoegel.locomotion.tiles.TileStation
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.inventory.{Container, IInventory, Slot}
import net.minecraft.item.ItemStack
import net.minecraftforge.items.{CapabilityItemHandler, IItemHandler, SlotItemHandler}

class ContainerStation(playerInventory: IInventory, tile: TileStation) extends Container {
  this.addOwnSlots()
  this.addPlayerSlots(this.playerInventory)

  private def addPlayerSlots(playerInventory: IInventory): Unit =
  {
    // Slots for the player's main inventory
    for (row <- 0 to 2) {
      for (col <- 0 to 8) {
        val x = 10 + col * 18
        val y = row * 18 + 70
        this.addSlotToContainer(new Slot(playerInventory, col + row * 9 + 10, x, y))
      }
    }
    // Slots for the player's hotbar
    for (col <- 0 to 8) {
      val x = 10 + col * 18
      val y = 58 + 70
      this.addSlotToContainer(new Slot(playerInventory, col, x, y))
    }
  }

  def addOwnSlots(): Unit =
  {
    val itemHandler: IItemHandler = this.tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null)
    var x = 10
    val y = 6
    // Slots for our block
    for (slotIndex <- 0 until itemHandler.getSlots) {
      this.addSlotToContainer(new SlotItemHandler(itemHandler, slotIndex, x, y))
      x += 18
    }
  }

  override def transferStackInSlot(playerIn: EntityPlayer, index: Int): ItemStack =
  {
    var itemStack: ItemStack = ItemStack.EMPTY
    val slot: Slot = this.inventorySlots.get(index)
    if (slot != null && slot.getHasStack) {
      val itemStack1 = slot.getStack
      itemStack = itemStack1.copy()
      if (index < TileStation.SIZE) {
        if (!this.mergeItemStack(itemStack1, TileStation.SIZE, this.inventorySlots.size(), true)) {
          return ItemStack.EMPTY
        }
      } else if (!this.mergeItemStack(itemStack1, 0, TileStation.SIZE, false)) {
        return ItemStack.EMPTY
      }
      if (itemStack1.isEmpty) {
        slot.putStack(ItemStack.EMPTY)
      } else {
        slot.onSlotChanged()
      }
    }
    itemStack
  }

  override def canInteractWith(playerIn: EntityPlayer): Boolean =
  {
    true
  }
}
