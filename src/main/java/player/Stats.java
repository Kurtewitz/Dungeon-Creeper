package player;

public abstract class Stats {
	protected int Str;
	protected int Int;
	protected int Spd;
	protected int Health;
	protected int Wis;
	protected int Dex;
	protected int StatPts;
	
	public Stats(int Str, int Int, int Spd, int Health, int Wis, int Dex) {
		this.Str = Str;
		this.Int = Int;
		this.Spd = Spd;
		this.Health = Health;
		this.Wis = Wis;
		this.Dex = Dex;
		StatPts = 0;
	}
	
	public int Str(){
		return Str;
	}
	public int Int(){
		return Int;
	}
	public int Spd(){
		return Spd;
	}
	public int Health(){
		return Health;
	}
	public int Wis(){
		return Wis;
	}
	public int Dex(){
		return Dex;
	}
	public int StatPts(){
		return StatPts;
	}
	
	public void changeStr(int change){
		Str += change;
	}
	public void changeInt(int change){
		Int += change;
	}
	public void changeSpd(int change){
		Spd += change;
	}
	public void changeHealth(int change){
		Health += change;
	}
	public void changeWis(int change){
		Wis += change;
	}
	public void changeDex(int change){
		Dex += change;
	}
	public void changeStatPts(int change){
		StatPts += change;
	}
	
	
	public void spendStatPoint(int i) {
		switch(i) {
			case 1:
				Str++;
				break;
			case 2:
				Int++;
				break;
			case 3:
				Spd++;
				break;
			case 4:
				Health++;
				break;
			case 5:
				Wis++;
				break;
			case 6:
				Dex++;
				break;
		}
		StatPts--;
	}
	
	/**
	 * Gibt die Stats aus
	 */
	public void printStats() {
		System.out.print(" Str: " + Str());
		System.out.print(" Int: " + Int());
		System.out.print(" Health: " + Health());
		System.out.print(" Spd: " + Spd());
		System.out.print(" Wis: " + Wis());
		System.out.print(" Dex: " + Dex());
		System.out.println();
	}
	
	public abstract void levelUp1Stat();
	
	public abstract void levelUp();
}
