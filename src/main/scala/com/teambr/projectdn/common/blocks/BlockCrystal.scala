package com.teambr.projectdn.common.blocks

import java.util.Random

import com.teambr.projectdn.common.tiles.TileCrystal
import net.minecraft.block.IGrowable
import net.minecraft.block.material.Material
import net.minecraft.block.state.IBlockState
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

/**
  * This file was created for SkyWorld
  *
  * SkyWorld is licensed under the
  * Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License:
  * http://creativecommons.org/licenses/by-nc-sa/4.0/
  *
  * @author Dyonovan
  * @since 8/4/16
  */
class BlockCrystal extends BaseBlock(Material.PLANTS, "blockCrystal", classOf[TileCrystal]) with IGrowable{

    override def canGrow(worldIn: World, pos: BlockPos, state: IBlockState, isClient: Boolean): Boolean = ???

    override def grow(worldIn: World, rand: Random, pos: BlockPos, state: IBlockState): Unit = ???

    override def canUseBonemeal(worldIn: World, rand: Random, pos: BlockPos, state: IBlockState): Boolean = false
}
