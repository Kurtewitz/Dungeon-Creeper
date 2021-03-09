package items;

import player.Stats;

public class Shield extends Item implements Equippable /*implements Comparable<Shield>*/{
	int Def;
	int Typ;
	
	
	public Shield(int def) {
		super(2*Math.max(1, def)); //shield with 0 defense makes no sense
		Def = Math.max(1, def);
		Typ = 0;
	}
	
	public Shield(int def, int type) {
		super(2*Math.max(1, def));
		Def = Math.max(1, def);
		Typ = type;
	}
	
	public int def() {
		return Def;
	}
	
	public int typ() {
		return Typ;
	}

//	public int compareTo(Shield s) {
//		if(def() > s.def()) return 1;
//		else if(def() < s.def()) return -1;
//		else return 0;
//	}
	
	public String toString() {
		return "Shield, " + def() + " defense.";
	}

	@Override
	public void influenceStats(Stats stats) {
		// TODO prefix/suffix could change the stats.
	
	}
}
