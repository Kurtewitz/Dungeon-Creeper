package items.shields;

import items.Equippable;
import items.Item;
import player.stats.Stats;

public class Shield extends Item implements Equippable {
	int defense;
	int type;
	
	public Shield(int defense) {
		this(defense, 0);
	}
	
	public Shield(int defense, int type) {
		super(2*Math.max(1, defense));
		this.defense = Math.max(1, defense); //shield with 0 defense makes no sense
		this.type = type;
	}
	
	public int defense() {
		return defense;
	}
	
	public int type() {
		return type;
	}
	
	public String toString() {
		return "Shield, " + defense() + " defense.";
	}

	@Override
	public void influenceStats(Stats stats) {
		// TODO prefix/suffix could change the stats.
	
	}
}
