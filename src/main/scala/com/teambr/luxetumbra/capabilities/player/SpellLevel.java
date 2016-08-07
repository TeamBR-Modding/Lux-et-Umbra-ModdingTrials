package com.teambr.luxetumbra.capabilities.player;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

/**
 * This file was created for Lux et Umbra
 * <p>
 * Lux et Umbra is licensed under the
 * Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License:
 * http://creativecommons.org/licenses/by-nc-sa/4.0/
 *
 * @author Dyonovan
 * @since 8/6/2016
 */
public class SpellLevel implements ISpellLevelCapability {

    private int spellLevel = 0;

    @Override
    public NBTTagList get() {

        NBTTagList tagList = new NBTTagList();
        NBTTagCompound tag = new NBTTagCompound();
        tag.setInteger("SpellLevel", spellLevel);
        tagList.appendTag(tag);

        return tagList;
    }

    @Override
    public void set(NBTTagList nbt) {
        if (!nbt.hasNoTags()) {
            NBTTagCompound tag = nbt.getCompoundTagAt(0);
            spellLevel = tag.getInteger("SpellLevel");
        }
    }

    @Override
    public void setSpellLevel(int level) {
        spellLevel = level;
    }

    @Override
    public int getSpellLevel() {
        return spellLevel;
    }
}
