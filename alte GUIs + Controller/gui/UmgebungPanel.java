package gui;
import javax.imageio.*;
import java.awt.image.*;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.Random;

@SuppressWarnings("serial")
public class UmgebungPanel extends JPanel{
	
	BufferedImage umgebung = null;
	private BufferedImage ribcage = null;
	private BufferedImage bones = null;
	private BufferedImage skull = null;
	
	boolean[][] objekt = new boolean[Main.SIZE][Main.SIZE];
	static int map = 1;
	public static final int objektSize = 30;
	public static final int grabSize = 60;
	
	public UmgebungPanel() {
		try{
			if(map == 1) umgebung = ImageIO.read(new File("baum.gif"));
			if(map == 2) {
				umgebung = ImageIO.read(new File("grab.gif"));
				ribcage = ImageIO.read(new File("ribcage.gif"));
				bones = ImageIO.read(new File("bones.gif"));
				skull = ImageIO.read(new File("skull.gif"));
			}
			
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public UmgebungPanel(int a) {
		setMap(a);
		new UmgebungPanel();
	}
	
	public void paintComponent(Graphics g) {
		if(map == 1) {
			drawRandWald(g);
			drawWald(g);
		}
		if(map == 2) {
			drawRandFriedhof(g);
			drawFriedhof(g);
		}
	}
	
	public void drawUmgebung(Graphics g, int x, int y) {
		g.drawImage(umgebung, x, y, null);
		setObjekt(x, y);
	}
	public void drawRandomBones(Graphics g, int x, int y) {
		Random r = new Random();
		int i = r.nextInt(3) + 1;
		if(i == 1) {g.drawImage(ribcage, x, y, null);}
		if(i == 2) {g.drawImage(skull, x, y, null);}
		if(i == 3) {g.drawImage(bones, x, y, null);}
		setObjekt(x, y);
	}
	
	public void drawGrab(Graphics g, int x, int y) {
		g.drawImage(umgebung, x, y, null);
		setObjekt(x, y);
		setObjekt(x + grabSize/2, y);
		setObjekt(x + grabSize/2, y + grabSize/2);
	}
	
	public void setObjekt(int x, int y) {
		//objekt[x][y] = true;
		for(int i = x; i < x + objektSize; i++) {
			for(int a = y; a < y + objektSize; a++) {
				objekt[i][a] = true;
			}
		}
	}
	
	
	
	public boolean checkObjekt(int x, int y) {
		return objekt[x][y];
	}
	
	public boolean checkObjekt(Point p) {
		return objekt[(int) p.getX()][(int) p.getY()];
	}
	
	public void setMap(int i) {
		map = i;
		/**try{
			if(i == 1) {
				umgebung = ImageIO.read(new File("baum.gif"));
			}
			if(i == 2) {
				umgebung = ImageIO.read(new File("grab.gif"));
				ribcage = ImageIO.read(new File("ribcage.gif"));
				bones = ImageIO.read(new File("bones.gif"));
				skull = ImageIO.read(new File("skull.gif"));
			}
			
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		*/
	}
	
	public void drawRandWald(Graphics g) {
		
		for(int i = 0; i <= Main.SIZE - objektSize; i+=objektSize) {
			drawUmgebung(g, i, 0);
			
		}
		for(int i = 0; i < Main.SIZE - objektSize; i+=objektSize) {
			drawUmgebung(g, 0, i);
		}
		for(int i = 0; i <= Main.SIZE - objektSize; i+=objektSize) {
			drawUmgebung(g, i, Main.SIZE - objektSize);
		}
		for(int i = objektSize + FunctionPanel.objektSize; i < Main.SIZE - objektSize; i+=objektSize) {
			drawUmgebung(g, Main.SIZE - objektSize, i);
		}
	}
	public void drawWald(Graphics g) {
		for(int i = Main.SIZE - objektSize*3 ; i >= objektSize * 2; i-=objektSize) {
			drawUmgebung(g, 2*objektSize, i);
		}
		for(int i = 2*objektSize; i <= Main.SIZE - 3*objektSize; i+=objektSize) {
			drawUmgebung(g, 4*objektSize, i);
		}
		for(int i = 5*objektSize; i <= Main.SIZE - 2*objektSize; i+=objektSize) {
			drawUmgebung(g, i, 2*objektSize);
		}
		for(int i = 3*objektSize; i <= Main.SIZE - 3*objektSize; i+=objektSize) {
			drawUmgebung(g, 6*objektSize, i);
		}
	}

	public void drawRandFriedhof(Graphics g) {
		for(int i = 0; i <= Main.SIZE - objektSize; i+=objektSize) {
			drawRandomBones(g, Main.SIZE - objektSize, i);
		}
		for(int i = objektSize + FunctionPanel.objektSize; i < Main.SIZE - objektSize; i+=objektSize) {
			drawRandomBones(g, 0, i);
		}
		for(int i = 0; i <= Main.SIZE - objektSize; i+=objektSize) {
			drawRandomBones(g, i, 0);
		}
		for(int i = 0; i < Main.SIZE - objektSize; i+=objektSize) {
			drawRandomBones(g, i, Main.SIZE - objektSize);
		}
	}
	
	public void drawFriedhof(Graphics g) {
		
		drawGrab(g, Main.SIZE - objektSize - grabSize, Main.SIZE - objektSize - grabSize);
		drawGrab(g, objektSize, Main.SIZE - objektSize - grabSize);
		for(int i = Main.SIZE - 5 * objektSize; i < Main.SIZE - objektSize; i+=2*objektSize) {
			drawGrab(g, i, objektSize);
		}
		for(int i = objektSize; i < 5 * objektSize; i+= objektSize) {
			drawRandomBones(g, Main.SIZE - 6 * objektSize, i);
		}
		for(int i = Main.SIZE - 6 * objektSize; i < Main.SIZE - 2 * objektSize; i+=objektSize) {
			drawRandomBones(g, i, 4 * objektSize);
		}
		for(int i = objektSize + grabSize; i < Main.SIZE - grabSize - 2 * objektSize; i +=objektSize) {
			drawRandomBones(g, i, Main.SIZE - 3*objektSize);
		}
	}
}
