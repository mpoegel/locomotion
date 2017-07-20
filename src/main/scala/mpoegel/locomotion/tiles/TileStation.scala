package mpoegel.locomotion.tiles

import net.minecraft.entity.player.EntityPlayer
import net.minecraft.init.Blocks
import net.minecraft.inventory.{IInventory, ISidedInventory}
import net.minecraft.item.{Item, ItemStack}
import net.minecraft.tileentity.TileEntity
import net.minecraft.util.{EnumFacing, ITickable}
import net.minecraftforge.common.capabilities.Capability
import net.minecraftforge.items.wrapper.InvWrapper
import net.minecraftforge.items.{CapabilityItemHandler, IItemHandler, ItemHandlerHelper}

import scala.reflect.ClassTag


object TileStation {
  val MAX_STACK: Int = 32
  val UPDATE_INTERVAL: Int = 20

  def putStackInInventoryAllSlots(iInventory: IInventory, stack: ItemStack, side: EnumFacing): ItemStack =
  {
    var returnStack: ItemStack = stack.copy()
    if (iInventory.isInstanceOf[ISidedInventory] && side != null && !iInventory.isInstanceOf[TileStation] &&
      iInventory.isItemValidForSlot(0, stack.copy())) {
      val iSidedInventory: ISidedInventory = iInventory.asInstanceOf[ISidedInventory]
      val slots: Array[Int] = iSidedInventory.getSlotsForFace(side)
      var i: Int = 0
      while (i < slots.length && returnStack != null && returnStack.getCount() > 0) {
        returnStack = TileStation.insertStack(iInventory, returnStack, slots(i), side)
        i += 1
      }
    } else {
      val i: Int = iInventory.getSizeInventory()
      var k: Int = 0
      while (k < i && returnStack != null && returnStack.getCount() > 0) {
        returnStack = TileStation.insertStack(iInventory, returnStack, k, side)
        k += 1
      }
    }
    if (returnStack != null && returnStack.getCount() == 0) {
      returnStack = null
    }
    return returnStack
  }

  private def insertStack(iInventory: IInventory, itemStack: ItemStack, index: Int, side: EnumFacing): ItemStack =
  {
    val itemStack2: ItemStack = iInventory.getStackInSlot(index)
    var returnStack: ItemStack = itemStack.copy()
    if (TileStation.canInsertItemInSlot(iInventory, itemStack, index, side)) {
      var flag: Boolean = false
      if (itemStack2 == null) {
        val max: Int = Math.min(itemStack.getMaxStackSize(), iInventory.getInventoryStackLimit())
        if (max >= itemStack.getCount()) {
          iInventory.setInventorySlotContents(index, itemStack)
          returnStack = null
        } else {
          iInventory.setInventorySlotContents(index, itemStack.splitStack(max))
        }
        flag = true
      } else if (TileStation.canCombine(itemStack, itemStack2)) {
        val max: Int = Math.min(itemStack.getMaxStackSize(), iInventory.getInventoryStackLimit())
        if (max > itemStack.getCount()) {
          val i: Int = max - itemStack2.getCount()
          val j: Int = Math.min(itemStack.getCount(), i)
          itemStack.setCount(itemStack.getCount() - j)
          itemStack2.setCount(itemStack2.getCount() + j)
          flag = j > 0
        }
      }
    }
    return returnStack
  }

  private def canInsertItemInSlot(iInventory: IInventory, itemStack: ItemStack, index: Int, side: EnumFacing): Boolean =
  {
    if (!iInventory.isItemValidForSlot(index, itemStack)) {
      return false
    } else {
      return !(iInventory.isInstanceOf[ISidedInventory]) ||
        iInventory.asInstanceOf[ISidedInventory].canInsertItem(index, itemStack, side)
    }
  }

  private def canCombine(stack1: ItemStack, stack2: ItemStack): Boolean =
  {
    if (stack1.getItem() != stack2.getItem()) {
      return false
    } else if (stack1.getMetadata() != stack2.getMetadata()) {
      return false
    } else if (stack1.getCount() > stack1.getMaxStackSize()) {
      return false
    } else {
      return ItemStack.areItemStackTagsEqual(stack1, stack2)
    }
  }
}

/**
  * Created by Matt Poegel on 7/17/2017.
  * This class is heavily based upon the TileEntityCobblegen.java class from the Tiny Progressions mod by Kashdeya
  */
