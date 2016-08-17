package com.teambr.luxetumbra.events

import com.google.common.eventbus.Subscribe
import com.teambr.luxetumbra.managers.PlayerSpellLevelManager
import net.minecraft.entity.passive.EntityAnimal
import net.minecraftforge.event.entity.player.AttackEntityEvent
import net.minecraftforge.event.entity.player.PlayerInteractEvent.EntityInteract
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.relauncher.SideOnly

/**
  * This file was created for Lux et Umbra
  *
  * Lux-et-Umbra is licensed under the
  * Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License:
  * http://creativecommons.org/licenses/by-nc-sa/4.0/
  *
  * @author Paul Davis <pauljoda>
  * @since 8/16/2016
  */
object PlayerEvents {

    @SubscribeEvent
    def onAttackPeaceful(event : AttackEntityEvent): Unit = {
        event.getTarget match {
            case animal : EntityAnimal =>
                PlayerSpellLevelManager.modifiyPlayerSpellLevel(event.getEntityPlayer, -1)
            case _ =>
        }
    }

    @SubscribeEvent
    def onAnimalFeed(event : EntityInteract): Unit = {
        event.getTarget match {
            case animal : EntityAnimal =>
                if(!animal.isInLove && animal.isBreedingItem(event.getItemStack))
                    PlayerSpellLevelManager.modifiyPlayerSpellLevel(event.getEntityPlayer, 1)
            case _ =>
        }
    }
}
