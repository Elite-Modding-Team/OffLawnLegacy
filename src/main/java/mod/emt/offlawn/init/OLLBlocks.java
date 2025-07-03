package mod.emt.offlawn.init;

import mod.emt.offlawn.OffLawnLegacy;
import mod.emt.offlawn.block.*;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;

import javax.annotation.Nonnull;

@Mod.EventBusSubscriber(modid = OffLawnLegacy.MOD_ID)
@GameRegistry.ObjectHolder(OffLawnLegacy.MOD_ID)
public class OLLBlocks {
    public static final OLLBlockBeanstalk BEANSTALK = null;
    public static final OLLBlockLawn LAWN = null;
    public static final OLLBlockSunflowerBush SUNFLOWER_BUSH = null;

    @SubscribeEvent
    public static void onRegisterBlocksEvent(@Nonnull final RegistryEvent.Register<Block> event) {
        OffLawnLegacy.LOGGER.info("Registering blocks...");

        final IForgeRegistry<Block> registry = event.getRegistry();

        // BLOCKS
        registry.registerAll
                (
                        OLLRegistry.setup(new OLLBlockBeanstalk(), "beanstalk").setCreativeTab(CreativeTabs.DECORATIONS),
                        OLLRegistry.setup(new OLLBlockLawn(), "lawn").setCreativeTab(CreativeTabs.DECORATIONS),
                        OLLRegistry.setup(new OLLBlockSunflowerBush(), "sunflower_bush").setCreativeTab(CreativeTabs.DECORATIONS)
                );
    }
}
