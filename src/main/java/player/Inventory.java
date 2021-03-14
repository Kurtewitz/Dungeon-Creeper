package player;

import items.Item;
import items.shields.Shield;
import items.weapons.Dagger;
import items.weapons.Mace;
import items.weapons.Sword;
import items.weapons.Weapon;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

import java.util.ArrayList;
import java.util.List;

public class Inventory {
	
	final int maxItems = 20;

	ArrayList<Weapon> weapons;
	ArrayList<Shield> shields;
	Equipment equipped;
	IntegerProperty healthPotions;
	
	public Inventory() {
		weapons = new ArrayList<>();
		shields = new ArrayList<>();
		equipped = new Equipment();
		healthPotions = new SimpleIntegerProperty(this, "healthPotions", 5);
	}
	
	public Inventory(Weapon w) {
		this();
		weapons.add(w);
		equipWeapon(w);
		weapons.remove(0);
	}
	
	public Inventory(Shield s) {
		this();
		shields.add(s);
		equipShield(s);
	}
	
	public Inventory(Weapon w, Shield s) {
		this(w);
		shields.add(s);
		equipShield(s);
	}
	
	public IntegerProperty getHealthPotions() {
		return healthPotions;
	}
	
	public boolean useHealthPotion() {
		if(healthPotions.get() > 0) {
			healthPotions.set(healthPotions.get() - 1);
			return true;
		}
		return false;
	}

	public void addHealthPotions(int amount) {
		healthPotions.set(healthPotions.getValue() + amount);
	}
	
	public Weapon equippedWeapon() {
		return equipped.weapon();
	}
	
	public Shield equippedShield() {
		return equipped.shield();
	}
	
	public List<Weapon> weapons() {
		return weapons;
	}
	
	public List<Shield> shields() {
		return shields;
	}
	
	public List<Item> items() {
		List<Item> items = new ArrayList<>(weapons);
		items.addAll(shields);
		return items;
	}
	
	public int maxItems() {
		return maxItems;
	}

	public int countWeapons() {
		return weapons.size();
	}
	
	public int countShields() {
		return shields.size();
	}
	
	/**
	 * Add weapon to inventory if inventory is not already full
	 * @param w Weapon to be added
	 * @return <code>true</code> if adding succeeded<br><code>false</code> if backpack too full to add another weapon
	 */
	public boolean addWeapon(Weapon w) {
		if(countWeapons() + countShields() < maxItems) {
			weapons.add(w);
			return true;
		}
		else return false;
	}
	

	/**
	 * Pick up a weapon and add it to your inventory.
	 * If backpack is full, picking up will not succeed.
	 * @param w Weapon to be added
	 * @return String with a message describing the outcome.
	 */
	public String pickUpWeapon(Weapon w) {
		String message = "";
		if(addWeapon(w)) message += "Picked up" + w;
		else message += "Backpack full";
		return message;
	}
	
	/**
	 * Add shield to inventory if inventory is not already full
	 * @param s Shield to be added
	 * @return <code>true</code> if adding succeeded<br><code>false</code> if backpack too full to add another shield
	 */
	public boolean addShield(Shield s) {
		if(countShields() + countWeapons() < maxItems) {
			shields.add(s);
			return true;
		}
		else return false;
	}
	
	
	/**
	 * Pick up a shield and add it to your inventory.
	 * If backpack is full, picking up will not succeed.
	 * @param s Shield to be added
	 * @return String with a message describing the outcome.
	 */
	public String pickUpShield(Shield s) {
		String message = "";
		if(addShield(s)) message += "Picked up" + s;
		else message += "Backpack full";
		return message;
	}
	
	/**
	 * basic weapon removal without notification.
	 * @param index index of the weapon to be removed
	 * @return <code>null</code> if invalid index<br>otherwise the thrown out weapon
	 */
	public Weapon removeWeapon(int index) {
		if(index < 0 || index >= countWeapons()) return null;
		return weapons.remove(index);
	}
	
	/**
	 * basic shield removal without notification.
	 * @param index index of the shield to be removed
	 * @return <code>null</code> if invalid index<br>otherwise the thrown out shield
	 */
	public Shield removeShield(int index) {
		if(index < 0 || index >= countShields()) return null;
		return shields.remove(index);
	}
	
//	public Weapon throwAwayWeapon(int nr) {
//		Weapon w = removeWeapon(nr);
//		if(w != null) System.out.println("Threw away " + w); 
//		else System.out.println("Throwing out failed");
//		return w;
//	}
	
//	public Shield throwAwayShield(int nr) {
//		Shield s = removeShield(nr);
//		if(s != null) System.out.println("You threw away " + s);
//		else System.out.println("Throwing out failed");
//		return s;
//	}
	
	/**
	 * Try to equip a weapon. Go through all the necessary steps:
	 * checking if equipping null (unequip current), adding the weapon to inventory if needed
	 * and finally equipping the weapon
	 * @param w Weapon to be equipped
	 */
	public void equipWeapon(Weapon w) {
		// equipping null means simply unequipping current weapon
		if(w == null) equipped.unequipWeapon();
		else {
			// weapon needs to be in inventory first before it can be equipped
			if(weapons().contains(w)) {
				equipped.equipWeapon(w);
			}
			else {
				// try to add and equip weapon
				if(addWeapon(w)) equipWeapon(w);
			}
		}
	}
	
	/**
	 * Try to equip a shield. Go through all the necessary steps:
	 * checking if equipping null (unequip current), adding the shield to inventory if needed
	 * and finally equipping the shield
	 * @param s Shield to be equipped
	 */
	public void equipShield(Shield s) {
		// equipping null means simply unequipping current shield
		if(s == null) equipped.unequipShield();
		else {
			// shield needs to be in the inventory first before it can be equipped
			if(shields().contains(s)) {
				equipped.equipShield(s);
			}
			else {
				// try to add and equip shield 
				if(addShield(s)) equipShield(s);
			}
		}
	}
	
	public void printWeapons() {
		for(Weapon w : weapons) System.out.println(w);
	}
	
	public void printShields() {
		for(Shield s : shields) System.out.println(s);
	}
	
	public static void main(String[] args) {
		Inventory i = new Inventory();
		i.pickUpWeapon(new Mace(1, false));
		i.pickUpWeapon(new Mace(4, true));
		i.pickUpWeapon(new Sword(3, true));
		i.pickUpWeapon(new Sword(3, false));
		i.pickUpWeapon(new Sword(5, true));
		i.pickUpWeapon(new Dagger(4));
		i.pickUpWeapon(new Dagger(3));
		i.pickUpWeapon(new Dagger(2));
		i.printWeapons();
		System.out.println(i.equippedWeapon());
		System.out.println();
		i.equipWeapon(i.weapons().get(2));
		i.printWeapons();
		System.out.println(i.equippedWeapon());

	}
}
