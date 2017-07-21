package mpoegel.locomotion.guis

import mpoegel.locomotion.Locomotion
import mpoegel.locomotion.containers.ContainerStation
import mpoegel.locomotion.tiles.TileStation
import net.minecraft.client.gui.inventory.GuiContainer
import net.minecraft.util.ResourceLocation

object GuiStation {
  final val WIDTH = 180
  final val HEIGHT = 152

  private final val background: ResourceLocation = new ResourceLocation(Locomotion.MODID, "textures/gui/station.png")
}

class GuiStation(tileStation: TileStation, container: ContainerStation) extends GuiContainer(container) {
  this.xSize = GuiStation.WIDTH
  this.ySize = GuiStation.HEIGHT

  override protected def drawGuiContainerBackgroundLayer(partialTicks: Float, mouseX: Int, mouseY: Int): Unit =
  {
    this.mc.getTextureManager.bindTexture(GuiStation.background)
    this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize)
  }

  override def drawScreen(mouseX: Int, mouseY: Int, partialTicks: Float): Unit =
  {
    super.drawDefaultBackground()
    super.drawScreen(mouseX, mouseY, partialTicks)
    super.renderHoveredToolTip(mouseX, mouseY)
  }
}
