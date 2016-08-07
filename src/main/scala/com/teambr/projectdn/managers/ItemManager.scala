package com.teambr.projectdn.managers

import com.teambr.projectdn.common.items._
import com.teambr.projectdn.lib.Constants
import net.minecraft.item.Item
import net.minecraftforge.fml.common.registry.{GameRegistry, IForgeRegistryEntry}
import net.minecraftforge.oredict.OreDictionary

/**
  * This file was created for ProjectDN
  *
  * ProjectDN is licensed under the
  * Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License:
  * http://creativecommons.org/licenses/by-nc-sa/4.0/
  *
  * @author Dyonovan
  * @since 8/4/16
  */
object ItemManager {

    lazy val dayCrystal = new ItemDayCrystal
    lazy val nightCrystal = new ItemNightCrystal
    lazy val spellLevelBook = new ItemSpellLevelBook

    lazy val dayShield = new ItemEnergyShield("dayShield")
    lazy val nightShield = new ItemEnergyShield("nightShield")

    def preInit(): Unit = {
        registerItem(spellLevelBook, "spellLevelBook")
        registerItem(dayCrystal, "dayCrystal")
        registerItem(nightCrystal, "nightCrystal")

        registerItem(dayShield, "dayShield")
        registerItem(nightShield, "nightShield")
        new DummyBlock().setRegistryName(Constants.MOD_ID + "dayShield")
        new DummyBlock().setRegistryName(Constants.MOD_ID + "nightShield")
    }

    def registerItem(item: Item, name: String, oreDict: String): Item = {
        item.setRegistryName(Constants.MOD_ID, name)
        GameRegistry.register(item)
        if (oreDict != null) OreDictionary.registerOre(oreDict, item)
        item
    }

    def registerItem(item: Item, name: String): Item = {
        registerItem(item, name, null)
    }

}
