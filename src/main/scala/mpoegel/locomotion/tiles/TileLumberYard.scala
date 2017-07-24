package mpoegel.locomotion.tiles
import mpoegel.locomotion.ModItems
import net.minecraft.item.Item

object TileLumberYard {

}

class TileLumberYard extends TileStation {
  override def getItemsProduced: Array[Item] = {
    val arr = new Array[Item](1)
    arr(0) = ModItems.lumber_crate
    arr
  }

  override def getStorageLimits: Array[Int] = {
    val arr = new Array[Int](1)
    arr(0) = 32
    arr
  }

  override def getProductionRates: Array[Int] = {
    val arr = new Array[Int](1)
    arr(0) = 20
    arr
  }
}
