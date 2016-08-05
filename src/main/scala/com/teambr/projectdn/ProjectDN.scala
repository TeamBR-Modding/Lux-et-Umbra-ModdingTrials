package com.teambr.projectdn

import java.io.File

import com.teambr.projectdn.collections.WorldStructure
import com.teambr.projectdn.commands.DebugAlter
import com.teambr.projectdn.common.CommonProxy
import com.teambr.projectdn.lib.Constants
import com.teambr.projectdn.managers.{BlockManager, ConfigManager, ItemManager}
import net.minecraft.creativetab.CreativeTabs
import net.minecraft.init.Items
import net.minecraft.item.Item
import net.minecraftforge.fml.common.Mod.EventHandler
import net.minecraftforge.fml.common.event.{FMLInitializationEvent, FMLPostInitializationEvent, FMLPreInitializationEvent, FMLServerStartingEvent}
import net.minecraftforge.fml.common.{Mod, SidedProxy}
import org.apache.logging.log4j.LogManager

/**
  * This file was created for ProjectDN
  *
  * ProjectDN is licensed under the
  * Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License:
  * http://creativecommons.org/licenses/by-nc-sa/4.0/
  *
  * @author Dyonovan
  * @since 8/3/2016
  */
@Mod(
    modid = Constants.MOD_ID,
    name = Constants.MOD_NAME,
    version = Constants.VERSION,
    dependencies = Constants.DEPENDENCIES,
    modLanguage = "scala",
    updateJSON = Constants.UPDATE_JSON)
object ProjectDN {

    var configLocation: String = ""

    final val logger = LogManager.getLogger(Constants.MOD_NAME)

    @SidedProxy(clientSide = "com.teambr.projectdn.client.ClientProxy",
        serverSide = "com.teambr.projectdn.common.CommonProxy")
    var proxy : CommonProxy = null

    val tabProjectDN = new CreativeTabs("tabProjectDN") {
        override def getTabIconItem: Item = Items.DIAMOND //TODO change
    }

    @EventHandler
    def preInit(event: FMLPreInitializationEvent) = {
        configLocation = event.getModConfigurationDirectory.getAbsolutePath + File.separator + Constants.MOD_NAME
        ConfigManager.preInit()
        BlockManager.preInit()
        ItemManager.preInit()
    }

    @EventHandler
    def init(event: FMLInitializationEvent) = {
        WorldStructure.buildDefaultAlters
        proxy.init()
    }

    @EventHandler
    def postInit(event: FMLPostInitializationEvent) = {

    }

    @EventHandler def serverLoad(event: FMLServerStartingEvent) {
        event.registerServerCommand(new DebugAlter)
    }
}
