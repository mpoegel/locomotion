package mpoegel.locomotion.blocks

import mpoegel.locomotion.Locomotion
import mpoegel.locomotion.tiles.TileStation
import net.minecraft.block.{Block, ITileEntityProvider}
import net.minecraft.block.material.Material
import net.minecraft.block.state.IBlockState
import net.minecraft.client.renderer.block.model.ModelResourceLocation
import net.minecraft.creativetab.CreativeTabs
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.{Item, ItemStack}
import net.minecraft.tileentity.TileEntity
import net.minecraft.util.math.BlockPos
import net.minecraft.util.text.TextComponentString
import net.minecraft.util.{BlockRenderLayer, EnumFacing, EnumHand}
import net.minecraft.world.{IBlockAccess, World}
import net.minecraftforge.client.model.ModelLoader
import net.minecraftforge.common.ForgeHooks
import net.minecraftforge.fml.relauncher.{Side, SideOnly}

/**
  * Created by Matt Poegel on 7/17/2017.
  */
class BlockStation extends Block(Material.ROCK) with ITileEntityProvider {
  this.setCreativeTab(CreativeTabs.BUILDING_BLOCKS)
  this.setUnlocalizedName(Locomotion.MODID + ".block_station")
  this.setRegistryName("block_station")
  this.setHardness(1.0F)
  this.setHarvestLevel("pickaxe", 0)

  // Called when the block is placed or loaded client side
  override def createNewTileEntity(worldIn: World, meta: Int): TileEntity = new TileStation()

  // Called when the block is right clicked
  override def onBlockActivated(worldIn: World, pos: BlockPos, state: IBlockState, playerIn: EntityPlayer,
                                hand: EnumHand, facing: EnumFacing, hitX: Float, hitY: Float, hitZ: Float): Boolean =
  {
    if (worldIn.isRemote) {
      return true
    }
    val tile: TileEntity = worldIn.getTileEntity(pos)
    if (!tile.isInstanceOf[TileStation]) {
      return false
    }
    val tileStation: TileStation = tile.asInstanceOf[TileStation]
    if (playerIn.isSneaking) {
      playerIn.sendMessage(new TextComponentString("sneaky sneaky: "  + tileStation.getCount()))
    } else {
      // drop the current inventory
      val stack: ItemStack = tileStation.removeStackFromSlot(0)
      if (stack != null) {
        if (!playerIn.inventory.addItemStackToInventory(stack)) {
          ForgeHooks.onPlayerTossEvent(playerIn, stack, false)
        }
      }
    }
    true
  }

  // Called when the block is broken (drop the block's inventory)
  override def breakBlock(worldIn: World, pos: BlockPos, state: IBlockState): Unit =
    super.breakBlock(worldIn, pos, state)

  @SideOnly(Side.CLIENT)
  def initModel(): Unit =
  {
    ModelLoader.setCustomModelResourceLocation(
      Item.getItemFromBlock(this),
      0,
      new ModelResourceLocation(getRegistryName, "inventory")
    )
  }

  @SideOnly(Side.CLIENT)
  override def getBlockLayer: BlockRenderLayer = BlockRenderLayer.CUTOUT_MIPPED

  override def doesSideBlockRendering(state: IBlockState, world: IBlockAccess, pos: BlockPos,
                                      face: EnumFacing): Boolean = false
}
