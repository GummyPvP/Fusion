package fusion.utils;

import java.lang.reflect.Constructor;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import fusion.main.Fusion;
import fusion.utils.editing.regions.ProtectedRegion.HealingItem;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;

/**
	 * 
	 * Copyright GummyPvP. Created on May 24, 2016 by Jeremy Gooch.
	 * All Rights Reserved.
	 * 
	 */

public class Utils {
	
	private Utils() {}
	
	private static Utils instance = new Utils();
	
	public static Utils get() {
		return instance;
	}
	
	private static boolean useOldMethods = false;
	public static String nmsver;
	static Plugin plugin;
	public static boolean works = true;
	
	public void load() {
		
		plugin = Fusion.getInstance();
		
		nmsver = Bukkit.getServer().getClass().getPackage().getName();
		nmsver = nmsver.substring(nmsver.lastIndexOf(".") + 1);
		
		if (nmsver.equalsIgnoreCase("v1_8_R1") || nmsver.equalsIgnoreCase("v1_7_")) { // Not sure if 1_7 works for the protocol hack?
			useOldMethods = true;
		}
		
	}
	
	public static boolean isAlpha(String name) {
	    return name.matches("[a-zA-Z]+");
	}
	
	public static String removeLast(StringBuilder sb) {

		String playerList = sb.toString();

		Pattern pattern = Pattern.compile(", $");

		Matcher matcher = pattern.matcher(playerList);

		playerList = matcher.replaceAll("");

		return playerList;

	}
	
	public static void giveDefaultItems(Player player) {
		
		Bukkit.getScheduler().scheduleSyncDelayedTask(Fusion.getInstance(), new Runnable() {

			@Override
			public void run() {
				
				player.getInventory().clear();
				
				mKitUser user = mKitUser.getInstance(player);
				
				ItemStack kitSelector = new ItemBuilder(Material.NETHER_STAR).name("&aKit Selector").lore("Click me to show your owned kits!").build();
				//ItemStack cosmeticSelector = new ItemBuilder(Material.CHEST).name("&aCosmetic Selector").lore("Click me to change cosmetic settings!").build();
				ItemStack healthSelector = new ItemBuilder((user.getHealingItem() == HealingItem.POTION ? Material.POTION : user.getHealingItem().getItem().getType())).name("&aCurrent Healing Item: &e" + user.getHealingItem().toString()).build();
				ItemStack warpSelector = new ItemBuilder(Material.COMPASS).name("&aWarps").lore("Click me to show cool warps!").build();
				ItemStack shopSelector = new ItemBuilder(Material.ENDER_CHEST).name("&aKit Shop").lore("Click me to buy kits!").build();
				
				player.getInventory().setItem(0, kitSelector);
				//player.getInventory().setItem(4, cosmeticSelector);
				player.getInventory().setItem(4, healthSelector);
				player.getInventory().setItem(6, shopSelector);
				player.getInventory().setItem(8, warpSelector);
				
				if (user.hasPreviousKit()) {
					
					ItemStack previousKit = new ItemBuilder(Material.CLOCK).name("&5Previous Kit: &a" + user.getPreviousKit().getName()).lore("Click to use your previous kit!").build();
					player.getInventory().setItem(2, previousKit);
					
				}
				
			}
			
		}, 1L);
		
	}
	
	public static double round(double value, int places) {
	    if (places < 0) throw new IllegalArgumentException();

	    BigDecimal bd = new BigDecimal(value);
	    bd = bd.setScale(places, RoundingMode.HALF_UP);
	    return bd.doubleValue();
	}
	
