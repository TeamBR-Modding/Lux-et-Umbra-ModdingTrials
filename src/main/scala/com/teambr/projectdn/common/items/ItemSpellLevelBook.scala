package com.teambr.projectdn.common.items

import com.teambr.projectdn.ProjectDN
import com.teambr.projectdn.capabilities.player.SpellLevelCapability
import com.teambr.projectdn.lib.Constants
import net.minecraft.client.resources.I18n
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.{Item, ItemStack}
import net.minecraft.util.math.BlockPos
import net.minecraft.util.text.TextComponentString
import net.minecraft.util.{ActionResult, EnumActionResult, EnumFacing, EnumHand}
import net.minecraft.world.World

/**
  * This file was created for ProjectDN
  *
  * ProjectDN is licensed under the
  * Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License:
  * http://creativecommons.org/licenses/by-nc-sa/4.0/
  *
  * @author Dyonovan
  * @since 8/6/2016
  */
class ItemSpellLevelBook extends Item {

    setUnlocalizedName(Constants.MOD_ID + ":spellLevelBook")
    setCreativeTab(ProjectDN.tabProjectDN)

    override def onItemRightClick(itemStack: ItemStack, world: World, player: EntityPlayer, hand: EnumHand): ActionResult[ItemStack] = {
        if (!world.isRemote)
            player.addChatComponentMessage(new TextComponentString(I18n.format("projectdn:itemSpellLevel:spellLevel") + " " +
                    player.getCapability(SpellLevelCapability.SPELL_LEVEL, null).getSpellLevel))

        new ActionResult(EnumActionResult.SUCCESS, itemStack)
    }


}
