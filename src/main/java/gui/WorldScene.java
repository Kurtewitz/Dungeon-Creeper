package gui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import gui.hud.Console;
import gui.popups.inventory.InventoryView;
import gui.hud.SideMenu;
import gui.popups.character.CharacterStats;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.ParallelTransition;
import javafx.animation.RotateTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import welt.Model;

public class WorldScene extends Scene {

    /**
     * future cheat code?
     */
    boolean fogOfWar = true;


    public static final int MAP_WIDTH = 1150;
    public static final int MAP_HEIGHT = 650;
    public static final int MENU_WIDTH = 150;
    public static final int MENU_HEIGHT = MAP_HEIGHT;
    public static final int CONSOLE_WIDTH = MAP_WIDTH + MENU_WIDTH;
    public static final int CONSOLE_HEIGHT = 100;
    public static final int INVENTORY_WIDTH = MAP_WIDTH * 6 / 10;
    public static final int INVENTORY_HEIGHT = MAP_HEIGHT * 6 / 10;
    public static final double ZOOM_FACTOR = 3;
    public static final int MAP_BUTTONS_X = 46;
    public static final int MAP_BUTTONS_Y = 26;
    public static final double SINGLE_SQUARE_WIDTH = MAP_WIDTH / MAP_BUTTONS_X;
    public static final double SINGLE_SQUARE_HEIGHT = MAP_HEIGHT / MAP_BUTTONS_Y;
    private static final double HERO_SCALE = 1;
    private static final int SIGHT_RADIUS = 3;
    private static final int ANIMATION_DELAY = 200;
    public static final int MONSTER_SPAWN_CHANCE = 5;
    public static final int TREASURE_SPAWN_CHANCE = 100;

    Image heroIcon;
    Image ground;
    Image obstacle;
    Image monster;
    Image treasure;

    /**
     * our hero, the one promised by the prophecies to rid the lands of evil
     */
    ImageView hero;
    /**
     * you don't need whole 7 days to create a world... if you make a plan beforehand
     */
    String maptxt = "";
    /**
     * model of our map, so we have easy access to coordinates etc
     */
    MapSquare[][] squares;


    /**
     * boolean to make sure move commands don't get stacked in case of pressed and held key
     */
    private boolean animationFinished = true;


    Model model;
    View view;

    FlowPane mapPane;
    Pane heroLayer;

    StackPane stack;
    SideMenu sideMenu;
    Console console;
    InventoryView inventory;
    CharacterStats stats;
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

        mapPane = new FlowPane();
        mapPane.setStyle("-fx-background-color: grey");
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
        hero.relocate((2 * SINGLE_SQUARE_WIDTH) - ((hero.getFitWidth() - SINGLE_SQUARE_WIDTH) / 2),
                (23 * SINGLE_SQUARE_HEIGHT) - ((hero.getFitHeight() - SINGLE_SQUARE_HEIGHT) / 2));
        uncoverHerosSurrounding();


        stack = new StackPane();
        stack.getChildren().addAll(mapPane, heroLayer);
        stack.setStyle("-fx-background-color: grey");
        stack.setScaleX(stack.getScaleX() * ZOOM_FACTOR);
        stack.setScaleY(stack.getScaleY() * ZOOM_FACTOR);

        stack.setPrefSize(MAP_WIDTH, MAP_HEIGHT);
        stack.setMaxSize(MAP_WIDTH, MAP_HEIGHT);
        stack.setMinSize(MAP_WIDTH, MAP_HEIGHT);


        sideMenu = new SideMenu(model.player().inventory(), MENU_WIDTH, MENU_HEIGHT);
        console = new Console(CONSOLE_WIDTH, CONSOLE_HEIGHT);
        inventory = new InventoryView(model.player().inventory(), INVENTORY_WIDTH, INVENTORY_HEIGHT);
        stats = new CharacterStats(model.player(), INVENTORY_WIDTH, INVENTORY_HEIGHT);


