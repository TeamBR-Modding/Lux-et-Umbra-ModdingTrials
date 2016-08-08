package com.teambr.luxetumbra.api.jei.altar

import com.teambr.luxetumbra.api.jei.LuxEtUmbraCategoryUID
import com.teambr.luxetumbra.lib.Constants
import mezz.jei.api.recipe.{IRecipeHandler, IRecipeWrapper}

/**
  * This file was created for Lux-et-Umbra
  *
  * Lux-et-Umbra is licensed under the
  * Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License:
  * http://creativecommons.org/licenses/by-nc-sa/4.0/
  *
  * @author Dyonovan
  * @since 8/7/2016
  */
class JEIAltarRecipeHandler extends IRecipeHandler[JEIAltarRecipe] {

    override def getRecipeWrapper(recipe: JEIAltarRecipe): IRecipeWrapper = recipe

    override def getRecipeCategoryUid: String = Constants.MOD_ID + ":altar"

    override def getRecipeCategoryUid(recipe: JEIAltarRecipe): String = Constants.MOD_ID + ":altar"

    override def isRecipeValid(recipe: JEIAltarRecipe): Boolean = true

    override def getRecipeClass: Class[JEIAltarRecipe] = classOf[JEIAltarRecipe]
}
