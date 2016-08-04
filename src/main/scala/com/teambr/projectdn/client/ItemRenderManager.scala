package com.teambr.projectdn.client

import com.teambr.projectdn.lib.Constants
import com.teambr.projectdn.managers.ItemManager
import net.minecraft.block.Block
import net.minecraft.client.renderer.block.model.ModelResourceLocation
import net.minecraft.item.Item
import net.minecraft.util.ResourceLocation
import net.minecraftforge.client.model.ModelLoader

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
object ItemRenderManager {

    def registerItemRenderer(): Unit = {
        registerItem(ItemManager.itemIngot, 0, new ModelResourceLocation(new ResourceLocation(Constants.MOD_ID, "itemIngot_0"), "inventory"))
        //registerItem(ItemManager.itemIngot, 1)


    }

    /*def registerItem(item: Item): Unit = {
        registerItem(item, 0)
        /*Minecraft.getMinecraft.getRenderItem.getItemModelMesher.register(item, 0,
            new ModelResourceLocation(item.getUnlocalizedName.substring(5), "inventory"))
        ModelLoader.setCustomModelResourceLocation(item, 0,
            new ModelResourceLocation(item.getUnlocalizedName.substring(5), "inventory"))*/
    }*/

    def registerItem(item: Item, meta: Int, resource: ModelResourceLocation): Unit = {
        ModelLoader.setCustomModelResourceLocation(item, meta, resource)
    }

    def registerItem(item: Item, meta: Int = 0): Unit = {
        /*Minecraft.getMinecraft.getRenderItem.getItemModelMesher.register(item, meta,
            new ModelResourceLocation(item.getRegistryName, "inventory"))*/
        /*ModelLoader.setCustomMeshDefinition(item, new ItemMeshDefinition {
            override def getModelLocation(stack: ItemStack): ModelResourceLocation = new ModelResourceLocation(item.getRegistryName, "inventory"))
        }) */
        ModelLoader.setCustomModelResourceLocation(item, meta,
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
