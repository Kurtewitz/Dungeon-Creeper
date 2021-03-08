package gui;

import java.awt.Graphics;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

@SuppressWarnings("serial")
public class ShopHintergrund extends BodenPanel {

	public ShopHintergrund() {
		try{
			boden = ImageIO.read(new File("shopInside.gif"));
			
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void paintComponent(Graphics g) {
		g.drawImage(boden, 0, 0, null);
	}
}
