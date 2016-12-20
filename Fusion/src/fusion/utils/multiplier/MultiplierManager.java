package fusion.utils.multiplier;

import org.bukkit.entity.Player;

import fusion.utils.mKitUser;
import klap.utils.mPlayer;

public class MultiplierManager {
	
	static MultiplierManager instance = new MultiplierManager();
	
	public static MultiplierManager getInstance() {
		return instance;
	}
	
	public static double slimeMultiplier = 1.2;
	public static double hariboMultiplier = 1.4;
	public static double gummyMultiplier = 1.6;
	
	public Multiplier getMultiplier(Player player) {
		
		return mKitUser.getInstance(player).getMultiplier();
	}
	
	public void setMultiplier(Player player, Multiplier type) {
		
		mKitUser.getInstance(player).setMultiplier(type);
		
    }
	
	public void updateMultiplier(Player player) {
		
		mPlayer mplayer = mPlayer.getInstance(player);
		mKitUser user = mKitUser.getInstance(player);
		
		switch (mplayer.getGroup().getRank()) {
		case MEMBER:
			
			user.setMultiplier(Multiplier.NONE);
			
			break;
		case YOUTUBER:
		
			user.setMultiplier(Multiplier.NONE);
			
			break;
		case SLIME:
			
			user.setMultiplier(Multiplier.SLIME);
			
			break;
		case HARIBO:
			
			user.setMultiplier(Multiplier.HARIBO);
			
			break;
		case GUMMY:
			
			user.setMultiplier(Multiplier.GUMMY);
			
			break;
		case MODERATOR:
			
			user.setMultiplier(Multiplier.HARIBO);
			
			break;
		case MODPLUS:
			
			user.setMultiplier(Multiplier.HARIBO);
			
			break;
		case ADMIN:
			
			user.setMultiplier(Multiplier.GUMMY);
			
			break;
		case ADMINPLUS:
			
			user.setMultiplier(Multiplier.GUMMY);
			
			break;
		default:
			break;
		}
		
		
	}
	public double executeMultiplier(Player player, double current) {
		
		switch (getMultiplier(player)) {
		case NONE:
			return current;
		case SLIME:
			
			double newamountslime = current * slimeMultiplier;
			
			return newamountslime;
		case HARIBO:
		
			double newamountharibo = current * hariboMultiplier;
			
			return newamountharibo;
		case GUMMY:
			
			double newamountgummy = current * gummyMultiplier;
			
			return newamountgummy;
		default:
			return current;
		}
	}
}
