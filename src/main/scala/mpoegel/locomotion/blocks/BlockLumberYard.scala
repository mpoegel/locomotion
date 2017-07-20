package mpoegel.locomotion.blocks
import mpoegel.locomotion.{Locomotion, ModItems}
import net.minecraft.item.Item

/**
  * Created by Matt Poegel on 7/19/2017.
  */
class BlockLumberYard extends BlockStation {
  this.setUnlocalizedName(Locomotion.MODID + ".block_lumber_yard")
  this.setRegistryName("block_lumber_yard")

  override def getItemsProduced: Array[Item] =
  {
    val arr = new Array[Item](1)
    arr(0) = ModItems.lumber_crate
    arr
  }

  override def getItemsConsumed: Array[Item] = null
}