        StackPane.setAlignment(sideMenu, Pos.TOP_LEFT);
        StackPane.setAlignment(console, Pos.BOTTOM_CENTER);
        StackPane.setAlignment(inventory, Pos.CENTER);
        root.setPrefSize(MAP_WIDTH + MENU_WIDTH, MAP_HEIGHT + CONSOLE_HEIGHT);
        root.setMaxSize(MAP_WIDTH + MENU_WIDTH, MAP_HEIGHT + CONSOLE_HEIGHT);
        root.setMinSize(MAP_WIDTH + MENU_WIDTH, MAP_HEIGHT + CONSOLE_HEIGHT);
        root.getChildren().addAll(stack, sideMenu, console, inventory, stats);


    }

    /**
     * read map-text from file
     */
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
     *
     * @param pane FlowPane on which the map will be painted
     */
    public void createMap(FlowPane pane) {

        //store the starting and ending point (portals / safeZones)
        int start = 0;
        int end = 0;


        int squareCounter = 0;
        for (int index = 0; index < maptxt.length(); index++) {

            MapSquare currentSquare = null;
            char currentChar = maptxt.charAt(index);


            if (currentChar == '!') currentSquare = new MapSquare(obstacle);
            else if (currentChar == '@') {
                currentSquare = new MapSquare(ground, monster, false);
            } else if (currentChar == 'm') {
                currentSquare = new MapSquare(ground, monster, true);
            } else if (currentChar == 'g') {
                currentSquare = new MapSquare(ground, monster, treasure, true);
            } else if (currentChar == 's') {
                currentSquare = new MapSquare(ground, monster, false);
                start = squareCounter;
            } else if (currentChar == 'e') {
                currentSquare = new MapSquare(ground, monster, false);
                end = squareCounter;
            }


            if (currentSquare != null) {

                currentSquare.setFitHeight(SINGLE_SQUARE_HEIGHT);
                currentSquare.setFitWidth(SINGLE_SQUARE_WIDTH);


                if (fogOfWar) currentSquare.coverWithFogOfWar();
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
        for (int y = yStart - 2; y <= yStart + 2; y++) {
            for (int x = xStart - 2; x <= xStart + 2; x++) {
                if (x >= 0 && y >= 0 && x < MAP_BUTTONS_X && y < MAP_BUTTONS_Y) {
                    squares[y][x].setSafeZone(true);
                }
            }
        }

        //end point safeZone
        for (int y = yEnd - 2; y <= yEnd + 2; y++) {
            for (int x = xEnd - 2; x <= xEnd + 2; x++) {
                if (x >= 0 && y >= 0 && x < MAP_BUTTONS_X && y < MAP_BUTTONS_Y) {
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

    public void pickUpTreasureAt(int x, int y) {
        squares[y][x].pickUpTreasure();
    }

    public ParallelTransition moveNorth() {

        Timeline moveHero = new Timeline();
        KeyValue keyValueX = new KeyValue(hero.layoutXProperty(), (model.player().x() * SINGLE_SQUARE_WIDTH) - ((hero.getFitWidth() - SINGLE_SQUARE_WIDTH) / 2));
        KeyValue keyValueY = new KeyValue(hero.layoutYProperty(), ((model.player().y() - 1) * SINGLE_SQUARE_HEIGHT) - ((hero.getFitHeight() - SINGLE_SQUARE_HEIGHT) / 2));

        KeyFrame keyFrame = new KeyFrame(Duration.millis(ANIMATION_DELAY), keyValueX, keyValueY);
        moveHero.getKeyFrames().add(keyFrame);

        RotateTransition rotateHeroRight = new RotateTransition(Duration.millis(ANIMATION_DELAY / 2.0), hero);
        rotateHeroRight.setFromAngle(hero.getRotate());
        rotateHeroRight.setToAngle(15);
        RotateTransition rotateHeroLeft = new RotateTransition(Duration.millis(ANIMATION_DELAY / 2.0), hero);
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
        KeyValue keyValueX = new KeyValue(hero.layoutXProperty(), (model.player().x() * SINGLE_SQUARE_WIDTH) - ((hero.getFitWidth() - SINGLE_SQUARE_WIDTH) / 2));
        KeyValue keyValueY = new KeyValue(hero.layoutYProperty(), ((model.player().y() + 1) * SINGLE_SQUARE_HEIGHT) - ((hero.getFitHeight() - SINGLE_SQUARE_HEIGHT) / 2));

        KeyFrame keyFrame = new KeyFrame(Duration.millis(ANIMATION_DELAY), keyValueX, keyValueY);
        moveHero.getKeyFrames().add(keyFrame);

        RotateTransition rotateHeroLeft = new RotateTransition(Duration.millis(ANIMATION_DELAY / 2.0), hero);
        rotateHeroLeft.setFromAngle(hero.getRotate());
        rotateHeroLeft.setToAngle(-15);
        RotateTransition rotateHeroRight = new RotateTransition(Duration.millis(ANIMATION_DELAY / 2.0), hero);
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
        KeyValue keyValueX = new KeyValue(hero.layoutXProperty(), ((model.player().x() - 1) * SINGLE_SQUARE_WIDTH) - ((hero.getFitWidth() - SINGLE_SQUARE_WIDTH) / 2));
        KeyValue keyValueY = new KeyValue(hero.layoutYProperty(), (model.player().y() * SINGLE_SQUARE_HEIGHT) - ((hero.getFitHeight() - SINGLE_SQUARE_HEIGHT) / 2));

        KeyFrame keyFrame = new KeyFrame(Duration.millis(ANIMATION_DELAY), keyValueX, keyValueY);
        moveHero.getKeyFrames().add(keyFrame);


        RotateTransition rotateHeroLeft = new RotateTransition(Duration.millis(ANIMATION_DELAY / 2.0), hero);
        rotateHeroLeft.setFromAngle(hero.getRotate());
        rotateHeroLeft.setToAngle(-30);
        RotateTransition rotateHeroRight = new RotateTransition(Duration.millis(ANIMATION_DELAY / 2.0), hero);
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
        KeyValue keyValueX = new KeyValue(hero.layoutXProperty(), ((model.player().x() + 1) * SINGLE_SQUARE_WIDTH) - ((hero.getFitWidth() - SINGLE_SQUARE_WIDTH) / 2));
        KeyValue keyValueY = new KeyValue(hero.layoutYProperty(), (model.player().y() * SINGLE_SQUARE_HEIGHT) - ((hero.getFitHeight() - SINGLE_SQUARE_HEIGHT) / 2));

        KeyFrame keyFrame = new KeyFrame(Duration.millis(ANIMATION_DELAY), keyValueX, keyValueY);
        moveHero.getKeyFrames().add(keyFrame);


        RotateTransition rotateHeroRight = new RotateTransition(Duration.millis(ANIMATION_DELAY / 2.0), hero);
        rotateHeroRight.setFromAngle(hero.getRotate());
        rotateHeroRight.setToAngle(30);
        RotateTransition rotateHeroLeft = new RotateTransition(Duration.millis(ANIMATION_DELAY / 2.0), hero);
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
     *
     * @param x coordinate in the map grid
     * @param y coordinate in the map grid
     */
    public void moveHeroTo(int x, int y) {

        int startingX = model.player().x();
        int startingY = model.player().y();

        int xDiff = x - (MAP_BUTTONS_X / 2);
        int yDiff = y - (MAP_BUTTONS_Y / 2);


        TranslateTransition translateMap = new TranslateTransition(Duration.millis(ANIMATION_DELAY), mapPane);
        translateMap.setFromX(mapPane.getTranslateX());
        translateMap.setFromY(mapPane.getTranslateY());
        translateMap.setToX((-xDiff) * squares[0][0].getFitWidth());
        translateMap.setToY((-yDiff) * squares[0][0].getFitHeight());

        TranslateTransition translateHero = new TranslateTransition(Duration.millis(ANIMATION_DELAY), hero);
        translateHero.setFromX(hero.getTranslateX());
        translateHero.setFromY(hero.getTranslateY());
        translateHero.setToX((-xDiff) * squares[0][0].getFitWidth());
        translateHero.setToY((-yDiff) * squares[0][0].getFitHeight());


        ParallelTransition stepAnimation = null;
        ParallelTransition translateAll = new ParallelTransition();
        translateAll.setOnFinished(e -> animationFinished = true);


        if (x < model.player().x() && canMoveWest()) {
            stepAnimation = moveWest();
        } else if (x > model.player().x() && canMoveEast()) {
            stepAnimation = moveEast();
        } else if (y < model.player().y() && canMoveNorth()) {
            stepAnimation = moveNorth();
        } else if (y > model.player().y() && canMoveSouth()) {
            stepAnimation = moveSouth();
        }


        translateAll.getChildren().addAll(translateMap, translateHero);
        if (stepAnimation != null) translateAll.getChildren().add(stepAnimation);


        //if step not possible make sure hero moves to his starting position
        if (stepAnimation == null) {
            model.player().moveTo(startingX, startingY);
            hero.relocate((startingX * SINGLE_SQUARE_WIDTH) - ((hero.getFitWidth() - SINGLE_SQUARE_WIDTH) / 2),
                    (startingY * SINGLE_SQUARE_HEIGHT) - ((hero.getFitHeight() - SINGLE_SQUARE_HEIGHT) / 2));
        }

        //if step possible play animation and check for monsters or treasures
        if (squares[y][x].isGround()) {
            animationFinished = false;
            translateAll.play();

            model.player().moveTo(x, y);

            uncoverHerosSurrounding();

            if (encounteredMonster(squares[y][x])) {
                model.fightJudge().initFight(model.fightJudge().spawnMonster(x, y));
                view.switchToFight(startingX, startingY);
            }
            if (foundTreasure(squares[y][x])) {
                model.player().pickUpHealthPotions(5);
                pickUpTreasureAt(x, y);
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
        for (int y = heroY - SIGHT_RADIUS - 1; y <= heroY + SIGHT_RADIUS + 1; y++) {
            //Look left and right
            for (int x = heroX - SIGHT_RADIUS - 1; x <= heroX + SIGHT_RADIUS + 1; x++) {
                //if it's still inside the map bounds...
                if (x >= 0 && y >= 0 && x < MAP_BUTTONS_X && y < MAP_BUTTONS_Y) {

                    int xDiff = Math.abs(heroX - x);
                    int yDiff = Math.abs(heroY - y);
                    //... and inside of our "circular" sight radius
                    if (xDiff + yDiff <= SIGHT_RADIUS) {
                        //uncover the map
                        if (!squares[y][x].uncovered()) squares[y][x].uncover();
                        if (!squares[y][x].inSight()) squares[y][x].setInSight(true);
                    } else {
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

        EventHandler<KeyEvent> eh = e -> {
            switch (e.getCode()) {

                case UP:
                    if (animationFinished) moveHeroTo(model.player().x(), model.player().y() - 1);
                    break;//moveNorth(); break;
                case DOWN:
                    if (animationFinished) moveHeroTo(model.player().x(), model.player().y() + 1);
                    break;//moveSouth(); break;
                case LEFT:
                    if (animationFinished) moveHeroTo(model.player().x() - 1, model.player().y());
                    break;//moveWest(); break;
                case RIGHT:
                    if (animationFinished) moveHeroTo(model.player().x() + 1, model.player().y());
                    break;//moveEast(); break;
                case I:
                    if (!inventory.showing()) inventory.show();
                    else inventory.hide();
                    break;
                case C:
                    if (!stats.showing()) stats.show();
                    else stats.hide();
                    break;
                case P:
                    model.player().useHpPotion();
                    break;
                default:
                    break;
            }
        };

        setEventHandler(KeyEvent.KEY_PRESSED, eh);
        console.addEventHandler(KeyEvent.KEY_PRESSED, eh);
    }

}
