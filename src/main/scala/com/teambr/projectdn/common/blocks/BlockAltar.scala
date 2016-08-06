package com.teambr.projectdn.common.blocks

import com.teambr.bookshelf.common.blocks.traits.DropsItems
import com.teambr.bookshelf.notification.{Notification, NotificationHelper}
import com.teambr.projectdn.capabilities.player.SpellLevelCapability
import com.teambr.projectdn.collections.WorldStructure
import com.teambr.projectdn.collections.WorldStructure.EnumAlterType
import com.teambr.projectdn.common.tiles.TileAltar
import com.teambr.projectdn.managers.BlockManager
import net.minecraft.block.material.Material
import net.minecraft.block.state.IBlockState
import net.minecraft.entity.effect.EntityLightningBolt
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack
import net.minecraft.util._
import net.minecraft.util.math.BlockPos
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
class BlockAltar extends BaseBlock(Material.IRON, "blockAltar", classOf[TileAltar]) with DropsItems {

    override def onBlockActivated(world: World, pos: BlockPos, state: IBlockState, player: EntityPlayer,
                                  hand: EnumHand, heldItem: ItemStack, side: EnumFacing, hitX: Float, hitY: Float, hitZ: Float): Boolean = {

        if (!world.isRemote) {
            val heldItem = player.getHeldItem(hand)
            val altar = world.getTileEntity(pos).asInstanceOf[TileAltar]
            val altarType = WorldStructure.getAlterType(world, pos)

            if (!player.isSneaking && altarType == EnumAlterType.NIGHT && player.getCapability(SpellLevelCapability.SPELL_LEVEL, null).getSpellLevel > 0) {
                player.setFire(10)
                return true
            } else if (!player.isSneaking && altarType == EnumAlterType.DAY && player.getCapability(SpellLevelCapability.SPELL_LEVEL, null).getSpellLevel < 0) {
                val entity = new EntityLightningBolt(world, player.getPosition.getX, player.getPosition.getY, player.getPosition.getZ, false)
                world.addWeatherEffect(entity)
                return true
            }

            if (heldItem == null && altar.getStackInSlot(0) != null) {
                player.inventory.addItemStackToInventory(altar.getStackInSlot(0).copy)
                altar.setStackInSlot(0, null)
            } else if (altar.getStackInSlot(0) == null && heldItem != null) {
                val item = heldItem.copy()
                item.stackSize = 1
                if (altar.insertItem(0, item, simulate = false) == null)
                    heldItem.stackSize -= 1
                world.markBlockRangeForRenderUpdate(pos, pos)
            } else if (player.isSneaking) {
                NotificationHelper.addNotification(new Notification(new ItemStack(BlockManager.blockAltar), "Altar Status", altarType.toString))
            }
        }
        true
    }

    override def getRenderType(state: IBlockState): EnumBlockRenderType = EnumBlockRenderType.MODEL

    override def isOpaqueCube(state: IBlockState): Boolean = false

    @SideOnly(Side.CLIENT)
    override def isTranslucent(state: IBlockState): Boolean = true

    override def isFullCube(state: IBlockState): Boolean = false
}
