package mod.emt.offlawn.event;

import mod.emt.offlawn.OffLawnLegacy;
import mod.emt.offlawn.block.OLLBlockSunflowerBush;
import mod.emt.offlawn.init.OLLBlocks;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemShears;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = OffLawnLegacy.MOD_ID)
public class OLLEventRightClickBlock {
    @SubscribeEvent
    public static void sunflowerBushShearedEvent(PlayerInteractEvent.RightClickBlock event) {
        World world = event.getEntityLiving().world;
        EntityPlayer player = event.getEntityPlayer();
        ItemStack stack = player.inventory.getStackInSlot(player.inventory.currentItem);

        // Sunflower Bushes break down into two vanilla sunflowers when right-clicked with shears
        if (world.getBlockState(event.getPos()).getBlock() instanceof OLLBlockSunflowerBush && stack != null && stack.getItem() instanceof ItemShears && player instanceof EntityPlayerMP) {
            if (world.getBlockState(event.getPos()) == OLLBlocks.SUNFLOWER_BUSH.getDefaultState() || world.getBlockState(event.getPos().down()) == OLLBlocks.SUNFLOWER_BUSH.getDefaultState()) {
                if (world.getBlockState(event.getPos()) == OLLBlocks.SUNFLOWER_BUSH.getDefaultState()) {
                    world.destroyBlock(event.getPos(), false);
                    world.destroyBlock(event.getPos().up(), false);
                } else if (world.getBlockState(event.getPos().down()) == OLLBlocks.SUNFLOWER_BUSH.getDefaultState()) {
                    world.destroyBlock(event.getPos().down(), false);
                    world.destroyBlock(event.getPos(), false);
                }

                if (!world.isRemote) {
                    EntityItem item = new EntityItem(world, event.getPos().getX(), event.getPos().getY(), event.getPos().getZ(), new ItemStack(Blocks.DOUBLE_PLANT, 2, 0));
                    world.spawnEntity(item);
                }

                player.world.playSound(null, event.getPos(), SoundEvents.ENTITY_SHEEP_SHEAR, SoundCategory.PLAYERS, 1.0F, 1.0F);
                player.addStat(StatList.getObjectUseStats(stack.getItem()));
                event.getItemStack().damageItem(1, player);
                event.setCanceled(true);
            }
        }
    }
}
