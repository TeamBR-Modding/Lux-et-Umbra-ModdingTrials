package com.teambr.luxetumbra.common.tiles

import com.teambr.bookshelf.common.tiles.traits.{Inventory, UpdatingTile}
import com.teambr.bookshelf.util.WorldUtils
import com.teambr.luxetumbra.collections.WorldStructure.{EnumAlterSubType, EnumAlterType}
import com.teambr.luxetumbra.collections.{AltarRecipe, WorldStructure}
import com.teambr.luxetumbra.registries.AltarRecipes
import com.teambr.luxetumbra.util.TimeUtils
import net.minecraft.entity.item.EntityItem
import net.minecraft.entity.monster.EntityZombie
import net.minecraft.init.SoundEvents
import net.minecraft.inventory.EntityEquipmentSlot
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.util._

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
    var currentRecipeDuration = 0F
    var dayRecipe = true

    override def onServerTick(): Unit = {
        if ((isWorking && getStackInSlot(0) == null) || (isWorking && recipe == null)) {
            reset()
            return
        }

        if (!isWorking && getStackInSlot(0) != null) {
            recipe = AltarRecipes.getRecipe(WorldStructure.getAlterType(getWorld, getPos), getStackInSlot(0))
            if (recipe != null) {
                isWorking = true
                altarSubType = recipe.getAltarSubType
                altarType = WorldStructure.getAlterType(getWorld, getPos)
                dayRecipe = recipe.getAltarType == EnumAlterType.DAY || recipe.getAltarSubType == EnumAlterSubType.DAY
            }
        } else if (isWorking && recipe != null) {
            if(TimeUtils.onSecond(10))
                getWorld.playSound(null, getPos, SoundEvents.AMBIENT_CAVE, SoundCategory.BLOCKS, 0.3F, 0.3F)

            if (chargeCount < recipe.getRequiredCharge) { //TODO check player spell level against spell
                altarType match {
                    case EnumAlterType.DAY => chargeCount += getWorld.getSunBrightnessFactor(1.0F)
                    case EnumAlterType.NEUTRAL =>
                        altarSubType match {
                            case EnumAlterSubType.DAY =>
                                if (getWorld.canSeeSky(getPos) && (getWorld.getWorldTime < 13805 || getWorld.getWorldTime > 22550))
                                    chargeCount += getWorld.getSunBrightnessFactor(1.0F)
                            case EnumAlterSubType.NIGHT =>
                                if (getWorld.canSeeSky(getPos) && (getWorld.getWorldTime > 13805 && getWorld.getWorldTime < 22550))
                                    chargeCount += getWorld.getCurrentMoonPhaseFactor
                            case _ =>
                        }
                    case EnumAlterType.NIGHT =>
                        if (getWorld.canSeeSky(getPos) && (getWorld.getWorldTime < 13805 || getWorld.getWorldTime > 22550))
                            chargeCount += getWorld.getCurrentMoonPhaseFactor * 0.25F
                        else chargeCount += getWorld.getCurrentMoonPhaseFactor
                    case _ =>
                }
            } else {
                getWorld.playSound(null, getPos, SoundEvents.ENTITY_ZOMBIE_VILLAGER_CURE, SoundCategory.BLOCKS, 0.3F, 0.3F)
                if (altarSubType == EnumAlterSubType.DAY)
                    WorldUtils.dropStack(getWorld, recipe.getOutputStack.copy(), getPos.offset(EnumFacing.UP))
                else {
                    val entity = new EntityZombie(getWorld) //TODO higher recipes more armor
                    entity.setHeldItem(EnumHand.MAIN_HAND, recipe.getOutputStack.copy())
                    entity.setDropItemsWhenDead(true)
                    entity.setDropChance(EntityEquipmentSlot.MAINHAND, 1.0F)
                    entity.enablePersistence()
                    entity.setLocationAndAngles(getPos.getX + 0.5, getPos.getY + 1, getPos.getZ + 0.5, 0.0F, 0.0F)
                    val closestPlayer = getWorld.getClosestPlayer(getPos.getX, getPos.getY, getPos.getZ, 10.0, false)
                    if (closestPlayer != null)
                        entity.attackEntityAsMob(closestPlayer)
                    getWorld.spawnEntityInWorld(entity)
                }
                setStackInSlot(0, null)
                reset()
            }
            getWorld.notifyBlockUpdate(getPos, getWorld.getBlockState(getPos), getWorld.getBlockState(getPos), 3)
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
                getWorld.spawnParticle(EnumParticleTypes.ENCHANTMENT_TABLE,
                    getPos.getX + (0.75 - (getWorld.rand.nextFloat() / 2)),
                    getPos.getY + (1.75 - (getWorld.rand.nextFloat() / 2)),
                    getPos.getZ + (0.75 - (getWorld.rand.nextFloat() / 2)),
                    0,
                    0,
                    0)

            val particle = if(dayRecipe) EnumParticleTypes.END_ROD else EnumParticleTypes.FLAME
            // Wave
            if(TimeUtils.onSecond(1)) {
                // Current Progress
                val radius = (chargeCount * 5) / currentRecipeDuration
                for (x <- 0 until 360 by 10) {
                    getWorld.spawnParticle(EnumParticleTypes.DRAGON_BREATH,
                        (getPos.getX + 0.5) + (5 - radius) * Math.cos(Math.toRadians(x)),
                        getPos.getY,
                        (getPos.getZ + 0.5) + (5 - radius) * Math.sin(Math.toRadians(x)),
                        0,
                        0,
                        0)
                }

                // Outer Ring
                for (x <- 0 until 360 by 10) {
                    getWorld.spawnParticle(particle,
                        (getPos.getX + 0.5) + 5 * Math.cos(Math.toRadians(x)),
                        getPos.getY + 0.5,
                        (getPos.getZ + 0.5) + 5 * Math.sin(Math.toRadians(x)),
                        0,
                        0.1,
                        0)
                }

                // Ring 4
                if(radius >= 1)
                    for (x <- 0 until 360 by 10) {
                        getWorld.spawnParticle(particle,
                            (getPos.getX + 0.5) + 4 * Math.cos(Math.toRadians(x)),
                            getPos.getY + 0.5,
                            (getPos.getZ + 0.5) + 4 * Math.sin(Math.toRadians(x)),
                            0,
                            0.080,
                            0)
                    }


                // Ring 3
                if(radius >= 2)
                    for (x <- 0 until 360 by 10) {
                        getWorld.spawnParticle(particle,
                            (getPos.getX + 0.5) + 3 * Math.cos(Math.toRadians(x)),
                            getPos.getY + 0.5,
                            (getPos.getZ + 0.5) + 3 * Math.sin(Math.toRadians(x)),
                            0,
                            0.06,
                            0)
                    }


                // Ring 2
                if(radius >= 3)
                    for (x <- 0 until 360 by 10) {
                        getWorld.spawnParticle(particle,
                            (getPos.getX + 0.5) + 2 * Math.cos(Math.toRadians(x)),
                            getPos.getY + 0.5,
                            (getPos.getZ + 0.5) + 2 * Math.sin(Math.toRadians(x)),
                            0,
                            0.04,
                            0)
                    }


                // Ring 1
                if(radius >= 4)
                    for (x <- 0 until 360 by 10) {
                        getWorld.spawnParticle(particle,
                            (getPos.getX + 0.5) + 1 * Math.cos(Math.toRadians(x)),
                            getPos.getY + 0.5,
                            (getPos.getZ + 0.5) + 1 * Math.sin(Math.toRadians(x)),
                            0,
                            0.02,
                            0)
                    }
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
        chargeCount = tag.getFloat("charge")
        currentRecipeDuration = tag.getFloat("maxCharge")
        dayRecipe = tag.getBoolean("dayRecipe")
        onInventoryChanged(0)
    }

    override def writeToNBT(tag: NBTTagCompound): NBTTagCompound = {
        super[TileEntity].writeToNBT(tag)
        super[Inventory].writeToNBT(tag)
        tag.setBoolean("isWorking", isWorking)
        if(recipe != null) {
            tag.setFloat("charge", chargeCount)
            tag.setFloat("maxCharge", recipe.getRequiredCharge)
            tag.setBoolean("dayRecipe", recipe.getAltarType == EnumAlterType.DAY || recipe.getAltarSubType == EnumAlterSubType.DAY)
        }
        tag
    }
}
