package gui;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

@SuppressWarnings("serial")
public class ShopFrame extends JFrame {

	private JFrame frame = new JFrame("Dungeon Creeper Shop");
    private JLayeredPane lpane;
    private ShopHintergrund hintergrund;
    private ShopMenuPfeil pfeil;
    private ShopMenu function;
    private SteuerungShop steuerung;
    
	public ShopFrame() {
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		lpane = new JLayeredPane();
		hintergrund = new ShopHintergrund();
		function = new ShopMenu();
		pfeil = new ShopMenuPfeil();
		steuerung = new SteuerungShop(this);
		
		frame.setPreferredSize(new Dimension(Main.shopSIZE + 10,Main.shopSIZE + 10));
        frame.setLayout(new BorderLayout());
        frame.add(lpane, BorderLayout.CENTER);
        lpane.setBounds(0, 0, Main.shopSIZE + 10, Main.shopSIZE + 38);
        
        hintergrund.setBounds(0, 0, Main.shopSIZE + 10, Main.shopSIZE + 10);
        //function.setBounds(0 , Main.shopSIZE - 2 * ShopMenu.objektHight, Main.shopSIZE + 10, Main.shopSIZE + 38);
        function.setBounds(0,0, Main.shopSIZE + 10, Main.shopSIZE + 38);
        pfeil.setBounds(Main.shopSIZE - ShopMenuPfeil.pfeilSize - ShopMenu.objektWidth/7 - 70, Main.shopSIZE - ShopMenu.objektHight/2 - ShopMenuPfeil.pfeilSize + 10, ShopMenuPfeil.pfeilSize, ShopMenuPfeil.pfeilSize);
	
        lpane.add(hintergrund, new Integer(0), 0);
        lpane.add(function, new Integer(0), 0);
        lpane.add(pfeil, new Integer(0), 0);
        frame.pack();
        //frame.setVisible(true);
	
	}

	public JFrame frame() {
    	return frame;
    }
    public JLayeredPane lpane() {
    	return lpane;
    }
    public ShopHintergrund hintergrund(){
    	return hintergrund;
    }
    public ShopMenuPfeil pfeil() {
    	return pfeil;
    }
    public ShopMenu function() {
    	return function;
    }
    public SteuerungShop steuerung() {
    	return steuerung;
    }

    public void schliessen() {
        WindowEvent wev = new WindowEvent(this, WindowEvent.WINDOW_CLOSING);
        Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(wev);
    }
}
