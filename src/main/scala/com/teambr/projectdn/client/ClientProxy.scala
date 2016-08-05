package com.teambr.projectdn.client

import com.teambr.projectdn.common.CommonProxy

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
class ClientProxy extends CommonProxy {

    override def preInit(): Unit = {
        // Add OBJ Domain
        OBJLoader.INSTANCE.addDomain(Constants.MOD_ID)
    }

    override def init(): Unit = {
        ItemRenderManager.registerItemRenderer()

        ClientRegistry.bindTileEntitySpecialRenderer(classOf[TileDayAltar], new AltarEntityRenderer[TileDayAltar])
    }

    override def postInit(): Unit = { }

}
