package mpoegel.locomotion.tabs

import mpoegel.locomotion.{ModBlocks, ModItems}
import net.minecraft.creativetab.CreativeTabs
import net.minecraft.item.ItemStack

class TabLocomotion(label: String) extends CreativeTabs(label) {
  override def getTabIconItem: ItemStack = new ItemStack(ModBlocks.blockLumberYard)
}
