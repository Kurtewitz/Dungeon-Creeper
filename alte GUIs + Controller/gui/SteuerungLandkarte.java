package gui;

import java.awt.Point;

public class SteuerungLandkarte {

	private WeltFrame landkarte;
	
	public SteuerungLandkarte(WeltFrame mf) {
		landkarte = mf;
	}
	
	public int moveHero() {
		char c = Main.ask();
		if(c == 'w') return moveHeroNorth();
		if(c == 's') return moveHeroSouth();
		if(c == 'a') return moveHeroWest();
		if(c == 'd') return moveHeroEast();
		if(c == 'x') return 10;
		else return moveHero();
	}
	
	public int moveHeroNorth() {
    	Point p1 = new Point(landkarte.hero().getX(), landkarte.hero().getY());
    	Point p2 = new Point(landkarte.hero().getX(), landkarte.hero().getY() - HeroPanel.heroSize);
    	int x = landkarte.hero().getX();
    	int y = landkarte.hero().getY();
    	if(landkarte.function().checkObjekt(p2)) {
    		if(landkarte.function().checkPortal(p2)) {
    			System.out.println("Map " + (WeltFrame.map+1));
    			landkarte.changeMap(WeltFrame.map + 1);
    			return 1;
    		}
    		if(landkarte.function().checkShop(p2)) {
    			System.out.println("Du hast den Shop betreten");
    			//shop();
    			return 2;
    		}
    	}
    	if(landkarte.umgebung().checkObjekt(p2)) {
    		System.out.println(p1);
    		return 0;
    	}
    	landkarte.hero().setBounds(x, y - HeroPanel.heroSize, HeroPanel.heroSize, HeroPanel.heroSize);
    	System.out.println(p2);
    	return 0;
    }
    public int moveHeroSouth() {
    	Point p1 = new Point(landkarte.hero().getX(), landkarte.hero().getY());
    	Point p2 = new Point(landkarte.hero().getX(), landkarte.hero().getY() + HeroPanel.heroSize);
    	int x = landkarte.hero().getX();
    	int y = landkarte.hero().getY();
    	if(landkarte.function().checkObjekt(p2)) {
    		if(landkarte.function().checkPortal(p2)) {
    			System.out.println("Map " + (WeltFrame.map-1));
    			landkarte.changeMap(WeltFrame.map - 1);
    			return 1;
    		}
    		if(landkarte.function().checkShop(p2)) {
    			System.out.println("Du hast den Shop betreten");
    			//shop();
    			return 2;
    		}
    	}
    	if(landkarte.umgebung().checkObjekt(p2)) {
    		System.out.println(p1);
    		return 0;
    	}
    	landkarte.hero().setBounds(x, y + HeroPanel.heroSize, HeroPanel.heroSize, HeroPanel.heroSize);
    	System.out.println(p2);
    	return 0;
    }
    public int moveHeroWest() {
    	Point p1 = new Point(landkarte.hero().getX(), landkarte.hero().getY());
    	Point p2 = new Point(landkarte.hero().getX() - HeroPanel.heroSize, landkarte.hero().getY());
    	int x = landkarte.hero().getX();
    	int y = landkarte.hero().getY();
    	if(landkarte.function().checkObjekt(p2)) {
    		if(landkarte.function().checkPortal(p2)) {
    			System.out.println("Map " + (WeltFrame.map-1));
    			landkarte.changeMap(WeltFrame.map - 1);
    			return 1;
    		}
    		if(landkarte.function().checkShop(p2)) {
    			System.out.println("Du hast den Shop betreten");
    			//shop();
    			return 2;
    		}
    	}
    	if(landkarte.umgebung().checkObjekt(x - HeroPanel.heroSize, y)) {
    		System.out.println(p1);
    		return 0;
    	}
    	landkarte.hero().setBounds(x - HeroPanel.heroSize, y, HeroPanel.heroSize, HeroPanel.heroSize);
    	System.out.println(p2);
    	return 0;
    }
    public int moveHeroEast() {
    	Point p1 = new Point(landkarte.hero().getX(), landkarte.hero().getY());
    	Point p2 = new Point(landkarte.hero().getX() + HeroPanel.heroSize, landkarte.hero().getY());
    	int x = landkarte.hero().getX();
    	int y = landkarte.hero().getY();
    	if(landkarte.function().checkObjekt(p2)) {
    		if(landkarte.function().checkPortal(p2)) {
    			System.out.println("Map " + (WeltFrame.map+1));
    			landkarte.changeMap(WeltFrame.map + 1);
    			return 1;
    		}
    		if(landkarte.function().checkShop(p2)) {
    			System.out.println("Du hast den Shop betreten");
    			//shop();
    			return 2;
    		}
    	}
    	if(landkarte.umgebung().checkObjekt(p2)) {
    		System.out.println(p1);
    		return 0;
    	}
    	landkarte.hero().setBounds(x + HeroPanel.heroSize, y, HeroPanel.heroSize, HeroPanel.heroSize);
    	System.out.println(p2);
    	return 0;
    }
}
