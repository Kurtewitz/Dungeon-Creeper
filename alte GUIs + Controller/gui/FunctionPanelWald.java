package gui;
import javax.imageio.*;
import java.awt.image.*;
import java.awt.*;
import java.io.*;

@SuppressWarnings("serial")
public class FunctionPanelWald extends FunctionPanel{
	
	private BufferedImage shop = null;

	public FunctionPanelWald() {
		try{
			portal = ImageIO.read(new File("portal_blitze_nachrechts.gif"));
			shop = ImageIO.read(new File("shop.gif"));
			
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		deleteAllObjekt();
		setObjekt(Main.SIZE - objektSize, UmgebungPanel.objektSize, true, false);
		setObjekt(Main.SIZE - UmgebungPanel.objektSize - objektSize, 2* UmgebungPanel.objektSize + objektSize, false, true);
	}
	
	public void paintComponent(Graphics g) {
		g.drawImage(portal, Main.SIZE - UmgebungPanel.objektSize, UmgebungPanel.objektSize, null);
		g.drawImage(shop, Main.SIZE - UmgebungPanel.objektSize - objektSize, 2* UmgebungPanel.objektSize + objektSize, null);
		
	}
}
