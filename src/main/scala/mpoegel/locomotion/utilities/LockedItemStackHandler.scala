package mpoegel.locomotion.utilities

import net.minecraft.item.{Item, ItemStack}
import net.minecraftforge.items.ItemStackHandler

class LockedItemStackHandler(size: Int, item: Item) extends ItemStackHandler(size) {
  override def insertItem(slot: Int, stack: ItemStack, simulate: Boolean): ItemStack = {
    if (item != null && item == stack.getItem) {
      return super.insertItem(slot, stack, simulate)
    }
    stack
  }
}
