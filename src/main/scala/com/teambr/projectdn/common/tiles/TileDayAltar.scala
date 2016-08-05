package com.teambr.projectdn.common.tiles

import com.teambr.bookshelf.common.tiles.traits.{Inventory, Syncable}
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.tileentity.TileEntity

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
class TileDayAltar extends Inventory with Syncable {

    var rotation = 0
    var bounce = 0F
    var bounceDir = 0.001F

    override def onServerTick(): Unit = {

    }

    override def onClientTick(): Unit = {
        // Do Rotation
        if (rotation >= 360)
            rotation = 0
        else
            rotation += 1

        // Do Bounce
        if(bounce > 0.05F)
            bounceDir = -bounceDir
        else if(bounce < -0.05F)
            bounceDir = - bounceDir
        bounce += bounceDir
    }

    override def setVariable(id: Int, value: Double): Unit = { }

    override def getVariable(id: Int): Double = { 0.0F }

    override def initialSize: Int = 1

    override def readFromNBT(tag: NBTTagCompound): Unit = {
        super[TileEntity].readFromNBT(tag)
        super[Inventory].readFromNBT(tag)
    }

    override def writeToNBT(tag: NBTTagCompound): NBTTagCompound = {
        super[TileEntity].writeToNBT(tag)
        super[Inventory].writeToNBT(tag)
        tag
    }
}
