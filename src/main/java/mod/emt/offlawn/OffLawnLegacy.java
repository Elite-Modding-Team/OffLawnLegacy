package mod.emt.offlawn;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import mod.emt.offlawn.init.OLLBlocks;

import static mod.emt.offlawn.OffLawnLegacy.*;

@Mod(modid = MOD_ID, name = NAME, version = VERSION, acceptedMinecraftVersions = ACCEPTED_VERSIONS)
public class OffLawnLegacy {
    public static final String MOD_ID = "offlawn";
    public static final String MOD_PREFIX = MOD_ID + ":";
    public static final String NAME = "OffLawn! Legacy";
    public static final String VERSION = "1.0.0";
    public static final String ACCEPTED_VERSIONS = "[1.12.2]";
    public static final Logger LOGGER = LogManager.getLogger(NAME);

    @Mod.Instance
    public static OffLawnLegacy instance;

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        LOGGER.info(NAME + " initialized");
        registerGrassColorHandlers();
    }

    @SideOnly(Side.CLIENT)
    private void registerGrassColorHandlers() {
        IBlockColor blockColor = new IBlockColor() {
            @Override
            public int colorMultiplier(IBlockState state, IBlockAccess world, BlockPos pos, int tintIndex) {
                return Minecraft.getMinecraft().getBlockColors().colorMultiplier(Blocks.GRASS.getDefaultState(), world, pos, tintIndex);
            }
        };

        IItemColor itemColor = new IItemColor() {
            @Override
            public int colorMultiplier(ItemStack stack, int tintIndex) {
                return Minecraft.getMinecraft().getBlockColors().colorMultiplier(Blocks.GRASS.getDefaultState(), null, null, tintIndex);
            }
        };

        Minecraft.getMinecraft().getBlockColors().registerBlockColorHandler(blockColor, OLLBlocks.LAWN);

        Minecraft.getMinecraft().getItemColors().registerItemColorHandler(itemColor, OLLBlocks.LAWN);
    }
}
