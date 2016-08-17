package com.teambr.luxetumbra.client

import com.teambr.luxetumbra.client.renderers.items.{ShieldRendererDay, ShieldRendererNight}
import com.teambr.luxetumbra.client.renderers.tiles.AltarEntityRenderer
import com.teambr.luxetumbra.common.CommonProxy
import com.teambr.luxetumbra.common.items.{DummyDayTile, DummyNightTile, DummyTile, ItemEnergyShield}
import com.teambr.luxetumbra.common.tiles.TileAltar
import com.teambr.luxetumbra.events.FlightEventTick
import com.teambr.luxetumbra.lib.Constants
import com.teambr.luxetumbra.managers.{BlockManager, ItemManager}
import net.minecraftforge.client.ForgeHooksClient
import net.minecraftforge.client.model.obj.OBJLoader
import net.minecraftforge.common.{ForgeHooks, MinecraftForge}
import net.minecraftforge.fml.client.registry.ClientRegistry

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
class ClientProxy extends CommonProxy {

    override def preInit(): Unit = {
        // Item Block Models
        ItemRenderManager.registerBlockModel(BlockManager.blockAltar, "blockAltar", "normal")
    }

    override def init(): Unit = {
        ItemRenderManager.registerItemRenderers()

        // Shield
        ForgeHooksClient.registerTESRItemStack(ItemManager.dayShield, 0, classOf[DummyDayTile])
        ForgeHooksClient.registerTESRItemStack(ItemManager.nightShield, 0, classOf[DummyNightTile])

        ClientRegistry.bindTileEntitySpecialRenderer(classOf[DummyDayTile], new ShieldRendererDay[DummyDayTile])
        ClientRegistry.bindTileEntitySpecialRenderer(classOf[DummyNightTile], new ShieldRendererNight[DummyNightTile])
        ClientRegistry.bindTileEntitySpecialRenderer(classOf[TileAltar], new AltarEntityRenderer[TileAltar])

        MinecraftForge.EVENT_BUS.register(FlightEventTick)
    }

    override def postInit(): Unit = { }

}
