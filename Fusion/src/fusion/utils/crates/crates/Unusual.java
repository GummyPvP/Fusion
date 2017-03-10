package fusion.utils.crates.crates;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import fusion.utils.crates.Crate;
import fusion.utils.crates.Reward;
import fusion.utils.crates.rewards.CandyReward;

/**
	 * 
	 * Copyright GummyPvP. Created on Jun 13, 2016 by Jeremy Gooch.
	 * All Rights Reserved.
	 * 
	 */

public class Unusual extends Crate {

	@Override
	public String getName() {
		return "Unusual";
	}

	@Override
	public List<Reward> getRewards() {
		return Collections.singletonList(new CandyReward(500));
	}

}