	public static Class<?> getNMSClass(String name) {
		String version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
		try {
			return Class.forName("net.minecraft.server." + version + "." + name);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}

	@SuppressWarnings("rawtypes")
	public void sendTitle(Player player, Integer fadeIn, Integer stay, Integer fadeOut, String title, String subtitle) {

		try { // YES I DID :]
			Object e;
			Object chatTitle;
			Object chatSubtitle;
			Constructor subtitleConstructor;
			Object titlePacket;
			Object subtitlePacket;

			if (title != null) {
				title = ChatColor.translateAlternateColorCodes('&', title);
				title = title.replaceAll("%player%", player.getDisplayName());
				// Times packets
				e = getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0].getField("TIMES").get((Object) null);
				chatTitle = getNMSClass("IChatBaseComponent").getDeclaredClasses()[0]
						.getMethod("a", new Class[] { String.class })
						.invoke((Object) null, new Object[] { "{\"text\":\"" + title + "\"}" });
				subtitleConstructor = getNMSClass("PacketPlayOutTitle")
						.getConstructor(new Class[] { getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0],
								getNMSClass("IChatBaseComponent"), Integer.TYPE, Integer.TYPE, Integer.TYPE });
				titlePacket = subtitleConstructor.newInstance(new Object[] { e, chatTitle, fadeIn, stay, fadeOut });
				sendPacket(player, titlePacket);

				e = getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0].getField("TITLE").get((Object) null);
				chatTitle = getNMSClass("IChatBaseComponent").getDeclaredClasses()[0]
						.getMethod("a", new Class[] { String.class })
						.invoke((Object) null, new Object[] { "{\"text\":\"" + title + "\"}" });
				subtitleConstructor = getNMSClass("PacketPlayOutTitle").getConstructor(new Class[] {
						getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0], getNMSClass("IChatBaseComponent") });
				titlePacket = subtitleConstructor.newInstance(new Object[] { e, chatTitle });
				sendPacket(player, titlePacket);
			}

			if (subtitle != null) {
				subtitle = ChatColor.translateAlternateColorCodes('&', subtitle);
				subtitle = subtitle.replaceAll("%player%", player.getDisplayName());
				// Times packets
				e = getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0].getField("TIMES").get((Object) null);
				chatSubtitle = getNMSClass("IChatBaseComponent").getDeclaredClasses()[0]
						.getMethod("a", new Class[] { String.class })
						.invoke((Object) null, new Object[] { "{\"text\":\"" + title + "\"}" });
				subtitleConstructor = getNMSClass("PacketPlayOutTitle")
						.getConstructor(new Class[] { getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0],
								getNMSClass("IChatBaseComponent"), Integer.TYPE, Integer.TYPE, Integer.TYPE });
				subtitlePacket = subtitleConstructor
						.newInstance(new Object[] { e, chatSubtitle, fadeIn, stay, fadeOut });
				sendPacket(player, subtitlePacket);

				e = getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0].getField("SUBTITLE").get((Object) null);
				chatSubtitle = getNMSClass("IChatBaseComponent").getDeclaredClasses()[0]
						.getMethod("a", new Class[] { String.class })
						.invoke((Object) null, new Object[] { "{\"text\":\"" + subtitle + "\"}" });
				subtitleConstructor = getNMSClass("PacketPlayOutTitle")
						.getConstructor(new Class[] { getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0],
								getNMSClass("IChatBaseComponent"), Integer.TYPE, Integer.TYPE, Integer.TYPE });
				subtitlePacket = subtitleConstructor
						.newInstance(new Object[] { e, chatSubtitle, fadeIn, stay, fadeOut });
				sendPacket(player, subtitlePacket);
			}
		} catch (Exception var11) {
			var11.printStackTrace();
		}
	}
	public static void sendActionBar(Player player, String message) {
		// Call the event, if cancelled don't send Action Bar
		
		player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(ChatColor.translateAlternateColorCodes('&', message)));
		
		return;
//
//		try {
//			Class<?> c1 = Class.forName("org.bukkit.craftbukkit." + nmsver + ".entity.CraftPlayer");
//			Object p = c1.cast(player);
//			Object ppoc;
//			Class<?> c4 = Class.forName("net.minecraft.server." + nmsver + ".PacketPlayOutChat");
//			Class<?> c5 = Class.forName("net.minecraft.server." + nmsver + ".Packet");
//			if (useOldMethods) {
//				Class<?> c2 = Class.forName("net.minecraft.server." + nmsver + ".ChatSerializer");
//				Class<?> c3 = Class.forName("net.minecraft.server." + nmsver + ".IChatBaseComponent");
//				Method m3 = c2.getDeclaredMethod("a", String.class);
//				Object cbc = c3.cast(m3.invoke(c2, "{\"text\": \"" + message + "\"}"));
//				ppoc = c4.getConstructor(new Class<?>[]{c3, byte.class}).newInstance(cbc, (byte) 2);
//			} else {
//				Class<?> c2 = Class.forName("net.minecraft.server." + nmsver + ".ChatComponentText");
//				Class<?> c3 = Class.forName("net.minecraft.server." + nmsver + ".IChatBaseComponent");
//				Object o = c2.getConstructor(new Class<?>[]{String.class}).newInstance(message);
//				ppoc = c4.getConstructor(new Class<?>[]{c3, byte.class}).newInstance(o, (byte) 2);
//			}
//			Method m1 = c1.getDeclaredMethod("getHandle");
//			Object h = m1.invoke(p);
//			Field f1 = h.getClass().getDeclaredField("playerConnection");
//			Object pc = f1.get(h);
//			Method m5 = pc.getClass().getDeclaredMethod("sendPacket", c5);
//			m5.invoke(pc, ppoc);
//		} catch (Exception ex) {
//			ex.printStackTrace();
//			works = false;
//		}
	}
	
	public static void sendActionBar(final Player player, final String message, int duration) {
		sendActionBar(player, message);

		if (duration >= 0) {
			// Sends empty message at the end of the duration. Allows messages shorter than 3 seconds, ensures precision.
			new BukkitRunnable() {
				@Override
				public void run() {
					sendActionBar(player, "");
				}
			}.runTaskLater(plugin, duration + 1);
		}

		// Re-sends the messages every 3 seconds so it doesn't go away from the player's screen.
		while (duration > 60) {
			duration -= 60;
			int sched = duration % 60;
			new BukkitRunnable() {
				@Override
				public void run() {
					sendActionBar(player, message);
				}
			}.runTaskLater(plugin, (long) sched);
		}
	}

	public void sendPacket(Player player, Object packet) {
		try {
			Object handle = player.getClass().getMethod("getHandle").invoke(player);
			Object playerConnection = handle.getClass().getField("playerConnection").get(handle);
			playerConnection.getClass().getMethod("sendPacket", getNMSClass("Packet")).invoke(playerConnection, packet);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public static boolean isNumber(String arg) {
		
		try {
			Integer.parseInt(arg);
			return true;
		}
		
		catch (NumberFormatException e) {
			return false;
		}
		
	}
}
