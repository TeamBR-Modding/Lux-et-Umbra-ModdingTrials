package com.teambr.luxetumbra.common.tiles

import com.teambr.bookshelf.common.tiles.traits.{Inventory, UpdatingTile}
import com.teambr.bookshelf.util.WorldUtils
import com.teambr.luxetumbra.collections.WorldStructure.{EnumAlterSubType, EnumAlterType}
import com.teambr.luxetumbra.collections.{AltarRecipe, WorldStructure}
import com.teambr.luxetumbra.registries.AltarRecipes
import com.teambr.luxetumbra.util.TimeUtils
import net.minecraft.entity.item.EntityItem
import net.minecraft.entity.monster.EntityZombie
import net.minecraft.inventory.EntityEquipmentSlot
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.util.{EnumHand, EnumParticleTypes}

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
class TileAltar extends UpdatingTile with Inventory {

    var isWorking = false
    var chargeCount = 0.0F
    var entity: EntityItem = _
    var recipe: AltarRecipe = _
    var altarSubType: EnumAlterSubType = _
    var altarType:EnumAlterType = _

    var rotation = 0
    var bounce = 0F
    var bounceDir = 0.001F

    override def onServerTick(): Unit = {
        if (!isWorking && getStackInSlot(0) != null) {
            recipe = AltarRecipes.getRecipe(WorldStructure.getAlterType(worldObj, getPos), getStackInSlot(0))
            if (recipe != null) {
                isWorking = true
                altarSubType = recipe.getAltarSubType
                altarType = WorldStructure.getAlterType(worldObj, getPos)
            }
        } else if (isWorking && recipe != null) {
            if (chargeCount < recipe.getRequiredCharge) { //TODO check player spell level against spell
                altarType match {
                    case EnumAlterType.DAY => chargeCount += worldObj.getSunBrightness(1.0F)
                    case EnumAlterType.NEUTRAL =>
                        altarSubType match {
                            case EnumAlterSubType.DAY =>
                                if (worldObj.canSeeSky(getPos) && (worldObj.getWorldTime < 13805 || worldObj.getWorldTime > 22550))
                                    chargeCount += worldObj.getSunBrightness(1.0F)
                            case EnumAlterSubType.NIGHT =>
                                if (worldObj.canSeeSky(getPos) && (worldObj.getWorldTime > 13805 && worldObj.getWorldTime < 22550))
                                    chargeCount += worldObj.getCurrentMoonPhaseFactor
                            case _ =>
                        }
                    case EnumAlterType.NIGHT =>
                        if (worldObj.canSeeSky(getPos) && (worldObj.getWorldTime < 13805 || worldObj.getWorldTime > 22550))
                            chargeCount += worldObj.getCurrentMoonPhaseFactor * 0.25F
                        else chargeCount += worldObj.getCurrentMoonPhaseFactor
                    case _ =>
                }
            } else {
                if (altarSubType == EnumAlterSubType.DAY)
                    WorldUtils.dropStack(getWorld, recipe.getOutputStack.copy(), getPos)
                else {
                    val entity = new EntityZombie(worldObj) //TODO higher recipes more armor
                    entity.setHeldItem(EnumHand.MAIN_HAND, recipe.getOutputStack.copy())
                    entity.setDropItemsWhenDead(true)
                    entity.setDropChance(EntityEquipmentSlot.MAINHAND, 1.0F)
                    entity.enablePersistence()
                    entity.setLocationAndAngles(getPos.getX + 0.5, getPos.getY + 1, getPos.getZ + 0.5, 0.0F, 0.0F)
                    entity.attackEntityAsMob(worldObj.getClosestPlayer(getPos.getX, getPos.getY, getPos.getZ, 10.0, false))
                    worldObj.spawnEntityInWorld(entity)
                }
                setStackInSlot(0, null)
                reset()
            }
            worldObj.notifyBlockUpdate(getPos, worldObj.getBlockState(getPos), worldObj.getBlockState(getPos), 3)
        }
    }

    private def reset(): Unit = {
        isWorking = false
        chargeCount = 0
        altarSubType = null
        recipe = null
        altarType = null
    }

    override def onClientTick(): Unit = {
        // Do Rotation
        if (rotation >= 360)
            rotation = 0
        else
            rotation += 1

        // Do Bounce
        if (bounce > 0.05F)
            bounceDir = -bounceDir
        else if (bounce < -0.05F)
            bounceDir = -bounceDir
        bounce += bounceDir

        // Do Particles
        if(isWorking) {
            // Do item stuff
            for (x <- 0 until 2)
                worldObj.spawnParticle(EnumParticleTypes.ENCHANTMENT_TABLE,
                    getPos.getX + (0.75 - (worldObj.rand.nextFloat() / 2)),
                    getPos.getY + (1.75 - (worldObj.rand.nextFloat() / 2)),
                    getPos.getZ + (0.75 - (worldObj.rand.nextFloat() / 2)),
                    0,
                    0,
                    0)

            // Wave
            if(TimeUtils.onSecond(1))
                for(x <- 0 until 360 by 10) {
                    worldObj.spawnParticle(EnumParticleTypes.REDSTONE,
                        (getPos.getX + 0.5) + 5 * Math.cos(Math.toRadians(x)),
                        getPos.getY,
                        (getPos.getZ +  0.5) + 5 * Math.sin(Math.toRadians(x)),
                        0,
                        0,
                        0)
                }
        }
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
            } else {
                entity = null
                reset()
            }
        }
    }

    override def initialSize: Int = 1

    override def readFromNBT(tag: NBTTagCompound): Unit = {
        super[TileEntity].readFromNBT(tag)
        super[Inventory].readFromNBT(tag)
        isWorking = tag.getBoolean("isWorking")
        onInventoryChanged(0)
    }

    override def writeToNBT(tag: NBTTagCompound): NBTTagCompound = {
        super[TileEntity].writeToNBT(tag)
        super[Inventory].writeToNBT(tag)
        tag.setBoolean("isWorking", isWorking)
        tag
    }
}
