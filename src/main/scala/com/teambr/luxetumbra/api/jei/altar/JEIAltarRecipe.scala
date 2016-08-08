package com.teambr.luxetumbra.api.jei.altar

import java.util
import java.util.Collections

import com.teambr.luxetumbra.collections.WorldStructure.{EnumAlterSubType, EnumAlterType}
import com.teambr.luxetumbra.managers.BlockManager
import mezz.jei.api.recipe.BlankRecipeWrapper
import net.minecraft.item.ItemStack

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
class JEIAltarRecipe(itemIn: ItemStack, itemOut: ItemStack, altarType: EnumAlterType, altarSubType: EnumAlterSubType, reqSpellLevel: Int) extends BlankRecipeWrapper {

    override def getInputs: util.List[_] = Collections.singletonList(itemIn)

    override def getOutputs: util.List[_] = Collections.singletonList(itemOut)

    def getInput: ItemStack = itemIn

    def getOutput: ItemStack = itemOut

    def getAltarType: EnumAlterType = altarType

    def getAltarSubType: EnumAlterSubType = altarSubType

    def getReqSpellLevel: Int = reqSpellLevel

    def getAltarIcon: ItemStack = new ItemStack(BlockManager.blockAltar)

}
