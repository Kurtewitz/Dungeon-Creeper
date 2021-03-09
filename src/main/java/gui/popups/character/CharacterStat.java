package gui.popups.character;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class CharacterStat extends HBox {

    String statName;
    int statValue;

    Label statValueLabel;

    public CharacterStat(String statName, int statValue) {

        this.statName = statName;
        this.statValue = statValue;

        statValueLabel = new Label(String.valueOf(statValue));

        this.getChildren().addAll(new Label(statName), statValueLabel);

    }

    public void setStatValue(int value) {
        statValueLabel.setText(String.valueOf(value));
    }


}
