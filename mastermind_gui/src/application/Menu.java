package application;
	

//import java.awt.Insets;
//import java.util.Random;
//import java.util.Scanner;
//import java.util.concurrent.TimeUnit;

import javafx.application.Application;
import javafx.application.Platform;
//import javafx.collections.ObservableList;
//import javafx.geometry.Insets;
import javafx.geometry.Pos;
//import javafx.event.EventHandler;
//import javafx.event.ActionEvent;
import javafx.stage.Stage;
//import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
//import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;

//TODO
//komentarze do programu zaktualizowac
//mastermind to klasa wewnetrzna i nie potrzebuje przekazania zmiennych
//settings - zmiana max moves i duplicates on/off
//usunac komentarze w importach
//zrobic plik uruchamialny



public class Menu extends Application{
	
	static Stage window;
	static Scene main_scene, tutorial_scene, game_scene;
	static VBox main_menu, tutorial, game_menu; //game;
	static GridPane game_grid;
	static GridPane[] results_grid;
	static ScrollPane game_pane;
	static Button[] menu_buttons;
	static Button[] game_choices;
	static Button tut1, game_exit, game_cancel, game_again;
	static Label tut2, game_timer, game_over;
	static Label[] game_answers, game_correct, game_results, game_numbers;
	
	//static Timer t1;
	
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
			window.setHeight(620);
			window.setResizable(false);
			
			
			
			//MENU
			menu_buttons = new Button[5];
			for(int i=0;i<5;i++)
			{
				menu_buttons[i] = new Button();
				menu_buttons[i].setFont(new Font(25.0));
				menu_buttons[i].setPrefSize(400, 60);
			}
			menu_buttons[0].setText("Start game");
			menu_buttons[1].setText("Settings");
			menu_buttons[2].setText("Highscores");
			menu_buttons[3].setText("Tutorial");
			menu_buttons[4].setText("Exit");
			
			
			main_menu = new VBox(20);
			main_menu.getChildren().addAll(menu_buttons[0], menu_buttons[1], menu_buttons[2], menu_buttons[3], menu_buttons[4]);
			main_menu.setStyle("-fx-padding: 10;" + 
                     "-fx-border-style: solid inside;" + 
                     "-fx-border-width: 2;" +
                     "-fx-border-insets: 5;" + 
                     "-fx-border-radius: 5;" + 
                     "-fx-border-color: black;");
			main_scene = new Scene(main_menu,620,400);
			
			
			menu_buttons[0].setOnAction(e ->
			{
				window.setScene(game_scene);
				Mastermind game = new Mastermind(code_length, max_moves, max_number, duplicates);
				game.Initialize();
			});
			//menu_buttons[1].setOnAction(this);
			//menu_buttons[2].setOnAction(this);
			menu_buttons[3].setOnAction(e -> window.setScene(tutorial_scene));
			menu_buttons[4].setOnAction(e -> Platform.exit());
			
			
			
			
			
			//TUTORIAL
			tut1 = new Button("Go back");
			tut1.setFont(new Font(25.0));
			tut1.setPrefSize(400, 60);
			tut2 = new Label("The game of Mastermind is a simple (yet intriguing) game of logic. The computer generates a code consisting of " + code_length +
					" colors. \nYour role is to crack that code. You have " + max_moves  + 
					" moves. On each move, you try to guess the correct code by choosing " + code_length + " colors." + 
					"\nSmall circles on the right represent the results of your guess."+
					" Each red circle represents a single color in the right place and each yellow circle represents a color in code, but in a different place." + 
					"\nBy analyzing these results, excluding the incorrect colors and confirming the place of correct colors, you can crack the code!" + 
					"\nRemember: Your time and moves are being counted!");
			tut2.setWrapText(true);
			
			tutorial=new VBox(200);
			tutorial.getChildren().addAll(tut2,tut1);
			tutorial.setStyle("-fx-padding: 10;" + 
                    "-fx-border-style: solid inside;" + 
                    "-fx-border-width: 2;" +
                    "-fx-border-insets: 5;" + 
                    "-fx-border-radius: 5;" + 
                    "-fx-border-color: black;");
			tutorial_scene = new Scene(tutorial,620,400);
		
			tut1.setOnAction(e -> window.setScene(main_scene));
			
			
			
			//GAME
			game_cancel = new Button("Cancel move");
			game_exit = new Button("Exit");
			game_again = new Button("Play again");
			game_timer = new Label();
			game_over = new Label("You lost!");
			game_cancel.setPrefWidth(90);
			game_exit.setPrefWidth(90);
			game_again.setPrefWidth(90);
			game_over.setPrefWidth(90);
			game_over.setVisible(false);
			
			game_choices = new Button[8];
			for(int i=0; i<8; i++)
			{
				game_choices[i] = new Button();
				game_choices[i].setPrefSize(50, 30);
				game_choices[i].setShape(new Circle(2));
			}
			
