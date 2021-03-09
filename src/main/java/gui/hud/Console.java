package gui.hud;

import javafx.scene.control.TextArea;
import javafx.scene.text.Font;

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
