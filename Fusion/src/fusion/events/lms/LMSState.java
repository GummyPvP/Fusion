package fusion.events.lms;

	/**
	 * 
	 * Copyright GummyPvP. Created on Jun 4, 2016 by Jeremy Gooch.
	 * All Rights Reserved.
	 * 
	 */

public enum LMSState {
	
	NOHOST, WAITING, GRACE, FIGHTING; // nohost = LMS is not being hosted, waiting = no pvp, grace = no pvp but started, fighting = started and pvp

}
