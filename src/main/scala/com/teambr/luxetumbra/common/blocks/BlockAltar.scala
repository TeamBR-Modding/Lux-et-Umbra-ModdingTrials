package com.teambr.luxetumbra.common.blocks

import com.teambr.bookshelf.common.blocks.traits.DropsItems
import com.teambr.luxetumbra.capabilities.player.SpellLevelCapability
import com.teambr.luxetumbra.collections.WorldStructure
import com.teambr.luxetumbra.collections.WorldStructure.EnumAlterType
import com.teambr.luxetumbra.common.tiles.TileAltar
import net.minecraft.block.material.Material
import net.minecraft.block.state.IBlockState
import net.minecraft.entity.effect.EntityLightningBolt
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.init.MobEffects
import net.minecraft.item.ItemStack
import net.minecraft.potion.PotionEffect
import net.minecraft.util._
import net.minecraft.util.math.BlockPos
import net.minecraft.util.text.TextComponentTranslation
import net.minecraft.world.World
import net.minecraftforge.fml.relauncher.{Side, SideOnly}

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
class BlockAltar extends BaseBlock(Material.IRON, "blockAltar", classOf[TileAltar]) with DropsItems {

    override def onBlockActivated(world: World, pos: BlockPos, state: IBlockState, player: EntityPlayer,
                                  hand: EnumHand, heldItem: ItemStack, side: EnumFacing, hitX: Float, hitY: Float, hitZ: Float): Boolean = {

        if (!world.isRemote) {
            val heldItem = player.getHeldItem(hand)
            val altar = world.getTileEntity(pos).asInstanceOf[TileAltar]
            val altarType = WorldStructure.getAlterType(world, pos)

            if (!player.isSneaking && altarType == EnumAlterType.NIGHT && player.getCapability(SpellLevelCapability.SPELL_LEVEL, null).getSpellLevel > 0) {
                player.setFire(10)
                player.addPotionEffect(new PotionEffect(MobEffects.WITHER, 200))
                player.addPotionEffect(new PotionEffect(MobEffects.NAUSEA, 200))
                return true
            } else if (!player.isSneaking && altarType == EnumAlterType.DAY && player.getCapability(SpellLevelCapability.SPELL_LEVEL, null).getSpellLevel < 0) {
                val entity = new EntityLightningBolt(world, player.getPosition.getX, player.getPosition.getY, player.getPosition.getZ, false)
                world.addWeatherEffect(entity)
                player.setFire(20)
                player.setPositionAndUpdate(pos.getX, pos.getY + 10, pos.getZ)
                player.addPotionEffect(new PotionEffect(MobEffects.LEVITATION, 100))
                player.addChatComponentMessage(new TextComponentTranslation("luxetumbra:altar.day.nopass"))
                return true
            }

            if (heldItem == null && altar.getStackInSlot(0) != null) {
                player.inventory.addItemStackToInventory(altar.getStackInSlot(0).copy)
                altar.setStackInSlot(0, null)
                world.notifyBlockUpdate(pos, world.getBlockState(pos), world.getBlockState(pos), 3)
            } else if (altar.getStackInSlot(0) == null && heldItem != null) {
                val item = heldItem.copy()
                item.stackSize = 1
                if (altar.insertItem(0, item, simulate = false) == null)
                    heldItem.stackSize -= 1
                world.notifyBlockUpdate(pos, world.getBlockState(pos), world.getBlockState(pos), 3)
            } else if (player.isSneaking) {
                //NotificationHelper.addNotification(new Notification(new ItemStack(BlockManager.blockAltar), "Altar Status", altarType.toString)) //TODO
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
