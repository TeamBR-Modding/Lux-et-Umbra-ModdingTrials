package com.teambr.projectdn.commands;

import com.teambr.projectdn.capabilities.player.SpellLevelCapability;
import net.minecraft.client.resources.I18n;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;

import java.util.Collections;
import java.util.List;

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
public class SetSpellLevel extends CommandBase {

    @Override
    public String getCommandName() {
        return "setSpellLevel";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return I18n.format("projectdn:commands.setspelllevel.usage");
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if (args.length != 3) throw new WrongUsageException("projectdn:commands.setspelllevel.usage");

        EntityPlayer player = getPlayer(server, sender, args[0]);

        int currentLevel = player.getCapability(SpellLevelCapability.SPELL_LEVEL, null).getSpellLevel();

        if (args[1].equalsIgnoreCase("add"))
            player.getCapability(SpellLevelCapability.SPELL_LEVEL, null).setSpellLevel(currentLevel + Integer.parseInt(args[2]));
        else if (args[1].equalsIgnoreCase("subtract"))
            player.getCapability(SpellLevelCapability.SPELL_LEVEL, null).setSpellLevel(currentLevel - Integer.parseInt(args[2]));
        else
            throw new WrongUsageException("projectdn:commands.setspelllevel.usage");

        sender.addChatMessage(new TextComponentString(args[0] + "'s " + I18n.format("projectdn:commands.setspelllevel.done") + " "
                + player.getCapability(SpellLevelCapability.SPELL_LEVEL, null).getSpellLevel()));

    }

    @Override
    public int getRequiredPermissionLevel() {
        return 3;
    }

    @Override
    public List<String> getTabCompletionOptions(MinecraftServer server, ICommandSender sender, String[] args, BlockPos pos)
    {
        return args.length == 1 ? getListOfStringsMatchingLastWord(args, server.getAllUsernames()) : Collections.emptyList();
    }

    @Override
    public boolean isUsernameIndex(String[] args, int index)
    {
        return index == 0;
    }
}
