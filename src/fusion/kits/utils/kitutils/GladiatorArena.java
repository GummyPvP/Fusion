package fusion.kits.utils.kitutils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;
import org.bukkit.util.Vector;

import fusion.kits.utils.Kit;
import fusion.kits.utils.KitManager;
import fusion.utils.Utils;
import fusion.utils.mKitUser;
import fusion.utils.chat.Chat;
import fusion.utils.editing.Bounds;
import fusion.utils.editing.regions.ProtectedRegion;
import fusion.utils.editing.regions.Region;
import fusion.utils.editing.regions.RegionManager;

/**
	 * 
	 * Created on Dec 6, 2016 by Jeremy Gooch.
	 * 
	 */

public class GladiatorArena {
	
	private Bounds bounds;
	
	private final int radius = 10;
	
	private Player attacker, attacked;
	
	private List<BlockState> oldBlockStates = new ArrayList<BlockState>();
	
	private Map<String, ItemStack[]> armorContents = new HashMap<String, ItemStack[]>();
	private Map<String, ItemStack[]> inventoryContents = new HashMap<String, ItemStack[]>();
	private Map<String, Kit> previousKits = new HashMap<String, Kit>();
	private Map<String, Location> locations = new HashMap<String, Location>();
	
	public GladiatorArena(mKitUser attacker, mKitUser attacked) {
		
		this.attacker = attacker.getPlayer();
		this.attacked = attacked.getPlayer();
		
	}
	
	public Player getAttacker() {
		return attacker;
	}
	
	public Player getAttacked() {
		return attacked;
	}
	
	public Bounds getBounds() {
		return bounds;
	}
	
	private void storePlayer(mKitUser user) {
		
		Player player = user.getPlayer();
		
		this.armorContents.put(player.getName(), player.getInventory().getArmorContents());
		this.inventoryContents.put(player.getName(), player.getInventory().getContents());
		this.previousKits.put(player.getName(), user.getKit());
		this.locations.put(player.getName(), player.getLocation());
		
	}
	
	private void restorePlayer(mKitUser user) {
		
		Player player = user.getPlayer();
		
		user.setKit(null);
		this.previousKits.get(player.getName()).apply(player); // sets the kit back to their previous one, reapplying their potion effects and abilities
		
		player.getInventory().setArmorContents(this.armorContents.get(player.getName())); // replace their contents anyways in case of one time use items or something
		player.getInventory().setContents(this.inventoryContents.get(player.getName()));
		player.teleport(this.locations.get(player.getName()));
		player.setHealth(20.0);
		player.updateInventory();
		
	}
	
	public void beginDuel() {
		
		storePlayer(mKitUser.getInstance(attacker));
		storePlayer(mKitUser.getInstance(attacked));
		
		applyFairKit(mKitUser.getInstance(getAttacker()));
		applyFairKit(mKitUser.getInstance(getAttacked()));
		
		createArena(attacker.getLocation());
		
		attacker.teleport(bounds.getMin().add(new Vector(2, 2, 2)).toLocation(bounds.getWorld()));
		attacked.teleport(new Vector(bounds.getMax().getBlockX() - 2, bounds.getMin().getBlockY() + 1, bounds.getMax().getBlockZ() - 2).toLocation(bounds.getWorld()));
		
		Utils.get().sendTitle(attacker, 5, 40, 5, "&4&lFight", "&9To the death!");
		Utils.get().sendTitle(attacked, 5, 40, 5, "&4&lFight", "&9To the death!");
		
		Chat.getInstance().messagePlayer(attacked, "You were teleported into the gladiator arena! Fight for your life!");
		
	}
	
	public void endDuel(mKitUser winner) {
		
		restorePlayer(winner);
		
		destroyArena();
		
	}
	
	private void applyFairKit(mKitUser player) {
		
		player.setKit(null);
		KitManager.getInstance().valueOf("PVP").apply(player.getPlayer());
		
	}
	
	private void createArena(Location location) {
		
		createBounds(location);
		
		for (BlockState currentBlockState : oldBlockStates) {
			
			currentBlockState.getBlock().setType(Material.AIR);
			
		}
		
		bounds.generateHollowCube(radius);
		
	}
	
	public void destroyArena() {
		
		for (BlockState currentBlockState : oldBlockStates) {
			
			currentBlockState.getBlock().setType(currentBlockState.getType());
			currentBlockState.getBlock().setBlockData(currentBlockState.getBlockData()); // unfortunately still deprecated, but seems to work fine
			
		}
		
	}
	
	private void createBounds(Location location) {
		
		int minX = location.getBlockX();
		int minY = location.getBlockY() + 5;
		int minZ = location.getBlockZ();
		
		int maxX = minX + radius;
		int maxY = minY + radius;
		int maxZ = minZ + radius;
		
		oldBlockStates.clear();
		
		for (int x = minX; x < maxX; x++) {
			for (int y = minY; y < maxY; y++) {
				for (int z = minZ; z < maxZ; z++) {
					
					Block currentBlock = location.getWorld().getBlockAt(x, y, z);
					
					if (intersectsProtectedRegions(currentBlock.getLocation()) || intersectsGladiatorArena(currentBlock.getLocation())) { // safety check TODO: check for other players that might be in the area
						
						createBounds(new Location(location.getWorld(), minX, minY + 5, minZ)); // attempt to create the arena 5 blocks higher
						
						return;
					}
					
					
					oldBlockStates.add(currentBlock.getState());
					
				}
			}
		}
		
		this.bounds = new Bounds(location.getWorld(), new Vector(minX, minY, minZ), new Vector(maxX, maxY, maxZ));
		
	}
	
	private boolean intersectsProtectedRegions(Location location) {
		
		boolean intersects = false;
		
		if (RegionManager.getInstance().getRegions(location.toVector()).isEmpty()) return false;
		
		for (Region region : RegionManager.getInstance().getRegions(location.toVector())) {
			
			if (!(region instanceof ProtectedRegion)) continue;
			
			if (((ProtectedRegion) region).isPVPEnabled()) continue;
			
			intersects = true; // assumes that any region that does not get passed over is a pvp disabled region
			
		}
		
		return intersects;
		
		
	}
	
	public boolean intersectsGladiatorArena(Location location) {
		
		boolean intersects = false;
		
		if (GladiatorManager.getInstance().getArenas() == null) return false;
		
		for (GladiatorArena currentArena : GladiatorManager.getInstance().getArenas()) {
			
			if (currentArena == this) continue;
			
			Bounds currentBounds = currentArena.getBounds();
			
			if (currentBounds.inBounds(location)) {
				intersects = true;
			}
			
		}
		
		return intersects;
		
	}

}
