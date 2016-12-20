package fusion.cmds;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import fusion.listeners.CombatLog;
import fusion.main.Fusion;
import fusion.utils.Utils;
import fusion.utils.mKitUser;
import fusion.utils.chat.Chat;
import fusion.utils.command.Command;
import fusion.utils.command.CommandArgs;
import fusion.utils.spawn.Spawn;
import klap.utils.mPlayer;
import mpermissions.utils.permissions.Rank;

/**
 * 
 * Copyright GummyPvP. Created on May 29, 2016 by Jeremy Gooch. All Rights
 * Reserved.
 * 
 */

public class ClearKit {

	@Command(name = "clearkit", aliases = "kitclear", description = "Clears your kit.", usage = "/clearkit", inGameOnly = true)
	public void clearKit(CommandArgs args) {

		if (args.length() != 1) {

			if (CombatLog.getInstance().isInCombat(args.getPlayer())) {

				Chat.getInstance().messagePlayer(args.getPlayer(),
						Chat.IMPORTANT_COLOR + "You are in combat! You may not use this command.");

				return;
			}

			mKitUser user = mKitUser.getInstance(args.getPlayer());

			if (!user.hasKit()) {

				Chat.getInstance().messagePlayer(args.getPlayer(),
						Chat.IMPORTANT_COLOR + "You do not have a kit selected!");

				return;
			}

			if (mPlayer.getInstance(args.getPlayer()).getGroup().getRank().hasRequiredRank(Rank.ADMIN)) {

				user.clearKit();

				Utils.giveDefaultItems(args.getPlayer());

				Spawn.getInstance().teleport(args.getPlayer());

				Chat.getInstance().messagePlayer(args.getPlayer(),
						" &4&4BYPASS: " + Chat.SECONDARY_BASE + "Your kit has been cleared!");

				return;
			}

			Chat.getInstance().messagePlayer(args.getPlayer(), Chat.SECONDARY_BASE + "Clearing kit in 5 seconds.....");

			Bukkit.getServer().getScheduler().runTaskLater(Fusion.getInstance(), new Runnable() {
				public void run() {

					if (CombatLog.getInstance().isInCombat(args.getPlayer())) {

						Chat.getInstance().messagePlayer(args.getPlayer(),
								Chat.IMPORTANT_COLOR + "You were combat tagged!");

						return;
					}

					user.clearKit();

					Utils.giveDefaultItems(args.getPlayer());

					Spawn.getInstance().teleport(args.getPlayer());

					Chat.getInstance().messagePlayer(args.getPlayer(),
							Chat.SECONDARY_BASE + "Your kit has been cleared!");

					return;

				}
			}, 20 * 5);

		}

		if (mPlayer.getInstance(args.getPlayer()).getGroup().getRank().hasRequiredRank(Rank.MODPLUS)) {

			Player target = Bukkit.getPlayer(args.getArgs(0));

			if (target == null) {

				Chat.getInstance().messagePlayer(args.getPlayer(), Chat.IMPORTANT_COLOR + "That player is not online!");

				return;
			}

			mKitUser user = mKitUser.getInstance(target);

			if (!user.hasKit()) {

				Chat.getInstance().messagePlayer(target, Chat.IMPORTANT_COLOR + "You do not have a kit selected!");

				return;
			}

			user.clearKit();

			Utils.giveDefaultItems(target);

			Spawn.getInstance().teleport(target);

			Chat.getInstance().messagePlayer(target,
					Chat.SECONDARY_BASE + "Your kit has been cleared by a staff member!");

			Chat.getInstance().messagePlayer(args.getPlayer(),
					Chat.STAFF_NOTIFICATION + "You cleared " + target.getName() + "'s kit");

			return;
		}

	}

}
