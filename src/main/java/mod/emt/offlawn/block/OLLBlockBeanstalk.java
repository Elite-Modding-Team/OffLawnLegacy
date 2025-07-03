package mod.emt.offlawn.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.IGrowable;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.Random;

import mod.emt.offlawn.init.OLLBlocks;

public class OLLBlockBeanstalk extends BlockBush implements IGrowable {
    protected static final AxisAlignedBB BEANSTALK_AABB = new AxisAlignedBB(0.15D, 0.0D, 0.15D, 0.85D, 1.0D, 0.85D);

    public OLLBlockBeanstalk() {
        super(Material.VINE);
        this.setHardness(0.0F);
        this.setSoundType(SoundType.PLANT);
        this.setTickRandomly(true);
    }

    @Override
    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
        grow(worldIn, rand, pos, state);
    }

    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return BEANSTALK_AABB;
    }

    @Override
    public boolean canPlaceBlockAt(World world, BlockPos pos) {
        return canBlockStay(world, pos, world.getBlockState(pos));
    }

    @Override
    public boolean isReplaceable(IBlockAccess worldIn, BlockPos pos) {
        return false;
    }

    @Override
    public boolean canBlockStay(World worldIn, BlockPos pos, IBlockState state) {
        return worldIn.getBlockState(pos.down()).isSideSolid(worldIn, pos, EnumFacing.DOWN) || worldIn.getBlockState(pos.down()).getBlock() == this;
    }

    @Override
    public void onEntityCollision(World world, BlockPos pos, IBlockState state, Entity entity) {
        entity.motionX = MathHelper.clamp(entity.motionX, -0.15D, 0.15D);
        entity.motionZ = MathHelper.clamp(entity.motionZ, -0.15D, 0.15D);
        entity.fallDistance = 0.0F;

        if (entity.motionY < -0.15D) {
            entity.motionY = -0.15D;
        }

        if (entity.isSneaking() && entity.motionY < 0.0D) {
            entity.motionY = 0.0D;
        }

        if (entity.collidedHorizontally) {
            entity.motionY = 0.2D;
        }

        if (world.isRemote && entity instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) entity;

            if (Minecraft.getMinecraft().gameSettings.keyBindJump.isKeyDown()) {
                player.motionY = 0.2D;
            }
        }
    }

    @Override
    public Block.EnumOffsetType getOffsetType() {
        return Block.EnumOffsetType.NONE;
    }

    @Override
    public boolean canGrow(World worldIn, BlockPos pos, IBlockState state, boolean isClient) {
        if ((worldIn.getBlockState(pos.down()).getBlock() == this || this.canBlockStay(worldIn, pos, state))) {
            return worldIn.isAirBlock(pos.up()) && worldIn.isAirBlock(pos.up(2)); // Next two upper blocks are air
        }

        return false;
    }

    @Override
    public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, IBlockState state) {
        return true;
    }

    @Override
    public void grow(World worldIn, Random rand, BlockPos pos, IBlockState state) {
        if (canGrow(worldIn, pos, state, false)) {
            if (worldIn.isAirBlock(pos.up(3))) { // Still air -> Beanstalk
                worldIn.setBlockState(pos.up(), this.getDefaultState());
            } else if (worldIn.getBlockState(pos.up()).getBlock() != this) { // Obstruction -> Sunflower Bush
                OLLBlocks.SUNFLOWER_BUSH.placeAt(worldIn, pos.up(), 2);
            }
        }
    }
}
