package player;

public class StatsWarrior extends Stats{
	
	public StatsWarrior() {
		super(8, 2, 4, 8, 2, 4);
	}
	
	/**
	 * Einen zufaellig gewaehlten Stat erhoehen. Wahrscheinlichkeiten fuer Stats:
	 * <li> Str und Health -> 30%
	 * <li> Int und Wis -> 5%
	 * <li> Spd und Dex -> 15%
	 */
	public void levelUp1Stat(){
		java.util.Random r = new java.util.Random();
		int i = r.nextInt(100) + 1;
		if(i <= 30) Str++;
		else if(i > 30 && i <= 35) Int++;
		else if(i > 35 && i <= 50) Spd++;
		else if(i > 50 && i <= 80) Health++;
		else if(i > 80 && i <= 85) Wis++;
		else Dex++;
	}
	
	/**
	 * Level Up-Methode. 3 Stat Punkte und folgende Wahrscheinlichkeiten einen Stat um 1 zu erhoehen:
	 * <li>Str und Health -> 50%
	 * <li>Int und Wisdom -> 15%
	 * <li>Spd und Dex -> 25%
	 * <li>Wenn kein Stat beim Level Up gestiegen -> zwei mal einen zufaelligen Stat mit levelUp1Stat() erhoehen.
	 */
	public void levelUp(){
		boolean check = false;
		StatPts+=3;
		java.util.Random r = new java.util.Random();
		int i = r.nextInt(100) + 1;
		if(i <= 50) {Str++; check = true;}
		i = r.nextInt(100) + 1;
		if(i > 20 && i <= 35) {Int++; check = true;}
		i = r.nextInt(100) + 1;
		if(i > 25 && i <= 50) {Spd++; check = true;}
		i = r.nextInt(100) + 1;
		if(i > 40 && i <= 90) {Health++; check = true;}
		i = r.nextInt(100) + 1;
		if(i > 70 && i <= 85) {Wis++; check = true;}
		i = r.nextInt(100) + 1;
		if(i > 75) {Dex++; check = true;}

		if(!check) {
			levelUp1Stat();
			levelUp1Stat();
		}
	}
	
	
	
	
	public static void main(String[] args) {
		StatsWarrior test = new StatsWarrior();
		System.out.println("Level 1 Warrior");
		test.printStats();
		System.out.println("Nach 10 Level Up");
		
		for(int z = 0; z < 10; z++) {
			test = new StatsWarrior();
			for(int i = 0; i < 10; i++) test.levelUp();
			test.printStats();
		}
	}
}
