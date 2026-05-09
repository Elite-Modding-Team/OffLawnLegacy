package mod.emt.offlawn.config;

import mod.emt.offlawn.OffLawnLegacy;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Config(modid = OffLawnLegacy.MOD_ID, name = "OffLawnLegacy")
public class OLLConfig {
    @Config.LangKey("config.offlawn.general")
    public static final GeneralSettings GENERAL_SETTINGS = new GeneralSettings();
    @Config.LangKey("config.offlawn.world_gen")
    public static final WorldGenSettings WORLD_GEN_SETTINGS = new WorldGenSettings();

    public static class GeneralSettings {
        @Config.RequiresMcRestart
        @Config.Name("Roasted Sunflower Bush Seed Heal Amount")
        @Config.Comment("Amount of health restored by eating a roasted sunflower bush seed")
        public int roastedSunflowerBushSeedHealAmount = 2;

        @Config.RequiresMcRestart
        @Config.Name("Roasted Sunflower Bush Seed Saturation")
        @Config.Comment("Amount of saturation restored by eating a roasted sunflower bush seed")
        public double roastedSunflowerBushSeedSaturation = 0.4D;
    }

    public static class WorldGenSettings {
        @Config.Name("Sunflower Bush Biome Types")
        @Config.Comment("Applicable biome dictionary types for generating sunflower bushes")
        public String[] sunflowerBushBiomeTypes = {"FOREST", "MOUNTAIN", "PLAINS"};

        @Config.Name("Sunflower Bush Dimensions")
        @Config.Comment("Applicable dimension IDs for generating sunflower bushes")
        public int[] sunflowerBushDimensions = {0};

        @Config.Name("Sunflower Bush Generation Min Altitude")
        @Config.Comment("Minimum Y level for generating sunflower bushes")
        public int sunflowerBushGenAltitudeMin = 60;

        @Config.Name("Sunflower Bush Generation Max Altitude")
        @Config.Comment("Maximum Y level for generating sunflower bushes")
        public int sunflowerBushGenAltitudeMax = 120;

        @Config.Name("Sunflower Bush Generation Density")
        @Config.Comment("Density for generating sunflower bushes, higher = denser")
        public int sunflowerBushGenDensity = 64;

        @Config.Name("Sunflower Bush Generation Rarity")
        @Config.Comment("Rarity for generating sunflower bushes in regular biomes, higher = rarer")
        public int sunflowerBushGenRarity = 64;

        @Config.Name("Sunflower Bush Plains Generation Rarity")
        @Config.Comment("Rarity for generating sunflower bushes in plains-like biomes, higher = rarer")
        public int sunflowerBushGenRarityPlains = 32;

        @Config.Name("Sunflower Bush Generation Min Temperature")
        @Config.Comment("Minimum biome temperature for generating sunflower bushes")
        public double sunflowerBushGenTemperatureMin = 0.5D;

        @Config.Name("Sunflower Bush Generation Max Temperature")
        @Config.Comment("Maximum biome temperature for generating sunflower bushes")
        public double sunflowerBushGenTemperatureMax = 1.0D;

        @Config.RequiresMcRestart
        @Config.Name("World Generation Weight")
        @Config.Comment("Weight to assign to this generator, heavy weights tend to sink to the bottom of the list of world generators (i.e. they run later)")
        public int worldGenWeight = 300;
    }

    @Mod.EventBusSubscriber(modid = OffLawnLegacy.MOD_ID)
    public static class EventHandler {
        @SubscribeEvent
        public static void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
            if (event.getModID().equals(OffLawnLegacy.MOD_ID)) {
                ConfigManager.sync(OffLawnLegacy.MOD_ID, Config.Type.INSTANCE);
            }
        }
    }
}
