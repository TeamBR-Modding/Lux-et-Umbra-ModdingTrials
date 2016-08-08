package com.teambr.luxetumbra.managers;

import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 * This file was created for Lux-et-Umbra
 * <p>
 * Lux-et-Umbra is licensed under the
 * Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License:
 * http://creativecommons.org/licenses/by-nc-sa/4.0/
 *
 * @author Dyonovan
 * @since 8/8/2016
 */
public class CraftingRecipeManager {

    public static void preInit() {
        GameRegistry.addRecipe(new ItemStack(BlockManager.blockAltar()),
                "SSS",
                " s ",
                "sss", 'S', Blocks.STONE_SLAB, 's', Blocks.STONE);
    }
}
