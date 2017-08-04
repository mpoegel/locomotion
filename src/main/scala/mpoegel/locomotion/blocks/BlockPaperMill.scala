package mpoegel.locomotion.blocks

import mpoegel.locomotion.Locomotion
import mpoegel.locomotion.tiles.{TilePaperMill, TileStation}
import net.minecraft.block.state.IBlockState
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.tileentity.TileEntity
import net.minecraft.util.{EnumFacing, EnumHand}
import net.minecraft.util.math.BlockPos
import net.minecraft.util.text.TextComponentString
import net.minecraft.world.World

/**
  * Created by Matt Poegel on 7/24/2017.
  */
object BlockPaperMill {
  val GUI_ID = 3
}

class BlockPaperMill extends BlockStation {
  this.setUnlocalizedName(Locomotion.MODID + ".block_paper_mill")
  this.setRegistryName("block_paper_mill")

  override def createNewTileEntity(worldIn: World, meta: Int): TileEntity = {
    new TilePaperMill
  }

  override def onBlockActivated(worldIn: World, pos: BlockPos, state: IBlockState, playerIn: EntityPlayer,
                                hand: EnumHand, facing: EnumFacing, hitX: Float, hitY: Float, hitZ: Float): Boolean = {
    if (worldIn.isRemote) {
      return true
    }
    val tile: TileEntity = worldIn.getTileEntity(pos)
    if (!tile.isInstanceOf[TileStation]) {
      return false
    }
    val tileStation: TileStation = tile.asInstanceOf[TileStation]
    if (playerIn.isSneaking) {
      playerIn.sendMessage(new TextComponentString("sneaky sneaky: "  + tileStation.getCount))
    } else {
      playerIn.openGui(Locomotion.instance, BlockPaperMill.GUI_ID, worldIn, pos.getX, pos.getY, pos.getZ)
    }
    true
  }
}
