package application;
	

//import java.awt.Insets;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
//import javafx.event.EventHandler;
//import javafx.event.ActionEvent;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;


//public class Menu extends Application implements EventHandler<ActionEvent> {
public class Menu extends Application{
	
	Stage window;
	Scene main_scene, tutorial_scene, game_scene;
	VBox main_menu, tutorial; //game;
	GridPane game_grid;
	ScrollPane game_pane;
	Button menu_start, menu_set, menu_high, menu_tut, menu_exit, tut1,
	game_exit, game_choice1, game_choice2, game_choice3, game_choice4,
	game_choice5, game_choice6, game_choice7, game_choice8;
	Label tut2, game_timer, game_answer1, game_answer2, game_answer3, game_answer4;
	
	Timer t1;
	
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
			window.setTitle("Mastermind");
			window.setWidth(400);
			window.setHeight(500);
			window.setResizable(false);
			
			
			//MENU
			menu_start = new Button("Start game");
			menu_set = new Button("Settings");
			menu_high = new Button("Highscores");
			menu_tut = new Button("Tutorial");
			menu_exit = new Button("Exit");
		
			menu_start.setPrefSize(400, 50);
			menu_set.setPrefSize(400, 50);
			menu_high.setPrefSize(400, 50);
			menu_tut.setPrefSize(400, 50);
			menu_exit.setPrefSize(400, 50);
			
			menu_start.setOnAction(e ->
			{
				//Mastermind game = new Mastermind(code_length, max_moves, max_number, duplicates);
				window.setScene(game_scene);
				Mastermind game = new Mastermind(code_length, max_moves, max_number, duplicates);
			});
			
			
			main_menu = new VBox(20);
			main_menu.getChildren().addAll(menu_start, menu_set, menu_high, menu_tut, menu_exit);
			main_menu.setStyle("-fx-padding: 10;" + 
                     "-fx-border-style: solid inside;" + 
                     "-fx-border-width: 2;" +
                     "-fx-border-insets: 5;" + 
                     "-fx-border-radius: 5;" + 
                     "-fx-border-color: black;");
			main_scene = new Scene(main_menu,500,400);
			
			//menu_set.setOnAction(this);
			//menu_high.setOnAction(this);
			menu_tut.setOnAction(e -> window.setScene(tutorial_scene));
			menu_exit.setOnAction(e -> Platform.exit());
			
			
			
			
			
			//TUTORIAL
			tut1 = new Button("Go back");
			tut1.setPrefSize(400, 50);
			//TODO change numbers to colors
			tut2 = new Label("The game of Mastermind is a simple (yet intriguing) game of logic. The computer generates a " + code_length + 
					"-digit long code, consisting of numbers from 1 to " + max_number + ". Your role is to crack that code.\nYou have " + max_moves  + 
					" moves. On each move, you try to guess the correct code by typing your own " + code_length + "-digit long code. " + 
					"Each time you do it, You get the information about how many digits are in the right place, and how many are in the code but in the wrong place. " + 
					"By analyzing these results, excluding the incorrect numbers and confirming the place of correct numbers, you can crack the code!" + 
					"\nRemember: Your time and moves are being counted!");
			tut2.setWrapText(true);
			
			tutorial=new VBox(20);
			tutorial.getChildren().addAll(tut2,tut1);
			tutorial.setStyle("-fx-padding: 10;" + 
                    "-fx-border-style: solid inside;" + 
                    "-fx-border-width: 2;" +
                    "-fx-border-insets: 5;" + 
                    "-fx-border-radius: 5;" + 
                    "-fx-border-color: black;");
			tutorial_scene = new Scene(tutorial,500,400);
		
			tut1.setOnAction(e -> window.setScene(main_scene));
			
			
			
			//GAME
			game_exit = new Button("Go back");
			game_timer = new Label();
			game_choice1 = new Button();
			game_choice2 = new Button();
			game_choice3 = new Button();
			game_choice4 = new Button();
			game_choice5 = new Button();
			game_choice6 = new Button();
			game_choice7 = new Button();
			game_choice8 = new Button();
			
			game_choice1.setPrefSize(50, 30);
			game_choice2.setPrefSize(50, 30);
			game_choice3.setPrefSize(50, 30);
			game_choice4.setPrefSize(50, 30);
			game_choice5.setPrefSize(50, 30);
			game_choice6.setPrefSize(50, 30);
			game_choice7.setPrefSize(50, 30);
			game_choice8.setPrefSize(50, 30);
			
			game_choice1.setShape(new Circle(2));
			game_choice2.setShape(new Circle(2));
			game_choice3.setShape(new Circle(2));
			game_choice4.setShape(new Circle(2));
			game_choice5.setShape(new Circle(2));
			game_choice6.setShape(new Circle(2));
			game_choice7.setShape(new Circle(2));
			game_choice8.setShape(new Circle(2));
			
			game_choice1.setStyle("-fx-background-color: red;" +
					"    -fx-cursor: hand;");
			game_choice2.setStyle("-fx-background-color: blue;" +
					"    -fx-cursor: hand;");
			game_choice3.setStyle("-fx-background-color: lime;" +
					"    -fx-cursor: hand;");
			game_choice4.setStyle("-fx-background-color: yellow;" +
					"    -fx-cursor: hand;");
			game_choice5.setStyle("-fx-background-color: purple;" +
					"    -fx-cursor: hand;");
			game_choice6.setStyle("-fx-background-color: black;" +
					"    -fx-cursor: hand;");
			game_choice7.setStyle("-fx-background-color: darkgreen;" +
					"    -fx-cursor: hand;");
			game_choice8.setStyle("-fx-background-color: brown;" +
					"    -fx-cursor: hand;");
			
