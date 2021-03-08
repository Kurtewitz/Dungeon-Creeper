package gui;

import java.awt.Point;

public class SteuerungShop {

	private ShopFrame laden;
	private int auswahl = 3;
	
	public SteuerungShop(ShopFrame sf) {
		laden = sf;
	}
	
	public int movePfeil() {
		char c = Main.ask();
		if(c == 'w') {movePfeilNorth(); return 0;}
		if(c == 's') {movePfeilSouth(); return 0;}
		if(c == 'a') {movePfeilWest(); return 0;}
		if(c == 'd') {movePfeilEast(); return 0;}
		if(c == 'e') return auswahl;
		if(c == 'x') return 20;
		else return movePfeil();
	}
	
	public void setAuswahl(Point p) {
		if(laden.function().checkExit(p)) auswahl =  1;
		else if(laden.function().checkBuy(p)) auswahl = 2;
		else if(laden.function().checkSell(p)) auswahl = 3;
		else if(laden.function().checkRepair(p)) auswahl = 4;
	}
	
	public void movePfeilNorth() {
    	Point p = new Point(laden.pfeil().getX(), laden.pfeil().getY() - ShopMenu.objektHight);
    	int x = laden.pfeil().getX();
    	int y = laden.pfeil().getY();
    	//if(!laden.function().checkObjekt(p)) {
    	//	laden.pfeil().setBounds(x, y + ShopMenu.objektHight, ShopMenuPfeil.pfeilSize, ShopMenuPfeil.pfeilSize);
    	//}
    	//else 
    	if((p.getY() >= 0) && (laden.function().checkObjekt(p)))	{
    		laden.pfeil().setBounds(x, y - ShopMenu.objektHight, ShopMenuPfeil.pfeilSize, ShopMenuPfeil.pfeilSize);
    		setAuswahl(p);
    	}
    }
    public void movePfeilSouth() {
    	Point p = new Point(laden.pfeil().getX(), laden.pfeil().getY() + ShopMenu.objektHight);
    	int x = laden.pfeil().getX();
    	int y = laden.pfeil().getY();
    	if((p.getY() < Main.shopSIZE) && (laden.function().checkObjekt(p))) {
    		laden.pfeil().setBounds(x, y + ShopMenu.objektHight, ShopMenuPfeil.pfeilSize, ShopMenuPfeil.pfeilSize);
    		setAuswahl(p);
    	}
    }
    public void movePfeilWest() {
    	Point p = new Point(laden.pfeil().getX() - ShopMenu.objektWidth, laden.pfeil().getY());
    	int x = laden.pfeil().getX();
    	int y = laden.pfeil().getY();
    	if((p.getX() >= 0) && (laden.function().checkObjekt(p))) {
    		laden.pfeil().setBounds(x - ShopMenu.objektWidth, y, ShopMenuPfeil.pfeilSize, ShopMenuPfeil.pfeilSize);
    		setAuswahl(p);
    	}
    }
    public void movePfeilEast() {
    	Point p = new Point(laden.pfeil().getX() + ShopMenu.objektWidth, laden.pfeil().getY());
    	int x = laden.pfeil().getX();
    	int y = laden.pfeil().getY();
    	if((p.getX() < Main.shopSIZE) && (laden.function().checkObjekt(p))) {
    		laden.pfeil().setBounds(x + ShopMenu.objektWidth, y, ShopMenuPfeil.pfeilSize, ShopMenuPfeil.pfeilSize);
    		setAuswahl(p);
    	}
    }
}
