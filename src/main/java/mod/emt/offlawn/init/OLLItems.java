package mod.emt.offlawn.init;

import javax.annotation.Nonnull;

import mod.emt.offlawn.OffLawnLegacy;
import mod.emt.offlawn.item.OLLItemSunflowerBushSeed;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.IForgeRegistry;

@SuppressWarnings({"deprecation", "unused"})
@Mod.EventBusSubscriber(modid = OffLawnLegacy.MOD_ID)
@GameRegistry.ObjectHolder(OffLawnLegacy.MOD_ID)
public class OLLItems {
    public static final OLLItemSunflowerBushSeed SUNFLOWER_BUSH_SEED = null;

    @SubscribeEvent
    public static void onRegisterItemsEvent(@Nonnull final RegistryEvent.Register<Item> event) {
        OffLawnLegacy.LOGGER.info("Registering items...");

        final IForgeRegistry<Item> registry = event.getRegistry();

        // ITEMS
        registry.registerAll
                (
                        OLLRegistry.setup(new OLLItemSunflowerBushSeed(OLLBlocks.SUNFLOWER_BUSH, Blocks.GRASS), "sunflower_bush_seed").setCreativeTab(CreativeTabs.DECORATIONS)
                );

        // ITEM BLOCKS
        ForgeRegistries.BLOCKS.getValues().stream()
                .filter(block -> block.getRegistryName().getNamespace().equals(OffLawnLegacy.MOD_ID))
                .forEach(block -> registry.register(OLLRegistry.setup(new ItemBlock(block), block.getRegistryName())));
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public static void onRegisterModelsEvent(@Nonnull final ModelRegistryEvent event) {
        OffLawnLegacy.LOGGER.info("Registering item models...");

        // ITEM MODELS
        for (final Item item : ForgeRegistries.ITEMS.getValues()) {
            if (item.getRegistryName().getNamespace().equals(OffLawnLegacy.MOD_ID)) {
                ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
            }
        }
    }
}
