package fusion.kits.utils.kitutils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PortalSchematics {
	
	private List<PortalSchematic> schematics;
	
	public PortalSchematics() {
		schematics = new ArrayList<PortalSchematic>();
	}
	
	public void addSchematic(PortalSchematic schematic) {
		this.schematics.add(schematic);
	}
	
	public PortalSchematic getRandomSchematic() {
		return schematics.get(new Random().nextInt(schematics.size()));
	}
	
}
