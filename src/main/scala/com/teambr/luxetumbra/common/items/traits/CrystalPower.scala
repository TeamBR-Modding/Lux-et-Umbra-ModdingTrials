package com.teambr.luxetumbra.common.items.traits

import com.teambr.luxetumbra.LuxEtUmbra
import com.teambr.luxetumbra.capabilities.player.SpellLevelCapability
import com.teambr.luxetumbra.collections.CrystalType
import net.minecraft.entity.Entity
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.{Item, ItemStack}
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

/**
  * This file was created for Lux et Umbra
  *
  * Lux et Umbra is licensed under the
  * Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License:
  * http://creativecommons.org/licenses/by-nc-sa/4.0/
  *
  * @author Dyonovan
  * @since 8/6/2016
  */
trait CrystalPower extends Item {

    lazy val rechargeRate = 10

    setMaxStackSize(1)
    setCreativeTab(LuxEtUmbra.luxEtUmbra)
    setMaxDamage(16)
    setNoRepair()

    var energyCapacity: Int
    var crystalType: CrystalType.crystalType

    def setDefaultTags(stack: ItemStack): Unit

    override def onCreated(stack: ItemStack, worldIn: World, playerIn: EntityPlayer): Unit = {
        setDefaultTags(stack)
        updateDamage(stack)
    }

    override def onUpdate(stack: ItemStack, world: World, entity: Entity, itemSlot: Int, isSelected: Boolean): Unit = {
        if(!stack.hasTagCompound)
            setDefaultTags(stack)

        if (world.canSeeSky(new BlockPos(entity.posX, entity.posY, entity.posZ))) {
            crystalType match {
                case CrystalType.crystalType.DAY =>
                    if (world.getWorldTime < 13805 || world.getWorldTime > 22550) {
                        receiveEnergy(stack, rechargeRate, simulate = false)
                    }
                case CrystalType.crystalType.NIGHT =>
                    if (world.getWorldTime > 13805 && world.getWorldTime < 22550)
                        receiveEnergy(stack, rechargeRate, simulate = false)
                case _ =>
            }
        }
    }

    def receiveEnergy(stack: ItemStack, maxReceive: Int, simulate: Boolean): Int = {
        if (!stack.hasTagCompound) {
            stack.setTagCompound(new NBTTagCompound)
        }
        var energy: Int = stack.getTagCompound.getInteger("Energy")
        val energyReceived: Int = Math.min(stack.getTagCompound.getInteger("EnergyCapacity") - energy,
            Math.min(stack.getTagCompound.getInteger("MaxReceive"), maxReceive))
        if (!simulate) {
            energy += energyReceived
            stack.getTagCompound.setInteger("Energy", energy)
            updateDamage(stack)
        }
        energyReceived
    }

    def extractEnergy(stack: ItemStack, maxExtract: Int, simulate: Boolean): Int = {
        if (stack.getTagCompound == null || !stack.getTagCompound.hasKey("Energy")) {
            return 0
        }
        var energy = stack.getTagCompound.getInteger("Energy")
        val energyExtracted = Math.min(energy, Math.min(stack.getTagCompound.getInteger("MaxExtract"), maxExtract))

        if (!simulate) {
            energy -= energyExtracted
            stack.getTagCompound.setInteger("Energy", energy)
            updateDamage(stack)
        }
        energyExtracted
    }

    /**
      * Get the amount of energy currently stored in the container item.
      */
    def getEnergyStored(stack: ItemStack): Int = {
        if (stack.getTagCompound == null || !stack.getTagCompound.hasKey("Energy"))
            return 0
        stack.getTagCompound.getInteger("Energy")
    }

    /**
      * Get the max amount of energy that can be stored in the container item.
      */
    def getMaxEnergyStored(stack: ItemStack): Int = {
        if (stack.getTagCompound == null || !stack.getTagCompound.hasKey("EnergyCapacity"))
            return 0
        stack.getTagCompound.getInteger("EnergyCapacity")
    }

    /**
      * Sets the Damage Bar on the item to correspond to amount of energy stored
      */
    def updateDamage(stack: ItemStack): Unit = {
        val r = getEnergyStored(stack).toFloat / getMaxEnergyStored(stack)
        var res = 16 - Math.round(r * 16)
        if (r < 1 && res == 0)
            res = 1
        stack.setItemDamage(res)
    }



}
