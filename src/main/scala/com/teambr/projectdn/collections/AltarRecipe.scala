package com.teambr.projectdn.collections

import com.teambr.projectdn.collections.WorldStructure.EnumAlterType
import net.minecraft.item.ItemStack

/**
  * This file was created for ProjectDN
  *
  * ProjectDN is licensed under the
  * Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License:
  * http://creativecommons.org/licenses/by-nc-sa/4.0/
  *
  * @author Dyonovan
  * @since 8/5/2016
  */
class AltarRecipe {

    private var altarType: EnumAlterType = _
    private var inputStack: ItemStack = _
    private var outputStack: ItemStack = _
    private var reqCharge: Float = 0.0F

    def this(alterType: EnumAlterType, inputStack: ItemStack, outputStack: ItemStack, reqCharge: Float) {
        this()
        this.altarType = alterType
        this.inputStack = inputStack
        this.outputStack = outputStack
        this.reqCharge = reqCharge
    }

    def getAltarType: EnumAlterType = altarType

    def getInputStack: ItemStack = inputStack

    def getOutputStack: ItemStack = outputStack

    def getRequiredCharge: Float = reqCharge
}
