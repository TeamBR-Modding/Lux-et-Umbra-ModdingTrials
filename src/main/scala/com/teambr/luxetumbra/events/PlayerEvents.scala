package com.teambr.luxetumbra.events

import com.teambr.luxetumbra.common.items.traits.CrystalPower
import com.teambr.luxetumbra.managers.ItemManager
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent

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
class PlayerEvents {

    var hadFlight = false

    @SubscribeEvent
    def onPlayerTick(event : PlayerTickEvent): Unit = {
        // Update check for flight
        if(event.player.getEntityWorld.rand.nextInt(80) == 0) {
            if(findStackInInventory(event.player, new ItemStack(ItemManager.flightStone, 1)) != -1) {
                val slot = findStackInInventory(event.player, new ItemStack(ItemManager.dayCrystal))
                if (slot == -1) {
                    return
                }
                val stack = event.player.inventory.getStackInSlot(slot)
                val item = stack.getItem.asInstanceOf[CrystalPower]
                val actual = item.extractEnergy(stack, 1, simulate = true)
                if(actual <= 0)
                    return
            }
            if(hadFlight && !event.player.isCreative) {
                event.player.capabilities.allowFlying = false
                event.player.capabilities.isFlying = false
            }
        }
        hadFlight = event.player.capabilities.allowFlying
    }

    protected def findStackInInventory(player: EntityPlayer, stack: ItemStack): Int = {
        for (i <- player.inventory.mainInventory.indices) {
            if (player.inventory.getStackInSlot(i) != null && player.inventory.getStackInSlot(i).isItemEqualIgnoreDurability(stack))
                return i
        }
        -1
    }
}
