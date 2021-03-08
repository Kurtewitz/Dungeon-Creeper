package Controller;

import guiVersuch2.View;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import welt.Model;

public class FightController implements ActionListener {

	Model m;
	View v;
	
	public FightController(Model m, View v) {
		this.m = m;
		this.v = v;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(m.fightJudge().opponent().HP() <= 0) {
			v.mf().switchToMap();
		}
//		if(m.fightJudge().opponentDead()) {
//			v.mf().switchToMap();
//		}
	}

}
