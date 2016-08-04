package com.teambr.projectdn.collections;

import net.minecraft.block.BlockStoneBrick;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;

/**
 * This file was created for ProjectDN
 * <p>
 * ProjectDN is licensed under the
 * Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License:
 * http://creativecommons.org/licenses/by-nc-sa/4.0/
 *
 * Class to define a world structure
 *
 * @author Paul Davis <pauljoda>
 * @since 8/4/2016
 */
public class WorldStructure {

    public static WorldStructure DAY_TIER_1;

    public IBlockState[][][] structure;

    /***
     * Constructor for a structure with symmetrical dimensions
     * @param dimension The size of the structure, length, width, and height
     */
    public WorldStructure(int dimension) {
        this(dimension, dimension, dimension);
    }

    /**
     * Constructor for a structure with three dimensions
     * @param length -x -> x
     * @param width -z -> z
     * @param height -y -> y
     */
    public WorldStructure(int length, int height, int width) {
        structure = new IBlockState[length][height][width];
    }

    public static void buildDefaultAlters() {
        DAY_TIER_1 =
                WorldStructure.buildAlter(
                        Blocks.STONEBRICK.getDefaultState(),
                        Blocks.STONE_BRICK_STAIRS.getDefaultState(),
                        Blocks.STONEBRICK.getDefaultState(),
                        Blocks.STONEBRICK.getDefaultState().withProperty(BlockStoneBrick.VARIANT, BlockStoneBrick.EnumType.CHISELED));
    }


    /**
     * Builds a structure using the given blocks
     * @param floor The floor block
     * @param stairs The stair block
     * @param pillar The pillar block
     * @param cap The cap blocks (top of pillars)
     * @return
     */
    public static WorldStructure buildAlter(IBlockState floor, IBlockState stairs, IBlockState pillar, IBlockState cap) {
        WorldStructure alter = new WorldStructure(13, 5, 13);

        // Stairs, Could be way easier with vector math but its too early for that
        // Bottom Left Corner
        alter.structure[1][0][3] = stairs;
        alter.structure[1][0][2] = stairs;
        alter.structure[1][0][1] = stairs;
        alter.structure[2][0][1] = stairs;
        alter.structure[3][0][1] = stairs;


        // Bottom row
        alter.structure[4][0][0] = stairs;
        for(int x = 5; x <= 7; x++)
            alter.structure[x][0][0] = stairs;
        alter.structure[8][0][0] = stairs;


        // Bottom Right Corner
        alter.structure[11][0][3] = stairs;
        alter.structure[11][0][2] = stairs;
        alter.structure[11][0][1] = stairs;
        alter.structure[9][0][1]  = stairs;
        alter.structure[10][0][1] = stairs;


        // Right row
        alter.structure[12][0][4] = stairs;
        for(int x = 5; x <= 7; x++)
            alter.structure[12][0][x] = stairs;
        alter.structure[12][0][8] = stairs;

        // Top Left Corner
        alter.structure[3][0][11] = stairs;
        alter.structure[2][0][11] = stairs;
        alter.structure[1][0][11] = stairs;
        alter.structure[1][0][9]  = stairs;
        alter.structure[1][0][10] = stairs;


        // Left row
        alter.structure[0][0][8] = stairs;
        for(int x = 5; x <= 7; x++)
            alter.structure[0][0][x] = stairs;
        alter.structure[0][0][4] = stairs;

        // Top Right Corner
        alter.structure[11][0][10] = stairs;
        alter.structure[11][0][9] = stairs;
        alter.structure[11][0][11] = stairs;
        alter.structure[9][0][11]  = stairs;
        alter.structure[10][0][11] = stairs;


        // Top row
        alter.structure[8][0][12] = stairs;
        for(int x = 5; x <= 7; x++)
            alter.structure[x][0][12] = stairs;
        alter.structure[4][0][12] = stairs;


        // Floor
        for(int x = 4; x <= 8; x++)
            alter.structure[x][0][11] = floor;

        for(int z = 9; z <= 10; z++) {
            for(int x = 2; x <= 10; x++)
                alter.structure[x][0][z] = floor;
        }

        for(int z = 4; z <= 8; z++) {
            for(int x = 1; x <= 11; x++)
                alter.structure[x][0][z] = floor;
        }

        for(int z = 2; z <= 3; z++) {
            for(int x = 2; x <= 10; x++)
                alter.structure[x][0][z] = floor;
        }

        for(int x = 4; x <= 8; x++)
            alter.structure[x][0][1] = floor;


        // Pillars
        for(int y = 0; y <= 4; y++) {
            alter.structure[4][y][1]  = y == 4 ? cap : pillar;
            alter.structure[8][y][1]  = y == 4 ? cap : pillar;
            alter.structure[1][y][4]  = y == 4 ? cap : pillar;
            alter.structure[11][y][4] = y == 4 ? cap : pillar;
            alter.structure[1][y][8]  = y == 4 ? cap : pillar;
            alter.structure[11][y][8] = y == 4 ? cap : pillar;
            alter.structure[4][y][11] = y == 4 ? cap : pillar;
            alter.structure[8][y][11] = y == 4 ? cap : pillar;
        }

        return alter;
    }
}
