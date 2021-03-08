package gui_FX;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import items.Item;
import items.Shield;
import items.weapons.Weapon;
import items.weapons.Dagger;
import items.weapons.Mace;
import items.weapons.Sword;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.ParallelTransition;
import javafx.animation.RotateTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.Effect;
import javafx.scene.effect.InnerShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;
import welt.Model;

public class WorldScene extends Scene {

	/** future cheat code? */
	boolean fogOfWar = true;
	

	public static final int mapWidth = 1150;
	public static final int mapHeight = 650;
	public static final int menuWidth = 150;
	public static final int menuHeight = mapHeight;
	public static final int consoleWidth = mapWidth + menuWidth;
	public static final int consoleHeight = 100;
	public static final int inventoryWidth = mapWidth * 6 / 10;
	public static final int inventoryHeight = mapHeight * 6 / 10;
	public static final double zoomFactor = 3;
	public static final int MAP_BUTTONS_X = 46;
	public static final int MAP_BUTTONS_Y = 26;
	public static final double SINGLE_SQUARE_WIDTH = mapWidth / MAP_BUTTONS_X;
	public static final double SINGLE_SQUARE_HEIGHT = mapHeight / MAP_BUTTONS_Y;
	private final double HERO_SCALE = 1;
	private final int SIGHT_RADIUS = 3;
	private final int animationDelay = 200;
	public static final int MONSTER_SPAWN_CHANCE = 5;
	public static final int TREASURE_SPAWN_CHANCE = 100;
	
	Image heroIcon;
	Image ground;
	Image obstacle;
	Image monster;
	Image treasure;
	
	/** our hero, the one promised by the prophecies to rid the lands of evil */
	ImageView hero;
	/** you don't need whole 7 days to create a world... if you make a plan beforehand */
	String maptxt = "";
	/** model of our map, so we have easy access to coordinates etc */
	MapSquare[][] squares;
	
	
	
	/** scheduler for animating the hero's steps */
//	private final ScheduledExecutorService animationThread;
	/** boolean to make sure move commands don't get stacked in case of pressed and held key */
	private boolean animationFinished = true;
	
	

	
	Model model;
	View view;
	
	FlowPane mapPane;
	Pane heroLayer;
	
	StackPane stack;
	SideMenu sideMenu;
	Console console;
	Inventory inventory;
	static StackPane root;
	
	public WorldScene(View view, Model model) {
		super(root = new StackPane());
		
		this.model = model;
		this.view = view;
		
		//read the image files
		heroIcon = new Image(WorldScene.class.getResourceAsStream("/images/hero.png"));
		ground = new Image(WorldScene.class.getResourceAsStream("/images/groundWithPath.png"));
		obstacle = new Image(WorldScene.class.getResourceAsStream("/images/baums.png"));
		monster = new Image(WorldScene.class.getResourceAsStream("/images/dragon.png"));
		treasure = new Image(WorldScene.class.getResourceAsStream("/images/gold.png"));
		
		
		squares = new MapSquare[MAP_BUTTONS_Y][MAP_BUTTONS_X];
//		animationThread = Executors.newSingleThreadScheduledExecutor();
		
		mapPane = new FlowPane();
		mapPane.setStyle("-fx-background-color: grey");;
		mapPane.setPrefWrapLength(MAP_BUTTONS_X * SINGLE_SQUARE_WIDTH);
		
		readMap("resources/map.txt");
		createMap(mapPane);
		
		
		
		//enter the hero
		hero = new ImageView(heroIcon);
		heroLayer = new Pane(hero);

		//resize
		hero.setFitHeight(SINGLE_SQUARE_HEIGHT * HERO_SCALE);
		hero.setFitWidth(SINGLE_SQUARE_WIDTH * HERO_SCALE);
		//move
		moveHeroTo(2, 23);
		animationFinished = true;
		
		model.player().moveTo(2, 23);
		hero.relocate( (2*SINGLE_SQUARE_WIDTH) - ( (hero.getFitWidth() - SINGLE_SQUARE_WIDTH ) / 2),
				( 23*SINGLE_SQUARE_HEIGHT) - ( (hero.getFitHeight() - SINGLE_SQUARE_HEIGHT) / 2) );
		uncoverHerosSurrounding();
		
		
		
		stack = new StackPane();
		stack.getChildren().addAll(mapPane, heroLayer);
		stack.setStyle("-fx-background-color: grey");
		stack.setScaleX(stack.getScaleX() * zoomFactor);
		stack.setScaleY(stack.getScaleY() * zoomFactor);
		
		stack.setPrefSize(mapWidth, mapHeight);
		stack.setMaxSize(mapWidth, mapHeight);
		stack.setMinSize(mapWidth, mapHeight);
		
		
		sideMenu = new SideMenu(menuWidth, menuHeight);
		console = new Console(consoleWidth, consoleHeight);
		inventory = new Inventory(inventoryWidth, inventoryHeight);
		
		
		StackPane.setAlignment(sideMenu, Pos.TOP_LEFT);
		StackPane.setAlignment(console, Pos.BOTTOM_CENTER);
		StackPane.setAlignment(inventory, Pos.CENTER);
		root.setPrefSize(mapWidth + menuWidth, mapHeight + consoleHeight);
		root.setMaxSize(mapWidth + menuWidth, mapHeight + consoleHeight);
		root.setMinSize(mapWidth + menuWidth, mapHeight + consoleHeight);
		root.getChildren().addAll(stack, sideMenu, console, inventory);
		
		
		
	}
	
