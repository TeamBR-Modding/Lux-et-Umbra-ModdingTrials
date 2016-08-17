package com.teambr.luxetumbra.events

import com.teambr.luxetumbra.capabilities.player.SpellLevelCapability
import com.teambr.luxetumbra.managers.PlayerSpellLevelManager
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.common.gameevent.PlayerEvent

/**
  * This file was created for Lux et Umbra
  *
  * Lux et Umbra is licensed under the
  * Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License:
  * http://creativecommons.org/licenses/by-nc-sa/4.0/
  *
  * @author Dyonovan
  * @since 8/6/2016
  */
object WorldEvents {

    @SubscribeEvent
    def onPlayerJoin(event: PlayerEvent.PlayerLoggedInEvent): Unit = {
        val player = event.player
        PlayerSpellLevelManager.setPlayerSpellLevel(player, player.getCapability(SpellLevelCapability.SPELL_LEVEL, null).getSpellLevel)
    }
}
