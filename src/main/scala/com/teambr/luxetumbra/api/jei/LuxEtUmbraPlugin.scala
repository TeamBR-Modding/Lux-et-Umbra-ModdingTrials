package com.teambr.luxetumbra.api.jei

import com.teambr.luxetumbra.api.jei.altar.{JEIAltarCategory, JEIAltarRecipeHandler, JEIAltarRecipeMaker}
import mezz.jei.api._

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

        registry.addRecipeCategories(new JEIAltarCategory)
        registry.addRecipeHandlers(new JEIAltarRecipeHandler)

        registry.addRecipes(JEIAltarRecipeMaker.getRecipes)

        //BlackList

    }

    override def onRuntimeAvailable(jeiRuntime: IJeiRuntime): Unit = { }
}
