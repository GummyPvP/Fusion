package fusion.utils.gui;

import java.util.Comparator;

import fusion.kits.utils.Kit;

public class KitSorter implements Comparator<Kit> {

	@Override
	public int compare(Kit o1, Kit o2) {
		
		if (o1.isDefault() || o1.getCost() == 0.0) return -1;
		
		return o1.getCost() > o2.getCost() ? -1 : o1.getCost() == o2.getCost() ? 0 : 1;
		
	}

}
