package com.teambr.projectdn.client.renderers.items

import com.teambr.projectdn.common.items.DummyTile
import com.teambr.projectdn.lib.Constants
import net.minecraft.client.Minecraft
import net.minecraft.client.model.ModelShield
import net.minecraft.client.renderer.GlStateManager
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer
import net.minecraft.util.ResourceLocation

/**
  * This file was created for ProjectDN
  *
  * ProjectDN is licensed under the
  * Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License:
  * http://creativecommons.org/licenses/by-nc-sa/4.0/
  *
  * @author Paul Davis <pauljoda>
  * @since 8/7/2016
  */
class ShieldRendererNight[T <: DummyTile] extends TileEntitySpecialRenderer[T] {

    lazy val modelShield = new ModelShield

    lazy val DAY_SHIELD_LOCATION: ResourceLocation = new ResourceLocation(Constants.MOD_ID, "textures/items/dayShield.png")
    lazy val NIGHT_SHIELD_LOCATION: ResourceLocation = new ResourceLocation(Constants.MOD_ID, "textures/items/nightShield.png")

    override def renderTileEntityAt(te: T, x: Double, y: Double, z: Double, partialTicks: Float, destroyStage: Int): Unit = {
        Minecraft.getMinecraft.getTextureManager.bindTexture(NIGHT_SHIELD_LOCATION)

        GlStateManager.pushMatrix()
        GlStateManager.pushAttrib()
        GlStateManager.enableBlend()
        GlStateManager.scale(1.0F, -1.0F, -1.0F)
        if(Minecraft.getMinecraft.thePlayer.isHandActive) {
            modelShield.plate.render(0.0625F)
            modelShield.handle.render(0.0625F)
        } else
            modelShield.handle.render(0.0625F)
        GlStateManager.popAttrib()
        GlStateManager.popMatrix()
    }
}
