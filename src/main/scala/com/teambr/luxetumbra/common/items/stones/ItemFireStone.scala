package com.teambr.luxetumbra.common.items.stones

import com.teambr.bookshelf.client.gui.{GuiColor, GuiTextFormat}
import com.teambr.luxetumbra.LuxEtUmbra
import com.teambr.luxetumbra.collections.CrystalType.crystalType
import com.teambr.luxetumbra.common.items.traits.EnergyUserItem
import com.teambr.luxetumbra.lib.Constants
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.entity.projectile.EntitySmallFireball
import net.minecraft.item.ItemStack
import net.minecraft.util.{ActionResult, EnumActionResult, EnumHand}
import net.minecraft.world.World
import net.minecraftforge.fml.relauncher.{Side, SideOnly}

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
class ItemFireStone extends EnergyUserItem {
    override val MAGIC_TYPE: crystalType = crystalType.NIGHT
    override val ENERGY_REQUIRED: Int = 200
    override val MIN_SPELL_LEVEL: Int = 20

    setUnlocalizedName(Constants.MOD_ID + ":fireStone")
    setCreativeTab(LuxEtUmbra.luxEtUmbra)

    override def onItemRightClick(stack: ItemStack, world: World, player: EntityPlayer, hand: EnumHand): ActionResult[ItemStack] = {
        if (world.isRemote) return new ActionResult(EnumActionResult.SUCCESS, stack)
        if (useEnergy(player)) {
            val look = player.getLookVec
            val fireball = new EntitySmallFireball(world, player, 1, 1, 1)
            fireball.setPosition(
                player.posX + look.xCoord,
                player.posY + look.yCoord + player.getEyeHeight,
                player.posZ + look.zCoord)
            fireball.accelerationX = look.xCoord * 0.1
            fireball.accelerationY = look.yCoord * 0.1
            fireball.accelerationZ = look.zCoord * 0.1
            world.spawnEntityInWorld(fireball)
            new ActionResult(EnumActionResult.SUCCESS, stack)
        }
        new ActionResult(EnumActionResult.PASS, stack)
    }

    @SideOnly(Side.CLIENT)
    override def addInformation(stack: ItemStack, player: EntityPlayer, list: java.util.List[String], boolean: Boolean): Unit = {
        list.add("Shoots fireballs on right click")
        list.add(GuiTextFormat.ITALICS + "" + GuiColor.ORANGE + "Required Spell Level: -" + MIN_SPELL_LEVEL)
    }
}