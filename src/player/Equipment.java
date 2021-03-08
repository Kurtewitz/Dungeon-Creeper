package player;
import items.*;
import items.weapons.Weapon;

public class Equipment{
	Weapon w;
	Shield s;
	
	public Equipment() {
		w = null;//new Mace(1, false);
		s = null;
	}
	
	/** @return the currently equipped weapon */
	public Weapon weapon() {
		return w;
	}
	
	/** @return the currently equipped shield */
	public Shield shield() {
		return s;
	}
	
	/**
	 * Unequips the currently equipped weapon by setting the equipped Weapon to <code>null</code> and
	 * returning the previously equipped one
	 * @return the weapon, that was just unequipped
	 */
	public Weapon unequipWeapon() {
		Weapon temp = w;
		w = null;
		return temp;
	}
	
	/**
	 * Equips a weapon. Unequips the currently equipped one, equips the new one and returns the old one
	 * @param w the weapon to be equipped
	 * @return <li>the unequipped weapon <li><code>null</code> if no weapon was equipped
	 */
	public Weapon equipWeapon(Weapon w) {
		Weapon temp = unequipWeapon();
		this.w = w;
		return temp;
		
	}
	
	/**
	 * Unequips the currently equipped shield by setting the equipped shield to <code>null</code> and
	 * returning the previously equipped one
	 * @return the shield, that was just unequipped
	 */
	public Shield unequipShield() {
		Shield temp = s;
		s = null;
		return temp;
	}
	
	/**
	 * Equips a shield. Unequips the currently equipped one, equips the new one and returns the old one
	 * @param w the shield to be equipped
	 * @return <li>the unequipped shield <li><code>null</code> if no shield was equipped
	 */
	public Shield equipShield(Shield s) {
		Shield temp = unequipShield();
		this.s = s;
		return temp;
		
	}
}
