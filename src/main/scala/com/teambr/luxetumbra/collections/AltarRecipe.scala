package com.teambr.luxetumbra.collections

import com.teambr.luxetumbra.collections.WorldStructure.{EnumAlterType, EnumAlterSubType}
import net.minecraft.item.ItemStack

/**
  * This file was created for Lux et Umbra
  *
  * Lux et Umbra is licensed under the
  * Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License:
  * http://creativecommons.org/licenses/by-nc-sa/4.0/
  *
  * @author Dyonovan
  * @since 8/5/2016
  */
class AltarRecipe {

    private var altarType: EnumAlterType = _
    private var altarSubType: EnumAlterSubType = _
    private var inputStack: ItemStack = _
    private var outputStack: ItemStack = _
    private var reqCharge: Float = 0.0F
    private var spellLevel: Int = 0

    def this(alterType: EnumAlterType, alterSubType: EnumAlterSubType, inputStack: ItemStack, outputStack: ItemStack, reqCharge: Float, spellLevel: Int) {
        this()
        this.altarType = alterType
        this.altarSubType = alterSubType
        this.inputStack = inputStack
        this.outputStack = outputStack
        this.reqCharge = reqCharge
        this.spellLevel = spellLevel
    }

    def getAltarType: EnumAlterType = altarType

    def getAltarSubType: EnumAlterSubType = altarSubType

    def getInputStack: ItemStack = inputStack

    def getOutputStack: ItemStack = outputStack

    def getRequiredCharge: Float = reqCharge

    def getSpellLevel: Int = spellLevel
}
