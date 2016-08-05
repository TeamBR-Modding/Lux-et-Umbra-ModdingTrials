package com.teambr.projectdn.client.renderers.tiles

import com.teambr.projectdn.common.tiles.TileDayAltar
import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer
import net.minecraft.client.renderer.{GlStateManager, RenderHelper}
import net.minecraft.entity.item.EntityItem

/**
  * This file was created for ProjectDN
  *
  * ProjectDN is licensed under the
  * Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License:
  * http://creativecommons.org/licenses/by-nc-sa/4.0/
  *
  * @author Dyonovan
  * @since 8/4/2016
  */
class AltarEntityRenderer[T <: TileDayAltar] extends TileEntitySpecialRenderer[T] {

    override def renderTileEntityAt(te: T, x: Double, y: Double, z: Double, partialTicks: Float, destroyStage: Int): Unit = {

        if (te.getStackInSlot(0) == null) return
        val item = new EntityItem(getWorld, 0.0, 0.0, 0.0, te.getStackInSlot(0))
        item.motionX = 0
        item.motionY = 0
        item.motionZ = 0
        item.hoverStart = 0
        item.rotationYaw = 0

        GlStateManager.pushMatrix()
        GlStateManager.translate(x + 0.5, y + 1.0D, z + 0.5)

        RenderHelper.enableStandardItemLighting()
        val renderManager = Minecraft.getMinecraft.getRenderManager

        renderManager.setRenderShadow(false)
        GlStateManager.pushAttrib()
        GlStateManager.rotate(te.rotation + partialTicks, 0, 1, 0)

        renderManager.doRenderEntity(item, 0.0, 0.0, 0.0, 0.0F, 0, true)

        GlStateManager.popAttrib()
        GlStateManager.enableLighting()
        renderManager.setRenderShadow(true)
        GlStateManager.popMatrix()
    }
}
