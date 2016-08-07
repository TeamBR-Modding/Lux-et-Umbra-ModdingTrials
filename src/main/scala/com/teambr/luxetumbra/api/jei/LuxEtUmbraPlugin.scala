package com.teambr.luxetumbra.api.jei

import com.teambr.luxetumbra.managers.BlockManager
import mezz.jei.api._
import net.minecraft.item.ItemStack

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
object LuxEtUmbraPlugin {
    var jeiHelpers: IJeiHelpers = _
}

@JEIPlugin
class LuxEtUmbraPlugin extends IModPlugin {

    override def register(registry: IModRegistry): Unit = {
        LuxEtUmbraPlugin.jeiHelpers = registry.getJeiHelpers

        //BlackList

    }

    override def onRuntimeAvailable(jeiRuntime: IJeiRuntime): Unit = { }
}
