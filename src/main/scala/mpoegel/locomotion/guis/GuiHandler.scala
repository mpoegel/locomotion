package mpoegel.locomotion.guis

import mpoegel.locomotion.blocks.{BlockPaperMill, BlockStation}
import mpoegel.locomotion.containers.{ContainerStationPrimary, ContainerStationSeconday}
import mpoegel.locomotion.tiles.{TileLumberYard, TilePaperMill, TileStation}
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.tileentity.TileEntity
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import net.minecraftforge.fml.common.network.IGuiHandler

class GuiHandler extends IGuiHandler {
  override def getServerGuiElement(ID: Int, player: EntityPlayer, world: World, x: Int, y: Int, z: Int): AnyRef =
  {
    val xyz: BlockPos = new BlockPos(x, y, z)
    val tileEntity: TileEntity = world.getTileEntity(xyz)
    if (ID == BlockStation.GUI_ID && tileEntity.isInstanceOf[TileLumberYard]) {
      val tileStation: TileLumberYard = tileEntity.asInstanceOf[TileLumberYard]
      return new ContainerStationPrimary(player.inventory, tileStation)
    } else if (ID == BlockPaperMill.GUI_ID && tileEntity.isInstanceOf[TilePaperMill]) {
      val tile = tileEntity.asInstanceOf[TilePaperMill]
      return new ContainerStationSeconday(player.inventory, tile)
    }
    null
  }

  override def getClientGuiElement(ID: Int, player: EntityPlayer, world: World, x: Int, y: Int, z: Int): AnyRef =
  {
    val xyz: BlockPos = new BlockPos(x, y, z)
    val tileEntity: TileEntity = world.getTileEntity(xyz)
    if (ID == BlockStation.GUI_ID && tileEntity.isInstanceOf[TileLumberYard]) {
      val tileStation: TileLumberYard = tileEntity.asInstanceOf[TileLumberYard]
      return new GuiStationPrimary(tileStation, new ContainerStationPrimary(player.inventory, tileStation))
    } else if (ID == BlockPaperMill.GUI_ID && tileEntity.isInstanceOf[TilePaperMill]) {
      val tile = tileEntity.asInstanceOf[TilePaperMill]
      return new GuiStationSecondary(tile, new ContainerStationSeconday(player.inventory, tile))
    }
    null
  }
}
