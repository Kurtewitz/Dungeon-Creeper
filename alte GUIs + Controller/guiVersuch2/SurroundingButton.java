package guiVersuch2;

import welt.Model;

@SuppressWarnings("serial")
public class SurroundingButton extends BaseButton {
	
	/**
	 * Konstruktor, laesst readImage() die richtigen Icons rauslesen,
	 * schneidet den JButton auf die Icongroesse zu
	 */
	public SurroundingButton() {
		super("surrounding");
		
	}

	@Override
	public boolean doClick(Model m) {
		return true;
	}

}