	/** read map-text from file */
	public void readMap(String file) {
		try {
		    BufferedReader in = new BufferedReader(new InputStreamReader(WorldScene.class.getResourceAsStream("/map.txt")));
		    String line;
		    while ((line = in.readLine()) != null) {
		        maptxt += line;
		    }
		    in.close();
		} catch (IOException e) {
		}
	}
	
	/**
	 * Once the map-text has been read, turn it into the actual map made of ImageViews
	 * @param pane FlowPane on which the map will be painted
	 */
	public void createMap(FlowPane pane) {
		
		//store the starting and ending point (portals / safeZones)
		int start = 0;
		int end = 0;
		
		
		int squareCounter = 0;
		for(int index = 0; index < maptxt.length(); index++) {
			
			MapSquare currentSquare = null;
			char currentChar = maptxt.charAt(index);
			
			
			if (currentChar == '!') currentSquare = new MapSquare(obstacle);
			else if (currentChar == '@') {currentSquare = new MapSquare(ground, monster, false);}
			else if (currentChar == 'm') {currentSquare = new MapSquare(ground, monster, true);}
			else if (currentChar == 'g') {currentSquare = new MapSquare(ground, monster, treasure, true);}
			else if (currentChar == 's') {currentSquare = new MapSquare(ground, monster, false); start = squareCounter;}
			else if (currentChar == 'e') {currentSquare = new MapSquare(ground, monster, false); end = squareCounter;}
			
			
			
			if(currentSquare != null) {
				
				currentSquare.setFitHeight(SINGLE_SQUARE_HEIGHT);
				currentSquare.setFitWidth(SINGLE_SQUARE_WIDTH);
				
				
				if(fogOfWar) currentSquare.coverWithFogOfWar();
				squares[squareCounter / MAP_BUTTONS_X][squareCounter % MAP_BUTTONS_X] = currentSquare;
				
				pane.getChildren().add(currentSquare);
				
				squareCounter++;
			}
		}
		
		
		
		int yStart = start / MAP_BUTTONS_X;
		int xStart = start % MAP_BUTTONS_X;
		int yEnd = end / MAP_BUTTONS_X;
		int xEnd = end % MAP_BUTTONS_X;
		
		//start point safeZone
		for(int y = yStart - 2; y <= yStart + 2; y++) {
			for(int x = xStart - 2; x <= xStart + 2; x++) {
				if(x >= 0 && y >=0 && x < MAP_BUTTONS_X && y < MAP_BUTTONS_Y) {
					squares[y][x].setSafeZone(true);
				}	
			}
		}
		
		//end point safeZone
		for(int y = yEnd - 2; y <= yEnd + 2; y++) {
			for(int x = xEnd - 2; x <= xEnd + 2; x++) {
				if(x >= 0 && y >=0 && x < MAP_BUTTONS_X && y < MAP_BUTTONS_Y) {
					squares[y][x].setSafeZone(true);
				}	
			}
		}
		
		
	}
	
