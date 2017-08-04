package mpoegel.locomotion.tiles

import mpoegel.locomotion.utilities.LockedItemStackHandler
import net.minecraft.item.{Item, ItemStack}
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.util.EnumFacing
import net.minecraftforge.common.capabilities.Capability
import net.minecraftforge.items.CapabilityItemHandler


abstract class TileStationPrimary extends TileStation {
  protected val output = new LockedItemStackHandler(1, null)

  def getItemConsumed: Item = null

  def getInputOutputRatio: Int = 1

  override def writeToNBT(compound: NBTTagCompound): NBTTagCompound = {
    compound.setTag("output", this.output.serializeNBT())
    super.writeToNBT(compound)
  }

  override def readFromNBT(compound: NBTTagCompound): Unit = {
    this.output.deserializeNBT(compound.getCompoundTag("output"))
    super.readFromNBT(compound)
  }

  override def getCount: Int = this.output.getStackInSlot(0).getCount

  override def update(): Unit = {
    if (getWorld.isRemote) return
    cycle += 1
    if (cycle >= this.getProductionRate) {
      cycle = 0
      var stack = this.output.getStackInSlot(0)
      if (stack == null || stack == ItemStack.EMPTY) {
        stack = new ItemStack(this.getItemProduced)
      } else {
        stack.setCount(Math.min(this.getStorageLimit, stack.getCount + 1))
      }
      this.output.setStackInSlot(0, stack)
      markDirty()
    }
  }

  @SuppressWarnings(Array("unchecked"))
  override def getCapability[T](capability: Capability[T], facing: EnumFacing): T = {
    if (capability eq CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
      return this.output.asInstanceOf[T]
    }
    super.getCapability(capability, facing)

  }

  override def hasCapability(capability: Capability[_], facing: EnumFacing): Boolean = {
    (capability eq CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) || super.hasCapability(capability, facing)
  }
}