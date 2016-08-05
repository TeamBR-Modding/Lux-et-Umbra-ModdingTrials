package com.teambr.projectdn.common.tiles

import com.teambr.bookshelf.common.tiles.traits.{Inventory, UpdatingTile}
import com.teambr.projectdn.collections.WorldStructure.EnumAlterType
import com.teambr.projectdn.collections.{AltarRecipe, WorldStructure}
import com.teambr.projectdn.registries.AltarRecipes
import net.minecraft.entity.item.EntityItem
import net.minecraft.nbt.NBTTagCompound

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
class TileAltar extends UpdatingTile with Inventory {

    var isWorking = false
    var chargeCount = 0.0F
    var entity: EntityItem = _
    var recipe: AltarRecipe = _
    var altarType: EnumAlterType = EnumAlterType.INVALID

    var rotation = 0
    var bounce = 0F
    var bounceDir = 0.001F

    override def onServerTick(): Unit = {
        if (!isWorking && getStackInSlot(0) != null) {
            recipe = AltarRecipes.getRecipe(WorldStructure.getAlterType(worldObj, getPos), getStackInSlot(0))
            if (recipe != null) {
                isWorking = true
                altarType = WorldStructure.getAlterType(worldObj, getPos)
            }
        } else if (isWorking) {
            if (chargeCount < recipe.getRequiredCharge) {
                altarType match {
                    case EnumAlterType.DAY_TIER_1 =>
                        if (worldObj.canSeeSky(getPos) && (worldObj.getWorldTime < 13805 ||  worldObj.getWorldTime > 22550))
                            chargeCount += worldObj.getSunBrightness(1.0F)
                    case EnumAlterType.NIGHT_TIER_1 =>
                        if (worldObj.canSeeSky(getPos) && (worldObj.getWorldTime > 13805 ||  worldObj.getWorldTime < 22550))
                            chargeCount += worldObj.getCurrentMoonPhaseFactor
                    case _ =>
                }
            } else {
                setStackInSlot(0, recipe.getOutputStack.copy())
                reset()
                worldObj.notifyBlockUpdate(getPos, worldObj.getBlockState(getPos), worldObj.getBlockState(getPos), 3)
            }
        }
    }

    private def reset(): Unit = {
        isWorking = false
        chargeCount = 0
        altarType = EnumAlterType.INVALID
        recipe = null
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

    override def onInventoryChanged(slot: Int): Unit = {
        super.onInventoryChanged(slot)
        if (slot == 0) {
            if (getStackInSlot(0) != null) {
                entity = new EntityItem(getWorld, 0.0, 0.0, 0.0, getStackInSlot(0))
                entity.motionX = 0
                entity.motionY = 0
                entity.motionZ = 0
                entity.hoverStart = 0
                entity.rotationYaw = 0
            } else entity = null
        }
        reset()
    }

    override def initialSize: Int = 1

    override def readFromNBT(tag: NBTTagCompound): Unit = {
        super[TileEntity].readFromNBT(tag)
        super[Inventory].readFromNBT(tag)
        /*isWorking = tag.getBoolean("IsWorking")
        chargeCount = tag.getFloat("ChannelCount")*/
        onInventoryChanged(0)
    }

    override def writeToNBT(tag: NBTTagCompound): NBTTagCompound = {
        super[TileEntity].writeToNBT(tag)
        super[Inventory].writeToNBT(tag)
        /*tag.setBoolean("IsWorking", isWorking)
        tag.setFloat("ChannelCount", chargeCount)*/
        tag
    }
}
