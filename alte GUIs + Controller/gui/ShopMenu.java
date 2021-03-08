package gui;

import java.io.*;
import java.awt.*;
import javax.swing.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

@SuppressWarnings("serial")
public class ShopMenu extends JPanel {

	private BufferedImage sell = null;
	private BufferedImage repair = null;
	private BufferedImage buy = null;
	private BufferedImage exit = null;
	
	public static final int objektWidth = 350;
	public static final int objektHight = 100;
	
	
	private boolean[][] objekt;
	private boolean[][] isExit;
	private boolean[][] isBuy;
	private boolean[][] isRepair;
	private boolean[][] isSell;
	
	
	public ShopMenu() {
		
		objekt = new boolean[Main.shopSIZE][Main.shopSIZE];
		isBuy = new boolean[Main.shopSIZE][Main.shopSIZE];
		isExit = new boolean[Main.shopSIZE][Main.shopSIZE];
		isSell = new boolean[Main.shopSIZE][Main.shopSIZE];
		isRepair = new boolean[Main.shopSIZE][Main.shopSIZE];
		
		try{
			exit = ImageIO.read(new File("verlassen.gif"));
			buy = ImageIO.read(new File("kaufen.gif"));
			sell = ImageIO.read(new File("verkaufen.gif"));
			repair = ImageIO.read(new File("reparieren.gif"));
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		setObjekt(0, Main.shopSIZE - 2 * objektHight, true, false, false, false);
		setObjekt(objektWidth, Main.shopSIZE - 2 * objektHight, false, true, false, false);
		setObjekt(0, Main.shopSIZE - objektHight, false, false, true, false);
		setObjekt(objektWidth, Main.shopSIZE - objektHight, false, false, false, true);
	}
	
	public boolean checkObjekt(int x, int y) {
		return objekt[x][y];
	}
	public boolean checkObjekt(Point p) {
		return objekt[(int) p.getX()][(int) p.getY()];
	}
	public boolean checkExit(int x, int y) {
		return isExit[x][y];
	}
	public boolean checkExit(Point p) {
		return isExit[(int) p.getX()][(int) p.getY()];
	}
	public boolean checkBuy(int x, int y) {
		return isBuy[x][y];
	}
	public boolean checkBuy(Point p) {
		return isBuy[(int) p.getX()][(int) p.getY()];
	}
	public boolean checkSell(int x, int y) {
		return isSell[x][y];
	}
	public boolean checkSell(Point p) {
		return isSell[(int) p.getX()][(int) p.getY()];
	}
	public boolean checkRepair(int x, int y) {
		return isRepair[x][y];
	}
	public boolean checkRepair(Point p) {
		return isRepair[(int) p.getX()][(int) p.getY()];
	}

	public void setObjekt(int x, int y, 
			boolean portal, 
			boolean shop, 
			boolean repair, 
			boolean sell) {
		for(int i = x; i < x + objektWidth; i++) {
			for(int a = y; a < y + objektHight; a++) {
				objekt[i][a] = true;
			}
		}
		if(portal) {
			for(int i = x; i < x + objektWidth; i++) {
				for(int a = y; a < y + objektHight; a++) {
					isExit[i][a] = true;
				}
			}
		}
		if(shop) {
			for(int i = x; i < x + objektWidth; i++) {
				for(int a = y; a < y + objektHight; a++) {
					isBuy[i][a] = true;
				}
			}
		}
		if(repair) {
			for(int i = x; i < x + objektWidth; i++) {
				for(int a = y; a < y + objektHight; a++) {
					isRepair[i][a] = true;
				}
			}
		}
		if(sell) {
			for(int i = x; i < x + objektWidth; i++) {
				for(int a = y; a < y + objektHight; a++) {
					isSell[i][a] = true;
				}
			}
		}
	}

	/**public void deleteObjekt(int x, int y) {
		for(int i = x; i < x + objektWidth; i++) {
			for(int a = y; a < y + objektHight; a++) {
				objekt[i][a] = false;
			}
		}
		for(int i = x; i < x + objektWidth; i++) {
			for(int a = y; a < y + objektHight; a++) {
				isAShop[i][a] = false;
			}
		}
		for(int i = x; i < x + objektWidth; i++) {
			for(int a = y; a < y + objektHight; a++) {
				isAPortal[i][a] = false;
			}
		}
		for(int i = x; i < x + objektWidth; i++) {
			for(int a = y; a < y + objektHight; a++) {
				isRepair[i][a] = false;
			}
		}
		for(int i = x; i < x + objektWidth; i++) {
			for(int a = y; a < y + objektHight; a++) {
				isSell[i][a] = false;
			}
		}
	}
	*/
	
	public void drawShopMenu(Graphics g) {
		g.drawImage(exit, 0, Main.shopSIZE - 2 * objektHight, null);
		setObjekt(0, Main.shopSIZE - 2 * objektHight, true, false, false, false);
		g.drawImage(buy, objektWidth, Main.shopSIZE - 2 * objektHight, null);
		setObjekt(objektWidth, Main.shopSIZE - 2 * objektHight, false, true, false, false);
		g.drawImage(repair, 0, Main.shopSIZE - objektHight, null);
		setObjekt(0, Main.shopSIZE - objektHight, false, false, true, false);
		g.drawImage(sell, objektWidth, Main.shopSIZE - objektHight, null);
		setObjekt(objektWidth, Main.shopSIZE - objektHight, false, false, false, true);
	}
	
	public void paintComponent(Graphics g) {
		g.drawImage(exit, 0, Main.shopSIZE - 2 * objektHight, null);
		g.drawImage(buy, objektWidth, Main.shopSIZE - 2 * objektHight, null);
		g.drawImage(repair, 0, Main.shopSIZE - objektHight, null);
		g.drawImage(sell, objektWidth, Main.shopSIZE - objektHight, null);
	}
	
	public static void main(String[] args) {
		new ShopMenu();
	}
}
