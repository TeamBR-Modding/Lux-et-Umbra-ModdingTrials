package com.teambr.projectdn.managers

import com.teambr.projectdn.common.items.{ItemCrystal, ItemCrystalSeed, ItemIngotSky}
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

    val itemCrystal = new ItemCrystal
    val itemCrystalSeed = new ItemCrystalSeed

    val ingotSky = new ItemIngotSky

    def preInit(): Unit = {
        //Crystals
        registerItem(itemCrystal, "itemCrystal")
        registerItem(itemCrystalSeed, "itemCrystalSeed")

        //Ingots
        registerItem(ingotSky, "ingotSky")
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
