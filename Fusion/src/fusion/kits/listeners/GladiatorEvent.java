package fusion.kits.listeners;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

import fusion.kits.utils.KitManager;
import fusion.main.Fusion;
import fusion.utils.ItemBuilder;
import fusion.utils.Utils;
import fusion.utils.mKitUser;
import fusion.utils.chat.Chat;
import fusion.utils.protection.RegionManager;
import klap.utils.mPlayer;

/**
 * 
 * Created on Dec 4, 2016 by Jeremy Gooch.
 * 
 */

public class GladiatorEvent implements Listener {

	private Map<String, String> duelingMap = new HashMap<String, String>();
	private Map<String, ItemStack[]> armorContents = new HashMap<String, ItemStack[]>();
	private Map<String, ItemStack[]> inventoryContents = new HashMap<String, ItemStack[]>();
	private Map<String, Collection<PotionEffect>> potionEffects = new HashMap<String, Collection<PotionEffect>>();
	private Map<String, Location> locations = new HashMap<String, Location>();
	// private Map<String, GladiatorArena> arenas = new HashMap<String,
	// GladiatorArena>();

	@EventHandler
	public void onPlayerInteractWithEntity(PlayerInteractEntityEvent e) {

		if (!KitManager.getInstance().hasRequiredKit(e.getPlayer(), KitManager.getInstance().valueOf("Gladiator")))
			return;

		if (!(e.getRightClicked() instanceof Player))
			return;

		if (e.getPlayer().getItemInHand().getType() != Material.IRON_FENCE)
			return;

		if (RegionManager.getInstance().isInProtectedRegion(e.getPlayer()))
			return;

		if (isInDuel(e.getPlayer()))
			return;

		e.setCancelled(true);

		Player player = e.getPlayer();
		Player attacked = (Player) e.getRightClicked();

		if (attacked.getGameMode() == GameMode.CREATIVE)
			return;

		setInDuel(player, attacked);
		setInDuel(attacked, player);

		teleportToArena(player, attacked);

	}

	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent e) {

		Player player = e.getEntity();

		if (isInDuel(player)) {

			endDuel(getAttacker(player), player);

		}

	}

	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent e) {

		Player player = e.getPlayer();

		if (isInDuel(player)) {

			endDuel(getAttacker(player), player);

		}

	}

	private void endDuel(Player winner, Player loser) { // Only use this after
														// you know these
														// players are in a
														// duel!

		/**
		 * 
		 * Reset player inventory and armor contents, teleport the player back
		 * 
		 */

		winner.getInventory().clear();

		winner.getInventory().setArmorContents(null);

		for (PotionEffect effect : winner.getActivePotionEffects()) {

			winner.removePotionEffect(effect.getType());

		}

		winner.setHealth(20.0);

		winner.getInventory().setContents(inventoryContents.get(winner.getName()));
		winner.getInventory().setArmorContents(armorContents.get(winner.getName()));

		winner.updateInventory();

		for (PotionEffect effect : potionEffects.get(winner.getName())) {

			winner.addPotionEffect(effect);

		}

		winner.teleport(locations.get(winner.getName()).add(0, 1, 0));

		/**
		 * 
		 * Correct visibility of players
		 * 
		 */

		for (Player online : Bukkit.getOnlinePlayers()) {

			mPlayer mplayer = mPlayer.getInstance(online);

			if (!mplayer.isVanished()) {

				winner.showPlayer(online);
				loser.showPlayer(online);

			}

		}

		winner.showPlayer(loser);
		loser.showPlayer(winner);

		mKitUser.getInstance(winner).setGlad(false);
		mKitUser.getInstance(loser).setGlad(false);
		
		/**
		 * 
		 * Cleanup maps
		 * 
		 */

		armorContents.remove(winner.getName());
		armorContents.remove(loser.getName());

		inventoryContents.remove(winner.getName());
		inventoryContents.remove(loser.getName());

		potionEffects.remove(winner.getName());
		potionEffects.remove(loser.getName());

		locations.remove(winner.getName());
		locations.remove(loser.getName());

		duelingMap.remove(winner.getName());
		duelingMap.remove(loser.getName());
	}

	private boolean isInDuel(Player player) {
		return duelingMap.containsKey(player.getName());
	}

	private void setInDuel(Player attacker, Player attacked) {
		duelingMap.put(attacker.getName(), attacked.getName());

		armorContents.put(attacker.getName(), attacker.getInventory().getArmorContents());

		inventoryContents.put(attacker.getName(), attacker.getInventory().getContents());

		potionEffects.put(attacker.getName(), attacker.getActivePotionEffects());

		locations.put(attacker.getName(), attacker.getLocation());

		attacker.getInventory().clear();

		attacker.getInventory().setArmorContents(null);

		for (PotionEffect effect : attacker.getActivePotionEffects()) {

			attacker.removePotionEffect(effect.getType());

		}

		attacker.setHealth(20.0);

		giveFairKit(attacker);

		for (Player online : Bukkit.getOnlinePlayers()) {

			attacker.hidePlayer(online);

		}

		attacker.showPlayer(attacked);
		attacked.showPlayer(attacker);

		mKitUser.getInstance(attacker).setGlad(true);
		mKitUser.getInstance(attacked).setGlad(true);
	}

	public void teleportToArena(Player attacker, Player attacked) {

		if (Fusion.getInstance().getKitInfoFile().getSection("gladiator") == null) {

			Chat.getInstance().messagePlayer(attacker,
					"&cGladiator is not setup correctly, please contact a staff member immediately!");

			return;
		}

		Location attackerLocation, attackedLocation;

		World world;
		double attackerX, attackedX, attackerY, attackedY, attackerZ, attackedZ;
		float attackerYaw, attackedYaw, attackerPitch, attackedPitch;

		world = Bukkit.getWorld(Fusion.getInstance().getKitInfoFile().getString("gladiator.world"));

		attackerX = Fusion.getInstance().getKitInfoFile().getDouble("gladiator.attacker.x");
		attackerY = Fusion.getInstance().getKitInfoFile().getDouble("gladiator.attacker.y");
		attackerZ = Fusion.getInstance().getKitInfoFile().getDouble("gladiator.attacker.z");
		attackerYaw = Fusion.getInstance().getKitInfoFile().getInt("gladiator.attacker.yaw");
		attackerPitch = Fusion.getInstance().getKitInfoFile().getInt("gladiator.attacker.pitch");
		attackerLocation = new Location(world, attackerX, attackerY, attackerZ, attackerYaw, attackerPitch);

		attackedX = Fusion.getInstance().getKitInfoFile().getDouble("gladiator.attacked.x");
		attackedY = Fusion.getInstance().getKitInfoFile().getDouble("gladiator.attacked.y");
		attackedZ = Fusion.getInstance().getKitInfoFile().getDouble("gladiator.attacked.z");
		attackedYaw = Fusion.getInstance().getKitInfoFile().getInt("gladiator.attacked.yaw");
		attackedPitch = Fusion.getInstance().getKitInfoFile().getInt("gladiator.attacked.pitch");
		attackedLocation = new Location(world, attackedX, attackedY, attackedZ, attackedYaw, attackedPitch);

		// GladiatorArena arena = new GladiatorArena(new
		// Bounds(attacker.getWorld(), new Vector(), max));

		// arenas.put(attacker.getName(), arena);

		Utils.get().sendTitle(attacker, 5, 40, 5, "&4&lFight", "&9To the death!");

		attacker.teleport(attackerLocation);

		attacked.teleport(attackedLocation);

		Utils.get().sendTitle(attacked, 5, 40, 5, "&4&lFight", "&9To the death!");

		Chat.getInstance().messagePlayer(attacked,
				"You were teleported into the gladiator arena! Fight for your life!");

	}

	private Player getAttacker(Player player) {
		if (!isInDuel(player))
			return null;

		return Bukkit.getPlayer(duelingMap.get(player.getName()));
	}

	private void giveFairKit(Player player) {

		ItemStack sword = new ItemBuilder(Material.DIAMOND_SWORD).name("&aPVP Sword").lore("u wot m8").build();
		ItemStack helmet = new ItemBuilder(Material.IRON_HELMET).build();
		ItemStack chestplate = new ItemBuilder(Material.IRON_CHESTPLATE).build();
		ItemStack leggings = new ItemBuilder(Material.IRON_LEGGINGS).build();
		ItemStack boots = new ItemBuilder(Material.IRON_BOOTS).build();

		player.getInventory().addItem(sword);
		player.getInventory().setHelmet(helmet);
		player.getInventory().setChestplate(chestplate);
		player.getInventory().setLeggings(leggings);
		player.getInventory().setBoots(boots);

		// for (int i = 0; i < player.getInventory().getSize(); i++) {
		//
		// player.getInventory().addItem(new
		// ItemBuilder(Material.MUSHROOM_SOUP).name("&bSoup")
		// .lore("Drinking this soup heals you 3.5 hearts").build());
		//
		// }
		for (int i = 0; i < player.getInventory().getSize(); i++) {

			player.getInventory().addItem(mKitUser.getInstance(player).getHealingItem().getItem());

		}

	}

}
