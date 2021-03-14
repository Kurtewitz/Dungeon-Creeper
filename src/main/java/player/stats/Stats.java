package player.stats;

import java.util.HashMap;
import java.util.Random;

import static player.stats.Stat.*;

public abstract class Stats {

	Random rng = new Random();

	private HashMap<Stat, Integer> coreStats;

	protected int StatPts;
	
	public Stats(int Str, int Int, int Spd, int health, int Wis, int Dex) {
		coreStats = new HashMap<>();
		coreStats.put(Strength, Str);
		coreStats.put(Intelligence, Int);
		coreStats.put(Speed, Spd);
		coreStats.put(Health, health);
		coreStats.put(Wisdom, Wis);
		coreStats.put(Dexterity, Dex);
		StatPts = 0;
	}
	
	public int strength(){
		return coreStats.get(Strength);
	}
	public int intelligence(){
		return coreStats.get(Intelligence);
	}
	public int speed(){
		return coreStats.get(Speed);
	}
	public int health(){
		return coreStats.get(Health);
	}
	public int wisdom(){
		return coreStats.get(Wisdom);
	}
	public int dexterity(){
		return coreStats.get(Dexterity);
	}
	public int statPoints(){
		return StatPts;
	}
	
	public void changeStr(int change){
		coreStats.put(Strength, strength() + change);
	}
	public void changeInt(int change){
		coreStats.put(Intelligence, intelligence() + change);
	}
	public void changeSpd(int change){
		coreStats.put(Speed, speed() + change);
	}
	public void changeHealth(int change){
		coreStats.put(Health, health() + change);
	}
	public void changeWis(int change){
		coreStats.put(Wisdom, wisdom() + change);
	}
	public void changeDex(int change){
		coreStats.put(Dexterity, dexterity() + change);
	}
	public void changeStatPts(int change){
		StatPts += change;
	}
	
	
	public void spendStatPoint(Stat stat) {
		switch(stat) {
			case Strength:
				changeStr(1);
				break;
			case Intelligence:
				changeInt(1);
				break;
			case Speed:
				changeSpd(1);
				break;
			case Health:
				changeHealth(1);
				break;
			case Wisdom:
				changeWis(1);
				break;
			case Dexterity:
				changeDex(1);
				break;
		}
		StatPts--;
	}
	
	/**
	 * Gibt die Stats aus
	 */
	public void printStats() {
		System.out.print(" Str: " + strength());
		System.out.print(" Int: " + intelligence());
		System.out.print(" Health: " + health());
		System.out.print(" Spd: " + speed());
		System.out.print(" Wis: " + wisdom());
		System.out.print(" Dex: " + dexterity());
		System.out.println();
	}
	
	public abstract void levelUp1Stat();
	
	public abstract void levelUp();
}
