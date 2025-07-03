package mod.emt.offlawn.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.IGrowable;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemShears;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.IShearable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import mod.emt.offlawn.init.OLLBlocks;
import mod.emt.offlawn.init.OLLItems;

@SuppressWarnings("deprecation")
public class OLLBlockSunflowerBush extends BlockBush implements IGrowable, IShearable {
    public static final PropertyEnum<EnumBlockHalf> HALF = PropertyEnum.create("half", EnumBlockHalf.class);

    public OLLBlockSunflowerBush() {
        super(Material.VINE);
        this.setDefaultState(this.blockState.getBaseState().withProperty(HALF, EnumBlockHalf.LOWER));
        this.setHardness(0.0F);
        this.setSoundType(SoundType.PLANT);
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return FULL_BLOCK_AABB;
    }

    @Override
    public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
        return super.canPlaceBlockAt(worldIn, pos) && worldIn.isAirBlock(pos.up());
    }

    @Override
    public boolean isReplaceable(IBlockAccess worldIn, BlockPos pos) {
        return false;
    }

    @Override
    protected void checkAndDropBlock(World worldIn, BlockPos pos, IBlockState state) {
        if (!this.canBlockStay(worldIn, pos, state)) {
            boolean flag = state.getValue(HALF) == EnumBlockHalf.UPPER;
            BlockPos blockpos = flag ? pos : pos.up();
            BlockPos blockpos1 = flag ? pos.down() : pos;
            Block block = (flag ? this : worldIn.getBlockState(blockpos).getBlock());
            Block block1 = (flag ? worldIn.getBlockState(blockpos1).getBlock() : this);

            if (!flag) this.dropBlockAsItem(worldIn, pos, state, 0);

            if (block == this) {
                worldIn.setBlockState(blockpos, Blocks.AIR.getDefaultState(), 2);
            }

            if (block1 == this) {
                worldIn.setBlockState(blockpos1, Blocks.AIR.getDefaultState(), 3);
            }
        }
    }

    @Override
    public boolean canBlockStay(World worldIn, BlockPos pos, IBlockState state) {
        if (worldIn.getBlockState(pos.down()).getBlock() == OLLBlocks.BEANSTALK) return true;
        if (state.getBlock() != this) return super.canBlockStay(worldIn, pos, state);
        if (state.getValue(HALF) == EnumBlockHalf.UPPER) {
            return worldIn.getBlockState(pos.down()).getBlock() == this;
        } else {
            IBlockState iblockstate = worldIn.getBlockState(pos.up());
            return iblockstate.getBlock() == this && super.canBlockStay(worldIn, pos, iblockstate);
        }
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        if (state.getValue(HALF) == EnumBlockHalf.UPPER) {
            return Items.AIR;
        } else {
            return super.getItemDropped(state, rand, fortune);
        }
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        worldIn.setBlockState(pos.up(), this.getDefaultState().withProperty(HALF, EnumBlockHalf.UPPER), 2);
    }

    @Override
    public void onBlockHarvested(World worldIn, BlockPos pos, IBlockState state, EntityPlayer player) {
        if (state.getValue(HALF) == EnumBlockHalf.UPPER) {
            if (worldIn.getBlockState(pos.down()).getBlock() == this) {
                if (player.capabilities.isCreativeMode) {
                    worldIn.setBlockToAir(pos.down());
                } else {
                    if (worldIn.isRemote) {
                        worldIn.setBlockToAir(pos.down());
                    } else if (!player.getHeldItemMainhand().isEmpty() && player.getHeldItemMainhand().getItem() instanceof ItemShears) {
                        player.addStat(StatList.getBlockStats(this));
                        worldIn.setBlockToAir(pos.down());
                    } else {
                        worldIn.destroyBlock(pos.down(), true);
                    }
                }
            }
        } else if (worldIn.getBlockState(pos.up()).getBlock() == this) {
            worldIn.setBlockState(pos.up(), Blocks.AIR.getDefaultState(), 2);
        }

        super.onBlockHarvested(worldIn, pos, state, player);
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return (meta & 8) > 0 ? this.getDefaultState().withProperty(HALF, EnumBlockHalf.UPPER) : this.getDefaultState().withProperty(HALF, EnumBlockHalf.LOWER);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(HALF) == EnumBlockHalf.UPPER ? 8 : (0);
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, HALF);
    }

    @Override
    public EnumOffsetType getOffsetType() {
        return EnumOffsetType.XZ;
    }

    @Override
    public Vec3d getOffset(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
        if (worldIn.getBlockState(pos.down()).getBlock() == OLLBlocks.BEANSTALK) {
            return Vec3d.ZERO;
        }
        return super.getOffset(state, worldIn, pos);
    }

    // We want both halves of the tall plant to drop itself when left-clicking it with shears
    @Override
    public boolean isShearable(ItemStack item, IBlockAccess world, BlockPos pos) {
        IBlockState state = world.getBlockState(pos);
        return state.getValue(HALF) == EnumBlockHalf.UPPER;
    }

    @Override
    public List<ItemStack> onSheared(ItemStack item, IBlockAccess world, BlockPos pos, int fortune) {
        return new ArrayList<>();
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
        spawnAsEntity(worldIn, pos, new ItemStack(OLLItems.SUNFLOWER_BUSH_SEED, 1));
    }

    public void placeAt(World worldIn, BlockPos lowerPos, int flags) {
        worldIn.setBlockState(lowerPos, this.getDefaultState().withProperty(HALF, EnumBlockHalf.LOWER), flags);
        worldIn.setBlockState(lowerPos.up(), this.getDefaultState().withProperty(HALF, EnumBlockHalf.UPPER), flags);
    }

    public enum EnumBlockHalf implements IStringSerializable {
        UPPER,
        LOWER;

        @Override
        public String toString() {
            return this.getName();
        }

        @Override
        public String getName() {
            return this == UPPER ? "upper" : "lower";
        }
    }
}
