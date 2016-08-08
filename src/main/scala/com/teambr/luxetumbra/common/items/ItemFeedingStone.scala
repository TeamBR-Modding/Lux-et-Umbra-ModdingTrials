package com.teambr.luxetumbra.common.items

import com.teambr.bookshelf.client.gui.{GuiColor, GuiTextFormat}
import com.teambr.luxetumbra.LuxEtUmbra
import com.teambr.luxetumbra.collections.CrystalType.crystalType
import com.teambr.luxetumbra.common.items.traits.{CrystalPower, EnergyUserItem}
import com.teambr.luxetumbra.lib.Constants
import com.teambr.luxetumbra.managers.ItemManager
import net.minecraft.entity.Entity
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.init.SoundEvents
import net.minecraft.item.ItemStack
import net.minecraft.util.SoundCategory
import net.minecraft.util.text.TextComponentTranslation
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
class ItemFeedingStone extends EnergyUserItem {
    override val MAGIC_TYPE: crystalType = crystalType.DAY
    override val ENERGY_REQUIRED: Int = 1000
    override val MIN_SPELL_LEVEL: Int = 50

    setUnlocalizedName(Constants.MOD_ID + ":feedingStone")
    setCreativeTab(LuxEtUmbra.luxEtUmbra)

    override def onUpdate(stack: ItemStack, worldIn: World, entityIn: Entity, itemSlot: Int, isSelected: Boolean): Unit = {
        if(worldIn.rand.nextInt(100) == 0) {
            entityIn match {
                case player: EntityPlayer =>
                    val slot = findStackInInventory(player, new ItemStack(ItemManager.dayCrystal))
                    if (slot == -1) {
                        return
                    }
                    val stack = player.inventory.getStackInSlot(slot)
                    val item = stack.getItem.asInstanceOf[CrystalPower]
                    val actual = item.extractEnergy(stack, ENERGY_REQUIRED, simulate = true)
                    if (actual > 0) {
                        if (player.getFoodStats.getFoodLevel <= 10) {
                            player.getFoodStats.addStats(20, 20F)
                            worldIn.playSound(player, player.getPosition, SoundEvents.ENTITY_PLAYER_BURP, SoundCategory.PLAYERS, 0.5F, worldIn.rand.nextFloat() * 0.1F + 0.9F)
                            useEnergy(player, displayMessage = false)
                        }
                    }
                case _ =>
            }
        }
    }

    @SideOnly(Side.CLIENT)
    override def addInformation(stack: ItemStack, player: EntityPlayer, list: java.util.List[String], boolean: Boolean): Unit = {
        list.add("Automatically Feeds the Player")
        list.add(GuiTextFormat.ITALICS + "" + GuiColor.ORANGE + "Required Spell Level: " + MIN_SPELL_LEVEL)
    }

    override def onDroppedByPlayer(item: ItemStack, player: EntityPlayer): Boolean = {
        super.onDroppedByPlayer(item, player)
    }
}
