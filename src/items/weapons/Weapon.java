package items.weapons;

import items.Equippable;
import items.Item;
import org.apache.commons.lang3.StringUtils;
import player.Stats;

public abstract class Weapon extends Item implements Equippable/*implements Comparable<Weapon> */{

	/** Weapon class: <li>1 = Mace <li>2 = Sword<li>3 = Dagger */
	private final WeaponType weaponType;
	/** Damage dealt by the weapon*/
	private final int dmg;
	/** is the weapon two handed */
	private final boolean twoHanded;
	
	public Weapon(int dmg, WeaponType weaponType, boolean twoHanded) {
		super(0);
		this.dmg = Math.max(1, dmg); // min 1 dmg
		this.weaponType = weaponType;
		this.twoHanded = twoHanded;
		
		int value = dmg;
		if(twoHanded) value /= 2;
//		if(prefix || suffix) value += additional value of upgrade;
		setValue(value);
		
	}
	
//	public int compareTo(Weapon w) {
//		if(Typ > w.Typ) return 1;
//		else if(Typ < w.Typ) return -1;
//		else {
//			if(twoHanded() && !w.twoHanded()) return 1;
//			else if(!twoHanded() && w.twoHanded()) return -1;
//			else return DMG - w.DMG;
//		}
//	}
	
	public int dmg() {
		return dmg;
	}
	
	public boolean twoHanded() {
		return twoHanded;
	}
	
	
	public String toString() {
		
		String namePattern = "%1$s, %2$d %3$s damage";
		
		String name = "";
		String dmgType = "";
		
		switch(weaponType) {
			case Mace: {
				name = twoHanded() ? "War hammer (2-handed)" : "Mace";
				dmgType = "blunt";
				break;
			}
			case Sword: {
				name = twoHanded() ? "Zweihänder" : "Sword";
				dmgType = "slashing";
				break;
			}
			case Dagger: {
				name = twoHanded() ? "Daggers" : "Dagger";
				dmgType = "piercing";
				break;
			}
		}

		if(StringUtils.isEmpty(name) && StringUtils.isEmpty(dmgType)) {
			return String.format(namePattern, name, dmg, dmgType);
		}
		return "Weapon not recognized";
	}
	
	public void influenceStats(Stats stats) {
		//TODO prefix / suffix could change stats instead of just contributing to the dmg()
	}
}
