package com.teambr.luxetumbra

import java.io.File

import com.teambr.luxetumbra.capabilities.player.SpellLevelCapability
import com.teambr.luxetumbra.collections.WorldStructure
import com.teambr.luxetumbra.commands.{DebugAlter, SetSpellLevel}
import com.teambr.luxetumbra.common.CommonProxy
import com.teambr.luxetumbra.lib.Constants
import com.teambr.luxetumbra.managers.{BlockManager, ConfigManager, ItemManager}
import com.teambr.luxetumbra.registries.AltarRecipes
import net.minecraft.creativetab.CreativeTabs
import net.minecraft.item.Item
import net.minecraftforge.fml.common.Mod.EventHandler
import net.minecraftforge.fml.common.event.{FMLInitializationEvent, FMLPostInitializationEvent, FMLPreInitializationEvent, FMLServerStartingEvent}
import net.minecraftforge.fml.common.{Mod, SidedProxy}
import org.apache.logging.log4j.LogManager

/**
  * This file was created for Lux et Umbra
  *
  * Lux et Umbra is licensed under the
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
object LuxEtUmbra {

    var configLocation: String = ""

    final val logger = LogManager.getLogger(Constants.MOD_NAME)

    @SidedProxy(clientSide = "com.teambr.luxetumbra.client.ClientProxy",
        serverSide = "com.teambr.luxetumbra.common.CommonProxy")
    var proxy : CommonProxy = _

    val luxEtUmbra = new CreativeTabs("tabLuxEtUmbra") {
        override def getTabIconItem: Item = ItemManager.dayCrystal
    }

    @EventHandler
    def preInit(event: FMLPreInitializationEvent) = {
        configLocation = event.getModConfigurationDirectory.getAbsolutePath + File.separator + Constants.MOD_NAME
        ConfigManager.preInit()
        BlockManager.preInit()
        ItemManager.preInit()
        proxy.preInit()
        SpellLevelCapability.register()
    }

    @EventHandler
    def init(event: FMLInitializationEvent) = {
        WorldStructure.buildDefaultAlters()
        proxy.init()
        AltarRecipes.init()
    }

    @EventHandler
    def postInit(event: FMLPostInitializationEvent) = {

    }

    @EventHandler def serverLoad(event: FMLServerStartingEvent) {
        event.registerServerCommand(new DebugAlter)
        event.registerServerCommand(new SetSpellLevel)
    }
}
