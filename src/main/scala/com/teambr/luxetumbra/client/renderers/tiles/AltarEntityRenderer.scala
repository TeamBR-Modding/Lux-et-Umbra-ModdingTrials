package com.teambr.luxetumbra.client.renderers.tiles

import com.teambr.luxetumbra.common.tiles.TileAltar
import net.minecraft.block.Block
import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer
import net.minecraft.client.renderer.{GlStateManager, RenderHelper}
import net.minecraft.entity.item.EntityItem

/**
  * This file was created for Lux et Umbra
  *
  * Lux et Umbra is licensed under the
  * Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License:
  * http://creativecommons.org/licenses/by-nc-sa/4.0/
  *
  * @author Dyonovan
  * @since 8/4/2016
  */
class AltarEntityRenderer[T <: TileAltar] extends TileEntitySpecialRenderer[T] {

    override def renderTileEntityAt(te: T, x: Double, y: Double, z: Double, partialTicks: Float, destroyStage: Int): Unit = {

        //if (te.getStackInSlot(0) == null) return
        if (te.entity == null) return
        val item = te.entity

        GlStateManager.pushMatrix()
        GlStateManager.translate(x + 0.5, y + 1.0D, z + 0.5)

        RenderHelper.enableStandardItemLighting()
        val renderManager = Minecraft.getMinecraft.getRenderManager

        renderManager.setRenderShadow(false)
        GlStateManager.pushAttrib()
        GlStateManager.rotate(te.rotation + partialTicks, 0, 1, 0)
        GlStateManager.scale(1.3, 1.3, 1.3)

        val dropHeight = if(Block.getBlockFromItem(te.getStackInSlot(0).getItem) != null) -0.05 else -0.18

        renderManager.doRenderEntity(item, 0.0, dropHeight + te.bounce, 0.0, 0.0F, 0, true)



        GlStateManager.popAttrib()
        GlStateManager.enableLighting()
        renderManager.setRenderShadow(true)
        GlStateManager.popMatrix()
    }
}
