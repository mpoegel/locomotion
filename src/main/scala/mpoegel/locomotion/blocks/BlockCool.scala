package mpoegel.locomotion.blocks

import mpoegel.locomotion.Locomotion
import net.minecraft.block.Block
import net.minecraft.block.material.Material
import net.minecraft.client.renderer.block.model.ModelResourceLocation
import net.minecraft.creativetab.CreativeTabs
import net.minecraft.item.Item
import net.minecraftforge.client.model.ModelLoader
import net.minecraftforge.fml.relauncher.{Side, SideOnly}

/**
  * Created by poegem on 7/17/2017.
  */
class BlockCool() extends Block(Material.GROUND) {
  this.setCreativeTab(Locomotion.tabLocomotion)
  setUnlocalizedName(Locomotion.MODID + ".block_cool")
  setRegistryName("block_cool")

  @SideOnly(Side.CLIENT)
  def initModel(): Unit =
  {
    ModelLoader.setCustomModelResourceLocation(
      Item.getItemFromBlock(this),
      0,
      new ModelResourceLocation(getRegistryName, "inventory")
    )
  }
}
