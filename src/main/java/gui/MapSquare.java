package gui;

import javafx.animation.FadeTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import util.RNG;

public class MapSquare extends StackPane {

	/** future cheat code? fly mode! */
	private boolean isGround = false;
	
	/** has the tile been uncovered before? */
	private boolean uncovered = false;
	
	/** already uncovered but currently out of sight? */
	private boolean inSight = false;
	
	/** we don't want any monsters jumping at our hero right as he arrives */
	private boolean safeZone = false;
	
	/** once a monster spawns, it doesn't just go away when you look away... kill it or find a way around it */
	private boolean monsterSpawned = false;
	
	/** spare some treasures for the weary adventurer? */
	private boolean treasureSpawned = false;
	
	/** no seriously... spare some treasures?! */
	private boolean possibleTreasure = false;
	
	
	
	
	private ImageView mapTile;
	private ImageView monster;
	private ImageView special;
	
	/** scheduler for animations */
//	private ScheduledExecutorService animation = Executors.newSingleThreadScheduledExecutor();
	
	/** in case we need to interrupt already scheduled animations */
//	ScheduledFuture<?> task;
	
	/**
	 * basic constructor for obstacle-tiles.
	 * @param img Image of the obstacle
	 */
	public MapSquare(Image img) {
		
		super();
		setStyle("-fx-background-color: grey");
		setOpacity(1.0);
		isGround = false;
		
		mapTile = new ImageView(img);
		monster = new ImageView();
		special = new ImageView();
		
		
//		mapTile.setOpacity(0.0);
		monster.setOpacity(0.0);
		special.setOpacity(0.0);
		
		this.getChildren().addAll(mapTile, monster, special);
	}
	
	/**
	 * Constructor for ground tiles with possible monsters.
	 * Monsters are present on every ground tile,
	 * they just appear with a probability specified in WorldScene.MONSTER_SPAWN_CHANCE
	 * @param ground Image of the ground
	 * @param monsterIMG Image of the monster that might spawn
	 */
	public MapSquare(Image ground, Image monsterIMG, boolean sureSpawn) {
		this(ground);
		isGround = true;
		
		monster.setImage(monsterIMG);
		if(sureSpawn) monsterSpawned = true;
	}
	
	
	public MapSquare(Image ground, Image monsterIMG, Image specialIMG, boolean alwaysShowSpecial) {
		this(ground, monsterIMG, false);
		
		special.setImage(specialIMG);
		if(alwaysShowSpecial) treasureSpawned = true;
		else possibleTreasure = true;
		
	}
	
	
	
	
	public void setIsGround(boolean b) {
		isGround = b;
	}
	
	public boolean isGround() {
		return isGround;
	}
	
	public void setSafeZone(boolean b) {
		safeZone = b;
	}
	
	public boolean isSafeZone() {
		return safeZone;
	}
	
	public void setTreasure(boolean b) {
		treasureSpawned = b;
	}
	
	public boolean treasure() {
		return treasureSpawned;
	}
	
	public boolean monster() {
		return monsterSpawned;
	}
	
	
	public void coverWithFogOfWar() {
		mapTile.setOpacity(0.0);
		monster.setOpacity(0.0);
		special.setOpacity(0.0);
	}
	
	public boolean uncovered() {
		return uncovered;
	}
	
	public void uncover() {
		setInSight(true);
	}
	
	public void setInSight(boolean b) {
		//in sight
		if(b) {
			mapTile.setOpacity(1.0);
			uncovered = true;
			inSight = true;
			
			spawnMonster();
			spawnTreasure();
		}
		//out of sight
		else if(uncovered){
			mapTile.setOpacity(0.5);
			inSight = false;
			
			hideMonster();
			hideTreasure();
		}
	}
	
	public boolean inSight() {
		return inSight;
	}
	
	public boolean checkMonsterSpawn() {
		//no spawns in safe zones
		if(safeZone) return false;
		//no spawns on top of treasure
		if(treasureSpawned) return false;
		//has the monster already spawned before?
		if(monsterSpawned) return true;
		
		//but else... roll the dice
		int d100 = RNG.randomInt(100);
		if(d100 < WorldScene.MONSTER_SPAWN_CHANCE) {
			monsterSpawned = true;
			return true;
		}
		else return false;
	}
	
	/** fade-in animation when a monster spawns in your sight */
	public void spawnMonster() {
		
		if(checkMonsterSpawn()) {
			FadeTransition anim = new FadeTransition(Duration.millis(600), monster);
			anim.setFromValue(monster.getOpacity());
			anim.setToValue(1.0);
			anim.play();
		}
	}
	/** fade-out animation when you lose sight of a monster */
	public void hideMonster() {
		
		if(monsterSpawned) {
			FadeTransition anim = new FadeTransition(Duration.millis(600), monster);
			anim.setFromValue(monster.getOpacity());
			anim.setToValue(0.0);
			anim.play();
		}
	}
	
	public void killMonster() {
		hideMonster();
		monsterSpawned = false;
	}
	
	public boolean checkTreasureSpawn() {
		//treasure already spawned
		if(treasureSpawned) return true;
		
		if(possibleTreasure) {
			//roll the dice
			int d100 = RNG.randomInt(100);
			if(d100 < WorldScene.TREASURE_SPAWN_CHANCE) {
				treasureSpawned = true;
				return true;
			}
		}
		return false;
	}
	
	public void spawnTreasure() {
		
		if(checkTreasureSpawn()) {
			FadeTransition anim = new FadeTransition(Duration.millis(600), special);
			anim.setFromValue(special.getOpacity());
			anim.setToValue(1.0);
			anim.play();
		}
	}
	
	public void hideTreasure() {
		
		if(treasureSpawned) {
			FadeTransition anim = new FadeTransition(Duration.millis(600), special);
			anim.setFromValue(special.getOpacity());
			anim.setToValue(0.0);
			anim.play();
		}
	}
	
	
	
	
	
	public void setFitHeight(double d) {
		mapTile.setFitHeight(d);
		monster.setFitHeight(d);
		special.setFitHeight(d);
	}
	
	public void setFitWidth(double d) {
		mapTile.setFitWidth(d);
		monster.setFitWidth(d);
		special.setFitWidth(d);
	}
	
	public double getFitHeight() {
		return mapTile.getFitHeight();
	}
	
	public double getFitWidth() {
		return mapTile.getFitWidth();
	}
	
	
}
