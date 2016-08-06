package com.teambr.projectdn.capabilities.player;

import net.minecraft.nbt.NBTTagList;

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
public interface ISpellLevelCapability {

    NBTTagList get();

    void set(NBTTagList nbt);

    void setSpellLevel(int level);

    int getSpellLevel();
}
