package fusion.utils;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import fusion.listeners.CombatLog;
import fusion.main.Fusion;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;

public class FusionPlaceholders extends PlaceholderExpansion {
	
	private FusionPlaceholders() { }
	
	private static FusionPlaceholders instance = new FusionPlaceholders();
	
	public static FusionPlaceholders get() {
		return instance;
	}
	
	@Override
	public String getAuthor() {
		return Fusion.getInstance().getDescription().getAuthors().toString();
	}

	@Override
	public String getIdentifier() {
		return "fusion";
	}

	@Override
	public String getVersion() {
		return Fusion.getInstance().getDescription().getVersion();
	}
	
    @Override
    public boolean persist(){
        return true;
    }
    
    @Override
    public boolean canRegister(){
        return true;
    }
    
    @Override
    public String onPlaceholderRequest(Player player, String identifier){

        if(player == null){
            return "null";
        }

        // %someplugin_placeholder1%
        if(identifier.equals("kills")){
			
			mKitUser user = mKitUser.getInstance(player);
			
			return String.valueOf(user.getKills());
        }

        // %someplugin_placeholder2%
        if(identifier.equals("deaths")){
        	
			mKitUser user = mKitUser.getInstance(player);

			return String.valueOf(user.getDeaths());
        }
        
        if(identifier.equals("kdr")){
        	
			mKitUser user = mKitUser.getInstance(player);
			
			return user.getKDRText();
        }
        
        if (identifier.equals("candies")) {
        	
			mKitUser user = mKitUser.getInstance(player);

			return String.valueOf(user.getCandies());
        }
        
        if (identifier.equals("currentkit")) {
        	
			mKitUser user = mKitUser.getInstance(player);

			if (user.getKit() == null) {
				return "None";
			}

			return String.valueOf(user.getKit().getName());
        }
        
        if (identifier.equals("team")) {
        	
			mKitUser user = mKitUser.getInstance(player);

			if (user.getTeam() == null) {
				return "None";
			}

			return String.valueOf(user.getTeam().getName());
        }
        
        if (identifier.equals("combattag")) {
        	
			if (!CombatLog.getInstance().isInCombat(player)) {
				return "Not in combat";
			}

			return ChatColor.RED + String.valueOf("Combat: " + CombatLog.getInstance().getRemainingTime(player));
        }
        
        if (identifier.equals("killstreak")) {
        	
    		mKitUser user = mKitUser.getInstance(player);
    		
    		return String.valueOf(user.getKillStreak());
        }
        
        if (identifier.equals("teamonline")) {
        	
			mKitUser user = mKitUser.getInstance(player);

			if (user.getTeam() == null) {
				return ChatColor.GREEN + "&fNone";
			}

			StringBuilder sb = new StringBuilder();

			int ontm = 0;

			for (UUID on : user.getTeam().getMembers().keySet()) {

				Player tplayer = Bukkit.getPlayer(on);

				if (tplayer != player) {

					if (tplayer != null) {

						sb.append(ChatColor.WHITE).append(tplayer.getName()).append(", ");

						ontm++;

					}
				}

			}
			
			if (ontm == 0) {
				sb.append(ChatColor.WHITE + "None");
			}
			
			return Utils.removeLast(sb);
        }
        
        // We return null if an invalid placeholder (f.e. %someplugin_placeholder3%) 
        // was provided
        return null;
    }

}
