package mpoegel.locomotion

import mpoegel.locomotion.blocks.{BlockCool, BlockLumberYard}
import net.minecraftforge.fml.common.registry.GameRegistry
import net.minecraftforge.fml.relauncher.{Side, SideOnly}

/**
  * Created by poegem on 7/17/2017.
  */
object ModBlocks {
  @GameRegistry.ObjectHolder("locomotion:block_cool")
  var blockCool: BlockCool = _

  @GameRegistry.ObjectHolder("locomotion:block_lumber_yard")
  var blockLumberYard: BlockLumberYard = _

  @SideOnly(Side.CLIENT)
  def initModels(): Unit =
  {
    blockCool.initModel()
    blockLumberYard.initModel()
  }
}
