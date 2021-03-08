package gui;
import javax.imageio.*;
import java.awt.image.*;
import javax.swing.*;
import java.awt.*;
import java.io.*;

@SuppressWarnings("serial")
public class FunctionPanel extends JPanel{
	
	BufferedImage portal = null;
	private BufferedImage shop = null;
	private boolean[][] objekt = new boolean[Main.SIZE][Main.SIZE];
	private boolean[][] isAPortal = new boolean[Main.SIZE][Main.SIZE];
	private boolean[][] isAShop = new boolean[Main.SIZE][Main.SIZE];
	static int map = 1;
	public static final int objektSize = 30;
	
	public FunctionPanel() {
		//objekt = new boolean[Main.SIZE][Main.SIZE];
		//isAPortal = new boolean[Main.SIZE][Main.SIZE];
		//isAShop = new boolean[Main.SIZE][Main.SIZE];
		try{
			if(map == 1) portal = ImageIO.read(new File("portal_blitze_nachrechts.gif"));
			if(map == 2) portal = ImageIO.read(new File("portal_blitze_nachlinks_grab.gif"));
			shop = ImageIO.read(new File("shop.gif"));
			
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		if(map == 1) {
			deleteAllObjekt();
			setObjekt(Main.SIZE - objektSize, UmgebungPanel.objektSize, true, false);
			setObjekt(Main.SIZE - UmgebungPanel.objektSize - objektSize, 2* UmgebungPanel.objektSize + objektSize, false, true);
		}
		if(map == 2) {
			deleteAllObjekt();
			setObjekt(0, UmgebungPanel.objektSize, true, false);
		}
		
		
	}
	
	public FunctionPanel(int a) {
		setMap(a);
		new FunctionPanel();
	}
	
	public boolean checkObjekt(int x, int y) {
		return objekt[x][y];
	}
	
	public boolean checkObjekt(Point p) {
		return objekt[(int) p.getX()][(int) p.getY()];
	}
	
	public boolean checkPortal(int x, int y) {
		return isAPortal[x][y];
	}
	
	public boolean checkPortal(Point p) {
		return isAPortal[(int) p.getX()][(int) p.getY()];
	}
	
	public boolean checkShop(int x, int y) {
		return isAShop[x][y];
	}
	
	public boolean checkShop(Point p) {
		return isAShop[(int) p.getX()][(int) p.getY()];
	}
	
	public void setObjekt(int x, int y, boolean portal, boolean shop) {
		for(int i = x; i < x + objektSize; i++) {
			for(int a = y; a < y + objektSize; a++) {
				objekt[i][a] = true;
			}
		}
		if(portal) {
			for(int i = x; i < x + objektSize; i++) {
				for(int a = y; a < y + objektSize; a++) {
					isAPortal[i][a] = true;
				}
			}
		}
		if(shop) {
			for(int i = x; i < x + objektSize; i++) {
				for(int a = y; a < y + objektSize; a++) {
					isAShop[i][a] = true;
				}
			}
		}
	}
	
	public void deleteObjekt(int x, int y) {
		for(int i = x; i < x + objektSize; i++) {
			for(int a = y; a < y + objektSize; a++) {
				objekt[i][a] = false;
			}
		}
		for(int i = x; i < x + objektSize; i++) {
			for(int a = y; a < y + objektSize; a++) {
				isAShop[i][a] = false;
			}
		}
		for(int i = x; i < x + objektSize; i++) {
			for(int a = y; a < y + objektSize; a++) {
				isAPortal[i][a] = false;
			}
		}
	}

	public void deleteAllObjekt() {
		for(int i = 0; i < Main.SIZE; i++) {
			for(int a = 0; a < Main.SIZE; a++) {
				objekt[i][a] = false;
			}
		}
		for(int i = 0; i < Main.SIZE; i++) {
			for(int a = 0; a < Main.SIZE; a++) {
				isAShop[i][a] = false;
			}
		}
		for(int i = 0; i < Main.SIZE; i++) {
			for(int a = 0; a < Main.SIZE; a++) {
				isAPortal[i][a] = false;
			}
		}
	}
	
	public void setMap(int i) {
		map = i;
		/**try{
			if(i == 1) portal = ImageIO.read(new File("portal_blitze_nachrechts.gif"));
			if(i == 2) portal = ImageIO.read(new File("portal_blitze_nachlinks_grab.gif"));
			shop = ImageIO.read(new File("shop.gif"));
			
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		*/
		
		if(i == 1) {
			deleteObjekt(0, UmgebungPanel.objektSize);
			setObjekt(Main.SIZE - objektSize, UmgebungPanel.objektSize, true, false);
			setObjekt(Main.SIZE - UmgebungPanel.objektSize - objektSize, 2* UmgebungPanel.objektSize + objektSize, false, true);
		}
		if(i == 2) {
			deleteObjekt(Main.SIZE - objektSize, UmgebungPanel.objektSize);
			deleteObjekt(Main.SIZE - UmgebungPanel.objektSize - objektSize, 2* UmgebungPanel.objektSize + objektSize);
			setObjekt(0, UmgebungPanel.objektSize, true, false);
		}
		
	}
	
	public void paintComponent(Graphics g) {
		if(map == 1) g.drawImage(portal, Main.SIZE - UmgebungPanel.objektSize, UmgebungPanel.objektSize, null);
		if(map == 2) g.drawImage(portal,  0,  UmgebungPanel.objektSize,  null);
		if(map == 1) g.drawImage(shop, Main.SIZE - UmgebungPanel.objektSize - objektSize, 2* UmgebungPanel.objektSize + objektSize, null);
		
	}
}
