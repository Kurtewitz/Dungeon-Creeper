package player.stats;

public class StatsThief extends Stats{
	public StatsThief() {
		super(4,3,7,5,2,7);
	}
	
	/**
	 * Einen zufaellig gewaehlten Stat erhoehen. Wahrscheinlichkeiten fuer Stats:
	 * <li> Str und Health -> 15%
	 * <li> Int und Wis -> 5%
	 * <li> Spd und Dex -> 30%
	 */
	public void levelUp1Stat(){
		java.util.Random r = new java.util.Random();
		int i = r.nextInt(100) + 1;
		if(i <= 15) changeStr(1);
		else if(i > 15 && i <= 20) changeInt(1);
		else if(i > 20 && i <= 50) changeSpd(1);
		else if(i > 50 && i <= 65) changeHealth(1);
		else if(i > 65 && i <= 70) changeWis(1);
		else changeDex(1);
	}
	
	
	/**
	 * Level Up-Methode. 5 Stat Punkte und folgende Wahrscheinlichkeiten einen Stat um 1 zu erhoehen:
	 * <li>Str und Health -> 25%
	 * <li>Int und Wisdom -> 15%
	 * <li>Spd und Dex -> 50%
	 * <li>Wenn kein Stat beim Level Up gestiegen -> zwei mal einen zufaelligen Stat mit levelUp1Stat() erhoehen.
	 */
	public void levelUp(){
		boolean check = false;
		StatPts+=5;
		java.util.Random r = new java.util.Random();
		int i = r.nextInt(100) + 1;
		if(i <= 25) {changeStr(1); check = true;}
		i = r.nextInt(100) + 1;
		if(i > 5 && i <= 20) {changeInt(1); check = true;}
		i = r.nextInt(100) + 1;
		if(i > 10 && i <= 60) {changeSpd(1); check = true;}
		i = r.nextInt(100) + 1;
		if(i > 40 && i <= 65) {changeHealth(1); check = true;}
		i = r.nextInt(100) + 1;
		if(i > 55 && i <= 70) {changeWis(1); check = true;}
		i = r.nextInt(100) + 1;
		if(i > 50) {changeDex(1); check = true;}

		if(!check) {
			levelUp1Stat();
			levelUp1Stat();
		}
	}
	
	public static void main(String[] args) {
		StatsThief test = new StatsThief();
		System.out.println("Level 1 thief");
		test.printStats();
		System.out.println("Nach 10 Level Up");
		
		for(int z = 0; z < 10; z++) {
			test = new StatsThief();
			for(int i = 0; i < 10; i++) test.levelUp();
			test.printStats();
		}
	}
}
