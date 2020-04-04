package fusion.utils.crates.rewards;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import fusion.utils.mKitUser;
import fusion.utils.chat.Chat;
import fusion.utils.crates.Reward;

/**
	 * 
	 * Copyright GummyPvP. Created on May 31, 2016 by Jeremy Gooch.
	 * All Rights Reserved.
	 * 
	 */

public class CandyReward extends Reward {

	private int candy;
	
	public CandyReward(int candies) {
		
		this.candy = candies;
		
	}
	
	@Override
	public String getName() {
		return "Candies";
	}

	@Override
	public ItemStack getItem() {
		return null;
	}
	
	public int getCandyAmount() {
		return candy;
	}
	
	public void apply(Player player) {
		
		Chat.getInstance().messagePlayer(player, Chat.SECONDARY_BASE + "You won " + getCandyAmount() + " candies!");
		
		mKitUser.getInstance(player).addCandies(getCandyAmount());
		
	}

}
