package player;

import items.shields.Shield;
import items.weapons.Weapon;

public class Equipment {
    Weapon weapon;
    Shield shield;

    public Equipment() {
        weapon = null;
        shield = null;
    }

    /**
     * @return the currently equipped weapon
     */
    public Weapon weapon() {
        return weapon;
    }

    /**
     * @return the currently equipped shield
     */
    public Shield shield() {
        return shield;
    }

    /**
     * Unequips the currently equipped weapon by setting the equipped Weapon to <code>null</code> and
     * returning the previously equipped one
     *
     * @return the weapon, that was just unequipped
     */
    public Weapon unequipWeapon() {
        Weapon currentlyEquipped = weapon;
        weapon = null;
        return currentlyEquipped;
    }

    /**
     * Equips a weapon. Unequips the currently equipped one, equips the new one and returns the old one
     *
     * @param weapon the weapon to be equipped
     * @return <li>the unequipped weapon <li><code>null</code> if no weapon was equipped
     */
    public Weapon equipWeapon(Weapon weapon) {
        Weapon currentlyEquipped = unequipWeapon();
        this.weapon = weapon;
        return currentlyEquipped;
    }

    /**
     * Unequips the currently equipped shield by setting the equipped shield to <code>null</code> and
     * returning the previously equipped one
     *
     * @return the shield, that was just unequipped
     */
    public Shield unequipShield() {
        Shield currentlyEquipped = shield;
        shield = null;
        return currentlyEquipped;
    }

    /**
     * Equips a shield. Unequips the currently equipped one, equips the new one and returns the old one
     *
     * @param shield the shield to be equipped
     * @return <li>the unequipped shield <li><code>null</code> if no shield was equipped
     */
    public Shield equipShield(Shield shield) {
        Shield currentlyEquipped = unequipShield();
        this.shield = shield;
        return currentlyEquipped;
    }
}
