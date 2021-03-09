package gui.popups.character;

import javafx.geometry.Insets;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.Effect;
import javafx.scene.effect.InnerShadow;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import player.Player;

import java.util.HashMap;
import java.util.Map;

public class CharacterStats extends VBox {

    final Background invBG = new Background(new BackgroundFill(Paint.valueOf("saddlebrown"), CornerRadii.EMPTY, Insets.EMPTY));
    final Effect fx = new InnerShadow(BlurType.THREE_PASS_BOX, Color.GRAY, 12, 0.5, 0, 0);

    Player player;
    
    HashMap<String, CharacterStat> characterStatLabels;
    
    public CharacterStats(Player player, int width, int height) {
        super();

        this.player = player;

        setPrefSize(width, height);
        setMaxSize(width, height);
        setMinSize(width, height);

        setOpacity(0.0);

//        setHgap(10);
//        setVgap(10);

        setBackground(invBG);
        setEffect(fx);

        setStyle("-fx-border-color: black;"
                + "-fx-border-width: 5");

//        setPrefWrapLength(width);

        //create starter stats

        characterStatLabels = new HashMap();

        
        characterStatLabels.put("Level", new CharacterStat("Level", this.player.level()));
        characterStatLabels.put("Experience", new CharacterStat("Experience to level up",
                this.player.expToLevelUp() - this.player.exp()));
        characterStatLabels.put("Health", new CharacterStat("Health", this.player.stats().Health()));
        characterStatLabels.put("Strength", new CharacterStat("Strength", this.player.stats().Str()));
        characterStatLabels.put("Dexterity", new CharacterStat("Dexterity", this.player.stats().Dex()));
        characterStatLabels.put("Intelligence", new CharacterStat("Intelligence", this.player.stats().Int()));
        characterStatLabels.put("Wisdom", new CharacterStat("Wisdom", this.player.stats().Wis()));
        characterStatLabels.put("Speed", new CharacterStat("Speed", this.player.stats().Spd()));


        this.getChildren().addAll(
                characterStatLabels.get("Level"),
                characterStatLabels.get("Experience"),
                characterStatLabels.get("Health"),
                characterStatLabels.get("Strength"),
                characterStatLabels.get("Dexterity"),
                characterStatLabels.get("Intelligence"),
                characterStatLabels.get("Wisdom"),
                characterStatLabels.get("Speed")
                
        );

        // and will it with your current stats
        update();
    }

    /**
     * Is the inventory currently opened / showing?
     *
     * @return boolean
     */
    public boolean showing() {
        return getOpacity() > 0;
    }

    /**
     * open / show the inventory
     */
    public void show() {
        update();
        setOpacity(1.0);
    }

    /**
     * hide / close the inventory
     */
    public void hide() {
        setOpacity(0.0);
    }

    /**
     * update the inventory to reflect your current inventory content
     */
    public void update() {

        characterStatLabels.get("Level").setStatValue(this.player.level());
        characterStatLabels.get("Experience").setStatValue(this.player.expToLevelUp() - this.player.exp());
        characterStatLabels.get("Health").setStatValue(this.player.stats().Health());
        characterStatLabels.get("Strength").setStatValue(this.player.stats().Str());
        characterStatLabels.get("Dexterity").setStatValue(this.player.stats().Dex());
        characterStatLabels.get("Intelligence").setStatValue(this.player.stats().Int());
        characterStatLabels.get("Wisdom").setStatValue(this.player.stats().Wis());
        characterStatLabels.get("Speed").setStatValue(this.player.stats().Spd());
    }
}
