package mpoegel.locomotion.tiles

import javax.annotation.Nullable

import net.minecraft.entity.player.EntityPlayer
import net.minecraft.init.Blocks
import net.minecraft.inventory.IInventory
import net.minecraft.inventory.ISidedInventory
import net.minecraft.item.{Item, ItemStack}
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.tileentity.TileEntity
import net.minecraft.util.EnumFacing
import net.minecraft.util.ITickable
import net.minecraft.util.text.TextComponentString
import net.minecraftforge.common.capabilities.Capability
import net.minecraftforge.items.{CapabilityItemHandler, ItemHandlerHelper, ItemStackHandler}
import net.minecraftforge.items.wrapper.InvWrapper


object TileStation {
  def putStackInInventoryAllSlots(inventoryIn: IInventory, stack: ItemStack, @Nullable side: EnumFacing): ItemStack = {
    var newStack: ItemStack = stack.copy()
    if (inventoryIn.isInstanceOf[ISidedInventory] && side != null && !inventoryIn.isInstanceOf[TileStation] &&
        inventoryIn.isItemValidForSlot(0, newStack.copy)) {
      val iSidedInventory = inventoryIn.asInstanceOf[ISidedInventory]
      val aint = iSidedInventory.getSlotsForFace(side)
      var k = 0
      while (k < aint.length && newStack != null && newStack.getCount > 0) {
        newStack = insertStack(inventoryIn, newStack, aint(k), side)
        k += 1
      }
    }
    else {
      val i = inventoryIn.getSizeInventory
      var j = 0
      while (j < i && newStack != null && newStack.getCount > 0) {
        newStack = insertStack(inventoryIn, newStack, j, side)
        j += 1
      }
    }
    if (newStack != null && newStack.getCount == 0) newStack = ItemStack.EMPTY
    newStack
  }

  private def insertStack(inventoryIn: IInventory, stack: ItemStack, index: Int, side: EnumFacing) = {
    val itemstack = inventoryIn.getStackInSlot(index)
    var newStack = stack.copy()
    if (canInsertItemInSlot(inventoryIn, newStack, index, side)) {
      var flag = false
      if (itemstack == null) { // Forge: BUGFIX: Again, make things respect max stack sizes.
        val max = Math.min(newStack.getMaxStackSize, inventoryIn.getInventoryStackLimit)
        if (max >= newStack.getCount) {
          inventoryIn.setInventorySlotContents(index, newStack)
          newStack = null
        }
        else inventoryIn.setInventorySlotContents(index, newStack.splitStack(max))
        flag = true
      }
      else if (canCombine(itemstack, newStack)) {
        val max = Math.min(newStack.getMaxStackSize, inventoryIn.getInventoryStackLimit)
        if (max > itemstack.getCount) {
          val i = max - itemstack.getCount
          val j = Math.min(newStack.getCount, i)
          newStack.setCount(newStack.getCount - j)
          itemstack.setCount(itemstack.getCount + j)
          flag = j > 0
        }
      }
    }
    newStack
  }

  private def canInsertItemInSlot(inventoryIn: IInventory, stack: ItemStack, index: Int, side: EnumFacing) = {
    if (!inventoryIn.isItemValidForSlot(index, stack)) {
      false
    } else {
      val iSidedInventory = inventoryIn.asInstanceOf[ISidedInventory]
      !inventoryIn.isInstanceOf[ISidedInventory] || iSidedInventory.canInsertItem(index, stack, side)
    }
  }

  private def canCombine(stack1: ItemStack, stack2: ItemStack) = {
    if (stack1.getItem ne stack2.getItem) {
      false
    } else if (stack1.getMetadata != stack2.getMetadata) {
      false
    } else if (stack1.getCount > stack1.getMaxStackSize) {
      false
    } else {
      ItemStack.areItemStackTagsEqual(stack1, stack2)
    }
  }
}

