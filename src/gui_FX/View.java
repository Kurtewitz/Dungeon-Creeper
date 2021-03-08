package gui_FX;

import java.util.Random;

import javafx.stage.Stage;
import welt.Model;

public class View extends Stage {

	
	/** is the player currently fighting a monster? If not -> he is exploring the map */
//	private boolean inFight = false;
	/** Scene for adventuring */
	private WorldScene world;
	/** Scene for thrilling fights with blood-chilling monstrosities */
	private FightScene fight;
	
	Model model;
	
	/** roll the dice */
	public static final Random rnd = new Random();
	
	
	
	
	
	
	
	public View(Model model) {
		super();
		
		this.model = model;
		
		world = new WorldScene(this, model);
		fight = new FightScene(this, model);
		
		addKeyboardControls();
		
		this.setScene(world);
		this.setOnCloseRequest(e -> {
			System.exit(0);
		});
	}
	
	
	public WorldScene world() {
		return world;
	}
	
	public FightScene fight() {
		return fight;
	}
	
	
	
	public void switchToFight(int lastX, int lastY) {
		this.setScene(fight);
		fight.setLastPosition(lastX, lastY);
	}
	
	public void switchToMap() {
		this.setScene(world);
	}
	
	
	
	
	public void addKeyboardControls() {
		
		world.addKeyboardControls();
//		fight.addKeyboardControls();
	}

	
	
	
	
}
