package gui.popups.inventory;

import items.Item;
import items.Shield;
import items.weapons.Dagger;
import items.weapons.Mace;
import items.weapons.Sword;
import items.weapons.Weapon;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.Effect;
import javafx.scene.effect.InnerShadow;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.TextAlignment;

import static gui.popups.inventory.InventoryView.invBG;

public class InventoryItem extends Label {

    Item item;
    Background emptyBG = new Background(new BackgroundFill(Paint.valueOf("LIGHTSLATEGRAY"), new CornerRadii(0), new Insets(0)));
    Background weaponBG = new Background(new BackgroundFill(Paint.valueOf("red"), new CornerRadii(0), new Insets(0)));
    Background shieldBG = new Background(new BackgroundFill(Paint.valueOf("blue"), new CornerRadii(0), new Insets(0)));

    public InventoryItem() {
        super();

        setPrefSize(InventoryView.ITEM_WIDTH, InventoryView.ITEM_HEIGHT);
        setMaxSize(InventoryView.ITEM_WIDTH, InventoryView.ITEM_HEIGHT);
        setMinSize(InventoryView.ITEM_WIDTH, InventoryView.ITEM_HEIGHT);

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
