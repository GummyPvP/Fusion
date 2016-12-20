package fusion.cmds;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import fusion.utils.Utils;
import fusion.utils.mKitUser;
import fusion.utils.chat.Chat;
import fusion.utils.command.Command;
import fusion.utils.command.CommandArgs;
import mpermissions.utils.permissions.Rank;

/**
 * 
 * Created on Dec 3, 2016 by Jacop Nonya.
 * 
 */

public class EcoSet {

	@Command(name = "ecoset", description = "Sets the balance of a player.", usage = "/ecoset <player> <amount>", rank = Rank.ADMIN)
	public void ecosetCommand(CommandArgs args) {

		if (args.length() != 2) {

			Chat.getInstance().messagePlayer(args.getSender(), "&cUsage: /ecoset <player> <amount>");

			return;
		}

		Player target = Bukkit.getPlayer(args.getArgs(0));

		if (target == null) {

			Chat.getInstance().messagePlayer(args.getSender(), args.getArgs(0) + " is not online!");

			return;
		}

		String amount = args.getArgs(1);

		if (!Utils.isNumber(amount)) {

			Chat.getInstance().messagePlayer(args.getSender(), "&c'" + amount + "' is not a valid amount!");

			return;
		}

		mKitUser targetUser = mKitUser.getInstance(target);

		final double oldAmount = targetUser.getCandies();

		targetUser.setCandies(Double.parseDouble(amount));
		targetUser.save();

		if ((args.getSender() instanceof Player) && target == ((Player) args.getSender())) {

			Chat.getInstance().messagePlayer(target, "&aYou set your candies to " + ChatColor.AQUA + amount);
			return;
		}

		Chat.getInstance().messagePlayer(target, "&aYour balance was set to &b" + amount + "&a!");

		Chat.getInstance().messagePlayer(args.getSender(),
				"&aYou set &e" + target.getName() + "'s &abalance from &3" + oldAmount + " &ato &b" + amount + "&a!");

	}
}
