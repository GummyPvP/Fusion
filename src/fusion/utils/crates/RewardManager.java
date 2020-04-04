package fusion.utils.crates;

import java.util.ArrayList;
import java.util.List;

/**
	 * 
	 * Copyright GummyPvP. Created on Jun 13, 2016 by Jeremy Gooch.
	 * All Rights Reserved.
	 * 
	 */

public class RewardManager {
	
	private RewardManager() { }
	
	private static RewardManager instance = new RewardManager();
	
	public static RewardManager getInstance() {
		return instance;
	}
	
	private List<Reward> rewards = new ArrayList<Reward>();
	
	public void addReward(Reward reward) {
		
		this.rewards.add(reward);
		
	}
	
	public Reward getReward(String name) {
		
		for (Reward allRewards : rewards) {
			
			if (allRewards.getName().equalsIgnoreCase(name)) return allRewards;
			
		}
		return null;
		
	}
	
	public Reward getReward(Reward reward) {
		
		for (Reward allRewards : rewards) {
			
			if (allRewards.getName().equalsIgnoreCase(reward.getName())) return allRewards;
			
		}
		return null;
		
	}

}
