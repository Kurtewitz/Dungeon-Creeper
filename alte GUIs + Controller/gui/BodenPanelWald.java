package gui;
import javax.imageio.*;
import java.awt.*;
import java.io.*;

@SuppressWarnings("serial")
public class BodenPanelWald extends BodenPanel{
	
	public BodenPanelWald() {
		try{
			boden = ImageIO.read(new File("boden1.gif"));
			
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void paintComponent(Graphics g) {
		g.drawImage(boden, 0, 0, null);
	}
}
