package player.stats;

public class StatsMage extends Stats{
	public StatsMage() {
		super(2, 8, 4, 4, 8, 2);
	}
	
	/**
	 * Einen zufaellig gewaehlten Stat erhoehen. Wahrscheinlichkeiten fuer Stats:
	 * <li> Str und Dex -> 5%
	 * <li> Int und Wis -> 30%
	 * <li> Spd und Health -> 15%
	 */
	public void levelUp1Stat(){

		int i = rng.nextInt(100) + 1;
		if(i <= 5) changeStr(1);
		else if(i > 5 && i <= 35) changeInt(1);
		else if(i > 35 && i <= 50) changeSpd(1);
		else if(i > 50 && i <= 65) changeHealth(1);
		else if(i > 65 && i <= 95) changeWis(1);
		else changeDex(1);
	}
	
	/**
	 * Level Up-Methode. 5 Stat Punkte und folgende Wahrscheinlichkeiten einen Stat um 1 zu erhoehen:
	 * <li>Str und Dex -> 15%
	 * <li>Int und Wisdom -> 50%
	 * <li>Spd und Health -> 25%
	 * <li>Wenn kein Stat beim Level Up gestiegen -> zwei mal einen zufaelligen Stat mit levelUp1Stat() erhoehen.
	 */
	public void levelUp(){
		boolean check = false;
		StatPts+=5;
		int i = rng.nextInt(100) + 1;
		if(i <= 15) {changeStr(1); check = true;}
		i = rng.nextInt(100) + 1;
		if(i > 5 && i <= 55) {changeInt(1); check = true;}
		i = rng.nextInt(100) + 1;
		if(i > 25 && i <= 50) {changeSpd(1); check = true;}
		i = rng.nextInt(100) + 1;
		if(i > 40 && i <= 65) {changeHealth(1); check = true;}
		i = rng.nextInt(100) + 1;
		if(i > 45 && i <= 95) {changeWis(1); check = true;}
		i = rng.nextInt(100) + 1;
		if(i > 85) {changeDex(1); check = true;}
		
		if(!check) {
			levelUp1Stat();
			levelUp1Stat();
		}
	}
	
	public static void main(String[] args) {
		StatsMage test = new StatsMage();
		System.out.println("Level 1 Mage");
		test.printStats();
		System.out.println("Nach 10 Level Up");
		
		for(int z = 0; z < 10; z++) {
			test = new StatsMage();
			for(int i = 0; i < 10; i++) test.levelUp();
			test.printStats();
		}
	}
}
