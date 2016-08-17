package com.teambr.luxetumbra.common

import com.teambr.luxetumbra.events.PlayerEvents
import net.minecraftforge.common.MinecraftForge

/**
  * This file was created for Lux et Umbra
  *
  * Lux et Umbra is licensed under the
  * Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License:
  * http://creativecommons.org/licenses/by-nc-sa/4.0/
  *
  * @author Dyonovan
  * @since 8/4/16
  */
class CommonProxy {

    def preInit(): Unit = { }

    def init(): Unit = {
        MinecraftForge.EVENT_BUS.register(PlayerEvents)
    }

    def postInit(): Unit = { }

}
