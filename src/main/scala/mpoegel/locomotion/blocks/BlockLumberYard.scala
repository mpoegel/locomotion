package mpoegel.locomotion.blocks
import mpoegel.locomotion.tiles.TileLumberYard
import mpoegel.locomotion.Locomotion
import net.minecraft.tileentity.TileEntity
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
}
