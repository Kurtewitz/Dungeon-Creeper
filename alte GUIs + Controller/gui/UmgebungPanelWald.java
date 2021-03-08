package gui;
import javax.imageio.*;
import java.awt.*;
import java.io.*;

@SuppressWarnings("serial")
public class UmgebungPanelWald extends UmgebungPanel{
	
	public UmgebungPanelWald() {
		try{
			umgebung = ImageIO.read(new File("baum.gif"));
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		
	}
		
	public void paintComponent(Graphics g) {
			drawRandWald(g);
			drawWald(g);
	}
	
	/**public void drawRandWald(Graphics g) {
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
		for(int i = Main.SIZE - objektSize * 2; i >= objektSize * 2; i-=objektSize) {
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
	*/
}
