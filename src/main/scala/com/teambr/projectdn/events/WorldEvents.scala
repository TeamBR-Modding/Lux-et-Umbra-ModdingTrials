package com.teambr.projectdn.events

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.common.gameevent.PlayerEvent

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
class WorldEvents {

    @SubscribeEvent
    def onPlayerJoin(event: PlayerEvent.PlayerLoggedInEvent): Unit = {

    }
}
