package player;

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
		java.util.Random r = new java.util.Random();
		int i = r.nextInt(100) + 1;
		if(i <= 5) Str++;
		else if(i > 5 && i <= 35) Int++;
		else if(i > 35 && i <= 50) Spd++;
		else if(i > 50 && i <= 65) Health++;
		else if(i > 65 && i <= 95) Wis++;
		else Dex++;
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
		java.util.Random r = new java.util.Random();
		int i = r.nextInt(100) + 1;
		if(i <= 15) {Str++; check = true;}
		i = r.nextInt(100) + 1;
		if(i > 5 && i <= 55) {Int++; check = true;}
		i = r.nextInt(100) + 1;
		if(i > 25 && i <= 50) {Spd++; check = true;}
		i = r.nextInt(100) + 1;
		if(i > 40 && i <= 65) {Health++; check = true;}
		i = r.nextInt(100) + 1;
		if(i > 45 && i <= 95) {Wis++; check = true;}
		i = r.nextInt(100) + 1;
		if(i > 85) {Dex++; check = true;}
		
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
