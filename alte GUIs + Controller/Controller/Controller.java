package Controller;

import guiVersuch2.View;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import welt.Model;

public class Controller implements KeyListener /*, ActionListener*/ {
	
	private View v;
	private Model m;
	
	public Controller() {
		m = new Model();

		v = new View(m);
		v.mf().requestFocus();
		v.mf().setKeyListener(this);
//		v.mf().setActionListener(this);
		
	}

	@Override
	public void keyPressed(KeyEvent e) {

		if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
			if(v.mf().step('r', m) == 2) {
				m.fightJudge().initFight(m.fightJudge().spawnMonster());
				v.mf().switchToFight(new FightController(m, v));
				//if(m.fightJudge().fight() == 2) {System.out.println("Game Over.");}
				//v.mf().switchToMap();
			}
			System.out.println("right");
		}
		if(e.getKeyCode() == KeyEvent.VK_LEFT) {
			v.mf().step('l', m);
			System.out.println("left");
		}
		if(e.getKeyCode() == KeyEvent.VK_UP) {
			v.mf().step('u', m);
			System.out.println("up");
		}
		if(e.getKeyCode() == KeyEvent.VK_DOWN) {
			v.mf().step('d', m);
			System.out.println("down");
		}
		

	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	public static void main(String[] args) {
		new Controller();
	}

/*	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		System.out.println("button pressed");
		v.mf().switchToMap();
	}
*/
}
