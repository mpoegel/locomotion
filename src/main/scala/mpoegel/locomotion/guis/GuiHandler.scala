package mpoegel.locomotion.guis

import mpoegel.locomotion.blocks.BlockStation
import mpoegel.locomotion.containers.ContainerStation
import mpoegel.locomotion.tiles.TileStation
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
    if (ID == BlockStation.GUI_ID && tileEntity.isInstanceOf[TileStation]) {
      val tileStation: TileStation = tileEntity.asInstanceOf[TileStation]
      return new ContainerStation(player.inventory, tileStation)
    }
    null
  }

  override def getClientGuiElement(ID: Int, player: EntityPlayer, world: World, x: Int, y: Int, z: Int): AnyRef =
  {
    val xyz: BlockPos = new BlockPos(x, y, z)
    val tileEntity: TileEntity = world.getTileEntity(xyz)
    if (ID == BlockStation.GUI_ID && tileEntity.isInstanceOf[TileStation]) {
      val tileStation: TileStation = tileEntity.asInstanceOf[TileStation]
      return new GuiStation(tileStation, new ContainerStation(player.inventory, tileStation))
    }
    null
  }
}
