package com.teambr.projectdn.common.items

import com.teambr.projectdn.ProjectDN
import com.teambr.projectdn.lib.Constants
import com.teambr.projectdn.managers.BlockManager
import net.minecraft.block.state.IBlockState
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.init.Blocks
import net.minecraft.item.{Item, ItemStack}
import net.minecraft.util.{EnumActionResult, EnumFacing, EnumHand}
import net.minecraft.util.math.BlockPos
import net.minecraft.world.{IBlockAccess, World}
import net.minecraftforge.common.EnumPlantType

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
class ItemCrystalSeed extends Item with net.minecraftforge.common.IPlantable {

    setUnlocalizedName(Constants.MOD_ID + ":itemCrystalSeed")
    setMaxStackSize(64)
    setCreativeTab(ProjectDN.tabProjectDN)

    val soilBlock = Blocks.STONE

    override def onItemUse(stack: ItemStack, playerIn: EntityPlayer, world: World, pos: BlockPos, hand: EnumHand, facing: EnumFacing, hitX: Float, hitY: Float, hitZ: Float): EnumActionResult = {
        val state = world.getBlockState(pos)
        if ((facing eq EnumFacing.UP) && playerIn.canPlayerEdit(pos.offset(facing), facing, stack) && state.getBlock == soilBlock && world.isAirBlock(pos.up)) {
            world.setBlockState(pos.up, BlockManager.blockCrystal.getDefaultState)
            stack.stackSize -= 1
            EnumActionResult.SUCCESS
        }
        else EnumActionResult.FAIL

    }


    override def getPlant(world: IBlockAccess, pos: BlockPos): IBlockState = BlockManager.blockCrystal.getDefaultState

    override def getPlantType(world: IBlockAccess, pos: BlockPos): EnumPlantType = EnumPlantType.Crop
}
