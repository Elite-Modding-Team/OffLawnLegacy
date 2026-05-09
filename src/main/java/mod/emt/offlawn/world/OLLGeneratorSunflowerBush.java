package mod.emt.offlawn.world;

import mod.emt.offlawn.config.OLLConfig;
import mod.emt.offlawn.init.OLLBlocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.fml.common.IWorldGenerator;

import java.util.Random;

public class OLLGeneratorSunflowerBush implements IWorldGenerator {
    private static final Random rand = new Random();

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
        BlockPos biomePos = new BlockPos(chunkX << 4, 0, chunkZ << 4);
        Biome biome = world.getBiome(biomePos);

        if (matchesDimensions(world, OLLConfig.WORLD_GEN_SETTINGS.sunflowerBushDimensions) && matchesBiomeTypes(biome, OLLConfig.WORLD_GEN_SETTINGS.sunflowerBushBiomeTypes)) {
            boolean hasPlains = BiomeDictionary.hasType(biome, BiomeDictionary.Type.PLAINS);
            int spawnChance = hasPlains ? OLLConfig.WORLD_GEN_SETTINGS.sunflowerBushGenRarityPlains : OLLConfig.WORLD_GEN_SETTINGS.sunflowerBushGenRarity;
            if (rand.nextInt(spawnChance) != 0) {
                return;
            }

            int baseX = (chunkX << 4) + 8 + rand.nextInt(8);
            int baseZ = (chunkZ << 4) + 8 + rand.nextInt(8);
            int baseY = world.getHeight(baseX, baseZ);

            if (baseY < OLLConfig.WORLD_GEN_SETTINGS.sunflowerBushGenAltitudeMin || baseY > OLLConfig.WORLD_GEN_SETTINGS.sunflowerBushGenAltitudeMax) {
                return;
            }

            BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos();
            for (int i = 0; i < OLLConfig.WORLD_GEN_SETTINGS.sunflowerBushGenDensity; i++) {
                int x = baseX + rand.nextInt(8) - rand.nextInt(8);
                int z = baseZ + rand.nextInt(8) - rand.nextInt(8);
                int y = baseY + rand.nextInt(4) - rand.nextInt(4);

                pos.setPos(x, y, z);
                if (world.getBlockState(pos).getMaterial().isReplaceable() && OLLBlocks.SUNFLOWER_BUSH.canPlaceBlockAt(world, pos)) {
                    OLLBlocks.SUNFLOWER_BUSH.placeAt(world, pos, 2);
                }
            }
        }
    }

    public boolean matchesDimensions(World world, int[] dimIds) {
        for (int id : dimIds) {
            if (world.provider.getDimension() == id) {
                return true;
            }
        }
        return false;
    }

    public boolean matchesBiomeTypes(Biome biome, String[] typeNames) {
        for (String name : typeNames) {
            BiomeDictionary.Type type = BiomeDictionary.Type.getType(name);
            if (BiomeDictionary.hasType(biome, type)) {
                return true;
            }
        }
        return false;
    }
}
