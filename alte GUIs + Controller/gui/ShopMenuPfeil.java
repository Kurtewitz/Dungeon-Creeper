package gui;
import java.awt.*;
import java.awt.image.*;
import java.io.*;
import javax.swing.*;
import javax.imageio.ImageIO;

@SuppressWarnings("serial")
public class ShopMenuPfeil extends JPanel {

	public static final int pfeilSize = 50;
	private BufferedImage pfeil;
	
	public ShopMenuPfeil() {
		try{
			pfeil = ImageIO.read(new File("ShopMenuPfeil.gif"));
			
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		setMaximumSize(new Dimension(pfeilSize,pfeilSize));
	}
	
	public void paintComponent(Graphics g) {
		g.drawImage(pfeil,0,0,null);
	}
}
