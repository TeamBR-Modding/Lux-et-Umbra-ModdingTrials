package com.teambr.projectdn.common.items
import com.teambr.projectdn.collections.CrystalType
import com.teambr.projectdn.collections.CrystalType.crystalType
import com.teambr.projectdn.lib.Constants
import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.{IItemPropertyGetter, ItemStack}
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.util.ResourceLocation
import net.minecraft.world.World
import net.minecraftforge.fml.relauncher.{Side, SideOnly}

/**
  * This file was created for ProjectDN
  *
  * ProjectDN is licensed under the
  * Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License:
  * http://creativecommons.org/licenses/by-nc-sa/4.0/
  *
  * @author Dyonovan
  * @since 8/6/2016
  */
class ItemNightCrystal extends CrystalPower {

    override var crystalType: crystalType = CrystalType.crystalType.NIGHT

    var tier: Int = 1
    override var energyCapacity = 10000
    var maxExtract = 10000
    var maxReceive = 10000

    setUnlocalizedName(Constants.MOD_ID + ":nightCrystal")

    override def setDefaultTags(stack: ItemStack): Unit = {
        val tagCompound = new NBTTagCompound

        tagCompound.setInteger("EnergyCapacity", energyCapacity)
        tagCompound.setInteger("MaxExtract", maxExtract)
        tagCompound.setInteger("MaxReceive", maxReceive)
        tagCompound.setInteger("Tier", tier)
        stack.setTagCompound(tagCompound)
    }

    @SideOnly(Side.CLIENT)
    override def addInformation(stack: ItemStack, player: EntityPlayer, list: java.util.List[String], boolean: Boolean): Unit = {
        list.add(getEnergyStored(stack) + " / " + getMaxEnergyStored(stack) + " Night Power")
    }

    this.addPropertyOverride(new ResourceLocation("charge"), new IItemPropertyGetter {
        @SideOnly(Side.CLIENT)
        override def apply(stack: ItemStack, worldIn: World, entityIn: EntityLivingBase): Float = {
            val currentStorage = getEnergyStored(stack).toFloat / getMaxEnergyStored(stack)

            if (currentStorage >= 0 && currentStorage < 0.22)  return 0.0F
            if (currentStorage >= 0.22 && currentStorage < 0.44) return 0.22F
            if (currentStorage >= 0.44 && currentStorage < 0.66) return 0.44F
            if (currentStorage >= 0.66 && currentStorage < 0.88) return 0.66F
            if (currentStorage >= 0.88 && currentStorage < 1.0) return 0.88F
            if (currentStorage >= 1.0) return 1.0F
            0.0F
        }
    })
}
