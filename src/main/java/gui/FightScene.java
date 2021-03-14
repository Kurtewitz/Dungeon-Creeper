package gui;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import welt.Model;

public class FightScene extends Scene {

	
	
	View view;
	Model model;
	
	static HBox stack;
	
	/** Hero's xCoordinate before entering the fight */
	private int lastX;
	/** Hero's yCoordinate before entering the fight */
	private int lastY;
	
	public FightScene(View view, Model model) {
		super(stack = new HBox(40));
		
		this.model = model;
		this.view = view;
		
		stack.setStyle("-fx-background-color: blue");
		
		
		
		Button kill = new Button("Attack [A]");
		kill.setOnAction(e -> {

			attackOpponent();

		});
		
		Button flee = new Button("Chicken out");
		flee.setOnAction(e -> {
			fleeFromOpponent();
		});
		
		stack.getChildren().addAll(kill, flee);
		addKeyboardControls();
	}

	private void attackOpponent() {
		int x = model.player().x();
		int y = model.player().y();


		model.fightJudge().attack(model.player(), model.fightJudge().opponent());

		if(model.fightJudge().opponent().isDead()) {

			model.player().gainExp(model.fightJudge().opponent().exp());

			String newsOfSpoils = model.fightJudge().loot();

			view.world().console().printMessage(newsOfSpoils);
			//notify the worldPane that the monster has been slain
			view.world().killMonsterAt(x, y);
			//go back to adventuring
			view.switchToMap();
		}
		else {
			model.fightJudge().attack(model.fightJudge().opponent(), model.player());
			if(model.player().isDead()) {
				view.world().console().printMessage("You died. Restart the game.");
			}
		}
	}

	private void fleeFromOpponent() {
		int x = model.player().x();
		int y = model.player().y();

		//spawn the monster and run away from it... what is wrong with you?!
		String fleeMessage = model.fightJudge().flee(model.fightJudge().opponent());

		//print the news of your cowardice to console
		view.world().console.printMessage(fleeMessage);
		//move a step back to the last location where you could still call yourself "brave"
		view.world().moveHeroTo(lastX, lastY);
		//go back to exploring... hiding your shame.
		view.switchToMap();
	}

	public void setLastPosition(int lastX, int lastY) {
		this.lastX = lastX;
		this.lastY = lastY;
	}

	public void addKeyboardControls() {

		EventHandler<KeyEvent> eh = new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent e) {
				switch(e.getCode()) {


					case A: attackOpponent(); break;
					case F: fleeFromOpponent(); break;
					case P: model.player().useHpPotion(); break;
					default: break;
				}
			}
		};

		setEventHandler(KeyEvent.KEY_PRESSED, eh);
	}
	
}
