package com.teambr.projectdn.commands;

import com.teambr.projectdn.collections.WorldStructure;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.BlockStoneBrick;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * This file was created for ProjectDN
 * <p>
 * ProjectDN is licensed under the
 * Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License:
 * http://creativecommons.org/licenses/by-nc-sa/4.0/
 *
 * @author Paul Davis <pauljoda>
 * @since 8/4/2016
 */
public class DebugAlter extends CommandBase {
    @Override
    public String getCommandName() {
        return "buildAlter";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "Debug for alter";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        EntityPlayer player = (EntityPlayer) sender.getCommandSenderEntity();
        World world = server.getEntityWorld();

        BlockPos center = player.getPosition();

        WorldStructure alter = WorldStructure.buildAlter(Blocks.STONEBRICK.getDefaultState(), Blocks.STONE_BRICK_STAIRS.getDefaultState(), Blocks.STONEBRICK.getDefaultState(), Blocks.STONEBRICK.getDefaultState().withProperty(BlockStoneBrick.VARIANT, BlockStoneBrick.EnumType.CHISELED));
        for(int x = 0; x < 13; x++) {
            for(int y = 0; y < 5; y++) {
                for(int z = 0; z < 13; z++) {
                    if(alter.structure[x][y][z] != null)
                        world.setBlockState(new BlockPos(center.getX() + x, center.getY() + y, center.getZ() + z), alter.structure[x][y][z]);
                }
            }
        }
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 3;
    }
}
