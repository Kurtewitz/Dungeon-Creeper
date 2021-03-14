package welt;
import java.util.ArrayList;
import java.util.Random;

import items.Item;
import items.shields.Shield;
import items.weapons.*;
import player.Opponent;
import player.Player;
import player.PlayerType;
import util.RNG;

import static items.weapons.WeaponType.*;

/**
 * This class handles monster spawns and the fighting in the game.
 * @author Michoo
 *
 */
public class FightJudge {

	Player player;
	Opponent opponent;
	/**  the distance between the player and the enemy */
	int distance;
	
	public FightJudge(Player player) {
		this.player = player;
	}
	
	public Opponent opponent() {
		return opponent;
	}
	
	/**
	 * spawns a new opponent
	 * @return an opponent
	 */
	public Opponent spawnMonster(int x, int y) {

		PlayerType opponentType = PlayerType.getRandom();
		WeaponType weaponType = WeaponType.getRandomForClass(opponentType);

		//opponent level can be between playerLvl-1 and playerLvl+1 for players of level > 3
		int opponentLevel = player.level() <= 3
				? player.level()
				: RNG.randomIntegerBetween(player.level() - 1, player.level() + 2);

		int weaponDmg = 1 + RNG.randomPositiveInt(opponentLevel);
		boolean twoHanded = RNG.randomBoolean();


		if(twoHanded && !weaponType.equals(Dagger)) weaponDmg *= 2;
		// daggers always two handed
		if(weaponType.equals(Dagger)) twoHanded = true;
		
		ArrayList<Item> items = new ArrayList<>();
		if(Mace.equals(weaponType)) items.add(new Mace(weaponDmg, twoHanded));
		else if (Sword.equals(weaponType)) items.add(new Sword(weaponDmg, twoHanded));
		else if (Dagger.equals(weaponType)) items.add(new Dagger(weaponDmg));
		
		// if second hand free, maybe a shield
		if(!twoHanded) {
			//40% chance the opponent with single handed weapon will have a shield
			boolean hasShield = RNG.randomInt(100) < 40;
			if(hasShield) {

				int def = RNG.randomPositiveInt(opponentLevel / 2);
				items.add(new Shield(def));
			}
		}
		
		return new Opponent(x, y, opponentType, opponentLevel, items);
	}
	
	/**
	 * sets an opponent as your opponent for the fight
	 * @param o the monster you'll be fighting
	 */
	public void initFight(Opponent o) {
		this.opponent = o;
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
		
		if(player.inventory().countWeapons() > 0) {
			if(i <= 10) {
				int a = r.nextInt(player.inventory().countWeapons());
				player.inventory().removeWeapon(a);
				message += " You notice, that you lost one of your weapons.";
			}	
		}

		if(player.inventory().countShields() > 0) {
			if(i % 10 == 0) {
				int a = r.nextInt(player.inventory().countShields());
				player.inventory().removeShield(a);
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
		String s = javax.swing.JOptionPane.showInputDialog(null,"Pick up " + opponent.dropWeapon() + " ?\n\ny = yes, n = no");
		char c;
		if (s == null || s.length() == 0) c = ' ';
		else c = s.charAt(0);
		
		if(c == 'y') {
			player.inventory().pickUpWeapon(opponent.inventory().equippedWeapon()); return true;}
		if(c == 'n') {return false;}
		else return askPickUpWeapon();
		
	}
	
	/**
	 * opens a dialog box asking whether you want to pick up the shield used by your opponent
	 * @return
	 */
	public boolean askPickUpShield() {
		String s = javax.swing.JOptionPane.showInputDialog(null,"Pick up " + opponent.dropShield() + " ?\n\ny = yes, n = no");
		char c;
		if (s == null || s.length() == 0) c = ' ';
		else c = s.charAt(0);
		
		if(c == 'y') {
			player.inventory().pickUpShield(opponent.inventory().equippedShield()); return true;}
		if(c == 'n') {return false;}
		else return askPickUpShield();
		
	}
	
	/**
	 * calls the methods asking you to pick up the spoils of war
	 */
	public String loot() {
		String message = "Picked up ";
		ArrayList<Item> loot = opponent.dropLoot();

		if(loot.isEmpty()) {
			message = "You searched the battlefield, but found no spoils ";
		}
		for(Item drop : loot) {

			if (drop instanceof Weapon) {
				if (askPickUpWeapon()) {
					message += opponent.dropWeapon();
				}
			}

			if (drop instanceof Shield) {
				if (askPickUpShield()) {
					message += opponent.dropShield();
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
		opponent().loseHP(opponent().getMaxHealth());
		
		player.gainExp(opponent.exp());
		
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

		if(c == 'q') {attack(player, opponent);return true;}
		if(c == 'e') {flee(opponent);return false;}
		if(c == 'p') {
			player.useHpPotion(); return true;}
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
