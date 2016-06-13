package fusion.utils;

import java.io.File;
import java.io.RandomAccessFile;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_7_R4.CraftChunk;
import org.bukkit.craftbukkit.v1_7_R4.CraftServer;
import org.bukkit.craftbukkit.v1_7_R4.CraftWorld;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.world.WorldUnloadEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

import net.minecraft.server.v1_7_R4.Chunk;
import net.minecraft.server.v1_7_R4.ChunkCoordIntPair;
import net.minecraft.server.v1_7_R4.ChunkSection;
import net.minecraft.server.v1_7_R4.MinecraftServer;
import net.minecraft.server.v1_7_R4.PacketPlayOutMultiBlockChange;
import net.minecraft.server.v1_7_R4.RegionFile;
import net.minecraft.server.v1_7_R4.RegionFileCache;

public class MapUtils {
    @SuppressWarnings("deprecation")
	public static void ReplaceOreInChunk(org.bukkit.Chunk chunk, Material replacee, Material replacer) {
        net.minecraft.server.v1_7_R4.Chunk c = ((CraftChunk) chunk).getHandle();
        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                for (int y = 0; y < 18; y++) {
                    int bX = c.locX << 4 | x & 0xF;
                    int bY = y & 0xFF;
                    int bZ = c.locZ << 4 | z & 0xF;
                    if (c.getType(bX & 0xF, bY, bZ & 0xF).k() == replacee.getId()) {
                        c.b(bX & 0xF, bY, bZ & 0xF, replacer.getId());
                    }
                }
            }
        }
        c.initLighting();
    }

    public static void QuickChangeBlockAt(Location location, Material setTo) {
        QuickChangeBlockAt(location.getWorld(), location.getBlockX(), location.getBlockY(), location.getBlockZ(), setTo);
    }

    public static void QuickChangeBlockAt(Location location, int id, byte data) {
        QuickChangeBlockAt(location.getWorld(), location.getBlockX(), location.getBlockY(), location.getBlockZ(), id, data);
    }

    public static void QuickChangeBlockAt(org.bukkit.World world, int x, int y, int z, Material setTo) {
        QuickChangeBlockAt(world, x, y, z, setTo, 0);
    }

    @SuppressWarnings("deprecation")
	public static void QuickChangeBlockAt(org.bukkit.World world, int x, int y, int z, Material setTo, int data) {
        QuickChangeBlockAt(world, x, y, z, setTo.getId(), data);
    }

    public static void QuickChangeBlockAt(org.bukkit.World world, int x, int y, int z, int id, int data) {
        org.bukkit.Chunk chunk = world.getChunkAt(x >> 4, z >> 4);
        net.minecraft.server.v1_7_R4.Chunk c = ((CraftChunk) chunk).getHandle();

        c.a(x & 0xF, y, z & 0xF, net.minecraft.server.v1_7_R4.Block.getById(id), data);
        ((CraftWorld) world).getHandle().notify(x, y, z);
    }

    @SuppressWarnings("unused")
	public static int GetHighestBlockInCircleAt(org.bukkit.World world, int bx, int bz, int radius) {
        int count = 0;
        int totalHeight = 0;

        double invRadiusX = 1 / radius;
        double invRadiusZ = 1 / radius;

        int ceilRadiusX = (int) Math.ceil(radius);
        int ceilRadiusZ = (int) Math.ceil(radius);

        double nextXn = 0.0D;
        for (int x = 0; x <= ceilRadiusX; x++) {
            double xn = nextXn;
            nextXn = (x + 1) * invRadiusX;
            double nextZn = 0.0D;
            for (int z = 0; z <= ceilRadiusZ; z++) {
                double zn = nextZn;
                nextZn = (z + 1) * invRadiusZ;

                double distanceSq = xn * xn + zn * zn;
                if (distanceSq > 1.0D) {
                    if (z != 0) {
                        break;
                    }
                    break;
                }
                totalHeight += world.getHighestBlockAt(bx + x, bz + z).getY();
                count++;
            }
        }
        label155:
        return totalHeight / count;
    }

    @SuppressWarnings("unused")
	public static void ResendChunksForNearbyPlayers(Collection<Chunk> chunks) {
        int j;
        int i;
        for (Iterator<Chunk> localIterator = chunks.iterator(); localIterator.hasNext();) {
            net.minecraft.server.v1_7_R4.Chunk c = (net.minecraft.server.v1_7_R4.Chunk) localIterator.next();
            Player[] arrayOfPlayer;
            j = (arrayOfPlayer = (Player[]) Bukkit.getOnlinePlayers().toArray()).length;
            i = 0;
            Player player = arrayOfPlayer[i];

            Vector pV = player.getLocation().toVector();
            int xDist = Math.abs((pV.getBlockX() >> 4) - c.locX);
            int zDist = Math.abs((pV.getBlockZ() >> 4) - c.locZ);
            if (xDist + zDist <= 12) {
                SendChunkForPlayer(c, player);
            }
            i++;
        }
    }

    public static net.minecraft.server.v1_7_R4.Chunk ChunkBlockChange(Location location, int id, byte data) {
        return ChunkBlockChange(location.getWorld(), location.getBlockX(), location.getBlockY(), location.getBlockZ(), id, data);
    }

    public static net.minecraft.server.v1_7_R4.Chunk ChunkBlockChange(org.bukkit.World world, int x, int y, int z, int id, byte data) {
        net.minecraft.server.v1_7_R4.Chunk c = ((CraftChunk) world.getChunkAt(x >> 4, z >> 4)).getHandle();
        net.minecraft.server.v1_7_R4.Block block = net.minecraft.server.v1_7_R4.Block.getById(id);

        x &= 0xF;
        z &= 0xF;

        int l1 = c.getType(x, y, z).k();
        int i2 = c.getData(x, y, z);
        ChunkSection chunksection = c.getSections()[(y >> 4)] == null ? new ChunkSection(y >> 4 << 4, true) : c.getSections()[(y >> 4)];

        int j2 = c.locX * 16 + x;
        int k2 = c.locZ * 16 + z;
        if ((l1 != 0) && (!c.world.isStatic)) {
            net.minecraft.server.v1_7_R4.Block.getById(l1).f(c.world, j2, y, k2, i2);
        }
        chunksection.setTypeId(x, y & 0xF, z, block);
        if (chunksection.getTypeId(x, y & 0xF, z) != block) {
            return null;
        }
        chunksection.setData(x, y & 0xF, z, data);

        return c;
    }

    public static void SendChunkForPlayer(net.minecraft.server.v1_7_R4.Chunk chunk, Player player) {
        SendChunkForPlayer(chunk.locX, chunk.locZ, player);
    }

    @SuppressWarnings("unchecked")
	public static void SendChunkForPlayer(int x, int z, Player player) {
        ((CraftPlayer) player).getHandle().chunkCoordIntPairQueue.add(new ChunkCoordIntPair(x, z));
    }

    public static void SendMultiBlockForPlayer(int x, int z, short[] dirtyBlocks, int dirtyCount, org.bukkit.World world, Player player) {
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(new PacketPlayOutMultiBlockChange(dirtyCount, dirtyBlocks, ((CraftWorld) world).getHandle().getChunkAt(x, z)));
    }

    @SuppressWarnings("unchecked")
	public static void UnloadWorld(JavaPlugin plugin, org.bukkit.World world) {
        world.setAutoSave(false);
        for (Entity entity : world.getEntities()) {
            entity.remove();
        }
        CraftServer server = (CraftServer) plugin.getServer();
        CraftWorld craftWorld = (CraftWorld) world;

        Bukkit.getPluginManager().callEvent(new WorldUnloadEvent(((CraftWorld) world).getHandle().getWorld()));

        Iterator<net.minecraft.server.v1_7_R4.Chunk> chunkIterator = ((CraftWorld) world).getHandle().chunkProviderServer.chunks.values().iterator();
        while (chunkIterator.hasNext()) {
            net.minecraft.server.v1_7_R4.Chunk chunk = (net.minecraft.server.v1_7_R4.Chunk) chunkIterator.next();
            chunk.removeEntities();
        }
        ((CraftWorld) world).getHandle().chunkProviderServer.chunks.clear();
        ((CraftWorld) world).getHandle().chunkProviderServer.unloadQueue.clear();
        try {
            Field f = server.getClass().getDeclaredField("worlds");
            f.setAccessible(true);

            Map<String, org.bukkit.World> worlds = (Map<String, World>) f.get(server);
            worlds.remove(world.getName().toLowerCase());
            f.setAccessible(false);
        } catch (IllegalAccessException ex) {
            System.out.println("Error removing world from bukkit master list: " + ex.getMessage());
        } catch (NoSuchFieldException ex) {
            System.out.println("Error removing world from bukkit master list: " + ex.getMessage());
        }
        MinecraftServer ms = null;
        try {
            Field f = server.getClass().getDeclaredField("console");
            f.setAccessible(true);
            ms = (MinecraftServer) f.get(server);
            f.setAccessible(false);
        } catch (IllegalAccessException ex) {
            System.out.println("Error getting minecraftserver variable: " + ex.getMessage());
        } catch (NoSuchFieldException ex) {
            System.out.println("Error getting minecraftserver variable: " + ex.getMessage());
        }
        ms.worlds.remove(ms.worlds.indexOf(craftWorld.getHandle()));
    }

    @SuppressWarnings("rawtypes")
	public static boolean ClearWorldReferences(String worldName) {
        HashMap regionfiles = null;
        Field rafField = null;
        try {
            Field a = RegionFileCache.class.getDeclaredField("a");
            a.setAccessible(true);
            regionfiles = (HashMap) a.get(null);
            rafField = RegionFile.class.getDeclaredField("c");
            rafField.setAccessible(true);
        } catch (Throwable t) {
            System.out.println("Error binding to region file cache.");
            t.printStackTrace();
        }
        if (regionfiles == null) {
            return false;
        }
        if (rafField == null) {
            return false;
        }
        ArrayList<Object> removedKeys = new ArrayList<Object>();
        try {
            for (Object o : regionfiles.entrySet()) {
                Map.Entry e = (Map.Entry) o;
                File f = (File) e.getKey();
                if (f.toString().startsWith("." + File.separator + worldName)) {
                    RegionFile file = (RegionFile) e.getValue();
                    try {
                        RandomAccessFile raf = (RandomAccessFile) rafField.get(file);
                        raf.close();
                        removedKeys.add(f);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        } catch (Exception ex) {
            System.out.println("Exception while removing world reference for '" + worldName + "'!");
            ex.printStackTrace();
        }
        for (Object key : removedKeys) {
            regionfiles.remove(key);
        }
        return true;
    }
}