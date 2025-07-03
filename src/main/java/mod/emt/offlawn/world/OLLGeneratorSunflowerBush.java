package mod.emt.offlawn.world;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.fml.common.IWorldGenerator;

import java.util.Random;

import mod.emt.offlawn.init.OLLBlocks;

// TODO: Add config options
public class OLLGeneratorSunflowerBush implements IWorldGenerator {
    private static final Random rand = new Random();

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
        BlockPos biomePos = new BlockPos(chunkX << 4, 0, chunkZ << 4);
        Biome biome = world.getBiome(biomePos);

        boolean hasForest = BiomeDictionary.hasType(biome, BiomeDictionary.Type.FOREST);
        boolean hasMountain = BiomeDictionary.hasType(biome, BiomeDictionary.Type.MOUNTAIN);
        boolean hasPlains = BiomeDictionary.hasType(biome, BiomeDictionary.Type.PLAINS);

        if (hasForest || hasMountain || hasPlains) {
            int spawnChance = hasPlains ? 32 : 64;
            if (rand.nextInt(spawnChance) != 0) {
                return;
            }

            int baseX = (chunkX << 4) + 8 + rand.nextInt(8);
            int baseZ = (chunkZ << 4) + 8 + rand.nextInt(8);
            int baseY = world.getHeight(baseX, baseZ);

            BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos();
            for (int i = 0; i < 64; i++) {
                int x = baseX + rand.nextInt(8) - rand.nextInt(8);
                int z = baseZ + rand.nextInt(8) - rand.nextInt(8);
                int y = baseY + rand.nextInt(4) - rand.nextInt(4);

                pos.setPos(x, y, z);
                if (y > 0 && world.isAirBlock(pos) && OLLBlocks.SUNFLOWER_BUSH.canPlaceBlockAt(world, pos)) {
                    OLLBlocks.SUNFLOWER_BUSH.placeAt(world, pos, 2);
                }
            }
        }
    }
}
