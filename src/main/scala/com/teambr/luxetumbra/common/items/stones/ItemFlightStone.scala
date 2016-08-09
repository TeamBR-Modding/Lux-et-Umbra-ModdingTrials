package com.teambr.luxetumbra.common.items.stones

import com.teambr.bookshelf.client.gui.{GuiColor, GuiTextFormat}
import com.teambr.luxetumbra.LuxEtUmbra
import com.teambr.luxetumbra.collections.CrystalType.crystalType
import com.teambr.luxetumbra.common.items.traits.EnergyUserItem
import com.teambr.luxetumbra.events.PlayerEvents
import com.teambr.luxetumbra.lib.Constants
import net.minecraft.entity.Entity
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack
import net.minecraft.world.World
import net.minecraftforge.fml.relauncher.{Side, SideOnly}

/**
  * This file was created for ProjectDN
  *
  * ProjectDN is licensed under the
  * Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License:
  * http://creativecommons.org/licenses/by-nc-sa/4.0/
  *
  * @author Paul Davis <pauljoda>
  * @since 8/7/2016
  */
class ItemFlightStone extends EnergyUserItem {
    override val MAGIC_TYPE: crystalType = crystalType.DAY
    override val ENERGY_REQUIRED: Int = 1
    override val MIN_SPELL_LEVEL: Int = 100

    setUnlocalizedName(Constants.MOD_ID + ":flightStone")
    setCreativeTab(LuxEtUmbra.luxEtUmbra)

    override def onUpdate(stack: ItemStack, worldIn: World, entityIn: Entity, itemSlot: Int, isSelected: Boolean): Unit = {
        entityIn match {
            case player: EntityPlayer if useEnergy(player, displayMessage = false, doSpellLevel = false) =>
                PlayerEvents.updateForPlayer(player)
            case _ =>
        }
    }

    @SideOnly(Side.CLIENT)
    override def addInformation(stack: ItemStack, player: EntityPlayer, list: java.util.List[String], boolean: Boolean): Unit = {
        list.add("Grants player flight")
        list.add(GuiTextFormat.ITALICS + "" + GuiColor.ORANGE + "Required Spell Level: " + MIN_SPELL_LEVEL)
    }
}
