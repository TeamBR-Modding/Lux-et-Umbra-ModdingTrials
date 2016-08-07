package com.teambr.luxetumbra.common.items

import com.teambr.luxetumbra.LuxEtUmbra
import com.teambr.luxetumbra.collections.CrystalType.crystalType
import com.teambr.luxetumbra.common.items.traits.EnergyUserItem
import com.teambr.luxetumbra.lib.Constants
import net.minecraft.client.resources.I18n
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.{Item, ItemStack}
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.util.math.BlockPos
import net.minecraft.util.text.TextComponentString
import net.minecraft.util.{ActionResult, EnumActionResult, EnumFacing, EnumHand}
import net.minecraft.world.World
import net.minecraftforge.fml.relauncher.{Side, SideOnly}

/**
  * This file was created for Lux-et-Umbra
  *
  * Lux-et-Umbra is licensed under the
  * Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License:
  * http://creativecommons.org/licenses/by-nc-sa/4.0/
  *
  * @author Dyonovan
  * @since 8/7/2016
  */
class ItemTeleporter extends EnergyUserItem {

    override val MAGIC_TYPE: crystalType = crystalType.DAY
    override val ENERGY_REQUIRED: Int = 1000
    override val MIN_SPELL_LEVEL: Int = 50

    setUnlocalizedName(Constants.MOD_ID + ":teleporter")
    setCreativeTab(LuxEtUmbra.luxEtUmbra)

    override def onItemRightClick(stack: ItemStack, world: World, player: EntityPlayer, hand: EnumHand): ActionResult[ItemStack] = {
        if (world.isRemote) return  new ActionResult(EnumActionResult.SUCCESS, stack)
        if (useEnergy(player)) {
            val tag = stack.getTagCompound
            player.setPositionAndUpdate(tag.getInteger("X"), tag.getInteger("Y"), tag.getInteger("Z"))
        }

        new ActionResult(EnumActionResult.SUCCESS, stack)
    }

    override def onItemUse(stack: ItemStack, player: EntityPlayer, world: World, pos: BlockPos, hand: EnumHand,
                           facing: EnumFacing, hitX: Float, hitY: Float, hitZ: Float): EnumActionResult = {
        if (player.isSneaking) {
            val nbt = new NBTTagCompound
            nbt.setInteger("X", pos.getX)
            nbt.setInteger("Y", pos.getY + 1)
            nbt.setInteger("Z", pos.getZ)
            stack.setTagCompound(nbt)
            player.addChatComponentMessage(new TextComponentString(I18n.format("luxetumbra:teleporter.locationset")))
        } else onItemRightClick(stack, world, player, hand)
        EnumActionResult.SUCCESS
    }

    @SideOnly(Side.CLIENT)
    override def addInformation(stack: ItemStack, player: EntityPlayer, list: java.util.List[String], boolean: Boolean): Unit = {
        if (!stack.hasTagCompound) list.add("Location Not Set")
        else {
            val tag = stack.getTagCompound
            list.add("Current Set Location:")
            list.add("     X: " + tag.getFloat("X").toInt)
            list.add("     Y: " + tag.getFloat("Y").toInt)
            list.add("     Z: " + tag.getFloat("Z").toInt)
        }
    }
}
