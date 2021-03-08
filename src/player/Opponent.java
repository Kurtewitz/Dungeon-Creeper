package player;

import java.util.ArrayList;
import java.util.Random;

import items.*;
import items.weapons.Weapon;

public class Opponent extends Player{

	private boolean fledFrom = false;
	
	public Opponent(int x, int y, int weaponType) {
		super(x , y, weaponType);
	}
	
	public Opponent(int x, int y, int weaponType, int lvl, Item item) {
		super(x, y, weaponType, lvl, item);
	}
	
	public Opponent(int x, int y, int weaponType, int lvl, ArrayList<Item> items) {
		super(x, y, weaponType, lvl, items);
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
	public int dropLoot() {
		Random r = new Random();
		int i = r.nextInt(100) + 1;
		int check = 0;
		if(i % 2 == 0) check += 1;
		if(i % 3 == 0) check += 2;
		
		ArrayList<Item> loot = new ArrayList<Items>();
		
		if(check == 1) return 1;
		else if(check == 2) {
			if(inventory.equippedShield() != null) return 2;
			else return 0;
		}
		else if(check == 3) {
			if(inventory.equippedShield() != null) return 3;
			else return 1;
		}
		else return 0;
	}
	
	public int exp() {
		return lvl() * 10;
	}
	
	/**
	 * Attack the player. Decrease his HP and print his HP afterwards.
	 */
	public void attack(Player p) {
		// calculate the dmg dealt based on attacker's dmg and defender def.
		// make sure the final dmg is at least 1.
		int dmgDealt = Math.max(1, dmg() - p.def());
		p.loseHP(dmgDealt);
		System.out.println("Player HP: " + p.HP() + " / " + p.healthPointsMax);
	}
	
	/**
	 * insert KI here. Opponent's turn during a fight
	 */
	public void turn(Player p) {
		attack(p);
	}

}
