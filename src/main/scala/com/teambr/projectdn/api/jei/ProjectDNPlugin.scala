package com.teambr.projectdn.api.jei

import com.teambr.projectdn.managers.BlockManager
import mezz.jei.api._
import net.minecraft.item.ItemStack

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
object ProjectDNPlugin {
    var jeiHelpers: IJeiHelpers = _
}

@JEIPlugin
class ProjectDNPlugin extends IModPlugin {

    override def register(registry: IModRegistry): Unit = {
        ProjectDNPlugin.jeiHelpers = registry.getJeiHelpers

        //BlackList

    }

    override def onRuntimeAvailable(jeiRuntime: IJeiRuntime): Unit = { }
}
