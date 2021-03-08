package gui;
import javax.imageio.*;
import java.awt.image.*;
import javax.swing.*;
import java.awt.*;
import java.io.*;

@SuppressWarnings("serial")
public class BodenPanel extends JPanel{
	BufferedImage boden = null;
	static int map = 1;
	
	public BodenPanel() {
		try{
			if(map == 1) boden = ImageIO.read(new File("boden1.gif"));
			if(map == 2) boden = ImageIO.read(new File("boden2.gif"));
			
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public BodenPanel(int a) {
		setMap(a);
		new BodenPanel();
	}
	
	public void setMap(int i) {
		map = i;
		try{
			if(i == 1) boden = ImageIO.read(new File("boden1.gif"));
			if(i == 2) boden = ImageIO.read(new File("boden2.gif"));
			
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void paintComponent(Graphics g) {
		g.drawImage(boden, 0, 0, null);
	}
}
