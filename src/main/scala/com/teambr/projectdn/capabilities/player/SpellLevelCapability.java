package com.teambr.projectdn.capabilities.player;

import com.teambr.projectdn.lib.Constants;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;


import javax.annotation.Nullable;

/**
 * This file was created for ProjectDN
 * <p>
 * ProjectDN is licensed under the
 * Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License:
 * http://creativecommons.org/licenses/by-nc-sa/4.0/
 *
 * @author Dyonovan
 * @since 8/6/2016
 */
public class SpellLevelCapability {

    @CapabilityInject(ISpellLevelCapability.class)
    public static final Capability<ISpellLevelCapability> SPELL_LEVEL = null;

    public static final EnumFacing DEFAULT_FACING = null;

    public static final ResourceLocation SPELLLEVEL = new ResourceLocation(Constants.MOD_ID(), "SpellLevelCapability");

    public static void register() {

        CapabilityManager.INSTANCE.register(ISpellLevelCapability.class, new Capability.IStorage<ISpellLevelCapability>() {

            @Override
            public NBTBase writeNBT(Capability<ISpellLevelCapability> capability, ISpellLevelCapability instance, EnumFacing side) {
                return instance.get();
            }

            @Override
            public void readNBT(Capability<ISpellLevelCapability> capability, ISpellLevelCapability instance, EnumFacing side, NBTBase nbt) {
                instance.set((NBTTagList) nbt);
            }
        }, SpellLevel::new);

        MinecraftForge.EVENT_BUS.register(new EventHandler());
    }

    public static class EventHandler {
        @SubscribeEvent
        public void playerCreation(AttachCapabilitiesEvent.Entity event) {
            if (event.getEntity() instanceof EntityPlayer)
                event.addCapability(SPELLLEVEL, new Provider());
        }

        @SubscribeEvent
        public void playerClone(PlayerEvent.Clone event) {
            if (event.isWasDeath()) {
                event.getEntityPlayer().getCapability(SpellLevelCapability.SPELL_LEVEL, null)
                        .setSpellLevel(event.getOriginal().getCapability(SpellLevelCapability.SPELL_LEVEL, null).getSpellLevel());
            }
        }
    }

    public static class Provider implements ICapabilitySerializable<NBTTagList> {

        private ISpellLevelCapability spellLevelCapability = new SpellLevel();

        @Override
        public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
            return capability == SPELL_LEVEL;
        }

        @Override
        public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
            if (capability == SPELL_LEVEL)
                return SPELL_LEVEL.cast(spellLevelCapability);

            return null;
        }

        @Override
        public NBTTagList serializeNBT() {
            return (NBTTagList) SPELL_LEVEL.getStorage().writeNBT(SPELL_LEVEL, spellLevelCapability, DEFAULT_FACING);
        }

        @Override
        public void deserializeNBT(NBTTagList nbt) {
            SPELL_LEVEL.getStorage().readNBT(SPELL_LEVEL, spellLevelCapability, DEFAULT_FACING, nbt);
        }
    }
}
