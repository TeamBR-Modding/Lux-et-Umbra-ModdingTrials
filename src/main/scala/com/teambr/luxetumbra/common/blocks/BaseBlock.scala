package com.teambr.luxetumbra.common.blocks

import com.teambr.luxetumbra.LuxEtUmbra
import com.teambr.luxetumbra.lib.Constants
import net.minecraft.block.BlockContainer
import net.minecraft.block.material.Material
import net.minecraft.tileentity.TileEntity
import net.minecraft.world.World

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
class BaseBlock(material: Material, name: String, te: Class[_ <: TileEntity]) extends BlockContainer(material) {

    setUnlocalizedName(Constants.MOD_ID + ":" + name)
    setCreativeTab(LuxEtUmbra.luxEtUmbra)
    setHardness(getHardness)

    def getHardness: Float = 2.0f

    override def createNewTileEntity(worldIn: World, meta: Int): TileEntity = {
        if (te != null) te.newInstance() else null
    }
}
