package guiVersuch2;

import java.awt.Insets;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;

import welt.Model;

@SuppressWarnings("serial")
public abstract class BaseButton extends JButton {

	protected Icon icon;
	protected Icon iconPressed;

	/**
	 * Konstruktor, laesst readIcon() die richtigen Icons rauslesen,
	 * schneidet den BaseButton auf die Icongroesse zu
	 */
	public BaseButton(String s) {
		super();
		readIcon(s);
		setIcon(icon);
		setContentAreaFilled(false);
		setMargin(new Insets(0,0,0,0));
		setBorder(null);
		
	}
	/**
	 * Uses the string parameter and MainFrame.mapnr to load the right icons for the button. Scales the icon accordingly
	 * @param s String representing the type of the button this Icon is supposed to be read for
	 */
	public void readIcon(String s) {
		if(s == "ground") {
			icon = new ImageIcon(((new ImageIcon("images/boden" + MainFrame.mapnr + ".gif")).getImage()).getScaledInstance(MainFrame.screenWidth / MainFrame.MAP_BUTTONS_X, MainFrame.screenHeight / MainFrame.MAP_BUTTONS_Y, java.awt.Image.SCALE_SMOOTH));
			iconPressed = new ImageIcon(((new ImageIcon("images/boden" + MainFrame.mapnr + "Pressed.gif")).getImage()).getScaledInstance(MainFrame.screenWidth / MainFrame.MAP_BUTTONS_X, MainFrame.screenHeight / MainFrame.MAP_BUTTONS_Y, java.awt.Image.SCALE_SMOOTH));
		}
		if(s == "surrounding") {
			icon = new ImageIcon(((new ImageIcon("images/baum.gif")).getImage()).getScaledInstance(MainFrame.screenWidth / MainFrame.MAP_BUTTONS_X, MainFrame.screenHeight / MainFrame.MAP_BUTTONS_Y, java.awt.Image.SCALE_SMOOTH));
		}
	}
	
	
	public abstract boolean doClick(Model m);
}
