package application;
	

//import java.awt.Insets;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
//import javafx.event.EventHandler;
//import javafx.event.ActionEvent;
import javafx.stage.Stage;
import javafx.scene.Node;
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

//TODO
//komentarze do programu zaktualizowac
//mastermind to klasa wewnetrzna i nie potrzebuje przekazania zmiennych



public class Menu extends Application{
	
	Stage window;
	Scene main_scene, tutorial_scene, game_scene;
	VBox main_menu, tutorial, game_menu; //game;
	GridPane game_grid;
	GridPane[] results_grid;
	ScrollPane game_pane;
	Button[] menu_buttons;
	Button[] game_choices;
	Button tut1, game_exit, game_cancel, game_again;
	Label tut2, game_timer, game_over;
	Label[] game_answers, game_correct, game_results, game_numbers;
	
	
	public static Button test_button;
	
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
			
			test_button=new Button("test");
			main_menu.getChildren().add(test_button);
			
			
			menu_buttons[0].setOnAction(e ->
			{
				window.setScene(game_scene);
				//Test test_class=new Test();
				//test_class.Change();
				Mastermind game = new Mastermind(code_length, max_moves, max_number, duplicates);
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
		
		private boolean is_over;
		
		
		
		public Mastermind(int code_length, int max_moves, int max_number, boolean duplicates) {
			this.code_length=code_length;
			this.max_moves=max_moves;
			this.max_number=max_number;
			this.duplicates=duplicates;
			correct_code=new int[code_length];
			current_code=new int[code_length];
			is_over=false;
			
			t1 = new Timer();
	    	t1.start();

			Create_code();
	    	
	    	game_exit.setOnAction(e -> 		//exit button
			{
				for(int i=0;i<(max_moves*code_length);i++)
				{
					game_answers[i].setStyle("-fx-background-color: lightgray");
					game_results[i].setVisible(false);
				}
				for(int i=0; i<code_length;i++)
	    		{
	    			game_correct[i].setStyle("-fx-background-color: gray");
	    			game_correct[i].setText("?");
	    		}
				game_over.setVisible(false);
				window.setScene(main_scene);
				if(t1!=null)
				{
					t1.Terminate();
				}
			});
	    	
	    	game_again.setOnAction(e ->
	    	{
	    		is_over=false;
	    		for(int i=0;i<(max_moves*code_length);i++)
				{
					game_answers[i].setStyle("-fx-background-color: lightgray");
					game_results[i].setVisible(false);
				}
	    		for(int i=0; i<code_length;i++)
	    		{
	    			current_code[i]=0;
	    			game_correct[i].setStyle("-fx-background-color: gray");
	    			game_correct[i].setText("?");
	    		}
	    		current_move=1;
	    		current_element=0;
	    		Create_code();
	    		game_over.setVisible(false);
	    		if(t1!=null)
	    		{
	    			t1.Start_again();
	    		}
	    		
	    	});
	    	game_cancel.setOnAction(e ->
	    	{
	    		if(current_element>0 && (current_move-1)*code_length+current_element < max_moves*code_length && !is_over)
	    		{
	    			current_element--;
	    			current_code[current_element]=0;
		    		game_answers[(current_move-1)*code_length+current_element].setStyle("-fx-background-color:lightgrey");
	    		}
	    	});
	    	
	    	game_choices[0].setOnAction(e ->
	    	{
	    		if((current_move-1)*code_length+current_element < max_moves*code_length && !is_over)
	    		{
	    			current_code[current_element]=1;
		    		game_answers[(current_move-1)*code_length+current_element].setStyle("-fx-background-color:red");
		    		if(Is_finished()) Play();
		    		else current_element++;
	    		}
	    	});
	    	game_choices[1].setOnAction(e ->
	    	{
	    		if((current_move-1)*code_length+current_element < max_moves*code_length && !is_over)
	    		{
	    			current_code[current_element]=2;
		    		game_answers[(current_move-1)*code_length+current_element].setStyle("-fx-background-color:royalblue");
		    		if(Is_finished()) Play();
		    		else current_element++;
	    		}
	    		
	    	});
	    	game_choices[2].setOnAction(e ->
	    	{
	    		if((current_move-1)*code_length+current_element < max_moves*code_length && !is_over)
	    		{
	    			current_code[current_element]=3;
		    		game_answers[(current_move-1)*code_length+current_element].setStyle("-fx-background-color:lime");
		    		if(Is_finished()) Play();
		    		else current_element++;
	    		}
	    	});
	    	game_choices[3].setOnAction(e ->
	    	{
	    		if((current_move-1)*code_length+current_element < max_moves*code_length && !is_over)
	    		{
	    			current_code[current_element]=4;
		    		game_answers[(current_move-1)*code_length+current_element].setStyle("-fx-background-color:gold");
		    		if(Is_finished()) Play();
		    		else current_element++;
	    		}
	    	});
	    	game_choices[4].setOnAction(e ->
	    	{
	    		if((current_move-1)*code_length+current_element < max_moves*code_length && !is_over)
	    		{
	    			current_code[current_element]=5;
		    		game_answers[(current_move-1)*code_length+current_element].setStyle("-fx-background-color:blueviolet");
		    		if(Is_finished()) Play();
		    		else current_element++;
	    		}
	    	});
	    	game_choices[5].setOnAction(e ->
	    	{
	    		if((current_move-1)*code_length+current_element < max_moves*code_length && !is_over)
	    		{
	    			current_code[current_element]=6;
		    		game_answers[(current_move-1)*code_length+current_element].setStyle("-fx-background-color:black");
		    		if(Is_finished()) Play();
		    		else current_element++;
	    		}
	    	});
	    	game_choices[6].setOnAction(e ->
	    	{
	    		if((current_move-1)*code_length+current_element < max_moves*code_length && !is_over)
	    		{
	    			current_code[current_element]=7;
		    		game_answers[(current_move-1)*code_length+current_element].setStyle("-fx-background-color:darkgreen");
		    		if(Is_finished()) Play();
		    		else current_element++;
	    		}
	    	});
	    	game_choices[7].setOnAction(e ->
	    	{
	    		if((current_move-1)*code_length+current_element < max_moves*code_length && !is_over)
	    		{
	    			current_code[current_element]=8;
		    		game_answers[(current_move-1)*code_length+current_element].setStyle("-fx-background-color: sienna");
		    		if(Is_finished()) Play();
		    		else current_element++;
	    		}
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
			if(Is_won())				// if the code is fully correct, the game is won
			{
				Game_over(true);
			}
			else
			{
				Check_move();			// compare the player code to the winning code
				current_move++;
			}
			if(current_move>max_moves)	Game_over(false);		// if the player used all his moves and didn't guess the code, the game is lost
			current_element=0;
			for(int i=0;i<code_length;i++)
			{
				current_code[i]=0;
			}
			
		}
		
		public void Check_move() // this method returns the amount of perfect digits (in the right place) and wrongly placed digits (they are in code, but in different place)
		{
			int[] support_dub = new int[code_length];
			for(int i=0;i<code_length;i++)		//0-nothing, 1-wrong place, 2-perfect
			{
				support_dub[i]=0;
			}
			int perfect=0;
			int wrong_place=0;
			for(int i=0;i<code_length;i++)
			{
				boolean dup=Check_duplicates_move(i);
				for(int j=0;j<code_length;j++)
				{
					if(current_code[i] == correct_code[j])
					{
						if(i==j && !dup)
						{
							perfect++;
							support_dub[i]=2;
						}
						else if(i==j && dup)
						{
							perfect++;
							support_dub[i]=2;
							wrong_place--;
						}
						
						else if(!dup)
						{
							wrong_place++;
							support_dub[i]=1;
						}
					}
				}
			}
			Results_move(perfect,wrong_place);
		}
		
		public boolean Check_duplicates_move(int l)   // this method supplements the Check_move(String move) method so that it gives the correct answers
		{
			if(duplicates) return false;
			for(int i=0;i<l;i++)
			{
				if(current_code[i]==current_code[l])
				{
					return true;
				}
			}
			return false;
		}
		
		public void Results_move(int perfect, int wrong_place)
		{
			for(int i=0;i<code_length;i++)
			{
				if(perfect>0)
				{
					game_results[i+(current_move-1)*code_length].setStyle("-fx-background-color:red");
					game_results[i+(current_move-1)*code_length].setVisible(true);
					perfect--;
				}
				else if(wrong_place>0)
				{
					game_results[i+(current_move-1)*code_length].setStyle("-fx-background-color:gold");
					game_results[i+(current_move-1)*code_length].setVisible(true);
					wrong_place--;
				}
				else	
				{
					game_results[i+(current_move-1)*code_length].setStyle("-fx-background-color:lightgray");
					game_results[i+(current_move-1)*code_length].setVisible(true);
				}
			}
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
		
		
		public void Game_over(boolean win)
		{
			is_over=true;
			game_over.setVisible(true);
			if(t1!=null)
    		{
				t1.Terminate();
    		}
			
			for(int i=0;i<code_length;i++)
			{
				game_correct[i].setText("");
				if(correct_code[i]==1)	game_correct[i].setStyle("-fx-background-color:red");
				else if(correct_code[i]==2)	game_correct[i].setStyle("-fx-background-color:royalblue");
				else if(correct_code[i]==3)	game_correct[i].setStyle("-fx-background-color:lime");
				else if(correct_code[i]==4)	game_correct[i].setStyle("-fx-background-color:gold");
				else if(correct_code[i]==5)	game_correct[i].setStyle("-fx-background-color:blueviolet");
				else if(correct_code[i]==6)	game_correct[i].setStyle("-fx-background-color:black");
				else if(correct_code[i]==7)	game_correct[i].setStyle("-fx-background-color:darkgreen");
				else if(correct_code[i]==8)	game_correct[i].setStyle("-fx-background-color:sienna");
			}
			
			for(int i=0;i<max_moves*code_length;i++)
			{
				game_results[i].setVisible(false);
			}
			
			
			Stage game_over_stage = new Stage();
			game_over_stage.setWidth(200);
			game_over_stage.setHeight(200);
			game_over_stage.setResizable(false);
			
			Label game_over_label = new Label();
			game_over_label.setFont(new Font(15.0));
			Button game_over_button = new Button("Close");
			game_over_button.setFont(new Font(15.0));
			game_over_button.setOnAction(e -> game_over_stage.close());
			VBox game_over_layout = new VBox(10);
			game_over_layout.getChildren().addAll(game_over_label,game_over_button);
			Scene game_over_scene = new Scene(game_over_layout);
			game_over_stage.setScene(game_over_scene);
			game_over_stage.show();
			game_over_layout.setStyle("-fx-padding: 10;" + 
                    "-fx-border-style: solid inside;" + 
                    "-fx-border-width: 2;" +
                    "-fx-border-insets: 5;" + 
                    "-fx-border-radius: 5;" + 
                    "-fx-border-color: black;");
			
			if(win)
			{
				Results_move(4,0);
				game_over_label.setText("You won!\nMoves: " + (current_move) + "/" +max_moves +"\n" + t1.Display_time());
				game_over.setText("You won!");
			}
			else
			{
				game_over_label.setText("You lost!\nMoves: " + (current_move-1) + "/" +max_moves +"\n" + t1.Display_time());
				game_over.setText("You lost!");
			}
		}
		

		
		public boolean Check_duplicates(int number, int l) // used if dupplicates are not allowed
		{
			for(int i=0; i<=l; i++)
			{
				if(correct_code[i]==number) return true;
			}
			return false;
		}
	}
	
	public class Timer extends Thread {
		private int seconds=0;
		private int minutes=0;
		private int hours=0;
		private volatile boolean running=true;
		
		public void Terminate()
		{
			running=false;
		}
		public void Start_again()
		{
			seconds=0;
			minutes=0;
			hours=0;
			running=true;
		}
		public void run()
		{
			try
			{
				while(true)
				{
					if(running)
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
				}
				//seconds=0;
				//minutes=0;
				//hours=0;
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
