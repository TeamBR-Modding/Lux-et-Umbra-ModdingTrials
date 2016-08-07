package com.teambr.luxetumbra.managers

import com.teambr.luxetumbra.common.blocks.{BlockCrystal, BlockAltar}
import com.teambr.luxetumbra.common.tiles.TileAltar
import com.teambr.luxetumbra.lib.Constants
import net.minecraft.block.Block
import net.minecraft.item.ItemBlock
import net.minecraft.tileentity.TileEntity
import net.minecraftforge.fml.common.registry.GameRegistry
import net.minecraftforge.oredict.OreDictionary

/**
  * This file was created for Lux et Umbra
  *
  * Lux et Umbra is licensed under the
  * Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License:
  * http://creativecommons.org/licenses/by-nc-sa/4.0/
  *
  * @author Dyonovan
  * @since 8/4/16
  */
object BlockManager {

    lazy val blockCrystal = new BlockCrystal
    lazy val blockAltar = new BlockAltar

    def preInit(): Unit = {
        //registerBlock(blockCrystal, "blockCrystal", null)
        registerBlock(blockAltar, "blockAltar", classOf[TileAltar])
    }

    def registerBlock(block: Block, name: String, te: Class[_ <: TileEntity], oreDict : String): Block = {
        block.setRegistryName(Constants.MOD_ID, name)
        val itemBlock = new ItemBlock(block)
        itemBlock.setRegistryName(Constants.MOD_ID, name)
        GameRegistry.register(block)
        GameRegistry.register(itemBlock)
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
