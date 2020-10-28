package application;
	

import javafx.application.Application;
import javafx.application.Platform;
//import javafx.event.EventHandler;
//import javafx.event.ActionEvent;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;


//public class Menu extends Application implements EventHandler<ActionEvent> {
public class Menu extends Application{
	
	Stage window;
	Scene main_scene, tutorial_scene;
	VBox main_menu, tutorial;
	Button menu1, menu2, menu3, menu4, menu5, tut1;
	Label tut2;
	
	
	private int code_length;
	private int max_moves;
	private int max_number;
	private boolean duplicates;
	public Menu()
	{
		code_length=4;
		max_moves=10;
		max_number=8;
		duplicates=false;
	}
	
	@Override
	public void start(Stage primaryStage) {
		try {
			window=primaryStage;
			primaryStage.setTitle("Mastermind");
			primaryStage.setWidth(400);
			primaryStage.setHeight(500);
			primaryStage.setResizable(false);
			
			menu1 = new Button("Start game");
			menu2 = new Button("Settings");
			menu3 = new Button("Highscores");
			menu4 = new Button("Tutorial");
			menu5 = new Button("Exit");
			
			tut1 = new Button("Go back");
			tut2 = new Label("The game of Mastermind is a simple (yet intriguing) game of logic. The computer generates a " + code_length + 
					"-digit long code, consisting of numbers from 1 to " + max_number + ". Your role is to crack that code.\nYou have " + max_moves  + 
					" moves. On each move, you try to guess the correct code by typing your own " + code_length + "-digit long code. " + 
					"Each time you do it, You get the information about how many digits are in the right place, and how many are in the code but in the wrong place. " + 
					"By analyzing these results, excluding the incorrect numbers and confirming the place of correct numbers, you can crack the code!" + 
					"\nRemember: Your time and moves are being counted!");
			tut2.setWrapText(true);
	
			menu1.setPrefSize(400, 50);
			menu2.setPrefSize(400, 50);
			menu3.setPrefSize(400, 50);
			menu4.setPrefSize(400, 50);
			menu5.setPrefSize(400, 50);
			tut1.setPrefSize(400, 50);
			
			//menu1.setOnAction(this);
			//menu2.setOnAction(this);
			//menu3.setOnAction(this);
			//menu4.setOnAction(this);
			//menu5.setOnAction(this);
			menu1.setOnAction(e ->
			{
				Mastermind game = new Mastermind(code_length, max_moves, max_number, duplicates);
			});
			menu4.setOnAction(e -> window.setScene(tutorial_scene));
			menu5.setOnAction(e -> Platform.exit());
			tut1.setOnAction(e -> window.setScene(main_scene));
			
			
			main_menu = new VBox(20);
			main_menu.getChildren().addAll(menu1,menu2,menu3,menu4,menu5);
			main_menu.setStyle("-fx-padding: 10;" + 
                     "-fx-border-style: solid inside;" + 
                     "-fx-border-width: 2;" +
                     "-fx-border-insets: 5;" + 
                     "-fx-border-radius: 5;" + 
                     "-fx-border-color: blue;");
			main_scene = new Scene(main_menu,500,400);
			
			tutorial=new VBox(20);
			tutorial.getChildren().addAll(tut2,tut1);
			tutorial.setStyle("-fx-padding: 10;" + 
                    "-fx-border-style: solid inside;" + 
                    "-fx-border-width: 2;" +
                    "-fx-border-insets: 5;" + 
                    "-fx-border-radius: 5;" + 
                    "-fx-border-color: blue;");
			
			tutorial_scene = new Scene(tutorial,500,400);
			
			//scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			
			
			primaryStage.setScene(main_scene);
			primaryStage.show();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		Menu m=new Menu();
		launch(args);
	}
}
