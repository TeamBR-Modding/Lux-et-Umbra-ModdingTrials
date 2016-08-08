package com.teambr.luxetumbra.api.jei.altar

import com.teambr.bookshelf.api.jei.drawables.{GuiComponentArrowJEI, SlotDrawable}
import com.teambr.luxetumbra.api.jei.{LuxEtUmbraCategoryUID, LuxEtUmbraPlugin}
import com.teambr.luxetumbra.lib.Constants
import mezz.jei.api.gui.{IDrawable, IRecipeLayout}
import mezz.jei.api.recipe.{IRecipeCategory, IRecipeWrapper}
import net.minecraft.client.Minecraft
import net.minecraft.util.ResourceLocation
import net.minecraft.util.text.translation.I18n

/**
  * This file was created for Lux-et-Umbra
  *
  * Lux-et-Umbra is licensed under the
  * Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License:
  * http://creativecommons.org/licenses/by-nc-sa/4.0/
  *
  * @author Dyonovan
  * @since 8/7/2016
  */
class JEIAltarCategory extends IRecipeCategory[IRecipeWrapper] {

    lazy val location = new ResourceLocation(Constants.MOD_ID, "textures/jei/jei.png")
    lazy val arrow = new GuiComponentArrowJEI(59, 21, LuxEtUmbraPlugin.jeiHelpers)

    lazy val slotInput = new SlotDrawable(31, 20)
    lazy val slotOutput = new SlotDrawable(96, 20)

    lazy val background: IDrawable = LuxEtUmbraPlugin.jeiHelpers.getGuiHelper.createDrawable(location, 0, 0, 170, 60)

    override def getBackground: IDrawable = background

    override def setRecipe(recipeLayout: IRecipeLayout, recipeWrapper: IRecipeWrapper): Unit = {
        val stacks = recipeLayout.getItemStacks
        stacks.init(0, true, 31, 20)
        stacks.init(1, false, 96, 20)
        stacks.init(2, false, 60, 40)

        recipeWrapper match {
            case altar: JEIAltarRecipe =>
                recipeLayout.getItemStacks.set(0, altar.getInput)
                recipeLayout.getItemStacks.set(1, altar.getOutput)
                recipeLayout.getItemStacks.set(2, altar.getAltarIcon)
            case _ =>
        }
    }

    override def drawAnimations(minecraft: Minecraft): Unit = arrow.draw(minecraft, 0, 0)

    override def drawExtras(minecraft: Minecraft): Unit = {
        slotInput.draw(minecraft)
        slotOutput.draw(minecraft)
    }

    override def getTitle: String = I18n.translateToLocal("tile.luxetumbra:blockAltar.name")

    override def getUid: String = Constants.MOD_ID + ":altar"
}
