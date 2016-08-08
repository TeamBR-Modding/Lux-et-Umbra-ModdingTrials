package com.teambr.luxetumbra.registries

import com.teambr.luxetumbra.collections.AltarRecipe
import com.teambr.luxetumbra.collections.WorldStructure.{EnumAlterType, EnumAlterSubType}
import com.teambr.luxetumbra.managers.ItemManager
import net.minecraft.init.Items
import net.minecraft.item.ItemStack

import scala.collection.mutable.ArrayBuffer

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
object AltarRecipes {

    private lazy val altarRecipes = new ArrayBuffer[AltarRecipe]

    def init(): Unit = {
        //Book
        altarRecipes += new AltarRecipe(EnumAlterType.NEUTRAL, EnumAlterSubType.DAY, new ItemStack(Items.BOOK), new ItemStack(ItemManager.spellLevelBook), 1500.0F, 0)
        altarRecipes += new AltarRecipe(EnumAlterType.NEUTRAL ,EnumAlterSubType.NIGHT, new ItemStack(Items.BOOK), new ItemStack(ItemManager.spellLevelBook), 1500.0F, 0)

        // Crystals
        altarRecipes += new AltarRecipe(EnumAlterType.NEUTRAL, EnumAlterSubType.DAY, new ItemStack(Items.DIAMOND), new ItemStack(ItemManager.dayCrystal), 2400.0F, 0)
        altarRecipes += new AltarRecipe(EnumAlterType.NEUTRAL ,EnumAlterSubType.NIGHT, new ItemStack(Items.EMERALD), new ItemStack(ItemManager.nightCrystal), 2400.0F, 0)

        // Shields
        altarRecipes += new AltarRecipe(EnumAlterType.DAY, EnumAlterSubType.DAY, new ItemStack(Items.SHIELD), new ItemStack(ItemManager.dayShield), 2400.0F, 0)
        altarRecipes += new AltarRecipe(EnumAlterType.NIGHT, EnumAlterSubType.NIGHT, new ItemStack(Items.SHIELD), new ItemStack(ItemManager.nightShield), 2400.0F, 0)

        //Stones
        //Flight
        altarRecipes += new AltarRecipe(EnumAlterType.NIGHT, EnumAlterSubType.NIGHT, new ItemStack(Items.NETHER_STAR), new ItemStack(ItemManager.flightStone), 2400.0F, 0)
        //Food
        altarRecipes += new AltarRecipe(EnumAlterType.DAY, EnumAlterSubType.DAY, new ItemStack(Items.GOLDEN_CARROT), new ItemStack(ItemManager.feeder), 2400.0F, 0)
        //Fire
        altarRecipes += new AltarRecipe(EnumAlterType.NIGHT, EnumAlterSubType.NIGHT, new ItemStack(Items.FIRE_CHARGE), new ItemStack(ItemManager.fireStone), 2400.0F, 0)
        //Teleporter
        altarRecipes += new AltarRecipe(EnumAlterType.DAY, EnumAlterSubType.DAY, new ItemStack(Items.GHAST_TEAR), new ItemStack(ItemManager.teleporter), 2400.0F, 0)

        // Testing
        /*altarRecipes += new AltarRecipe(EnumAlterType.NIGHT, EnumAlterSubType.NIGHT, new ItemStack(Items.NETHER_STAR), new ItemStack(Items.DIAMOND_AXE), 10.0F, -3)
        altarRecipes += new AltarRecipe(EnumAlterType.DAY, EnumAlterSubType.DAY, new ItemStack(Items.NETHER_WART), new ItemStack(Items.DIAMOND_SWORD), 10.0F, 1)
        altarRecipes += new AltarRecipe(EnumAlterType.DAY, EnumAlterSubType.DAY, new ItemStack(Items.STICK), new ItemStack(Items.DIAMOND), 10.0F, 1)*/
    }

    def isItemValid(enumAlterType: EnumAlterType, itemStack: ItemStack): Boolean = {
        if (enumAlterType == EnumAlterType.INVALID || itemStack == null) return false
        if (getRecipe(enumAlterType, itemStack) != null) return true
        false
    }

    def getRecipe(enumAlterType: EnumAlterType, input: ItemStack): AltarRecipe = {
        if (enumAlterType == EnumAlterType.INVALID || input == null) return null
        for (recipe <- altarRecipes) {
            if (recipe.getInputStack.isItemEqual(input))
                recipe.getAltarType match {
                    case EnumAlterType.DAY =>
                        if (enumAlterType == EnumAlterType.DAY) return recipe
                    case EnumAlterType.NEUTRAL =>
                        if (enumAlterType == EnumAlterType.NEUTRAL ||
                                (enumAlterType == EnumAlterType.DAY && recipe.getAltarSubType == EnumAlterSubType.DAY ) ||
                                (enumAlterType == EnumAlterType.NIGHT && recipe.getAltarSubType == EnumAlterSubType.NIGHT))
                            return recipe
                    case EnumAlterType.NIGHT =>
                        if (enumAlterType == EnumAlterType.NIGHT) return recipe
                    case _ =>
                }
        }
        null
    }

    def getRecipes: ArrayBuffer[AltarRecipe] = {
        altarRecipes
    }

}
