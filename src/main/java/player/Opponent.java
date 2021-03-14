package player;

import java.util.ArrayList;

import items.*;
import items.shields.Shield;
import items.weapons.Weapon;
import util.RNG;

public class Opponent extends Player{

	private boolean fledFrom = false;
	
	public Opponent(int x, int y, PlayerType playerType) {
		super(x , y, playerType);
	}
	
	public Opponent(int x, int y, PlayerType playerType, int lvl, Item item) {
		super(x, y, playerType, lvl, item);
	}
	
	public Opponent(int x, int y, PlayerType playerType, int lvl, ArrayList<Item> items) {
		super(x, y, playerType, lvl, items);
	}
	
	/**
	 * set-method for the fledFrom status. Called when you flee from a fight with a monster.
	 */
	public void fleeFrom() {
		fledFrom = true;
	}
	
	/**
	 * get Method for the fledFrom status
	 * @return <code>true</code> if the monster has been fled from, <code>false</code> otherwise
	 */
	public boolean fledFrom() {
		return fledFrom;
	}
	
	/**
	 * Method called when calculating the loot after the player wins a fight
	 * @return the weapon used by the opponent
	 */
	public Weapon dropWeapon() {
		return inventory.equippedWeapon();
	}
	
	/**
	 * method called when calculating the loot after the player wins a fight
	 * @return the shield used by the opponent
	 */
	public Shield dropShield() {
		return inventory.equippedShield();
	}
	
	/**
	 * random loot-generator. Returns an integer based on which FightJudge calculates the player's loot.
	 * @return <ul><li><code>0</code> if no loot
	 * <li><code>1</code> if weapon
	 * <li><code>2</code> if shield
	 * <li><code>3</code> if weapon + shield
	 */
	public ArrayList<Item> dropLoot() {
		int i = RNG.randomPositiveInt(101);

		boolean dropWeapon = i % 2 == 0;
		boolean dropShield = i % 3 == 0;

		ArrayList<Item> loot = new ArrayList<>();

		if(dropWeapon && inventory.equippedWeapon() != null) {
			loot.add(inventory.equippedWeapon());
		}

		if(dropShield && inventory.equippedShield() != null) {
			loot.add(inventory.equippedShield());
		}

		return loot;
	}
	
	public int exp() {
		return level() * 10;
	}
	
	/**
	 * Attack the player. Decrease his HP and print his HP afterwards.
	 */
	public void attack(Player p) {
		// calculate the dmg dealt based on attacker's dmg and defender def.
		// make sure the final dmg is at least 1.
		int dmgDealt = Math.max(1, dmg() - p.def());
		p.loseHP(dmgDealt);
		System.out.println("Player HP: " + p.getCurrentHealth() + " / " + p.getMaxHealth());
	}
	
	/**
	 * insert KI here. Opponent's turn during a fight
	 */
	public void turn(Player p) {
		attack(p);
	}

}
