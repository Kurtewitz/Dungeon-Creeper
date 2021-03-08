package gui;

public class Main{
	
	public static final int SIZE = 270;
	public static final int shopSIZE = 700;
    
	private ShopFrame sf;
	private WeltFrame wf;
    
    public Main() {
    	sf = new ShopFrame();
    	wf = new WeltFrame();
    }
    
    
    static char ask() {
		String s = javax.swing.JOptionPane.showInputDialog(null,"Welche Richtung (w/s/a/d)?  X = beenden");
		if (s == null || s.length() == 0) return ' ';
		return s.charAt(0);
	}
    
    public int playerMove() {
    	return wf.steuerung().moveHero();
    }
    
    public int menuAuswahl() {
    	return sf.steuerung().movePfeil();
    }
    
    public void enterShop() {
    	wf.frame().setVisible(false);
    	if(sf != null) sf.frame().setVisible(true);
    	else sf = new ShopFrame();
    }
    
    public void exitShop() {
    	sf.frame().setVisible(false);
    	if(wf != null) {
    		wf.frame().setVisible(true);
    	}
    	else {
    		wf = new WeltFrame();
    	}
    	//sf.schliessen();
    }
    
    public int spielenKarte() {
    	while(playerMove() != 10) {
    		if (playerMove() == 2) {   //Das fuehrt wahrscheinlich zu Problemen. Zwei abfragen.
				enterShop();
				return spielenShop();
			}
    	}
    	return 1;
    }
    public int spielenShop() {
    	while(menuAuswahl() != 1) {}
   		exitShop();
   		return spielenKarte();
    }
    
    public void spielen() {
    	while(spielenKarte() != 1) {}
   		sf.schliessen();
   		//lf.schliessen();
    }
    
    public static void main(String[] args) {
    	Main m = new Main();
   		m.spielen();
    }
}
