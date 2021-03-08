package guiVersuch2;

import javax.swing.*;

import welt.Model;

import java.awt.*;
import java.awt.event.KeyListener;

/**
 * JPanel holding JButtons in the GridLayout representing the adventure map.
 * The JButtons during runtime are of the type GroundButton, SurroundingButton etc. 
 * @author Michoo
 *
 */
@SuppressWarnings("serial")
public class MapPanel extends JPanel{
	

	/**
	 * The constructor initializes the panel and reads the map (buttongrid) from a txt file stored in the MainFrame class.
	 */
	public MapPanel() {
		
		super();
		setLayout(new GridLayout(MainFrame.MAP_BUTTONS_Y,MainFrame.MAP_BUTTONS_X));
		
		for(int i = 0; i < MainFrame.maptxt.length(); i++) {
			if(MainFrame.maptxt.charAt(i) == '!') {
				
				add(new SurroundingButton());
			}
			if(MainFrame.maptxt.charAt(i) == '@') {
				add(new GroundButton());
			}
		}
	}	
	
	public void setKeyListener(KeyListener l) {
		for(Component c : getComponents()) {
			c.addKeyListener(l);
		}
	}
	
	/**
	 * 
	 * @param c char indicating the direction of the step
	 * @param m Model
	 * @return <ul><li><code>1</code> if regular step
	 * <li><code>2</code> if step + fight
	 * <li><code>3</code> if no step due to obstacle
	 * <li><code>4</code> if no step due to error
	 * <li><code>5</code> if invalid char parameter
	 */
	public int step(char c, Model m) {
		BaseButton current = (BaseButton) KeyboardFocusManager.getCurrentKeyboardFocusManager().getFocusOwner();
		int i = getComponentZOrder(current);
		BaseButton next;
		if(c == 'r') {
			
			if((i + 1) % MainFrame.MAP_BUTTONS_X != 0) {
				if(getComponent(i + 1) instanceof GroundButton) {
					next = (GroundButton) getComponent(i + 1);
					System.out.println("ground");
					next.requestFocus();
					if(next.doClick(m)) return 1;
					else return 2;
				}
				else if(getComponent(i + 1) instanceof SurroundingButton) {
					next = (SurroundingButton) getComponent(i + 1);
					System.out.println("baum");
					return 3;
				}
				else {
					next = null;
					System.out.println("fehler");
					return 4;
				}
			}
			return 4;
			
		}
		if(c == 'l') {
			if((i)  % MainFrame.MAP_BUTTONS_X != 0) {
				if(getComponent(i - 1) instanceof GroundButton) {
					next = (GroundButton) getComponent(i - 1);
					System.out.println("ground");
					next.requestFocus();
					if(next.doClick(m)) return 1;
					else return 2;
				}
				else if(getComponent(i - 1) instanceof SurroundingButton) {
					next = (SurroundingButton) getComponent(i - 1);
					System.out.println("baum");
					return 3;
				}
				else {
					next = null;
					System.out.println("fehler");
					return 4;
				}
			}
			return 4;
		}
		if(c == 'u') {
			if((i)  >= MainFrame.MAP_BUTTONS_X) {
				if(getComponent(i - MainFrame.MAP_BUTTONS_X) instanceof GroundButton) {
					next = (GroundButton) getComponent(i - MainFrame.MAP_BUTTONS_X);
					System.out.println("ground");
					next.requestFocus();
					if(next.doClick(m)) return 1;
					else return 2;
				}
				else if(getComponent(i - MainFrame.MAP_BUTTONS_X) instanceof SurroundingButton) {
					next = (SurroundingButton) getComponent(i - MainFrame.MAP_BUTTONS_X);
					System.out.println("baum");
					return 3;
				}
				else {
					next = null;
					System.out.println("fehler");
					return 4;
				}
			}
			return 4;
		}
		if(c == 'd') {
			if((i)  < (MainFrame.MAP_BUTTONS_Y - 1) * MainFrame.MAP_BUTTONS_X) {
				if(getComponent(i + MainFrame.MAP_BUTTONS_X) instanceof GroundButton) {
					next = (GroundButton) getComponent(i + MainFrame.MAP_BUTTONS_X);
					System.out.println("ground");
					next.requestFocus();
					if(next.doClick(m)) return 1;
					else return 2;
				}
				else if(getComponent(i + MainFrame.MAP_BUTTONS_X) instanceof SurroundingButton) {
					next = (SurroundingButton) getComponent(i + MainFrame.MAP_BUTTONS_X);
					System.out.println("baum");
					return 3;
				}
				else {
					next = null;
					System.out.println("fehler");
					return 4;
				}
			}
			return 4;
		}
		return 5;
	}
}