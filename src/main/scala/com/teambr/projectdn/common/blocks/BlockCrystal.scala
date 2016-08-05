package com.teambr.projectdn.common.blocks

import java.util
import java.util.Random

import com.google.common.collect.Lists
import com.teambr.bookshelf.helper.LogHelper
import com.teambr.projectdn.lib.Constants
import com.teambr.projectdn.managers.ItemManager
import net.minecraft.block.properties.{IProperty, PropertyInteger}
import net.minecraft.block.state.{BlockStateContainer, IBlockState}
import net.minecraft.block.{BlockCrops, IGrowable, SoundType}
import net.minecraft.init.Blocks
import net.minecraft.item.{Item, ItemStack}
import net.minecraft.util.EnumBlockRenderType
import net.minecraft.util.math.BlockPos
import net.minecraft.world.{IBlockAccess, World}
import net.minecraftforge.client.model.obj.OBJModel
import net.minecraftforge.common.property.{ExtendedBlockState, IExtendedBlockState, IUnlistedProperty}

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

    override def getCrop: Item = ItemManager.itemCrystal

    override def getSeed: Item = ItemManager.itemCrystalSeed

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
