package items.weapons;

import items.Equippable;
import items.Item;
import player.stats.Stats;

public abstract class Weapon extends Item implements Equippable {

	/** Weapon class: <li>1 = Mace <li>2 = Sword<li>3 = Dagger */
	private final WeaponType weaponType;
	/** Damage dealt by the weapon*/
	private final int damage;
	/** is the weapon two handed */
	private final boolean twoHanded;

	protected Weapon(int damage, WeaponType weaponType, boolean twoHanded) {
		super(0);
		this.damage = Math.max(1, damage); // min 1 dmg
		this.weaponType = weaponType;
		this.twoHanded = twoHanded;

		int value = damage;
		if(twoHanded) value /= 2;
//		if(prefix || suffix) value += additional value of upgrade;
		setValue(value);

	}

	public int damage() {
		return damage;
	}

	public boolean twoHanded() {
		return twoHanded;
	}


	@Override
	public String toString() {
		return "Weapon not recognized";
	}

	public void influenceStats(Stats stats) {
		//TODO prefix / suffix could change stats instead of just contributing to the dmg()
	}
}