			game_answer1 = new Label();
			game_answer2 = new Label();
			game_answer3 = new Label();
			game_answer4 = new Label();
			

			game_answer1.setPrefSize(50, 30);
			game_answer2.setPrefSize(50, 30);
			game_answer3.setPrefSize(50, 30);
			game_answer4.setPrefSize(50, 30);
			
			game_answer1.setShape(new Circle(2));
			game_answer2.setShape(new Circle(2));
			game_answer3.setShape(new Circle(2));
			game_answer4.setShape(new Circle(2));
			
			game_answer1.setStyle("-fx-background-color: gray");
			game_answer2.setStyle("-fx-background-color: gray");
			game_answer3.setStyle("-fx-background-color: gray");
			game_answer4.setStyle("-fx-background-color: gray");
			
			
			Pane spring1 = new Pane();
			Pane spring2 = new Pane();
			//spring.minWidthProperty().bind(game_choice1.widthProperty());
			spring1.setMinWidth(20);
			spring2.setMinWidth(20);
			game_grid = new GridPane();
			game_grid.setHgap(5);
			game_grid.setVgap(5);
			//game_grid.setPadding(new Insets(10, 10, 10, 10)); //margins around the whole grid
			                                             //(top/right/bottom/left)
			game_grid.add(game_choice1, 0, 0);
			game_grid.add(game_choice2, 1, 0);
			game_grid.add(game_choice3, 2, 0);
			game_grid.add(game_choice4, 3, 0);
			game_grid.add(game_choice5, 0, 1);
			game_grid.add(game_choice6, 1, 1);
			game_grid.add(game_choice7, 2, 1);
			game_grid.add(game_choice8, 3, 1);
			
			Line line1 = new Line(330, 0, 330, 410);
			Line line2 = new Line(0, 50, 330, 50);
			game_grid.add(line1, 4, 0, 1, 20);
			game_grid.add(line2, 0, 2, 9, 1);
			
			game_grid.add(spring1, 5, 0, 1, 2);
			game_grid.add(game_timer, 6, 0);
			
			//game_grid.add(spring2, 5, 1);
			game_grid.add(game_exit, 6, 1);
			
			game_grid.add(spring2, 0, 3, 9, 2);
			game_grid.add(game_answer1, 0, 5);
			game_grid.add(game_answer2, 1, 5);
			game_grid.add(game_answer3, 2, 5);
			game_grid.add(game_answer4, 3, 5);
			
		    game_pane = new ScrollPane(game_grid);
		    game_pane.setStyle("-fx-padding: 10;" + 
	                "-fx-border-style: solid inside;" + 
	                "-fx-border-width: 2;" +
	                "-fx-border-insets: 5;" + 
	                "-fx-border-radius: 5;" + 
	                "-fx-border-color: black;");
		    game_scene = new Scene(game_pane, 500, 400);
			
			//scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			
			
			window.setScene(main_scene);
			//window.setScene(game_scene);
			window.show();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public class Mastermind {
		Random rand=new Random();
		
		private int code_length; 		// amount of digits in a code, usually between 4 and 8
		private int max_moves;   		// amount of moves (guesses) player has before he loses
		private int max_number;  		// range of digits in a code, from 1 to max_number
		private boolean duplicates; 	// are duplicated digits in a code allowed
		
		private int current_move=1;		// index for moves, if it reaches max_moves player loses
		private int[] correct_code;		// array that holds the digits of a correct code
		
		
		
		public Mastermind(int code_length, int max_moves, int max_number, boolean duplicates) {
			this.code_length=code_length;
			this.max_moves=max_moves;
			this.max_number=max_number;
			this.duplicates=duplicates;
			correct_code=new int[code_length];
			
			t1 = new Timer();
	    	t1.start();

			//Create_code();
			//Play();
	    	
	    	game_exit.setOnAction(e -> 		//exit button
			{
				window.setScene(main_scene);
				if(t1!=null)
				{
					t1.terminate();
				}
			});
		}
	}
	
	public class Timer extends Thread {
		private int seconds=0;
		private int minutes=0;
		private int hours=0;
		private volatile boolean running=true;
		
		public void terminate()
		{
			running=false;
		}
		public void run()
		{
			try
			{
				while(running)
				{
					seconds++;
					if(seconds>=60)
					{
						seconds=0;
						minutes++;
						if(minutes>=60)
						{
							minutes=0;
							hours++;
						}
					}
					Platform.runLater(new Runnable() {
			            public void run() {
			            	game_timer.setText(Display_time());
			            }
			        });
					TimeUnit.SECONDS.sleep(1);
				}
				seconds=0;
				minutes=0;
				hours=0;
			}
			catch (Exception e)
			{
				System.out.println("Timer error");
			}
		}
		
		public String Display_time()	// timer is displayed in format: HH:MM:SS, if hours or minutes are missing (player did it in less than 1 hour/minute) they are not displayed at all
		{
			String sec=Integer.toString(seconds);
			if(seconds<10 && minutes>0) sec="0" + Integer.toString(seconds);
			String min=Integer.toString(minutes);
			if(minutes<10 && hours>0) min="0" + Integer.toString(minutes);
			String h=Integer.toString(hours);
			String display = sec;
			if(minutes>0)
			{
				display = min + ":" + sec;
			}
			if(hours>0)
			{
				display = h + ":" + min + ":" + sec;
			}
			return "Timer: " + display;
		}
	}
	
	public static void main(String[] args) {
		Menu m=new Menu();
		launch(args);
	}
}
