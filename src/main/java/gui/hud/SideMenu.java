package gui.hud;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import player.Inventory;

public class SideMenu extends VBox {

    Inventory inventory;

    public SideMenu(Inventory inventory, double width, double height) {
        super();

        this.inventory = inventory;

        setStyle("-fx-background-color: red");
        setPrefSize(width, height);
        setMaxSize(width, height);
        setMinSize(width, height);

        //make sure the weapon and shield descriptions break the line BEFORE the dmg number. -> Zweihänder,\n4 slashing damage
        String brokenUpWeaponText = this.inventory.equippedWeapon().toString();
        for(int i = 0; i < brokenUpWeaponText.length(); i++) {
            if(Character.isDigit(brokenUpWeaponText.charAt(i))) {
                brokenUpWeaponText = brokenUpWeaponText.substring(0, i) + "\n" + brokenUpWeaponText.substring(i);
                break;
            }
        }
        String brokenUpShieldText = "" + this.inventory.equippedShield();
        for(int i = 0; i < brokenUpShieldText.length(); i++) {
            if(Character.isDigit(brokenUpShieldText.charAt(i))) {
                brokenUpShieldText = brokenUpShieldText.substring(0, i) + "\n" + brokenUpShieldText.substring(i);
                break;
            }
        }

        Label equippedWeapon = new Label("Weapon: " + brokenUpWeaponText);
        Label equippedShield = new Label("Shield: " + brokenUpShieldText);
        Label potions = new Label("HP Potions: " + this.inventory.getPotions());


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