			game_choices[0].setStyle("-fx-background-color: red;" +
					"    -fx-cursor: hand;");
			game_choices[1].setStyle("-fx-background-color: royalblue;" +
					"    -fx-cursor: hand;");
			game_choices[2].setStyle("-fx-background-color: lime;" +
					"    -fx-cursor: hand;");
			game_choices[3].setStyle("-fx-background-color: gold;" +
					"    -fx-cursor: hand;");
			game_choices[4].setStyle("-fx-background-color: blueviolet;" +
					"    -fx-cursor: hand;");
			game_choices[5].setStyle("-fx-background-color: black;" +
					"    -fx-cursor: hand;");
			game_choices[6].setStyle("-fx-background-color: darkgreen;" +
					"    -fx-cursor: hand;");
			game_choices[7].setStyle("-fx-background-color: sienna;" +
					"    -fx-cursor: hand;");
			
			
			game_answers = new Label[(max_moves*code_length)];
			game_results = new Label[(max_moves*code_length)];
			game_correct = new Label[code_length];
			game_numbers = new Label[max_moves];
			for(int i=0;i<(max_moves*code_length);i++)
			{
				
				game_answers[i] = new Label();
				game_answers[i].setPrefSize(50, 30);
				game_answers[i].setShape(new Circle(2));
				game_answers[i].setStyle("-fx-background-color: lightgray");
				
				
				game_results[i] = new Label();
				game_results[i].setPrefSize(15,15);
				game_results[i].setShape(new Circle(1));
				game_results[i].setStyle("-fx-background-color: lightgray");
				game_results[i].setVisible(false);
			}
			
			for(int i=0;i<code_length;i++)
			{
				game_correct[i] = new Label("?");
				game_correct[i].setPrefSize(50, 30);
				game_correct[i].setShape(new Circle(2));
				game_correct[i].setStyle("-fx-background-color: gray");
				game_correct[i].setAlignment(Pos.BASELINE_CENTER);
				game_correct[i].setFont(new Font(20.0));
			}
			
			for(int i=0;i<max_moves;i++)
			{
				game_numbers[i] = new Label(Integer.toString(i+1));
			}
			
			

			game_grid = new GridPane();
			game_grid.setHgap(5);
			game_grid.setVgap(2);
			
			Line line1 = new Line(330, 0, 330, 460);
			Line line2 = new Line(0, 50, 340, 50);
			Line line3 = new Line(0, 50, 340, 50);
			game_grid.add(line1, 5, 0, 1, 27);
			game_grid.add(line2, 0, 2, 9, 1);
			game_grid.add(line3, 0, 26, 9, 1);
			
			Line[] lines = new Line[max_moves-1];
			for(int i=0;i<max_moves-1;i++)
			{
				lines[i] = new Line(0,50,340,50);
				lines[i].setStyle("-fx-stroke: lightgrey;");
				game_grid.add(lines[i], 0, 6+(i*2), 9, 1 );
				
			}
				
			Pane spring1 = new Pane();
			Pane spring2 = new Pane();
			Pane spring3 = new Pane();
			Pane spring4 = new Pane();
			spring1.setMinWidth(10);
			spring2.setMinWidth(20);
			spring3.setMinHeight(10);
			spring4.setMinHeight(10);
			game_grid.add(spring1, 6, 0, 1, 2);
			game_grid.add(spring2, 0, 3, 9, 2);
			game_grid.add(spring3, 0, 27, 9, 1);
			game_grid.add(spring4, 0, 29, 9, 1);
			
			
			game_grid.add(game_timer, 7, 0);
			game_grid.add(game_cancel, 7, 1);
			

			for(int i=0; i<max_number; i++)
			{
				game_grid.add(game_choices[i], i%code_length+1, i/code_length);
			}
			
			for(int i=0;i<max_moves;i++)
			{
				game_grid.add(game_numbers[i], 0 , 5+i*2);
			}
			
			for(int i=0;i<(max_moves*code_length);i++)
			{
				game_grid.add(game_answers[i], i%code_length+1, 5+(i/code_length*2));
			}
			
			for(int i=0;i<code_length;i++)
			{
				game_grid.add(game_correct[i], i%code_length+1, 28);
			}
			
			results_grid = new GridPane[max_moves];
			for(int i=0;i<max_moves;i++)
			{
				results_grid[i] = new GridPane();
				results_grid[i].setHgap(2);
				results_grid[i].setVgap(1);
				results_grid[i].add(game_results[i*code_length+0], 0, 0);
				results_grid[i].add(game_results[i*code_length+1], 1, 0);
				results_grid[i].add(game_results[i*code_length+2], 0, 1);
				results_grid[i].add(game_results[i*code_length+3], 1, 1);
				game_grid.add(results_grid[i], 7, 5+i*2);
			}
			
			game_grid.add(game_over, 7, 28);
			game_grid.add(game_exit, 1, 30, 2, 1);
			game_grid.add(game_again, 7, 30);
			
			//game_grid.setStyle("-fx-background-color: white");
			
			
			
			game_menu = new VBox();
			game_menu.getChildren().add(game_grid);
			game_menu.setStyle("-fx-padding: 10;" + 
                     "-fx-border-style: solid inside;" + 
                     "-fx-border-width: 2;" +
                     "-fx-border-insets: 5;" + 
                     "-fx-border-radius: 5;" + 
                     "-fx-border-color: black;");
			
			game_scene = new Scene(game_menu, 620, 400);
			
			//scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			
			
			window.setScene(main_scene);
			window.show();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	
	
	
	public static void main(String[] args) {
		Menu m=new Menu();
		launch(args);
	}
}
