package com.teambr.luxetumbra.events

import gnu.trove.map.hash.THashMap
import net.minecraft.entity.player.EntityPlayer
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.common.gameevent.TickEvent.{Phase, PlayerTickEvent}

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
object PlayerEvents {

    var resetTimer = new THashMap[EntityPlayer, Int]()
    var lastResetValue = new THashMap[EntityPlayer, Int]()

    @SubscribeEvent
    def onPlayerTick(event : PlayerTickEvent): Unit = {
        if(resetTimer.containsKey(event.player)) {
            // Only run once a tick
            if (event.phase == Phase.START) {
                if (lastResetValue.get(event.player) > -5 && resetTimer.get(event.player) <= 0) {
                    event.player.capabilities.allowFlying = false
                    event.player.capabilities.isFlying = false
                } else if(resetTimer.get(event.player) > 0) {
                    event.player.capabilities.allowFlying = true
                }
                // Update Last number
                lastResetValue.put(event.player, resetTimer.get(event.player))
                resetTimer.put(event.player, resetTimer.get(event.player) - 1)
            }
        }
    }

    def updateForPlayer(player : EntityPlayer): Unit = {
        resetTimer.put(player, 20)
        lastResetValue.put(player, 20)
    }
}
