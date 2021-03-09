package gui.popups.inventory;

import gui.WorldScene;
import gui.popups.inventory.InventoryItem;
import javafx.geometry.Insets;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.Effect;
import javafx.scene.effect.InnerShadow;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import player.Inventory;

public class InventoryView extends FlowPane {

    static final double ITEM_WIDTH = WorldScene.SINGLE_SQUARE_WIDTH * WorldScene.zoomFactor;
    static final double ITEM_HEIGHT = WorldScene.SINGLE_SQUARE_HEIGHT * WorldScene.zoomFactor;
    static final int ITEMS_PER_ROW = 5;

    static final Background invBG = new Background(new BackgroundFill(Paint.valueOf("saddlebrown"), CornerRadii.EMPTY, Insets.EMPTY));
    final Effect fx = new InnerShadow(BlurType.THREE_PASS_BOX, Color.GRAY, 12, 0.5, 0, 0);


    Inventory inventory;

    public InventoryView(Inventory inventory, int width, int height) {
        super();
        this.inventory = inventory;

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
        int rows = this.inventory.maxItems() / ITEMS_PER_ROW;
        if(this.inventory.maxItems() % ITEMS_PER_ROW > 0) rows++;
        //calculate the horizontal and vertical insets
        double horInset = ( width - ((ITEM_WIDTH + getHgap()) * ITEMS_PER_ROW) ) / 2;
        double verInset = (height - ((ITEM_HEIGHT + getVgap()) * rows) ) / 2;
        setPadding(new Insets(verInset, horInset, verInset, horInset));


        setPrefWrapLength(width);

        //create an empty inventory
        for(int i = 0; i < this.inventory.maxItems(); i++) {
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

                if(counter < this.inventory.items().size()) {

                    current.setItem(this.inventory.items().get(counter));
                    counter++;
                }
                else {
                    current.setItem(null);
                }
            }
        }
    }


}