	public boolean canMoveNorth() {
		return squares[model.player().y() - 1][model.player().x()].isGround();
	}
	public boolean canMoveSouth() {
		return squares[model.player().y() + 1][model.player().x()].isGround();
	}
	public boolean canMoveWest() {
		return squares[model.player().y()][model.player().x() - 1].isGround();
	}
	public boolean canMoveEast() {
		return squares[model.player().y()][model.player().x() + 1].isGround();
	}
	
	public boolean encounteredMonster(MapSquare destination) {
		return destination.monster();
	}
	
	public void killMonsterAt(int x, int y) {
		squares[y][x].killMonster();
	}
	
	public boolean foundTreasure(MapSquare destination) {
		return destination.treasure();
	}
	
	
	public ParallelTransition moveNorth() {
		
		Timeline moveHero = new Timeline();
		KeyValue keyValueX = new KeyValue(hero.layoutXProperty(), (model.player().x()*SINGLE_SQUARE_WIDTH) - ( (hero.getFitWidth() - SINGLE_SQUARE_WIDTH ) / 2));
		KeyValue keyValueY = new KeyValue(hero.layoutYProperty(), ((model.player().y() - 1)*SINGLE_SQUARE_HEIGHT) - ( (hero.getFitHeight() - SINGLE_SQUARE_HEIGHT) / 2));
		
		KeyFrame keyFrame = new KeyFrame(Duration.millis(animationDelay), keyValueX, keyValueY);
		moveHero.getKeyFrames().add(keyFrame);
		
		RotateTransition rotateHeroRight = new RotateTransition(Duration.millis(animationDelay/2), hero);
		rotateHeroRight.setFromAngle(hero.getRotate());
		rotateHeroRight.setToAngle(15);
		RotateTransition rotateHeroLeft = new RotateTransition(Duration.millis(animationDelay/2), hero);
		rotateHeroLeft.setFromAngle(hero.getRotate());
		rotateHeroLeft.setToAngle(0);
		
		SequentialTransition rotateHero = new SequentialTransition();
		rotateHero.getChildren().addAll(rotateHeroRight, rotateHeroLeft);
		
		ParallelTransition stepAnim = new ParallelTransition();
		stepAnim.getChildren().addAll(moveHero, rotateHero);
			
		return stepAnim;
			
		
	}
	public ParallelTransition moveSouth() {
		
		
		Timeline moveHero = new Timeline();
		KeyValue keyValueX = new KeyValue(hero.layoutXProperty(), (model.player().x()*SINGLE_SQUARE_WIDTH) - ( (hero.getFitWidth() - SINGLE_SQUARE_WIDTH ) / 2));
		KeyValue keyValueY = new KeyValue(hero.layoutYProperty(), ((model.player().y() + 1)*SINGLE_SQUARE_HEIGHT) - ( (hero.getFitHeight() - SINGLE_SQUARE_HEIGHT) / 2));
		
		KeyFrame keyFrame = new KeyFrame(Duration.millis(animationDelay), keyValueX, keyValueY);
		moveHero.getKeyFrames().add(keyFrame);
			
		RotateTransition rotateHeroLeft = new RotateTransition(Duration.millis(animationDelay/2), hero);
		rotateHeroLeft.setFromAngle(hero.getRotate());
		rotateHeroLeft.setToAngle(-15);
		RotateTransition rotateHeroRight = new RotateTransition(Duration.millis(animationDelay/2), hero);
		rotateHeroRight.setFromAngle(hero.getRotate());
		rotateHeroRight.setToAngle(0);
		
		SequentialTransition rotateHero = new SequentialTransition();
		rotateHero.getChildren().addAll(rotateHeroLeft, rotateHeroRight);
		
		ParallelTransition stepAnim = new ParallelTransition();
		stepAnim.getChildren().addAll(moveHero, rotateHero);
			
		return stepAnim;
	}
	public ParallelTransition moveWest() {
			
		Timeline moveHero = new Timeline();
		KeyValue keyValueX = new KeyValue(hero.layoutXProperty(), ((model.player().x() - 1)*SINGLE_SQUARE_WIDTH) - ( (hero.getFitWidth() - SINGLE_SQUARE_WIDTH ) / 2));
		KeyValue keyValueY = new KeyValue(hero.layoutYProperty(), (model.player().y()*SINGLE_SQUARE_HEIGHT) - ( (hero.getFitHeight() - SINGLE_SQUARE_HEIGHT) / 2));
		
		KeyFrame keyFrame = new KeyFrame(Duration.millis(animationDelay), keyValueX, keyValueY);
		moveHero.getKeyFrames().add(keyFrame);
		
			
		RotateTransition rotateHeroLeft = new RotateTransition(Duration.millis(animationDelay/2), hero);
		rotateHeroLeft.setFromAngle(hero.getRotate());
		rotateHeroLeft.setToAngle(-30);
		RotateTransition rotateHeroRight = new RotateTransition(Duration.millis(animationDelay/2), hero);
		rotateHeroRight.setFromAngle(hero.getRotate());
		rotateHeroRight.setToAngle(0);
			
		SequentialTransition rotateHero = new SequentialTransition();
		rotateHero.getChildren().addAll(rotateHeroLeft, rotateHeroRight);
		
		ParallelTransition stepAnim = new ParallelTransition();
		stepAnim.getChildren().addAll(moveHero, rotateHero);
			
		return stepAnim;
	}
	public ParallelTransition moveEast() {
		
		Timeline moveHero = new Timeline();
		KeyValue keyValueX = new KeyValue(hero.layoutXProperty(), ((model.player().x() + 1)*SINGLE_SQUARE_WIDTH) - ( (hero.getFitWidth() - SINGLE_SQUARE_WIDTH ) / 2));
		KeyValue keyValueY = new KeyValue(hero.layoutYProperty(), (model.player().y()*SINGLE_SQUARE_HEIGHT) - ( (hero.getFitHeight() - SINGLE_SQUARE_HEIGHT) / 2));
		
		KeyFrame keyFrame = new KeyFrame(Duration.millis(animationDelay), keyValueX, keyValueY);
		moveHero.getKeyFrames().add(keyFrame);
		
		
		RotateTransition rotateHeroRight = new RotateTransition(Duration.millis(animationDelay/2), hero);
		rotateHeroRight.setFromAngle(hero.getRotate());
		rotateHeroRight.setToAngle(30);
		RotateTransition rotateHeroLeft = new RotateTransition(Duration.millis(animationDelay/2), hero);
		rotateHeroLeft.setFromAngle(hero.getRotate());
		rotateHeroLeft.setToAngle(0);
			
		SequentialTransition rotateHero = new SequentialTransition();
		rotateHero.getChildren().addAll(rotateHeroRight, rotateHeroLeft);
			
		ParallelTransition stepAnim = new ParallelTransition();
		stepAnim.getChildren().addAll(moveHero, rotateHero);
			
		return stepAnim;
			
	}
	/**
	 * move hero to the specified coordinates (map squares, NOT pixels).
	 * Used after spawning the hero on map or generally if a step animation is not needed.
	 * Don't forget: coordinates start with 0.
	 * @param x coordinate in the map grid
	 * @param y coordinate in the map grid
	 */
	public void moveHeroTo(int x, int y) {
		
		int startingX = model.player().x();
		int startingY = model.player().y();

		int xDiff = x - (MAP_BUTTONS_X / 2);
		int yDiff = y - (MAP_BUTTONS_Y / 2);
		
		
//		mapPane.setTranslateX((-xDiff) * squares[0][0].getFitWidth());
//		mapPane.setTranslateY((-yDiff) * squares[0][0].getFitHeight());
//		hero.setTranslateX((-xDiff) * squares[0][0].getFitWidth());
//		hero.setTranslateY((-yDiff) * squares[0][0].getFitHeight());
		
		
		
		TranslateTransition translateMap = new TranslateTransition(Duration.millis(animationDelay), mapPane);
		translateMap.setFromX(mapPane.getTranslateX());
		translateMap.setFromY(mapPane.getTranslateY());
		translateMap.setToX((-xDiff) * squares[0][0].getFitWidth());
		translateMap.setToY((-yDiff) * squares[0][0].getFitHeight());
		
		TranslateTransition translateHero = new TranslateTransition(Duration.millis(animationDelay), hero);
		translateHero.setFromX(hero.getTranslateX());
		translateHero.setFromY(hero.getTranslateY());
		translateHero.setToX((-xDiff) * squares[0][0].getFitWidth());
		translateHero.setToY((-yDiff) * squares[0][0].getFitHeight());
		
		
		ParallelTransition stepAnimation = null;
		ParallelTransition translateAll = new ParallelTransition();
		translateAll.setOnFinished(e -> {
			animationFinished = true;
		});
		
		
		
		
		if(x < model.player().x() && canMoveWest()) {
			stepAnimation = moveWest();
		}
		else if (x > model.player().x() && canMoveEast()) {
			stepAnimation = moveEast();
		}
		else if(y < model.player().y() && canMoveNorth()) {
			stepAnimation = moveNorth();
		}
		else if (y > model.player().y() && canMoveSouth()) {
			stepAnimation = moveSouth();
		}
		
		
		translateAll.getChildren().addAll(translateMap, translateHero);
		if(stepAnimation != null) translateAll.getChildren().add(stepAnimation);
		
		
		//if step not possible make sure hero moves to his starting position
		if(stepAnimation == null) {
			model.player().moveTo(startingX, startingY);
			hero.relocate( (startingX*SINGLE_SQUARE_WIDTH) - ( (hero.getFitWidth() - SINGLE_SQUARE_WIDTH ) / 2),
					( startingY*SINGLE_SQUARE_HEIGHT) - ( (hero.getFitHeight() - SINGLE_SQUARE_HEIGHT) / 2) );
		}
		
		//if step possible play animation and check for monsters or treasures
		if(squares[y][x].isGround()) {
			animationFinished = false;
			translateAll.play();
			
			model.player().moveTo(x, y);
	
			uncoverHerosSurrounding();
			
			if(encounteredMonster(squares[y][x])) {
				
				view.switchToFight(startingX, startingY);
			}
		}
		
	}
	
	
	
	
	/**
	 * uncover the hero's surrounding by lifting the fog of war in a radius defined in SIGHT_RADIUS
	 */
	public void uncoverHerosSurrounding() {
		
		int heroX = model.player().x();
		int heroY = model.player().y();
		
		//Look up and down
		for(int y = heroY - SIGHT_RADIUS - 1; y <= heroY + SIGHT_RADIUS + 1; y++) {
			//Look left and right
			for(int x = heroX - SIGHT_RADIUS - 1; x <= heroX + SIGHT_RADIUS + 1; x++) {
				//if it's still inside the map bounds...
				if(x >= 0 && y >=0 && x < MAP_BUTTONS_X && y < MAP_BUTTONS_Y) {
				
					int xDiff = Math.abs(heroX - x);
					int yDiff = Math.abs(heroY - y);
					//... and inside of our "circular" sight radius
					if(xDiff + yDiff <= SIGHT_RADIUS) {
						//uncover the map
						if(!squares[y][x].uncovered()) squares[y][x].uncover();
						if(!squares[y][x].inSight()) squares[y][x].setInSight(true);
					}
					else {
						squares[y][x].setInSight(false);
					}
				}
			}
		}
	}
	
	
	public Console console() {
		return console;
	}
	
