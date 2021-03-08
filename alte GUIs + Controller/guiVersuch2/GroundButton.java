package guiVersuch2;

import welt.Model;

@SuppressWarnings("serial")
public class GroundButton extends BaseButton {

//	private JButton button;
//	private Icon icon;
//	private Icon iconPressed;
	
	/**
	 * Konstruktor, laesst readImage() die richtigen Icons rauslesen,
	 * schneidet den JButton auf die Icongroesse zu
	 */
	public GroundButton() {
		super("ground");
		setPressedIcon(iconPressed);
		
	}
	
	@Override
	public boolean doClick(Model m) {
		if(m.fightJudge().checkForMonster()) {
			m.fightJudge().initFight(m.fightJudge().spawnMonster());
//			m.fightJudge().fight();
			return false;
		}
		return true;
	}
}
