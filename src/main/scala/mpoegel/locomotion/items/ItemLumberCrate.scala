package mpoegel.locomotion.items

import mpoegel.locomotion.Locomotion
import net.minecraft.client.renderer.block.model.ModelResourceLocation
import net.minecraft.item.{Item, ItemStack}
import net.minecraftforge.client.model.ModelLoader
import net.minecraftforge.fml.relauncher.{Side, SideOnly}

class ItemLumberCrate extends Item {
  super.setMaxStackSize(64)
  super.setContainerItem(this)
  this.setCreativeTab(Locomotion.tabLocomotion)
  this.setUnlocalizedName(Locomotion.MODID + ".lumber_crate")
  this.setRegistryName("lumber_crate")

  override def getContainerItem(itemStack: ItemStack): ItemStack =
  {
    val stack: ItemStack = itemStack.copy()
    stack.setCount(1)
    stack
  }

  @SideOnly(Side.CLIENT)
  def initModel(): Unit =
  {
    ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(this.getRegistryName, "inventory"))
  }
}