	public void addKeyboardControls() {
		
		
		EventHandler<KeyEvent> eh = new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent e) {
				switch(e.getCode()) {
				
				case UP: if(animationFinished) moveHeroTo(model.player().x(), model.player().y() - 1); break;//moveNorth(); break;
				case DOWN: if(animationFinished) moveHeroTo(model.player().x(), model.player().y() + 1); break;//moveSouth(); break;
				case LEFT: if(animationFinished) moveHeroTo(model.player().x() - 1, model.player().y()); break;//moveWest(); break;
				case RIGHT: if(animationFinished) moveHeroTo(model.player().x() + 1, model.player().y()); break;//moveEast(); break;
				case I: if(!inventory.showing()) inventory.show(); else inventory.hide(); break;
				default: break;
			}
			}
		};
		
		
		setEventHandler(KeyEvent.KEY_PRESSED, eh);
		console.addEventHandler(KeyEvent.KEY_PRESSED, eh);
		
		
	}
	
	private class SideMenu extends VBox {
		
		public SideMenu(double width, double height) {
			
			super();
			setStyle("-fx-background-color: red");
			setPrefSize(width, height);
			setMaxSize(width, height);
			setMinSize(width, height);
			
			//make sure the weapon and shield descriptions break the line BEFORE the dmg number. -> Zweihänder,\n4 slashing damage
			String brokenUpWeaponText = model.player().inv().equippedWeapon().toString();
			for(int i = 0; i < brokenUpWeaponText.length(); i++) {
				if(Character.isDigit(brokenUpWeaponText.charAt(i))) {
					brokenUpWeaponText = brokenUpWeaponText.substring(0, i) + "\n" + brokenUpWeaponText.substring(i);
					break;
				}
			}
			String brokenUpShieldText = "" + model.player().inv().equippedShield();
			for(int i = 0; i < brokenUpShieldText.length(); i++) {
				if(Character.isDigit(brokenUpShieldText.charAt(i))) {
					brokenUpShieldText = brokenUpShieldText.substring(0, i) + "\n" + brokenUpShieldText.substring(i);
					break;
				}
			}
			
			Label equippedWeapon = new Label("Weapon: " + brokenUpWeaponText);
			Label equippedShield = new Label("Shield: " + brokenUpShieldText);
			Label potions = new Label("HP Potions: " + model.player().inv().getPotions());
			
			
			equippedWeapon.setPrefSize(width, height / 3);
			equippedWeapon.setMinSize(width, height / 3);
			equippedWeapon.setMaxSize(width, height / 3);
			equippedShield.setPrefSize(width, height / 3);
			equippedShield.setMinSize(width, height / 3);
			equippedShield.setMaxSize(width, height / 3);
			potions.setPrefSize(width, height / 3);
			potions.setMaxSize(width, height / 3);
			potions.setMinSize(width, height / 3);
			
			equippedWeapon.setAlignment(Pos.CENTER);
			equippedShield.setAlignment(Pos.CENTER);
			potions.setAlignment(Pos.CENTER);
			
			equippedWeapon.setTextAlignment(TextAlignment.CENTER);
			equippedShield.setTextAlignment(TextAlignment.CENTER);
			potions.setTextAlignment(TextAlignment.CENTER);
			
			equippedWeapon.setWrapText(true);
			equippedShield.setWrapText(true);
			potions.setWrapText(true);
			
			equippedWeapon.setStyle("-fx-border-color: black");
			equippedShield.setStyle("-fx-border-color: black");
			potions.setStyle("-fx-border-color: black");
			
			getChildren().addAll(potions, equippedWeapon, equippedShield);
			
		}
		
		
	}
	
	public class Console extends TextArea {
		
		public Console(int width, int height) {
			super();
			
			setPrefSize(width, height);
			setMaxSize(width, height);
			setMinSize(width, height);
			
			setEditable(false);
			setOpacity(0.5);
			
//			Font.getDefault();
			setFont(Font.font(Font.getDefault().getSize() + 1));
			
			
//			setStyle("-fx-border-insets: 0;"
//					+ "-fx-border-width: 2px;"
//					+ "-fx-border-color: darkgray lightgray lightgray darkgrey;");
			
			setStyle("-fx-effect: innershadow(three-pass-box, gray, 12 , 0.5, 1, 1);");
			
			printMessage("Welcome traveler.");
			
			
		}
		
		
		
		public void printMessage(String message) {
			if(message != null && !message.equals("")) {
				appendText("\t" + message + "\n");
				setScrollTop(Double.MAX_VALUE);
			}
		}
		
	}
	
	
	public class Inventory extends FlowPane {
	
		static final double itemWidth = SINGLE_SQUARE_WIDTH * zoomFactor;
		static final double itemHeight = SINGLE_SQUARE_HEIGHT * zoomFactor;
		static final int itemsPerRow = 5;
		
		
		final Background invBG = new Background(new BackgroundFill(Paint.valueOf("saddlebrown"), CornerRadii.EMPTY, Insets.EMPTY));
		final Effect fx = new InnerShadow(BlurType.THREE_PASS_BOX, Color.GRAY, 12, 0.5, 0, 0);
		
		public Inventory(int width, int height) {
			super();
			
			setPrefSize(width, height);
			setMaxSize(width, height);
			setMinSize(width, height);
			
			setOpacity(0.0);
			
			setHgap(10);
			setVgap(10);
			
			setBackground(invBG);
			setEffect(fx);
			
			setStyle("-fx-border-color: black;"
					+ "-fx-border-width: 5");
			
			//check how many rows the inventory will have (based on itemsPerRow and maxItems in Inventory)
			int rows = model.player().inv().maxItems() / itemsPerRow;
			if(model.player().inv().maxItems() % itemsPerRow > 0) rows++;
			//calculate the horizontal and vertical insets
			double horInset = ( width - ((itemWidth + getHgap()) * itemsPerRow) ) / 2;
			double verInset = (height - ((itemHeight + getVgap()) * rows) ) / 2;
			setPadding(new Insets(verInset, horInset, verInset, horInset));
			
			
			setPrefWrapLength(width);
			
			//create an empty inventory
			for(int i = 0; i < model.player().inv().maxItems(); i++) {
				this.getChildren().add(new InventoryItem());
			}
			
			// and will it with your current items
			update();
			
		}
		
		/** Is the inventory currently opened / showing?
		 * @return boolean */
		public boolean showing() {
			return getOpacity() > 0;
		}
		
		/** open / show the inventory */
		public void show() {
			update();
			setOpacity(1.0);
		}
		
		/** hide / close the inventory */
		public void hide() {
			setOpacity(0.0);
		}
		
		/** update the inventory to reflect your current inventory content */
		public void update() {
			
			int counter = 0;
			for(int i = 0; i < this.getChildren().size(); i++) {
				
				if(this.getChildren().get(i) instanceof InventoryItem) {
					
					InventoryItem current = (InventoryItem) this.getChildren().get(i);
					
					if(counter < model.player().inv().items().size()) {
						
						current.setItem(model.player().inv().items().get(counter));
						counter++;
					}
					else {
						current.setItem(null);
					}
				}
			}
		}
		
		
		
		public class InventoryItem extends Label {
			
			Item item;
			Background emptyBG = new Background(new BackgroundFill(Paint.valueOf("LIGHTSLATEGRAY"), new CornerRadii(0), new Insets(0)));
			Background weaponBG = new Background(new BackgroundFill(Paint.valueOf("red"), new CornerRadii(0), new Insets(0)));
			Background shieldBG = new Background(new BackgroundFill(Paint.valueOf("blue"), new CornerRadii(0), new Insets(0)));
			
			public InventoryItem() {
				super();
				
				setPrefSize(itemWidth, itemHeight);
				setMaxSize(itemWidth, itemHeight);
				setMinSize(itemWidth, itemHeight);
				
//				setGraphic(new ImageView());

				setBackground(emptyBG);
				setTextAlignment(TextAlignment.CENTER);
				setAlignment(Pos.BOTTOM_CENTER);
				
				Border bord = new Border(new BorderStroke(Paint.valueOf("black"), BorderStrokeStyle.SOLID, new CornerRadii(0), new BorderWidths(3)));
				setBorder(bord);
				
				Effect fx = new InnerShadow(BlurType.THREE_PASS_BOX, Color.valueOf(invBG.getFills().get(0).getFill().toString()), 12, 0.5, 0, 0);
				setEffect(fx);
				
				
			}
			
			public InventoryItem(Item item) {
				this();
				this.item = item;
				
				if(item instanceof Weapon) {
					setBackground(weaponBG);
					
					Weapon w = (Weapon) item;
					String text = w.dmg() + "\n ";
					if(w instanceof Sword) text += "slash";
					else if (w instanceof Mace) text += "blunt";
					else if (w instanceof Dagger) text += "pierce";
					
					setText(text) ;
				}
				else if (item instanceof Shield) {
					setBackground(shieldBG);
					
					Shield s = (Shield) item;
					String text = s.def() + "\n def";
					
					setText(text);
				}
			}
			
			
			public void setItem(Item i) {
				item = i;
				
				if(i == null) {
					setText("");
					setBackground(emptyBG);
					item = null;
				}
				
				else if(item instanceof Weapon) {
					setBackground(weaponBG);
					
					Weapon w = (Weapon) item;
					String text = w.dmg() + "\n ";
					if(w instanceof Sword) text += "slash";
					else if (w instanceof Mace) text += "blunt";
					else if (w instanceof Dagger) text += "pierce";
					
					setText(text) ;
				}
				else if (item instanceof Shield) {
					setBackground(shieldBG);
					
					Shield s = (Shield) item;
					String text = s.def() + "\n def";
					
					setText(text);
				}
			}
			
			
		}
		
		
	}
	
}
