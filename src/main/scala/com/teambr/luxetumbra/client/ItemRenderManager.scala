package com.teambr.luxetumbra.client

import com.teambr.luxetumbra.lib.Constants
import com.teambr.luxetumbra.managers.{BlockManager, ItemManager}
import net.minecraft.block.Block
import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.block.model.ModelResourceLocation
import net.minecraft.item.Item
import net.minecraft.util.ResourceLocation
import net.minecraftforge.client.model.ModelLoader

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
object ItemRenderManager {

    def registerItemRenderers(): Unit = {
        registerItem(ItemManager.spellLevelBook)
        registerItem(ItemManager.dayCrystal)
        registerItem(ItemManager.nightCrystal)
        registerItem(ItemManager.dayShield)
        registerItem(ItemManager.nightShield)
        registerItem(ItemManager.teleporter)
        registerItem(ItemManager.feeder)
        registerItem(ItemManager.fireStone)
        registerItem(ItemManager.flightStone)
    }

    def registerItem(item: Item): Unit = {
        Minecraft.getMinecraft.getRenderItem.getItemModelMesher.register(item, 0,
            new ModelResourceLocation(item.getRegistryName, "inventory"))
        ModelLoader.setCustomModelResourceLocation(item, 0,
            new ModelResourceLocation(item.getRegistryName, "inventory"))
    }

    def registerBlockModel(block : Block, name : String, variants : String, meta : Int = 0) : Unit = {
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block),
            meta, new ModelResourceLocation(new ResourceLocation(Constants.MOD_ID, name), variants))
    }

    def registerItemModel(item : Item, name : String, variants : String, meta : Int = 0) : Unit = {
        ModelLoader.setCustomModelResourceLocation(item,
            meta, new ModelResourceLocation(new ResourceLocation(Constants.MOD_ID, name), variants))
    }
}