class TileStation(produced: Array[Item], consumed: Array[Item], name: String) extends TileEntity with ISidedInventory
    with ITickable {
  private var tick: Int = 0
  private var count: Int = 0
  private var stack: ItemStack = _

  override def getSizeInventory: Int = return 1

  override def getStackInSlot(index: Int): ItemStack = return this.stack

  override def decrStackSize(index: Int, count: Int): ItemStack =
  {
    if (this.stack != null && this.stack.getMaxStackSize() > this.count) {
      return this.stack.splitStack(this.count)
    } else {
      val tmp: ItemStack = this.stack
      this.stack = null
      return tmp
    }
  }

  override def removeStackFromSlot(index: Int): ItemStack =
  {
    if (index == 0) {
      val tmp: ItemStack = this.stack
      this.stack = null
      return tmp
    } else {
      return null
    }
  }

  override def setInventorySlotContents(index: Int, stack: ItemStack): Unit =
  {
    if (index == 0) {
      this.stack = stack
    }
  }

  override def getInventoryStackLimit: Int = return TileStation.MAX_STACK

  override def isUsableByPlayer(player: EntityPlayer): Boolean = return true

  override def openInventory(player: EntityPlayer): Unit = return

  override def closeInventory(player: EntityPlayer): Unit = return

  override def isItemValidForSlot(index: Int, stack: ItemStack): Boolean =
  {
    return index == 0 &&
           stack != null &&
           stack.getItem() == Item.getItemFromBlock(Blocks.COBBLESTONE)
  }

  override def getField(id: Int): Int = return 0

  override def setField(id: Int, value: Int): Unit = return

  override def getFieldCount: Int = return 0

  override def clear(): Unit = this.stack = null

  override def getName: String = return this.name

  override def hasCustomName: Boolean = return false

  override def getSlotsForFace(side: EnumFacing): Array[Int] = return new Array[Int](0)

  override def canInsertItem(index: Int, itemStackIn: ItemStack, direction: EnumFacing): Boolean = return false

  override def canExtractItem(index: Int, stack: ItemStack, direction: EnumFacing): Boolean =
  {
    return index == 0 &&
           stack != null &&
           stack.getItem() == Item.getItemFromBlock(Blocks.COBBLESTONE)
  }

  override def isEmpty: Boolean = return this.stack == null

  override def update(): Unit =
  {
    if (getWorld().isRemote) {
      return;
    }
    this.tick += 1
    if (this.tick > TileStation.UPDATE_INTERVAL) {
      this.count += 1
      this.tick = 0
      if (this.stack == null) {
        this.stack = new ItemStack(this.produced(0))
      } else {
        this.stack.setCount(Math.min(TileStation.MAX_STACK, this.stack.getCount() + 1))
      }
      this.setInventorySlotContents(0, this.stack)
      // not quite sure what this does yet
      val tile = getWorld().getTileEntity(pos.offset(EnumFacing.UP))
      if (tile != null && tile.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.DOWN)) {
        val handler: IItemHandler = tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.DOWN)
        if (this.getStackInSlot(0) != null) {
          val stack: ItemStack = this.getStackInSlot(0).copy()
          stack.setCount(1)
          val stack2: ItemStack = ItemHandlerHelper.insertItem(handler, stack, true)
          if (stack2 == null || stack2.getCount() == 0) {
            ItemHandlerHelper.insertItem(handler, this.decrStackSize(0, 1), false)
            markDirty()
          }
        }
      // or this...
      } else if (tile.isInstanceOf[IInventory]) {
        val iinventory: IInventory = tile.asInstanceOf[IInventory]
        if (this.isInventoryFull(iinventory, EnumFacing.UP)) {
          return
        } else {
          if (this.getStackInSlot(0) != null) {
            val stack: ItemStack = this.getStackInSlot(0).copy()
            val stack2: ItemStack = TileStation.putStackInInventoryAllSlots(iinventory, this.decrStackSize(0, 1),
              EnumFacing.UP)
            if (stack2 == null || stack2.getCount() == 0) {
              iinventory.markDirty()
            } else {
              this.setInventorySlotContents(0, stack)
            }
          }
        }
      }
      markDirty()
    }
  }

  protected def isInventoryFull(iInventory: IInventory, side: EnumFacing): Boolean =
  {
    if (iInventory.isInstanceOf[ISidedInventory]) {
      val iSidedInventory: ISidedInventory = iInventory.asInstanceOf[ISidedInventory]
      val slots: Array[Int] = iSidedInventory.getSlotsForFace(side)
      for (k: Int <- slots) {
        val itemStack: ItemStack = iSidedInventory.getStackInSlot(k)
        if (itemStack == null || itemStack.getCount() != itemStack.getMaxStackSize()) {
          return false
        }
      }
    } else {
      val i: Int = iInventory.getSizeInventory()
      for (j: Int <- 0 to i-1) {
        val itemStack: ItemStack = iInventory.getStackInSlot(j)
        if (itemStack == null || itemStack.getCount() != itemStack.getMaxStackSize()) {}
        return false
      }
    }
    return true
  }

  def getCapability[T: ClassTag](capability: Capability[T], facing: EnumFacing): T =
  {
    if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
      return (new InvWrapper(this)).asInstanceOf[T]
    }
    return super.getCapability(capability, facing)
  }

  override def hasCapability(capability: Capability[_], facing: EnumFacing): Boolean =
  {
    return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY || super.hasCapability(capability, facing)
  }

  def getCount(): Int = return this.stack.getCount()

}
