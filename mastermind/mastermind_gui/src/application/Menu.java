package application;
	

//import java.awt.Insets;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
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
import javafx.scene.text.Font;


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
	Label tut2, game_timer, 
	game_answer1, game_answer2, game_answer3, game_answer4,
	game_answer5, game_answer6, game_answer7, game_answer8,
	game_answer9, game_answer10, game_answer11, game_answer12,
	game_answer13, game_answer14, game_answer15, game_answer16,
	game_answer17, game_answer18, game_answer19, game_answer20,
	game_answer21, game_answer22, game_answer23, game_answer24,
	game_answer25, game_answer26, game_answer27, game_answer28,
	game_answer29, game_answer30, game_answer31, game_answer32,
	game_answer33, game_answer34, game_answer35, game_answer36,
	game_answer37, game_answer38, game_answer39, game_answer40;
	
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
			window.setHeight(530);
			window.setResizable(false);
			
			
			//MENU
			menu_start = new Button("Start game");
			menu_set = new Button("Settings");
			menu_high = new Button("Highscores");
			menu_tut = new Button("Tutorial");
			menu_exit = new Button("Exit");
			
			menu_start.setFont(new Font(25.0));
			menu_set.setFont(new Font(25.0));
			menu_high.setFont(new Font(25.0));
			menu_tut.setFont(new Font(25.0));
			menu_exit.setFont(new Font(25.0));
		
			menu_start.setPrefSize(400, 60);
			menu_set.setPrefSize(400, 60);
			menu_high.setPrefSize(400, 60);
			menu_tut.setPrefSize(400, 60);
			menu_exit.setPrefSize(400, 60);
			
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
			main_scene = new Scene(main_menu,530,400);
			
			//menu_set.setOnAction(this);
			//menu_high.setOnAction(this);
			menu_tut.setOnAction(e -> window.setScene(tutorial_scene));
			menu_exit.setOnAction(e -> Platform.exit());
			
			
			
			
			
			//TUTORIAL
			tut1 = new Button("Go back");
			tut1.setFont(new Font(25.0));
			tut1.setPrefSize(400, 60);
			//TODO change numbers to colors
			tut2 = new Label("The game of Mastermind is a simple (yet intriguing) game of logic. The computer generates a " + code_length + 
					"-digit long code, consisting of numbers from 1 to " + max_number + ". Your role is to crack that code.\nYou have " + max_moves  + 
					" moves. On each move, you try to guess the correct code by typing your own " + code_length + "-digit long code. " + 
					"Each time you do it, You get the information about how many digits are in the right place, and how many are in the code but in the wrong place. " + 
					"By analyzing these results, excluding the incorrect numbers and confirming the place of correct numbers, you can crack the code!" + 
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
			tutorial_scene = new Scene(tutorial,530,400);
		
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
			game_answer5 = new Label();
			game_answer6 = new Label();
			game_answer7 = new Label();
			game_answer8 = new Label();
			game_answer9 = new Label();
			game_answer10 = new Label();
			game_answer11 = new Label();
			game_answer12 = new Label();
			game_answer13 = new Label();
			game_answer14 = new Label();
			game_answer15 = new Label();
			game_answer16 = new Label();
			game_answer17 = new Label();
			game_answer18 = new Label();
			game_answer19 = new Label();
			game_answer20 = new Label();
			game_answer21 = new Label();
			game_answer22 = new Label();
			game_answer23 = new Label();
			game_answer24 = new Label();
			game_answer25 = new Label();
			game_answer26 = new Label();
			game_answer27 = new Label();
			game_answer28 = new Label();
			game_answer29 = new Label();
			game_answer30 = new Label();
			game_answer31 = new Label();
			game_answer32 = new Label();
			game_answer33 = new Label();
			game_answer34 = new Label();
			game_answer35 = new Label();
			game_answer36 = new Label();
			game_answer37 = new Label("?");
			game_answer38 = new Label("?");
			game_answer39 = new Label("?");
			game_answer40 = new Label("?");
			
			game_answer37.setAlignment(Pos.BASELINE_CENTER);
			game_answer37.setFont(new Font(20.0));
			game_answer38.setAlignment(Pos.BASELINE_CENTER);
			game_answer38.setFont(new Font(20.0));
			game_answer39.setAlignment(Pos.BASELINE_CENTER);
			game_answer39.setFont(new Font(20.0));
			game_answer40.setAlignment(Pos.BASELINE_CENTER);
			game_answer40.setFont(new Font(20.0));
		

			game_answer1.setPrefSize(50, 30);
			//game_answer2.setPrefSize(50, 30);
			game_answer3.setPrefSize(50, 30);
			game_answer4.setPrefSize(50, 30);
			game_answer5.setPrefSize(50, 30);
			game_answer6.setPrefSize(50, 30);
			game_answer7.setPrefSize(50, 30);
			game_answer8.setPrefSize(50, 30);
			game_answer9.setPrefSize(50, 30);
			game_answer10.setPrefSize(50, 30);
			game_answer11.setPrefSize(50, 30);
			game_answer12.setPrefSize(50, 30);
			game_answer13.setPrefSize(50, 30);
			game_answer14.setPrefSize(50, 30);
			game_answer15.setPrefSize(50, 30);
			game_answer16.setPrefSize(50, 30);
			game_answer17.setPrefSize(50, 30);
			game_answer18.setPrefSize(50, 30);
			game_answer19.setPrefSize(50, 30);
			game_answer20.setPrefSize(50, 30);
			game_answer21.setPrefSize(50, 30);
			game_answer22.setPrefSize(50, 30);
			game_answer23.setPrefSize(50, 30);
			game_answer24.setPrefSize(50, 30);
			game_answer25.setPrefSize(50, 30);
			game_answer26.setPrefSize(50, 30);
			game_answer27.setPrefSize(50, 30);
			game_answer28.setPrefSize(50, 30);
			game_answer29.setPrefSize(50, 30);
			game_answer30.setPrefSize(50, 30);
			game_answer31.setPrefSize(50, 30);
			game_answer32.setPrefSize(50, 30);
			game_answer33.setPrefSize(50, 30);
			game_answer34.setPrefSize(50, 30);
			game_answer35.setPrefSize(50, 30);
			game_answer36.setPrefSize(50, 30);
			game_answer37.setPrefSize(50, 30);
			game_answer38.setPrefSize(50, 30);
			game_answer39.setPrefSize(50, 30);
			game_answer40.setPrefSize(50, 30);
			
			
			game_answer1.setShape(new Circle(2));
			//game_answer2.setShape(new Circle(2));
			game_answer3.setShape(new Circle(2));
			game_answer4.setShape(new Circle(2));
			game_answer5.setShape(new Circle(2));
			game_answer6.setShape(new Circle(2));
			game_answer7.setShape(new Circle(2));
			game_answer8.setShape(new Circle(2));
			game_answer9.setShape(new Circle(2));
			game_answer10.setShape(new Circle(2));
			game_answer11.setShape(new Circle(2));
			game_answer12.setShape(new Circle(2));
			game_answer13.setShape(new Circle(2));
			game_answer14.setShape(new Circle(2));
			game_answer15.setShape(new Circle(2));
			game_answer16.setShape(new Circle(2));
			game_answer17.setShape(new Circle(2));
			game_answer18.setShape(new Circle(2));
			game_answer19.setShape(new Circle(2));
			game_answer20.setShape(new Circle(2));
			game_answer21.setShape(new Circle(2));
			game_answer22.setShape(new Circle(2));
			game_answer23.setShape(new Circle(2));
			game_answer24.setShape(new Circle(2));
			game_answer25.setShape(new Circle(2));
			game_answer26.setShape(new Circle(2));
			game_answer27.setShape(new Circle(2));
			game_answer28.setShape(new Circle(2));
			game_answer29.setShape(new Circle(2));
			game_answer30.setShape(new Circle(2));
			game_answer31.setShape(new Circle(2));
			game_answer32.setShape(new Circle(2));
			game_answer33.setShape(new Circle(2));
			game_answer34.setShape(new Circle(2));
			game_answer35.setShape(new Circle(2));
			game_answer36.setShape(new Circle(2));
			game_answer37.setShape(new Circle(2));
			game_answer38.setShape(new Circle(2));
			game_answer39.setShape(new Circle(2));
			game_answer40.setShape(new Circle(2));
			
			
			game_answer1.setStyle("-fx-background-color: lightgray");
			//game_answer2.setStyle("-fx-background-color: lightgray");
			game_answer3.setStyle("-fx-background-color: lightgray");
			game_answer4.setStyle("-fx-background-color: lightgray");
			game_answer5.setStyle("-fx-background-color: lightgray");
			game_answer6.setStyle("-fx-background-color: lightgray");
			game_answer7.setStyle("-fx-background-color: lightgray");
			game_answer8.setStyle("-fx-background-color: lightgray");
			game_answer9.setStyle("-fx-background-color: lightgray");
			game_answer10.setStyle("-fx-background-color: lightgray");
			game_answer11.setStyle("-fx-background-color: lightgray");
			game_answer12.setStyle("-fx-background-color: lightgray");
			game_answer13.setStyle("-fx-background-color: lightgray");
			game_answer14.setStyle("-fx-background-color: lightgray");
			game_answer15.setStyle("-fx-background-color: lightgray");
			game_answer16.setStyle("-fx-background-color: lightgray");
			game_answer17.setStyle("-fx-background-color: lightgray");
			game_answer18.setStyle("-fx-background-color: lightgray");
			game_answer19.setStyle("-fx-background-color: lightgray");
			game_answer20.setStyle("-fx-background-color: lightgray");
			game_answer21.setStyle("-fx-background-color: lightgray");
			game_answer22.setStyle("-fx-background-color: lightgray");
			game_answer23.setStyle("-fx-background-color: lightgray");
			game_answer24.setStyle("-fx-background-color: lightgray");
			game_answer25.setStyle("-fx-background-color: lightgray");
			game_answer26.setStyle("-fx-background-color: lightgray");
			game_answer27.setStyle("-fx-background-color: lightgray");
			game_answer28.setStyle("-fx-background-color: lightgray");
			game_answer29.setStyle("-fx-background-color: lightgray");
			game_answer30.setStyle("-fx-background-color: lightgray");
			game_answer31.setStyle("-fx-background-color: lightgray");
			game_answer32.setStyle("-fx-background-color: lightgray");
			game_answer33.setStyle("-fx-background-color: lightgray");
			game_answer34.setStyle("-fx-background-color: lightgray");
			game_answer35.setStyle("-fx-background-color: lightgray");
			game_answer36.setStyle("-fx-background-color: lightgray");
			game_answer37.setStyle("-fx-background-color: lightgray");
			game_answer38.setStyle("-fx-background-color: lightgray");
			game_answer39.setStyle("-fx-background-color: lightgray");
			game_answer40.setStyle("-fx-background-color: lightgray");
			
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
			
			Line line1 = new Line(330, 0, 330, 450);
			Line line2 = new Line(0, 50, 340, 50);
			game_grid.add(line1, 4, 0, 1, 16);
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
			game_grid.add(game_answer5, 0, 6);
			game_grid.add(game_answer6, 1, 6);
			game_grid.add(game_answer7, 2, 6);
			game_grid.add(game_answer8, 3, 6);
			game_grid.add(game_answer9, 0, 7);
			game_grid.add(game_answer10, 1, 7);
			game_grid.add(game_answer11, 2, 7);
			game_grid.add(game_answer12, 3, 7);
			game_grid.add(game_answer13, 0, 8);
			game_grid.add(game_answer14, 1, 8);
			game_grid.add(game_answer15, 2, 8);
			game_grid.add(game_answer16, 3, 8);
			game_grid.add(game_answer17, 0, 9);
			game_grid.add(game_answer18, 1, 9);
			game_grid.add(game_answer19, 2, 9);
			game_grid.add(game_answer20, 3, 9);
			game_grid.add(game_answer21, 0, 10);
			game_grid.add(game_answer22, 1, 10);
			game_grid.add(game_answer23, 2, 10);
			game_grid.add(game_answer24, 3, 10);
			game_grid.add(game_answer25, 0, 11);
			game_grid.add(game_answer26, 1, 11);
			game_grid.add(game_answer27, 2, 11);
			game_grid.add(game_answer28, 3, 11);
			game_grid.add(game_answer29, 0, 12);
			game_grid.add(game_answer30, 1, 12);
			game_grid.add(game_answer31, 2, 12);
			game_grid.add(game_answer32, 3, 12);
			game_grid.add(game_answer33, 0, 13);
			game_grid.add(game_answer34, 1, 13);
			game_grid.add(game_answer35, 2, 13);
			game_grid.add(game_answer36, 3, 13);
			game_grid.add(game_answer37, 0, 14);
			game_grid.add(game_answer38, 1, 14);
			game_grid.add(game_answer39, 2, 14);
			game_grid.add(game_answer40, 3, 14);
			
			
		    game_pane = new ScrollPane(game_grid);
		    game_pane.setStyle("-fx-padding: 10;" + 
	                "-fx-border-style: solid inside;" + 
	                "-fx-border-width: 2;" +
	                "-fx-border-insets: 5;" + 
	                "-fx-border-radius: 5;" + 
	                "-fx-border-color: black;");
		    game_scene = new Scene(game_pane, 530, 400);
			
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
		private int current_element=0;
		private int[] current_code;
		private int[] correct_code;		// array that holds the digits of a correct code
		
		
		
		public Mastermind(int code_length, int max_moves, int max_number, boolean duplicates) {
			this.code_length=code_length;
			this.max_moves=max_moves;
			this.max_number=max_number;
			this.duplicates=duplicates;
			correct_code=new int[code_length];
			current_code=new int[code_length];
			
			t1 = new Timer();
	    	t1.start();

			Create_code();
			//Play();
	    	
	    	Display_code();
	    	
	    	game_exit.setOnAction(e -> 		//exit button
			{
				window.setScene(main_scene);
				if(t1!=null)
				{
					t1.terminate();
				}
			});
	    	
	    	game_choice1.setOnAction(e ->
	    	{
	    		current_code[current_element]=1;
	    		if(Is_finished()) Play();
	    		else current_element++;
	    	});
	    	game_choice2.setOnAction(e ->
	    	{
	    		current_code[current_element]=2;
	    		if(Is_finished()) Play();
	    		else current_element++;
	    	});
	    	game_choice3.setOnAction(e ->
	    	{
	    		current_code[current_element]=3;
	    		if(Is_finished()) Play();
	    		else current_element++;
	    	});
	    	game_choice4.setOnAction(e ->
	    	{
	    		current_code[current_element]=4;
	    		if(Is_finished()) Play();
	    		else current_element++;
	    	});
	    	game_choice5.setOnAction(e ->
	    	{
	    		current_code[current_element]=5;
	    		if(Is_finished()) Play();
	    		else current_element++;
	    	});
	    	game_choice6.setOnAction(e ->
	    	{
	    		current_code[current_element]=6;
	    		if(Is_finished()) Play();
	    		else current_element++;
	    	});
	    	game_choice7.setOnAction(e ->
	    	{
	    		current_code[current_element]=7;
	    		if(Is_finished()) Play();
	    		else current_element++;
	    	});
	    	game_choice8.setOnAction(e ->
	    	{
	    		current_code[current_element]=8;
	    		if(Is_finished()) Play();
	    		else current_element++;
	    	});
		}
		
		public boolean Is_finished()
		{
			for(int i=0;i<code_length;i++)
			{
				if(current_code[i]==0) return false; 
			}
			return true;
		}
		
		public void Create_code()		// generate the correct code by random
		{
			for(int i=0;i<code_length;i++)
			{
			    int random=rand.nextInt(max_number)+1;
				while(!duplicates && Check_duplicates(random,i))  // if the duplicates are turned off, we have to change digits that are already in the code
				{
					random=rand.nextInt(max_number)+1;
				}
				correct_code[i]=random;
			}
		}
		
		public void Play()		// main method, where the player makes his moves
		{
			//System.out.println("\nI have generated a " + code_length +" digits long code, consisting of numbers from 1 to " + max_number +".");
			//if(duplicates) System.out.println("Duplicates are allowed.");
			//else System.out.println("Duplicates are not allowed.");
			//System.out.println("You have " + max_moves +" moves to break the code. Good luck.");
			
			//Scanner input=new Scanner(System.in);
			
			//while(current_move<=max_moves)		// main loop, where the game takes place
			//{
				//System.out.print("\nMove #" + current_move + ": ");
				//String move=input.nextLine();
				//while(!Is_correct(current_code))		// if the move is incorrect, the player has to type it again
				//{
					//System.out.println("Error. Incorrect move");
					//move=input.nextLine();
				//}
				if(Is_won())				// if the code is fully correct, the game is won
				{
					Game_won();
					//break;
				}
				else
				{
					Check_move();			// compare the player code to the winning code
					current_move++;
				}
			//}
			if(current_move>max_moves)	Game_lost();		// if the player used all his moves and didn't guess the code, the game is lost
			current_element=0;
			for(int i=0;i<code_length;i++)
			{
				current_code[i]=0;
			}
			
		}
		
		/*public boolean Is_correct() // checks if the code that player typed is correct
		{
			if(move.length()!=code_length) return false; // the winning code, and player typed code have to be equal in length
			for(int i=0;i<move.length();i++)			 // digits in the code have to be between 1 and max_number
			{
				if(Character.getNumericValue(move.charAt(i))<1 || Character.getNumericValue(move.charAt(i))>max_number)
				{
					return false;
				}
			}
			return true;
		}*/
		
		public void Check_move() // this method returns the amount of perfect digits (in the right place) and wrongly placed digits (they are in code, but in different place)
		{
			int perfect=0;
			int wrong_place=0;
			for(int i=0;i<code_length;i++)
			{
				if(!Check_duplicates_move(i))
				{
					for(int j=0;j<code_length;j++)
					{
						if(current_code[i] == correct_code[j])
						{
							if(i==j) perfect++;
							else wrong_place++;
						}
					}
				}
			}
			System.out.println("Perfect: " + perfect + " Wrong place: " + wrong_place);
		}
		
		public boolean Check_duplicates_move(int l)   // this method supplements the Check_move(String move) method so that it gives the correct answers
		{
			for(int i=0;i<l;i++)
			{
				if(current_code[i]==current_code[l])
				//if(move.charAt(i) == move.charAt(l))
				{
					return true;
				}
			}
			return false;
		}
		
		public boolean Is_won()	//simple method, that checks if the code typed by the player equals winning code
		{
			for(int i=0;i<code_length;i++)
			{
				if(current_code[i]!=correct_code[i])
				{
					return false;
				}
			}
			return true;
		}
		
		public void Game_won()		// game results
		{
			System.out.println("\nCongratulations! You have won in " + current_move + " moves. The code was: ");
			Display_code();
			t1.Display_time();
		}
		
		public void Game_lost()		// game results
		{
			System.out.println("\nYou lost, correct code was: ");
			Display_code();
			t1.Display_time();
		}
		
		/*public String Correct_code_to_string() // change code from array of ints to string
		{
			String code="";
			for(int i=0;i<code_length;i++)
			{
				code+=Integer.toString(correct_code[i]);
			}
			return code;
		}*/
		
		public boolean Check_duplicates(int number, int l) // used if dupplicates are not allowed
		{
			for(int i=0; i<=l; i++)
			{
				if(correct_code[i]==number) return true;
			}
			return false;
		}
		public void Display_code()
		{
			for(int i=0;i<code_length;i++)
			{
				System.out.println(correct_code[i]);
			}
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
