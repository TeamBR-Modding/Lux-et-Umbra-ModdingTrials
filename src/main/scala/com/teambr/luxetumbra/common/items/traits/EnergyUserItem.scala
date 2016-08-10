package com.teambr.luxetumbra.common.items.traits

import com.teambr.bookshelf.client.gui.{GuiColor, GuiTextFormat}
import com.teambr.luxetumbra.capabilities.player.SpellLevelCapability
import com.teambr.luxetumbra.collections.CrystalType
import com.teambr.luxetumbra.managers.ItemManager
import net.minecraft.client.resources.I18n
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.{Item, ItemStack}
import net.minecraft.util.text.TextComponentTranslation
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
trait EnergyUserItem extends Item {

    val MAGIC_TYPE: CrystalType.crystalType
    val ENERGY_REQUIRED: Int
    val MIN_SPELL_LEVEL: Int

    def useEnergy(player: EntityPlayer, displayMessage : Boolean = true, doSpellLevel : Boolean = true): Boolean = { //, energyType: CrystalType.crystalType, energyUsed: Int, simulate: Boolean):  Boolean = {
        if (player == null || ENERGY_REQUIRED == 0) return false

        val playerSpellLevel = player.getCapability(SpellLevelCapability.SPELL_LEVEL, null).getSpellLevel

        if(doSpellLevel)
            MAGIC_TYPE match {
                case CrystalType.crystalType.DAY =>
                    player.getCapability(SpellLevelCapability.SPELL_LEVEL, null).setSpellLevel(playerSpellLevel + 1)
                case CrystalType.crystalType.NIGHT =>
                    player.getCapability(SpellLevelCapability.SPELL_LEVEL, null).setSpellLevel(playerSpellLevel - 1)
                case _ =>
            }

        MAGIC_TYPE match {
            case CrystalType.crystalType.DAY =>
                if (MIN_SPELL_LEVEL != 0 && playerSpellLevel < MIN_SPELL_LEVEL) {
                    if(displayMessage)
                        player.addChatComponentMessage(new TextComponentTranslation("luxetumbra:energyuse.nolevel"))
                    return false
                }
                val slot = findStackInInventory(player, new ItemStack(ItemManager.dayCrystal))
                if (slot == -1) {
                    if(displayMessage)
                        player.addChatComponentMessage(new TextComponentTranslation("luxetumbra:energyuse.nodaycrystal"))
                    return false
                }

                val stack = player.inventory.getStackInSlot(slot)
                val item = stack.getItem.asInstanceOf[CrystalPower]
                val actual = item.extractEnergy(stack, ENERGY_REQUIRED, simulate = true)
                if (actual < ENERGY_REQUIRED) {
                    if(displayMessage)
                        player.addChatComponentMessage(new TextComponentTranslation("luxetumbra:energyuse.notenoughenergy"))
                    return false
                } else {
                    item.extractEnergy(stack, ENERGY_REQUIRED, simulate = false)
                    return true
                }
            case CrystalType.crystalType.NIGHT =>
                if (MIN_SPELL_LEVEL != 0 && playerSpellLevel > MIN_SPELL_LEVEL) {
                    if(displayMessage)
                        player.addChatComponentMessage(new TextComponentTranslation("luxetumbra:energyuse.nolevel"))
                    return false
                }
                val slot = findStackInInventory(player, new ItemStack(ItemManager.nightCrystal))
                if (slot == -1 && displayMessage) {
                    player.addChatComponentMessage(new TextComponentTranslation("luxetumbra:energyuse.nonightcrystal"))
                    return false
                }
                val stack = player.inventory.getStackInSlot(slot)
                val item = stack.getItem.asInstanceOf[CrystalPower]
                val actual = item.extractEnergy(stack, ENERGY_REQUIRED, simulate = true)
                if (actual < ENERGY_REQUIRED) {
                    if(displayMessage)
                        player.addChatComponentMessage(new TextComponentTranslation("luxetumbra:energyuse.notenoughenergy"))
                    return false
                } else {
                    item.extractEnergy(stack, ENERGY_REQUIRED, simulate = false)
                    return true
                }
            case _ =>
        }
        false
    }

    protected def findStackInInventory(player: EntityPlayer, stack: ItemStack): Int = {
        for (i <- player.inventory.mainInventory.indices) {
            if (player.inventory.getStackInSlot(i) != null && player.inventory.getStackInSlot(i).isItemEqualIgnoreDurability(stack))
                return i
        }
        -1
    }

    @SideOnly(Side.CLIENT)
    override def addInformation(stack: ItemStack, player: EntityPlayer, list: java.util.List[String], boolean: Boolean): Unit = {
        list.add(GuiTextFormat.ITALICS + "" + GuiColor.ORANGE + "Required Spell Level: " + MIN_SPELL_LEVEL)
        var magicType = ""
        MAGIC_TYPE match {
            case CrystalType.crystalType.DAY => magicType = "Required Day Power: "
            case CrystalType.crystalType.NIGHT => magicType = "Required Night Power: "

        }
        list.add(GuiTextFormat.ITALICS + "" + GuiColor.ORANGE + magicType + ENERGY_REQUIRED)
    }
}
