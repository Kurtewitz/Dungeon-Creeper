package gui;

import javafx.scene.Scene;
import javafx.scene.control.Button;
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
		
		
		
		Button kill = new Button("Give 'em the ol' one-two!");
		kill.setOnAction(e -> {
			
			int x = model.player().x();
			int y = model.player().y();
			
			//spawn and kill the monster... because you're just THAT strong.
			model.fightJudge().initFight(model.fightJudge().spawnMonster(x, y));
//			model.fightJudge().opponent().loseHP(model.fightJudge().opponent().HP());
			
			
			//check loot and print it to console
//			String newsOfSpoils = model.fightJudge().loot();
			String newsOfSpoils = model.fightJudge().killOpponent();
			view.world().console().printMessage(newsOfSpoils);
			//notify the worldPane that the monster has been slain
			view.world().killMonsterAt(x, y);
			//go back to adventuring
			view.switchToMap();
			
		});
		
		Button flee = new Button("Chicken out");
		flee.setOnAction(e -> {
			
			int x = model.player().x();
			int y = model.player().y();
			
			//spawn the monster and run away from it... what is wrong with you?!
			model.fightJudge().initFight(model.fightJudge().spawnMonster(x, y));
			String fleeMessage = model.fightJudge().flee(model.fightJudge().opponent());
			
			//print the news of your cowardice to console
			view.world().console.printMessage(fleeMessage);
			//move a step back to the last location where you could still call yourself "brave"
			view.world().moveHeroTo(lastX, lastY);
			//go back to exploring... hiding your shame.
			view.switchToMap();
			
		});
		
		stack.getChildren().addAll(kill, flee);
		
	}
	
	public void setLastPosition(int lastX, int lastY) {
		this.lastX = lastX;
		this.lastY = lastY;
	}
	
}
