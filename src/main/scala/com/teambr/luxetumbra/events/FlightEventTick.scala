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
object FlightEventTick {

    var resetTimer = -5
    var lastResetValue = -5

    @SubscribeEvent
    def onPlayerTick(event : PlayerTickEvent): Unit = {
            // Only run once a tick
            if (event.phase == Phase.START) {
                if (lastResetValue > -5 && resetTimer <= 0) {
                    event.player.capabilities.allowFlying = false
                    event.player.capabilities.isFlying = false
                } else if(resetTimer > 0) {
                    event.player.capabilities.allowFlying = true
                }
                // Update Last number
                lastResetValue = resetTimer
                resetTimer -= 1
            }
    }

    def updateForPlayer(): Unit = {
        resetTimer = 20
        lastResetValue = 20
    }
}
