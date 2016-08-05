package com.teambr.projectdn.managers

import com.teambr.projectdn.common.blocks.{BlockCrystal, BlockDayAltar}
import com.teambr.projectdn.common.tiles.TileDayAltar
import com.teambr.projectdn.lib.Constants
import net.minecraft.block.Block
import net.minecraft.tileentity.TileEntity
import net.minecraftforge.fml.common.registry.GameRegistry
import net.minecraftforge.oredict.OreDictionary

/**
  * This file was created for ProjectDN
  *
  * ProjectDN is licensed under the
  * Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License:
  * http://creativecommons.org/licenses/by-nc-sa/4.0/
  *
  * @author Dyonovan
  * @since 8/4/16
  */
object BlockManager {

    val blockCrystal = new BlockCrystal
    val blockDayAltar = new BlockDayAltar

    def preInit(): Unit = {
        //registerBlock(blockCrystal, "blockCrystal", null)
        registerBlock(blockDayAltar, "blockDayAltar", classOf[TileDayAltar])
    }

    def registerBlock(block: Block, name: String, te: Class[_ <: TileEntity], oreDict:String): Block = {
        block.setRegistryName(Constants.MOD_ID, name)
        GameRegistry.register(block)
        if (te != null) {
            GameRegistry.registerTileEntity(te, name)
        }
        if (oreDict != null) {
            OreDictionary.registerOre(oreDict, block)
        }
        block
    }

    def registerBlock(block: Block, name: String, te: Class[_ <: TileEntity]): Block = {
        registerBlock(block, name, te, null)
    }
}
