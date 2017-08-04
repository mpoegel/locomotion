package mpoegel.locomotion.guis

import mpoegel.locomotion.Locomotion
import mpoegel.locomotion.containers.ContainerStation
import mpoegel.locomotion.tiles.TileStation
import net.minecraft.util.ResourceLocation

object GuiStationSecondary {
  final val WIDTH = 180
  final val HEIGHT = 152

  private final val background: ResourceLocation = new ResourceLocation(Locomotion.MODID,
    "textures/gui/gui_station_secondary.png")
}

class GuiStationSecondary(tileStation: TileStation, container: ContainerStation) extends GuiStation(tileStation, container) {
  this.xSize = GuiStationPrimary.WIDTH
  this.ySize = GuiStationPrimary.HEIGHT

  override protected def drawGuiContainerBackgroundLayer(partialTicks: Float, mouseX: Int, mouseY: Int): Unit = {
    this.mc.getTextureManager.bindTexture(GuiStationSecondary.background)
    this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize)
  }
}
