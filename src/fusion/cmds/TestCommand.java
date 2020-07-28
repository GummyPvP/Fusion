package fusion.cmds;

import fusion.utils.command.Command;
import fusion.utils.command.CommandArgs;

public class TestCommand {
	
	@Command(name = "fusiontest", description = "Fusion test command", usage = "/fusiontest", permission = "fusion.test")
	public void testCommand(CommandArgs args) {
//		
//		if (Bukkit.getWorld("pocketWorld") == null) {
//			Bukkit.createWorld(WorldCreator.name("pocketWorld").type(WorldType.FLAT).generator(new ChunkGenerator() {
//                @Override
//                public ChunkData generateChunkData(World world, Random random, int cx, int cz, ChunkGenerator.BiomeGrid biome) {
//                    ChunkData chunkData = createChunkData(world);
//                    for (int x = 0; x <= 15; x++) {
//                        for (int z = 0; z <= 15; z++) {
//                            biome.setBiome(x, z, Biome.PLAINS);
//                        }
//                    }
//                    return chunkData;
//                }
//            }));
//		}
//		
//		Location newLocation = new Location(Bukkit.getWorld("pocketWorld"), new Random().nextInt(30000), new Random().nextInt(220) + 10, new Random().nextInt(30000));
//		
//		Fusion.getInstance().getPortalSchematics().getRandomSchematic().pasteSchematic(newLocation);
//		
//		Bukkit.getScheduler().runTaskLater(Fusion.getInstance(), new Runnable() {
//
//			@Override
//			public void run() {
//				args.getPlayer().teleport(newLocation);
//			}
//			
//		}, 20);
	}

}
