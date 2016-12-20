package fusion.cmds;

import org.bukkit.Bukkit;

import fusion.main.Fusion;
import fusion.utils.command.Command;
import fusion.utils.command.CommandArgs;
import klap.utils.Chat;
import mpermissions.utils.permissions.Rank;

public class FreeKitFriday {

	int taskid = 0;
	
	@Command(name = "freekitfriday", description = "FREE KITS HELL YEAH!.", usage = "/freekitfriday", aliases = {
			"fff" }, rank = Rank.ADMIN)
	public void candyEco(CommandArgs args) {

		if (Fusion.getInstance().freekitfriday) {

			Fusion.getInstance().freekitfriday = false;
			Chat.getInstance().messagePlayer(args.getSender(), "&cFree Kit Friday disabled.");

			Bukkit.getServer().getScheduler().cancelTask(taskid);
			taskid = 0;

		} else {

			Fusion.getInstance().freekitfriday = true;
			Chat.getInstance().messagePlayer(args.getSender(), "&aFree Kit Friday enabled.");

			int id = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(Fusion.getInstance(), new Runnable() {
				public void run() {

					Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "broadcast It's Friday! All kits are free for today!");

				}
			}, 0L, 20 * 60);

			taskid = id;

		}

	}

}