class TileStation extends TileEntity with ISidedInventory
  with ITickable {

  private val inventory = new ItemStackHandler(this.getSize)
  private var cycle: Int = 0

  def getSize: Int = 1

  def getItemsProduced: Array[Item] = {
    val arr = new Array[Item](1)
    arr(0) = Item.getItemFromBlock(Blocks.COBBLESTONE)
    arr
  }

  def getStorageLimits: Array[Int] = {
    val arr = new Array[Int](1)
    arr(0) = 32
    arr
  }

  def getItemsConsumed: Array[Item] = {
    val arr = new Array[Item](1)
    arr(0) = null
    arr
  }

  def getProductionRates: Array[Int] = {
    val arr = new Array[Int](1)
    arr(0) = 20
    arr
  }

  override def writeToNBT(compound: NBTTagCompound): NBTTagCompound = {
    compound.setTag("inventory", this.inventory.serializeNBT())
    super.writeToNBT(compound)
  }

  override def readFromNBT(compound: NBTTagCompound): Unit = {
    this.inventory.deserializeNBT(compound.getCompoundTag("inventory"))
    super.readFromNBT(compound)
  }

  override def getSizeInventory = 1

  override def getStackInSlot(index: Int): ItemStack = this.inventory.getStackInSlot(0)

  override def decrStackSize(index: Int, count: Int): ItemStack = {
    val stack = this.inventory.getStackInSlot(0)
    if (stack != null && stack.getCount > count) {
      stack.splitStack(count)
    }
    else {
      val tmp = stack
      this.inventory.setStackInSlot(0, ItemStack.EMPTY)
      tmp
    }
  }

  override def removeStackFromSlot(index: Int): ItemStack = {
    if (index == 0) {
      val tmp = this.inventory.getStackInSlot(0)
      this.inventory.setStackInSlot(0, ItemStack.EMPTY)
      tmp
    } else {
      null
    }
  }

  override def setInventorySlotContents(index: Int, stack: ItemStack): Unit = {
    if (index == 0) this.inventory.setStackInSlot(0, stack)
  }

  override def getInventoryStackLimit: Int = this.getStorageLimits(0)

  def isUseableByPlayer(player: EntityPlayer) = true

  override def openInventory(player: EntityPlayer): Unit = {
  }

  override def closeInventory(player: EntityPlayer): Unit = {
  }

  override def isItemValidForSlot(index: Int, stack: ItemStack): Boolean = {
    index == 0 && stack != null && (stack.getItem eq this.getItemsProduced(0))
  }

  override def getField(id: Int) = 0

  override def setField(id: Int, value: Int): Unit = {
  }

  override def getFieldCount = 0

  override def clear(): Unit = {
    this.inventory.setStackInSlot(0, ItemStack.EMPTY)
  }

  override def getName: String = Blocks.COBBLESTONE.getLocalizedName

  override def hasCustomName = false

  override def getDisplayName = new TextComponentString(getName)

  override def getSlotsForFace(side: EnumFacing): Array[Int] = Array[Int](0)

  override def canInsertItem(index: Int, itemStackIn: ItemStack, direction: EnumFacing) = false

  override def canExtractItem(index: Int, stack: ItemStack, direction: EnumFacing): Boolean = {
    index == 0 && stack != null && (stack.getItem eq this.getItemsProduced(0))
  }

  override def update(): Unit = {
    if (getWorld.isRemote) return
    cycle += 1
    if (cycle >= this.getProductionRates(0)) {
      cycle = 0
      var stack = this.inventory.getStackInSlot(0)
      if (stack == null || stack == ItemStack.EMPTY) stack = new ItemStack(this.getItemsProduced(0))
      else stack.setCount(Math.min(this.getStorageLimits(0), stack.getCount + 1))
      this.setInventorySlotContents(0, stack)
      val tile = getWorld.getTileEntity(pos.offset(EnumFacing.UP))
      if (tile != null && tile.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.DOWN)) {
        val handler = tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.DOWN)
        if (getStackInSlot(0) != null) {
          val stack = getStackInSlot(0).copy
          stack.setCount(1)
          val stack1 = ItemHandlerHelper.insertItem(handler, stack, true)
          if (stack1 == null || stack1.getCount == 0) {
            ItemHandlerHelper.insertItem(handler, this.decrStackSize(0, 1), false)
            markDirty()
          }
        }
      }
      else if (tile.isInstanceOf[IInventory]) {
        val iinventory = tile.asInstanceOf[IInventory]
        if (isInventoryFull(iinventory, EnumFacing.UP)) return
        else if (getStackInSlot(0) != null) {
          val stack = getStackInSlot(0).copy
          val stack1 = TileStation.putStackInInventoryAllSlots(iinventory, decrStackSize(0, 1), EnumFacing.UP)
          if (stack1 == null || stack1.getCount == 0) iinventory.markDirty()
          else setInventorySlotContents(0, stack)
        }
      }
      markDirty()
    }
  }

  protected def isInventoryFull(inventoryIn: IInventory, side: EnumFacing): Boolean = {
    if (inventoryIn.isInstanceOf[ISidedInventory]) {
      val isidedinventory = inventoryIn.asInstanceOf[ISidedInventory]
      val aint = isidedinventory.getSlotsForFace(side)
      for (k <- aint) {
        val itemstack1 = isidedinventory.getStackInSlot(k)
        if (itemstack1 == null || itemstack1.getCount != itemstack1.getMaxStackSize) return false
      }
    } else {
      val i = inventoryIn.getSizeInventory
      var j = 0
      while (j < i) {
        val itemstack = inventoryIn.getStackInSlot(j)
        if (itemstack == null || itemstack.getCount != itemstack.getMaxStackSize) {
          return false
        }
        j += 1
      }
    }
    true
  }

  override def isUsableByPlayer(player: EntityPlayer): Boolean = true

  override def isEmpty: Boolean = getStackInSlot(0).getCount == 0

  @SuppressWarnings(Array("unchecked"))
  override def getCapability[T](capability: Capability[T], facing: EnumFacing): T = {
    if (capability eq CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) return new InvWrapper(this).asInstanceOf[T]
    super.getCapability(capability, facing)
  }

  override def hasCapability(capability: Capability[_], facing: EnumFacing): Boolean = {
    (capability eq CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) || super.hasCapability(capability, facing)
  }
}