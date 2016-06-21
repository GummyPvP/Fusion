package fusion.utils.crates;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Location;

import fusion.utils.crates.rewards.CandyReward;

/**
	 * 
	 * Copyright GummyPvP. Created on May 31, 2016 by Jeremy Gooch.
	 * All Rights Reserved.
	 * 
	 */

public class Crate {
	
	CrateTier tier;
	Location location;
	
	public Crate(Location location, CrateTier tier) {
		
		this.tier = tier;
		this.location = location;
		
	}
	
	public CrateTier getTier() {
		return tier;
	}
	
	public enum CrateTier {
		
		COMMON("Common", Arrays.asList(new CandyReward(250, 1))), UNCOMMON("Uncommon", Arrays.asList(new CandyReward(250, 2))), RARE("Rare", Arrays.asList(new CandyReward(250, 3))),
		LEGENDARY("Legendary", Arrays.asList(new CandyReward(250, 4)));
		
		String name;
		List<Reward> rewards;
		
		CrateTier(String name, List<Reward> rewards) {
			
			this.name = name;
			this.rewards = rewards;
			
		}
		
		public String getName() {
			return name;
		}
		
		public List<Reward> getRewards() {
			return rewards;
		}
		
	}

}
