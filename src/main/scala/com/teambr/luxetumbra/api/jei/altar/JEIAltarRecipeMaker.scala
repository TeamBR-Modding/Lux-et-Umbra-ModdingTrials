package com.teambr.luxetumbra.api.jei.altar

import java.util

import com.teambr.luxetumbra.registries.AltarRecipes

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
object JEIAltarRecipeMaker {
    def getRecipes: java.util.List[JEIAltarRecipe] = {
        val recipesJEI = new util.ArrayList[JEIAltarRecipe]()
        val recipes = AltarRecipes.getRecipes
        for (recipe <- recipes) {
            recipesJEI.add(new JEIAltarRecipe(recipe.getInputStack, recipe.getOutputStack, recipe.getAltarType, recipe.getAltarSubType, recipe.getSpellLevel))
        }

        recipesJEI
    }
}
