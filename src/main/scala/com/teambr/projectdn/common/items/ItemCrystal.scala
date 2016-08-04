package com.teambr.projectdn.common.items

import java.util

import com.teambr.projectdn.ProjectDN
import com.teambr.projectdn.lib.Constants
import net.minecraft.creativetab.CreativeTabs
import net.minecraft.item.{Item, ItemStack}
import net.minecraftforge.fml.relauncher.{Side, SideOnly}

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
class ItemCrystal extends Item {

    val numCrystals = 6
    val unLocName = Constants.MOD_ID + ":itemCrystal"

    setCreativeTab(ProjectDN.tabProjectDN)
    setMaxStackSize(64)

    setHasSubtypes(true)
    setMaxDamage(0)

    override def getUnlocalizedName(stack: ItemStack): String = {
        unLocName + "_" + stack.getMetadata
    }

    @SideOnly(Side.CLIENT)
    override def getSubItems(item: Item, tab: CreativeTabs, subItems: util.List[ItemStack]): Unit = {
        for (i:Int <- 0 until numCrystals) {
            subItems.add(new ItemStack(item, 1, i))
        }
    }

}
