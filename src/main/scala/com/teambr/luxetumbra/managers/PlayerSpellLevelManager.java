package com.teambr.luxetumbra.managers;

import com.teambr.luxetumbra.capabilities.player.SpellLevelCapability;
import com.teambr.luxetumbra.network.PacketDispatcher;
import com.teambr.luxetumbra.network.UpdateSpellLevelPacket;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;

/**
 * This file was created for Lux et Umbra
 * <p>
 * Lux-et-Umbra is licensed under the
 * Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License:
 * http://creativecommons.org/licenses/by-nc-sa/4.0/
 *
 * @author Paul Davis <pauljoda>
 * @since 8/16/2016
 */
public class PlayerSpellLevelManager {

    /**
     * Get the spell level from the given player
     * @param player The player
     * @return The current spell level, 0 if not found
     */
    public static int getPlayerSpellLevel(EntityPlayer player) {
        return player.getCapability(SpellLevelCapability.SPELL_LEVEL, null).getSpellLevel();
    }

    /**
     * Modify the player spell level
     * @param player Player
     * @param amount Spell level modifier, can be positive or negative
     */
    public static void modifiyPlayerSpellLevel(EntityPlayer player, int amount) {
        player.getCapability(SpellLevelCapability.SPELL_LEVEL, null)
                .setSpellLevel(player.getCapability(SpellLevelCapability.SPELL_LEVEL, null).getSpellLevel() + amount);
        PacketDispatcher.net.sendTo(new UpdateSpellLevelPacket(player, player.getCapability(SpellLevelCapability.SPELL_LEVEL, null).getSpellLevel()), (EntityPlayerMP) player);
    }

    /**
     * Set the player spell level
     * @param player Player
     * @param amount Spell level to set, can be positive or negative
     */
    public static void setPlayerSpellLevel(EntityPlayer player, int amount) {
        player.getCapability(SpellLevelCapability.SPELL_LEVEL, null)
                .setSpellLevel(amount);
        PacketDispatcher.net.sendTo(new UpdateSpellLevelPacket(player, amount), (EntityPlayerMP) player);
    }
}
