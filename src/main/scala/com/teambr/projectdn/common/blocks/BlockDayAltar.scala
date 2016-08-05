package com.teambr.projectdn.common.blocks

import com.teambr.bookshelf.common.blocks.traits.DropsItems
import com.teambr.projectdn.common.tiles.TileDayAltar
import net.minecraft.block.material.Material
import net.minecraft.block.state.IBlockState
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack
import net.minecraft.util.math.BlockPos
import net.minecraft.util.{EnumBlockRenderType, EnumFacing, EnumHand}
import net.minecraft.world.World
import net.minecraftforge.fml.relauncher.{Side, SideOnly}

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
class BlockDayAltar extends BaseBlock(Material.IRON, "blockDayAltar", classOf[TileDayAltar]) with DropsItems {

    override def onBlockActivated(world: World, pos: BlockPos, state: IBlockState, player: EntityPlayer,
                                  hand: EnumHand, heldItem: ItemStack, side: EnumFacing, hitX: Float, hitY: Float, hitZ: Float): Boolean = {

        val heldItem = player.getHeldItem(hand)

        if (heldItem == null || !world.getTileEntity(pos).isInstanceOf[TileDayAltar]) return true

        val altar = world.getTileEntity(pos).asInstanceOf[TileDayAltar]

        if (altar.getStackInSlot(0) == null) {
            val item = heldItem.copy()
            item.stackSize = 1
            if (altar.insertItem(0, item, simulate = false) == null)
                heldItem.stackSize -= 1
            world.markBlockRangeForRenderUpdate(pos, pos)
        }
        true
    }

    override def getRenderType(state: IBlockState): EnumBlockRenderType = EnumBlockRenderType.MODEL

    override def isOpaqueCube(state: IBlockState): Boolean = false

    @SideOnly(Side.CLIENT)
    override def isTranslucent(state: IBlockState): Boolean = true

    override def isFullCube(state: IBlockState): Boolean = false
}
