package mod.emt.offlawn.item;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemSeeds;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;

import mod.emt.offlawn.init.OLLBlocks;

public class OLLItemSunflowerBushSeed extends ItemSeeds {
    public OLLItemSunflowerBushSeed(Block crops, Block soil) {
        super(crops, soil);
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        EnumActionResult result = super.onItemUse(player, worldIn, pos, hand, facing, hitX, hitY, hitZ);

        if (result == EnumActionResult.SUCCESS) {
            if (!worldIn.isRemote) {
                OLLBlocks.SUNFLOWER_BUSH.placeAt(worldIn, pos.up(), 2);
            }

            worldIn.playSound(pos.getX(), pos.getY(), pos.getZ(), SoundEvents.BLOCK_GRASS_BREAK, SoundCategory.BLOCKS, 1.0F, 1.0F, true);
        }

        return result;
    }

    @Override
    public EnumPlantType getPlantType(IBlockAccess world, BlockPos pos) {
        return EnumPlantType.Plains;
    }

    @Override
    public IBlockState getPlant(IBlockAccess world, BlockPos pos) {
        return OLLBlocks.SUNFLOWER_BUSH.getDefaultState();
    }

    @Override
    public boolean isDamageable() {
        return false;
    }
}
