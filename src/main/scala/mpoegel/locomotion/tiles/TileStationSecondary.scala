package mpoegel.locomotion.tiles

import mpoegel.locomotion.utilities.LockedItemStackHandler
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.util.EnumFacing
import net.minecraftforge.common.capabilities.Capability
import net.minecraftforge.items.CapabilityItemHandler


abstract class TileStationSecondary extends TileStation {
  protected val input = new LockedItemStackHandler(1, this.getItemConsumed)
  protected val output = new LockedItemStackHandler(1, null)

  override def getCount: Int = this.output.getStackInSlot(0).getCount

  override def writeToNBT(compound: NBTTagCompound): NBTTagCompound = {
    compound.setTag("input", this.input.serializeNBT())
    compound.setTag("output", this.output.serializeNBT())
    super.writeToNBT(compound)
  }

  override def readFromNBT(compound: NBTTagCompound): Unit = {
    this.input.deserializeNBT(compound.getCompoundTag("input"))
    this.output.deserializeNBT(compound.getCompoundTag("output"))
    super.readFromNBT(compound)
  }

  override def update(): Unit = {
    if (getWorld.isRemote) {
      return
    }
    cycle += 1
    if (cycle >= this.getProductionRate) {
      cycle = 0
      val inStack = this.input.getStackInSlot(0)
      var outStack = this.output.getStackInSlot(0)
      var flag = true
      if (outStack == null || outStack == ItemStack.EMPTY) {
        outStack = new ItemStack(this.getItemProduced)
        flag = false
      }
      if (inStack != null && outStack.getCount < this.getStorageLimit &&
        inStack.getCount >= this.getInputOutputRatio) {
        inStack.setCount(inStack.getCount - this.getInputOutputRatio)
        if (flag) {
          outStack.setCount(outStack.getCount + 1)
        }
        this.input.setStackInSlot(0, inStack)
        this.output.setStackInSlot(0, outStack)
      }
      markDirty()
    }
  }

  @SuppressWarnings(Array("unchecked"))
  override def getCapability[T](capability: Capability[T], facing: EnumFacing): T = {
    if (capability eq CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
      if (facing == EnumFacing.DOWN) return this.output.asInstanceOf[T]
      return this.input.asInstanceOf[T]
    }
    super.getCapability(capability, facing)

  }

  override def hasCapability(capability: Capability[_], facing: EnumFacing): Boolean = {
    (capability eq CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) || super.hasCapability(capability, facing)
  }
}
