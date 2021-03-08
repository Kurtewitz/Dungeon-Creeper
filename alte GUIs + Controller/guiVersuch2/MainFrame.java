package guiVersuch2;

import java.awt.Dimension;
import java.awt.KeyboardFocusManager;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JFrame;

import welt.Model;

@SuppressWarnings("serial")
public class MainFrame extends JFrame{
	
	public static final int MAP_BUTTONS_X = 46;
	public static final int MAP_BUTTONS_Y = 26;
	public static int mapnr = 1;
	public static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	public static int screenHeight = screenSize.height / 2;
	public static int screenWidth = screenSize.width / 2;
	public static String maptxt = "";
	
	Model m;
	MapPanel mapPan;
	FightPanel fight;
	int focusOwnerNr;
	
	public MainFrame(Model m) {
		super("Dungeon Creeper");
		this.m = m;
		readMap();
		mapPan = new MapPanel();
		fight = new FightPanel(m);
//		fight.setVisible(false);
		
//		add(fight);
		add(mapPan);

		setPreferredSize(new Dimension(screenWidth, screenHeight));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setVisible(true);
	}
	
	public void readMap() {
		try {
		    BufferedReader in = new BufferedReader(new FileReader("map.txt"));
		    String line;
		    while ((line = in.readLine()) != null) {
		        maptxt += line;
		    }
		    in.close();
		} catch (IOException e) {
		}
	}
	
	public void setKeyListener(KeyListener l ) {
		mapPan.setKeyListener( l ) ;
	}
	
	public void setActionListener(ActionListener a) {
		fight.setActionListener(a);
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
		return mapPan.step(c, m);
	}
	
	public void switchToFight(ActionListener a) {
		mapPan.setVisible(false);
		BaseButton current = (BaseButton) KeyboardFocusManager.getCurrentKeyboardFocusManager().getFocusOwner();
		focusOwnerNr = mapPan.getComponentZOrder(current);
		System.out.println(focusOwnerNr + "(MainFrame)");

		fight = new FightPanel(m);
		fight.setActionListener(a);
		add(fight);
		pack();
	}
	
	public void switchToMap() {
		mapPan.setVisible(true);
		remove(fight);
		mapPan.getComponent(focusOwnerNr).requestFocus();
		pack();
	}
	
	public static void main(String[] args) {
//		MainFrame mf = new MainFrame(new Model());
//		mf.switchToFight();
	//	mf.switchToMap();
	}
}
