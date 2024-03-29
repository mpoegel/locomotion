package mpoegel.locomotion.blocks

import mpoegel.locomotion.tiles.{TileLumberYard, TileStation}
import mpoegel.locomotion.Locomotion
import net.minecraft.block.state.IBlockState
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.tileentity.TileEntity
import net.minecraft.util.{EnumFacing, EnumHand}
import net.minecraft.util.math.BlockPos
import net.minecraft.util.text.TextComponentString
import net.minecraft.world.World

/**
  * Created by Matt Poegel on 7/19/2017.
  */
object BlockLumberYard {
  val GUI_ID = 2
}

class BlockLumberYard extends BlockStation {
  this.setUnlocalizedName(Locomotion.MODID + ".block_lumber_yard")
  this.setRegistryName("block_lumber_yard")

  override def createNewTileEntity(worldIn: World, meta: Int): TileEntity = {
    new TileLumberYard
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
      playerIn.openGui(Locomotion.instance, BlockStation.GUI_ID, worldIn, pos.getX, pos.getY, pos.getZ)
    }
    true
  }
}
