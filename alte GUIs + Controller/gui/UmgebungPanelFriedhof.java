package gui;
import javax.imageio.*;

import java.awt.image.*;
import java.awt.*;
import java.io.*;
import java.util.Random;

@SuppressWarnings("serial")
public class UmgebungPanelFriedhof extends UmgebungPanel{
	private BufferedImage ribcage = null;
	private BufferedImage bones = null;
	private BufferedImage skull = null;
	
	public UmgebungPanelFriedhof() {
		try{
			umgebung = ImageIO.read(new File("grab.gif"));
			ribcage = ImageIO.read(new File("ribcage.gif"));
			bones = ImageIO.read(new File("bones.gif"));
			skull = ImageIO.read(new File("skull.gif"));
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		
	}
		
	public void paintComponent(Graphics g) {
		drawRandFriedhof(g);
		drawFriedhof(g);
	}
	
	public void drawRandomBones(Graphics g, int x, int y) {
		Random r = new Random();
		int i = r.nextInt(3) + 1;
		if(i == 1) {g.drawImage(ribcage, x, y, null);}
		if(i == 2) {g.drawImage(skull, x, y, null);}
		if(i == 3) {g.drawImage(bones, x, y, null);}
		setObjekt(x, y);
	}
	
	/**public void drawGrab(Graphics g, int x, int y) {
		g.drawImage(umgebung, x, y, null);
		setObjekt(x, y);
		setObjekt(x + grabSize/2, y);
		setObjekt(x + grabSize/2, y + grabSize/2);
	}
	*/
}
