package welt;
import java.util.ArrayList;
import java.util.Random;

import items.Item;
import items.Shield;
import items.weapons.*;
import player.Opponent;
import player.Player;
import player.PlayerType;

/**
 * This class handles monster spawns and the fighting in the game.
 * @author Michoo
 *
 */
public class FightJudge {

	Player p;
	Opponent o;
	/**  the distance between the player and the enemy */
	int distance;
	Random r = new Random();
	
	public FightJudge(Player p) {
		this.p = p;
	}
	
	public Opponent opponent() {
		return o;
	}
	
	/**
	 * checks for monsters. 30% chance for an encounter.
	 * @return <code>true</code> if you encountered a monster <br><code>false</code> if there is no monster
	 */
	public boolean checkForMonster() {
		
		if(r.nextInt(10) % 4 == 0) return true;
		return false;
	}
	
	/**
	 * spawns a new opponent
	 * @return an opponent
	 */
	public Opponent spawnMonster(int x, int y) {

		int weaponType = 1 + r.nextInt(3);

		PlayerType opponentClass = PlayerType.values()[weaponType - 1];


		int lvl = p.level()-1 + r.nextInt(3);
		if(p.level() < 3) lvl = p.level();
		
		int weaponDmg = 1 + r.nextInt(lvl);
		boolean zh = r.nextBoolean();
		
		if(zh && weaponType != 3) weaponDmg *= 2;
		// daggers always two handed
		if(weaponType == 3) zh = true;
		
		ArrayList<Item> items = new ArrayList<Item>();
		if(weaponType == 1) items.add(new Mace(weaponDmg, zh));
		else if (weaponType == 2) items.add(new Sword(weaponDmg, zh));
		else if (weaponType == 3) items.add(new Dagger(weaponDmg));
		
		// if second hand free, maybe a shield
		if(!zh) {
			int rnd = r.nextInt(100);
			if(rnd < 40) {
				// p.lvl + 1 to avoid problems at lvl 1. (1 / 2 = 0, bound must be positive)
				int def = r.nextInt(( lvl+1 ) / 2);
				items.add(new Shield(def));
			}
		}
		
		return new Opponent(x, y, opponentClass, lvl, items);
	}
	
	/**
	 * sets an opponent as your opponent for the fight
	 * @param o the monster you'll be fighting
	 */
	public void initFight(Opponent o) {
		this.o = o;
	}
	
	/**
	 * Flee from a monster. 20% chance to lose a random weapon/shield from inventory (if you have any). 2% chance for both
	 * @param o the opponent you wish to flee from
	 */
	public String flee(Opponent o) {
		String message = "You ran away.";
		o.fleeFrom();
		Random r = new Random();
		int i = r.nextInt(50) + 1;
		
		if(p.inventory().countWeapons() > 0) {
			if(i <= 10) {
				int a = r.nextInt(p.inventory().countWeapons());
				p.inventory().removeWeapon(a);
				message += " You notice, that you lost one of your weapons.";
			}	
		}

		if(p.inventory().countShields() > 0) {
			if(i % 10 == 0) {
				int a = r.nextInt(p.inventory().countShields());
				p.inventory().removeShield(a);
				message += " You notice, that you lost one of your shields.";
			}
		}
		System.out.println(message);
		return message;
	}
	
	/**
	 * Player attacks the Opponent.
	 * @param p attacking player
	 * @param o attacked opponent
	 */
	public void attack(Player p, Player o) {
		p.attack(o);
	}
	
	public void useSkill(int nr, Player p) {
		
	}
	
	
	/**
	 * opens a dialog box asking whether you want to pick up the weapon used by your opponent
	 * @return
	 */
	public boolean askPickUpWeapon() {
		String s = javax.swing.JOptionPane.showInputDialog(null,"Pick up " + o.dropWeapon() + " ?\n\ny = yes, n = no");
		char c;
		if (s == null || s.length() == 0) c = ' ';
		else c = s.charAt(0);
		
		if(c == 'y') {p.inventory().pickUpWeapon(o.inventory().equippedWeapon()); return true;}
		if(c == 'n') {return false;}
		else return askPickUpWeapon();
		
	}
	
	/**
	 * opens a dialog box asking whether you want to pick up the shield used by your opponent
	 * @return
	 */
	public boolean askPickUpShield() {
		String s = javax.swing.JOptionPane.showInputDialog(null,"Pick up " + o.dropShield() + " ?\n\ny = yes, n = no");
		char c;
		if (s == null || s.length() == 0) c = ' ';
		else c = s.charAt(0);
		
		if(c == 'y') {p.inventory().pickUpShield(o.inventory().equippedShield()); return true;}
		if(c == 'n') {return false;}
		else return askPickUpShield();
		
	}
	
	/**
	 * calls the methods asking you to pick up the spoils of war
	 */
	public String loot() {
		String message = "Picked up ";
		ArrayList<Item> loot = o.dropLoot();

		if(loot.isEmpty()) {
			message = "You searched the battlefield, but found no spoils ";
		}
		for(Item drop : loot) {

			if (drop instanceof Weapon) {
				if (askPickUpWeapon()) {
					message += o.dropWeapon();
				}
			}

			if (drop instanceof Shield) {
				if (askPickUpShield()) {
					message += o.dropShield();
				}
			}
		}


		if(message.equals("Picked up ")) message = "";
		
		System.out.println(message);
		return message;
	}
	
	/**
	 * kill enemy, gain exp and loot
	 * @return String representing the outcome of the looting
	 */
	public String killOpponent() {
		opponent().loseHP(opponent().maxHP());
		
		p.gainExp(o.exp());
		
		return loot();
		
	}
	
	
	/**
	 * opens a dialog box asking for your action. calls the method according to your input.
	 * @return <code>false</code> if you flee, <br><code>true</code> otherwise
	 */
	boolean actFight() {
		String s = javax.swing.JOptionPane.showInputDialog(null,"Fight!\n\nq = Attack, e = Flee, p = Health Potion");
		char c;
		if (s == null || s.length() == 0) c= ' ';
		else c = s.charAt(0);

		if(c == 'q') {attack(p, o);return true;}
		if(c == 'e') {flee(o);return false;}
		if(c == 'p') {p.useHpPotion(); return true;}
		else return actFight();
	}
	
	/**
	 * The fight between the hero and a beast. Includes looting the dead monstrosity.
	 * @return <ul><li><code>1</code> if you killed the beast
	 * <li><code>2</code> if your best was not enough
	 * <li><code>3</code> if you're a coward
	 */
	/*public int fight() {
		while(p.HP() > 0) {
			if(!actFight()) return 3;
			if(o.HP() > 0) o.turn(p);
			else {
				loot();
				return 1;
			}
		}
		return 2;
	}*/

	

}
