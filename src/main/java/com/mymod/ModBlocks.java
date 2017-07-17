package com.mymod;

import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Created by poegem on 7/17/2017.
 */
public class ModBlocks {
    @GameRegistry.ObjectHolder("mymod:block_cool")
    public static BlockCool blockCool;

    @SideOnly(Side.CLIENT)
    public static void initModel()
    {
        blockCool.initModel();
    }
}
