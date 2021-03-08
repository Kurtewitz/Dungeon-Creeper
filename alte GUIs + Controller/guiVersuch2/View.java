package guiVersuch2;

import welt.Model;


public class View {
	
	MainFrame mf;
	Model m;
	

	
	public View(Model m) {
		this.m = m;
		mf = new MainFrame(m);
	}
	
	/**
	 * returns the MainFrame with the adventure map.
	 * @return MainFrame
	 */
	public MainFrame mf() {
		return mf;
	}
	
	public static void main(String[] args) {
		new View(new Model());
	}
}
