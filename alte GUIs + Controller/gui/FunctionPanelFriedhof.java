package gui;
import javax.imageio.*;
//import java.awt.image.*;
import java.awt.*;
import java.io.*;

@SuppressWarnings("serial")
public class FunctionPanelFriedhof extends FunctionPanel{
	
	//private BufferedImage shop = null;
	
	public FunctionPanelFriedhof() {
		try{
			portal = ImageIO.read(new File("portal_blitze_nachlinks_grab.gif"));
			//shop = ImageIO.read(new File("shop.gif"));
			
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		deleteAllObjekt();
		setObjekt(0, UmgebungPanel.objektSize, true, false);
	}
	
	public void paintComponent(Graphics g) {
		g.drawImage(portal,  0,  UmgebungPanel.objektSize,  null);
	}
}
