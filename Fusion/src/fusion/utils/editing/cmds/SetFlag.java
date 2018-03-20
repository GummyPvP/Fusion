package fusion.utils.editing.cmds;

import fusion.kits.utils.KitManager;
import fusion.utils.chat.Chat;
import fusion.utils.command.Command;
import fusion.utils.command.CommandArgs;
import fusion.utils.editing.regions.ProtectedRegion;
import fusion.utils.editing.regions.Region;
import fusion.utils.editing.regions.RegionManager;
import fusion.utils.editing.regions.ProtectedRegion.HealingItem;

/**
 * 
 * Copyright GummyPvP. Created on May 28, 2016 by Jeremy Gooch. All Rights
 * Reserved.
 * 
 */

public class SetFlag {

	@Command(name = "setflag", description = "Sets flags to a region.", usage = "/setflag (region) (flag) (modifier)", permission = "region.setflag")
	public void addFlag(CommandArgs args) {

		if (args.length() < 3) {

			Chat.getInstance().messagePlayer(args.getSender(),
					Chat.IMPORTANT_COLOR + "Usage: /setflag (region) (flag) (modifier)");

			return;
		}

		String regionName = args.getArgs(0);
		String flag = args.getArgs(1);
		String modifier = args.getArgs(2);

		if (!RegionManager.getInstance().regionExists(regionName)) {

			Chat.getInstance().messagePlayer(args.getSender(), Chat.IMPORTANT_COLOR + "That region doesn't exist!");

			return;
		}

		Region region = RegionManager.getInstance().getRegion(regionName);

		if (!(region instanceof ProtectedRegion)) {

			Chat.getInstance().messagePlayer(args.getSender(), Chat.IMPORTANT_COLOR + "That region is not correct!");

			return;
		}

		ProtectedRegion protectedRegion = (ProtectedRegion) region;

		switch (flag.toLowerCase()) {
		case "pvp":
		case "pvpenable":
		case "enablepvp":

			protectedRegion.togglePVP(checkOption(modifier));
			Chat.getInstance().messagePlayer(args.getSender(), Chat.STAFF_NOTIFICATION + "Flag: PVP - "
					+ (protectedRegion.isPVPEnabled() ? "enabled" : "disabled"));

			break;

		case "refills":

			protectedRegion.toggleRefills(checkOption(modifier));
			Chat.getInstance().messagePlayer(args.getSender(), Chat.STAFF_NOTIFICATION + "Flag: Refills - "
					+ (protectedRegion.areRefillsAllowed() ? "enabled" : "disabled"));

			break;

		case "health":
		case "healthitem":

			HealingItem item = null;

			try {

				item = HealingItem.valueOf(modifier.toUpperCase());

			} catch (IllegalArgumentException e) {

				Chat.getInstance().messagePlayer(args.getSender(),
						Chat.IMPORTANT_COLOR + "That's not an option! Use ANY, SOUP, POTION");

				return;

			}

			protectedRegion.setHealthItem(item == null ? HealingItem.SOUP : item);

			Chat.getInstance().messagePlayer(args.getSender(), Chat.STAFF_NOTIFICATION
					+ "Successfully changed healing item to " + (item == null ? HealingItem.SOUP : item).toString());

			break;

		case "blockkit":

			if (KitManager.getInstance().valueOf(modifier) == null) {

				Chat.getInstance().messagePlayer(args.getSender(), Chat.IMPORTANT_COLOR + "That kit does not exist!");

				return;
			}

			if (protectedRegion.getBlockedKits().contains(KitManager.getInstance().valueOf(modifier))) {

				protectedRegion.removeBlockedKit(KitManager.getInstance().valueOf(modifier));

				Chat.getInstance().messagePlayer(args.getSender(),
						Chat.STAFF_NOTIFICATION + "Successfully removed blocked kit: " + modifier);

				return;
			}

			protectedRegion.addBlockedKit(KitManager.getInstance().valueOf(modifier));

			Chat.getInstance().messagePlayer(args.getSender(),
					Chat.STAFF_NOTIFICATION + "Successfully added blocked kit: " + modifier);

			break;

		default:

			Chat.getInstance().messagePlayer(args.getSender(),
					Chat.IMPORTANT_COLOR + "Flags: PVP, Refills, HealthItem, BlockKit");

			break;
		}

	}

	private boolean checkOption(String input) {

		switch (input.toLowerCase()) {
		case "yes":
		case "enable":
		case "on":
		case "true":
		case "allow":

			return true;

		case "no":
		case "disable":
		case "off":
		case "false":
		case "deny":

			return false;

		default:
			return false;

		}

	}

}
