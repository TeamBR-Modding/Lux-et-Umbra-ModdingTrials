package com.teambr.projectdn.common.blocks

import java.util
import java.util.Random

import com.teambr.projectdn.lib.Constants
import net.minecraft.block.properties.PropertyInteger
import net.minecraft.block.state.IBlockState
import net.minecraft.block.{BlockCrops, IGrowable, SoundType}
import net.minecraft.init.{Blocks, Items}
import net.minecraft.item.{Item, ItemStack}
import net.minecraft.util.math.BlockPos
import net.minecraft.world.{IBlockAccess, World}

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
class BlockCrystal extends BlockCrops with IGrowable {
    val AGE: PropertyInteger = PropertyInteger.create("age", 0, 7)

    setHardness(2.0f)
    setSoundType(SoundType.GLASS)
    setUnlocalizedName(Constants.MOD_ID + ":blockCrystal")

    override def getMaxAge: Int = 7

    override def canSustainBush(state: IBlockState): Boolean = state.getBlock == Blocks.STONE //TODO maybe use our own type of block depending on type of crystal

    override def getCrop: Item = Items.DIAMOND

    override def getSeed: Item = Items.DIAMOND

    override def canUseBonemeal(worldIn: World, rand: Random, pos: BlockPos, state: IBlockState): Boolean = true //TODO reset after testing

    override def getDrops(world: IBlockAccess, pos: BlockPos, state: IBlockState, fortune: Int): util.List[ItemStack] = {
        val age = getAge(state)
        val ret: util.List[ItemStack] = new util.ArrayList[ItemStack]
        if (age >= getMaxAge) {
            ret.add(new ItemStack(getCrop)) //return actual crop
        } else {
            val rand = new Random().nextInt(10)
            if (rand >= 8)
                ret.add(new ItemStack(getSeed)) //20% chance to get diamond back
        }
        ret
    }
}
