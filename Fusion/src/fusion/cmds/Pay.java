package fusion.cmds;

import org.bukkit.Bukkit;
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

public class Pay {

	@Command(name = "pay", description = "pays another player", usage = "/pay <player> <amount>", rank = Rank.MEMBER)
	public void ecosetCommand(CommandArgs args) {

		if (args.length() != 2) {

			Chat.getInstance().messagePlayer(args.getSender(), "&cUsage: /pay <player> <amount>");

			return;
		}

		Player target = Bukkit.getPlayer(args.getArgs(0));

		if (target == null) {

			Chat.getInstance().messagePlayer(args.getSender(), args.getArgs(0) + " is not online!");

			return;
		}

		String amount = args.getArgs(1);

		if (amount.contains("-")) {
			
			Chat.getInstance().messagePlayer(args.getPlayer(), "&cInvalid Usage");
			
			return;
		}
		
		if (!Utils.isNumber(amount)) {

			Chat.getInstance().messagePlayer(args.getSender(), "&c'" + amount + "' is not a valid amount!");

			return;
		}
		

		Player pn = (Player) args.getPlayer();
		Player p = (Player) args.getSender();

		mKitUser targetUser = mKitUser.getInstance(target);
		mKitUser senderUser = mKitUser.getInstance(p);

		final double oldAmount = targetUser.getCandies();

		double check = senderUser.getCandies() - Double.parseDouble(amount);
		if (check <= 0) {
			
			Chat.getInstance().messagePlayer(senderUser.getPlayer(), "&cYou do not have the required amount of funds to pay &e" + targetUser.getPlayer().getName() + "&c!");
			
			return;
		}
		
		targetUser.setCandies((Double.parseDouble(amount) + oldAmount));
		senderUser.removeCandies(Double.parseDouble(amount));
		targetUser.save();

		if ((args.getSender() instanceof Player) && target == ((Player) args.getSender())) {

			Chat.getInstance().messagePlayer(target, "&4You can't pay yourself!");
			return;
		}

		Chat.getInstance().messagePlayer(target, "&9You were paid " + amount + " from " + pn.getName() + "!");

		Chat.getInstance().messagePlayer(args.getSender(), "&9You paid " + amount + " to " + target.getName() + "!");

	}
}
