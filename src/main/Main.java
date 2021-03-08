package main;

import gui_FX.View;
import javafx.application.Application;
import javafx.stage.Stage;
import welt.Model;

public class Main extends Application {

	Model model;
	View view;

	public Main() {
		model = new Model();
	}
	

	@Override
	public void start(Stage primaryStage) throws Exception {
		view = new View(model);
		view.show();
		
	}
	
	public static void main(String[] args) {
		new Main();
		Main.launch(args);
	}
	
}
