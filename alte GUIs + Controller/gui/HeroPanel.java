package gui;
import javax.imageio.*;
import java.awt.image.*;
import javax.swing.*;
import java.awt.*;
import java.io.*;

@SuppressWarnings("serial")
public class HeroPanel extends JPanel{
	
	private BufferedImage hero = null;
	public static final int heroSize = 30;
	
	public HeroPanel() {
		try{
			hero = ImageIO.read(new File("hero.gif"));
			
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		setMaximumSize(new Dimension(heroSize,heroSize));
	}
	
	public void paintComponent(Graphics g) {
		g.drawImage(hero,0,0,null);
	}
}
