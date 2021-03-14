package player;

import items.Item;
import items.shields.Shield;
import items.weapons.Mace;
import items.weapons.Sword;
import items.weapons.Weapon;
import player.stats.Stats;
import player.stats.StatsMage;
import player.stats.StatsThief;
import player.stats.StatsWarrior;

import java.util.ArrayList;


public class Player {
	int Level;
	int manaPointsCurrent;
	int manaPointsMax;
	int healthPointsCurrent;
	private int healthPointsMax;
	int EXP;
	int expToLvlUp;
	Inventory inventory;
	Stats stats;
	Skills skills;
	PlayerType playerType;
	// Formel fuer EXP fuer LvlUp : 100 * (2.0 ^ Level+1) ?
	
	int x;
	int y;
	
	public Player(int x, int y, PlayerType playerType) {
		this.x = x;
		this.y = y;
		this.playerType = playerType;

		Level = 1;
		EXP = 0;
		expToLvlUp = 100;
		
		inventory = new Inventory();
		if(PlayerType.Warrior.equals(playerType)) stats = new StatsWarrior();
		else if (PlayerType.Mage.equals(playerType)) stats = new StatsMage();
		else if (PlayerType.Thief.equals(playerType)) stats = new StatsThief();
		
		skills = new Skills(this);
		
		manaPointsMax = 10 * stats.intelligence();
		manaPointsCurrent = manaPointsMax;
		healthPointsMax = 10 * stats.health();
		healthPointsCurrent = healthPointsMax;
	}
	
	public Player(int x, int y, PlayerType playerType, int lvl, Item item) {
		this(x, y, playerType);
		for(int i = level(); i <= lvl; i++) levelUp();
		if(item instanceof Weapon) { inventory.equipWeapon( (Weapon) item ); }
		else if (item instanceof Shield) { inventory.equipShield( (Shield) item ); }
	}
	
	public Player(int x, int y, PlayerType playerType, int lvl, ArrayList<Item> equipment) {
		this(x, y, playerType);
		
		for(int i = level(); i <= lvl; i++) levelUp();
		//add all the items into inventory / equipment
		for(int i = 0; i < equipment.size(); i++) {
			if(equipment.get(i) instanceof Weapon) {
				inventory.addWeapon((Weapon)equipment.get(i));
			}
			else if (equipment.get(i) instanceof Shield) {
				inventory.addShield((Shield) equipment.get(i) );
			}
		}
		
		//equip the most valuable items
		if(inventory.weapons().size() > 0) inventory.equipWeapon(inventory.weapons().get(0));
		if(inventory.shields().size() > 0) inventory.equipShield(inventory.shields().get(0));
	}
	
	public int y() {
		return y;
	}
	public int x() {
		return x;
	}
	public Stats stats() {
		return stats;
	}
	
	public int level() {
		return Level;
	}
	
	public Inventory inventory() {
		return inventory;
	}
	
	public Skills skills() {
		return skills;
	}
	
	
	public void moveBy(int dx, int dy) {
		x += dx;
		y += dy;
	}
	
	public void moveTo(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public void useHpPotion() {
		if(inventory.useHealthPotion()) healthPointsCurrent += 80;
		if(healthPointsCurrent > healthPointsMax) healthPointsCurrent = healthPointsMax;
	}
	
	public void attack(Player p) {
		// calculate the dmg dealt based on attacker's dmg and defender def.
		// make sure the final dmg is at least 1.
		int dmgDealt = Math.max(1, dmg() - p.def());
		p.loseHP(dmgDealt);
		System.out.println("Gegner HP: " + p.getCurrentHealth() + " / " + p.getMaxHealth());
	}
	public int dmg() {
		if(inventory.equippedWeapon() instanceof Sword) { return stats().strength() + inventory.equippedWeapon().damage();}
		else if(inventory.equippedWeapon() instanceof Mace) {return stats().strength() + inventory.equippedWeapon().damage();}
		else return stats.strength() / 5 + stats().dexterity() / 3 + inventory.equippedWeapon().damage();
	}
	
	public int def() {
		int def = 0;
		if(inventory.equippedShield() != null) def += inventory.equippedShield().defense();
		return def;
	}

	public int exp() {
		return EXP;
	}

	public int expToLevelUp() {
		return expToLvlUp;
	}
	
	public void loseHP(int x) {
		healthPointsCurrent -= x;
		healthPointsCurrent = Math.max(0, healthPointsCurrent);
	}
	
	public void gainExp(int exp) {
		EXP += exp;
		if(EXP >= expToLvlUp) {
			EXP -= expToLvlUp;
			levelUp();
			
		}
	}
	
	public void levelUp() {
		Level++;
		expToLvlUp = 100 * (int) Math.pow(2, Level - 1);
		stats().levelUp();
		manaPointsMax = 10 * stats.intelligence();
		manaPointsCurrent = manaPointsMax;
		healthPointsMax = 10 * stats.health();
		healthPointsCurrent = healthPointsMax;
	}

	public int getCurrentHealth() {
		return healthPointsCurrent;
	}

	public int getMaxHealth() {
		return healthPointsMax;
	}

	public boolean isDead() {
		return healthPointsCurrent <= 0;
	}

    public void pickUpHealthPotions(int amountOfPotions) {
		inventory.addHealthPotions(amountOfPotions);
    }

	
/*	public static void main(String[] args) {
		Player p = new Player(5,5,1);
		p.stats().printStats();
		Opponent o = new Opponent();
		System.out.println(o.HP());
		p.attack(o);
		System.out.println(o.HP());
		System.out.println(p.HP());
		o.attack(p);
		System.out.println(p.HP());
		System.out.println(p.skills().getSkill(0, 0).toString());
	}
	*/
}
