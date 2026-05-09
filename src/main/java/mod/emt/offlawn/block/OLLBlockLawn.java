package mod.emt.offlawn.block;

import mod.emt.offlawn.init.OLLBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockTallGrass;
import net.minecraft.block.IGrowable;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.IPlantable;

import java.util.Random;

public class OLLBlockLawn extends Block implements IGrowable {
    public OLLBlockLawn() {
        super(Material.GRASS);
        this.setHardness(0.6F);
        this.setSoundType(SoundType.PLANT);
    }

    // Allows plants to be placed on it like grass blocks
    @Override
    public boolean canSustainPlant(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing direction, IPlantable plantable) {
        return plantable.getPlantType(world, pos.offset(direction)) == EnumPlantType.Plains;
    }

    @Override
    public boolean canGrow(World worldIn, BlockPos pos, IBlockState state, boolean isClient) {
        return true;
    }

    @Override
    public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, IBlockState state) {
        return true;
    }

    @Override
    public void grow(World worldIn, Random rand, BlockPos pos, IBlockState state) {
        BlockPos posUp = pos.up();

        for (int i = 0; i < 128; ++i) {
            BlockPos posUp1 = posUp;
            int j = 0;

            while (true) {
                if (j >= i / 16) {
                    if (worldIn.isAirBlock(posUp1)) {
                        if (rand.nextInt(8) == 0) {
                            worldIn.getBiome(posUp1).plantFlower(worldIn, rand, posUp1);
                        } else {
                            IBlockState grassState = Blocks.TALLGRASS.getDefaultState().withProperty(BlockTallGrass.TYPE, BlockTallGrass.EnumType.GRASS);

                            if (Blocks.TALLGRASS.canBlockStay(worldIn, posUp1, grassState)) {
                                worldIn.setBlockState(posUp1, grassState, 3);
                            }
                        }
                    }

                    break;
                }

                posUp1 = posUp1.add(rand.nextInt(3) - 1, (rand.nextInt(3) - 1) * rand.nextInt(3) / 2, rand.nextInt(3) - 1);

                if (worldIn.getBlockState(posUp1.down()).getBlock() != OLLBlocks.LAWN || worldIn.getBlockState(posUp1).isNormalCube()) {
                    break;
                }

                ++j;
            }
        }
    }
}
