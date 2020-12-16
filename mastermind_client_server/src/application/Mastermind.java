package application;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Mastermind {
	Random rand=new Random();
	
	private int code_length; 		// amount of colors in a code (4)
	private int max_moves;   		// amount of moves (guesses) player has before he loses
	private int max_color;  		// amount of possible colors in code (8)
	private boolean duplicates; 	// are duplicated colors in a code allowed
	
	private int current_move=1;		// index for moves, if it reaches max_moves player loses
	private int current_element=0;  // index for current part of the move
	private int[] current_code;     // array that holds the previously typed code
	private int[] correct_code;		// array that holds the correct code
	
	private Timer t1;				// for counting time, from class timer
	
	private boolean is_over;
	
	
	public Mastermind(int code_length, int max_moves, int max_color, boolean duplicates) {
		this.code_length=code_length;
		this.max_moves=max_moves;
		this.max_color=max_color;
		this.duplicates=duplicates;
		correct_code=new int[code_length];
		current_code=new int[code_length];
		is_over=false;
	}
	
	
	//sets up all the necessary components:
	//- starts timer,
	//- generates code for the current game,
	//- modifies components according to settings
	//- creates button functionality
	public void initialize()
	{
		t1 = new Timer();
    	t1.start();

		create_code();
		
		
		//to display code, delete
		for(int i=0;i<code_length;i++)
		{
			System.out.println(correct_code[i]);
		}
		
		
		for(int i=0;i<10*code_length;i++)
		{
			if(i<max_moves*code_length)	Menu.game_answers[i].setVisible(true);
			else	Menu.game_answers[i].setVisible(false);
		}
		for(int i=0;i<10;i++)
		{
			if(i<max_moves)	Menu.game_numbers[i].setVisible(true);
			else	Menu.game_numbers[i].setVisible(false);
			
			if(i<max_moves && i<9) Menu.lines[i].setVisible(true);
			else if(i<9)	Menu.lines[i].setVisible(false);
		}
		for(int i=0;i<8;i++)
		{
			if(i<max_color)
			{
				Menu.game_choices[i].setVisible(true);
				Menu.game_choices[i].setDisable(false);
			}
			else
			{
				Menu.game_choices[i].setVisible(false);
				Menu.game_choices[i].setDisable(true);
				
			}
		}
		
		
    	Menu.game_exit.setOnAction(e -> 		//exit button
		{
			
			for(int i=0;i<(max_moves*code_length);i++)
			{
				Menu.game_answers[i].setStyle("-fx-background-color: lightgray");
				Menu.game_results[i].setVisible(false);
			}
			for(int i=0; i<code_length;i++)
    		{
    			Menu.game_correct[i].setStyle("-fx-background-color: gray");
    			Menu.game_correct[i].setText("?");
    		}
			Menu.game_over.setVisible(false);
			Menu.window.setScene(Menu.main_scene);
			if(t1!=null)
			{
				t1.terminate();
			}
		});
    	Menu.game_again.setOnAction(e ->		//play again button
    	{
    		is_over=false;
    		for(int i=0;i<(max_moves*code_length);i++)
			{
    			Menu.game_answers[i].setStyle("-fx-background-color: lightgray");
    			Menu.game_results[i].setVisible(false);
			}
    		for(int i=0; i<code_length;i++)
    		{
    			current_code[i]=0;
    			correct_code[i]=0;
    			Menu.game_correct[i].setStyle("-fx-background-color: gray");
    			Menu.game_correct[i].setText("?");
    		}
    		current_move=1;
    		current_element=0;
    		create_code();
    		Menu.game_over.setVisible(false);
    		if(t1!=null)
    		{
    			t1.start_again();
    		}
    	});
    	Menu.game_cancel.setOnAction(e ->		//button that cancels the last part of the current move
    	{
    		if(current_element>0 && (current_move-1)*code_length+current_element < max_moves*code_length && !is_over)
    		{
    			current_element--;
    			current_code[current_element]=0;
    			Menu.game_answers[(current_move-1)*code_length+current_element].setStyle("-fx-background-color:lightgrey");
    		}
    	});
    	
    	
    	//series of buttons that chose the next color in code
    	Menu.game_choices[0].setOnAction(e ->
    	{
    		if((current_move-1)*code_length+current_element < max_moves*code_length && !is_over)
    		{
    			current_code[current_element]=1;
    			Menu.game_answers[(current_move-1)*code_length+current_element].setStyle("-fx-background-color:red");
	    		if(is_finished()) play();
	    		else current_element++;
    		}
    	});
    	Menu.game_choices[1].setOnAction(e ->
    	{
    		if((current_move-1)*code_length+current_element < max_moves*code_length && !is_over)
    		{
    			current_code[current_element]=2;
    			Menu.game_answers[(current_move-1)*code_length+current_element].setStyle("-fx-background-color:royalblue");
	    		if(is_finished()) play();
	    		else current_element++;
    		}
    		
    	});
    	Menu.game_choices[2].setOnAction(e ->
    	{
    		if((current_move-1)*code_length+current_element < max_moves*code_length && !is_over)
    		{
    			current_code[current_element]=3;
    			Menu.game_answers[(current_move-1)*code_length+current_element].setStyle("-fx-background-color:lime");
	    		if(is_finished()) play();
	    		else current_element++;
    		}
    	});
    	Menu.game_choices[3].setOnAction(e ->
    	{
    		if((current_move-1)*code_length+current_element < max_moves*code_length && !is_over)
    		{
    			current_code[current_element]=4;
    			Menu.game_answers[(current_move-1)*code_length+current_element].setStyle("-fx-background-color:gold");
	    		if(is_finished()) play();
	    		else current_element++;
    		}
    	});
    	Menu.game_choices[4].setOnAction(e ->
    	{
    		if((current_move-1)*code_length+current_element < max_moves*code_length && !is_over)
    		{
    			current_code[current_element]=5;
    			Menu.game_answers[(current_move-1)*code_length+current_element].setStyle("-fx-background-color:blueviolet");
	    		if(is_finished()) play();
	    		else current_element++;
    		}
    	});
    	Menu.game_choices[5].setOnAction(e ->
    	{
    		if((current_move-1)*code_length+current_element < max_moves*code_length && !is_over)
    		{
    			current_code[current_element]=6;
    			Menu.game_answers[(current_move-1)*code_length+current_element].setStyle("-fx-background-color:black");
	    		if(is_finished()) play();
	    		else current_element++;
    		}
    	});
    	Menu.game_choices[6].setOnAction(e ->
    	{
    		if((current_move-1)*code_length+current_element < max_moves*code_length && !is_over)
    		{
    			current_code[current_element]=7;
    			Menu.game_answers[(current_move-1)*code_length+current_element].setStyle("-fx-background-color:darkgreen");
	    		if(is_finished()) play();
	    		else current_element++;
    		}
    	});
    	Menu.game_choices[7].setOnAction(e ->
    	{
    		if((current_move-1)*code_length+current_element < max_moves*code_length && !is_over)
    		{
    			current_code[current_element]=8;
    			Menu.game_answers[(current_move-1)*code_length+current_element].setStyle("-fx-background-color: sienna");
	    		if(is_finished()) play();
	    		else current_element++;
    		}
    	});
		
	}

	
	public boolean is_finished()	// checks if the code is fully inputted in the current move
	{
		for(int i=0;i<code_length;i++)
		{
			if(current_code[i]==0) return false; 
		}
		return true;
	}
	
	public void create_code()		// generate the correct code by random
	{
		for(int i=0;i<code_length;i++)
		{
		    int random=rand.nextInt(max_color)+1;
			while(!duplicates && check_duplicates(random,i))  // if the duplicates are turned off, we have to change digits that are already in the code
			{
				random=rand.nextInt(max_color)+1;
			}
			correct_code[i]=random;
		}
		
	}
	
	public void play()		// main method, where the player makes his moves
	{
		if(is_won())				// if the code is fully correct, the game is won
		{
			try 
			{
				game_over(true);
			} 
			catch (InterruptedException e) 
			{
				e.printStackTrace();
			}
		}
		else
		{
			check_move();			// compare the player code to the winning code
			current_move++;
		}
		if(current_move>max_moves)			// if the player used all his moves and didn't guess the code, the game is lost
			try 
			{
				game_over(false);
			} 
			catch (InterruptedException e) 
			{
				e.printStackTrace();
			}
		current_element=0;
		for(int i=0;i<code_length;i++)
		{
			current_code[i]=0;
		}
		
	}
	
	public void check_move() // this method counts the amount of perfect digits (in the right place) and wrongly placed digits (they are in code, but in different place)
	{
		int perfect=0;
		int wrong_place=0;
		for(int i=0;i<code_length;i++)
		{
			if(correct_code[i]==current_code[i])
			{
				perfect++;
			}
			else
			{
				for(int j=0;j<code_length;j++)
				{
					if(current_code[j]==correct_code[i])
					{
						wrong_place++;
						break;
					}
				}
			}
		}
		results_move(perfect,wrong_place);	
	}
	
	public void results_move(int perfect, int wrong_place)	// this method colors the result labels: red (perfect), yellow (wrong place), grey (none)
	{
		for(int i=0;i<code_length;i++)
		{
			if(perfect>0)
			{
				Menu.game_results[i+(current_move-1)*code_length].setStyle("-fx-background-color:red");
				Menu.game_results[i+(current_move-1)*code_length].setVisible(true);
				perfect--;
			}
			else if(wrong_place>0)
			{
				Menu.game_results[i+(current_move-1)*code_length].setStyle("-fx-background-color:gold");
				Menu.game_results[i+(current_move-1)*code_length].setVisible(true);
				wrong_place--;
			}
			else	
			{
				Menu.game_results[i+(current_move-1)*code_length].setStyle("-fx-background-color:lightgray");
				Menu.game_results[i+(current_move-1)*code_length].setVisible(true);
			}
		}
	}
	
	public boolean is_won()	//simple method, that checks if the code typed by the player equals winning code
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
	
	
	public void game_over(boolean win) throws InterruptedException	//method that "cleans" elements after the game and concludes the game. boolean win is used to check whether the player won or lost
	{
		is_over=true;
		Menu.game_over.setVisible(true);
		
		for(int i=0;i<code_length;i++)
		{
			Menu.game_correct[i].setText("");
			if(correct_code[i]==1)	Menu.game_correct[i].setStyle("-fx-background-color:red");
			else if(correct_code[i]==2)	Menu.game_correct[i].setStyle("-fx-background-color:royalblue");
			else if(correct_code[i]==3)	Menu.game_correct[i].setStyle("-fx-background-color:lime");
			else if(correct_code[i]==4)	Menu.game_correct[i].setStyle("-fx-background-color:gold");
			else if(correct_code[i]==5)	Menu.game_correct[i].setStyle("-fx-background-color:blueviolet");
			else if(correct_code[i]==6)	Menu.game_correct[i].setStyle("-fx-background-color:black");
			else if(correct_code[i]==7)	Menu.game_correct[i].setStyle("-fx-background-color:darkgreen");
			else if(correct_code[i]==8)	Menu.game_correct[i].setStyle("-fx-background-color:sienna");
		}
		
		//a new window that sumps up the game: elapsed time, amount of moves and whether the player won or lost
		Stage game_over_stage = new Stage();
		game_over_stage.setWidth(300);
		game_over_stage.setHeight(400);
		game_over_stage.setResizable(false);
		GridPane game_over_grid = new GridPane();
		game_over_grid.setHgap(5);
		game_over_grid.setVgap(5);
		
		Label game_over_label1 = new Label();
		game_over_label1.setFont(new Font(15.0));
		
		Label game_over_label2 = new Label("If you want to save your score, input your name and press \"Save\". Otherwise press \"Close\".");
		game_over_label2.setFont(new Font(15.0));
		game_over_label2.setWrapText(true);
		game_over_label2.setVisible(false);
		
		Label game_over_label3 = new Label("Error, your name needs to be between 1 and 14 letters long.");
		game_over_label3.setFont(new Font(12.0));
		game_over_label3.setWrapText(true);
		game_over_label3.setStyle("-fx-text-fill: red;");
		game_over_label3.setVisible(false);
		
		TextField game_over_text = new TextField();
		game_over_text.setVisible(false);
		
		Button game_over_button1 = new Button("Close");
		game_over_button1.setFont(new Font(15.0));
		game_over_button1.setOnAction(e -> game_over_stage.close());
		
		Button game_over_button2 = new Button("Save");
		game_over_button2.setFont(new Font(15.0));
		game_over_button2.setOnAction(e ->
		{
			//TO DO add to database, check if id works with 0 rows in table
			if(game_over_text.getText().length()>14 || game_over_text.getText().length()<1)
			{
				game_over_label3.setVisible(true);
			}
			else
			{
				System.out.println(game_over_text.getText());
				
				try {
					String dub;
					if(duplicates)	dub = "yes";
					else	dub = "no";
					int id=1;
					String SQL1 = "select max(id_highscores) as max from highscores;";
		            ResultSet rs = Menu.myConn.createStatement().executeQuery(SQL1);
		            if(rs.next())
		            {
		            	id = rs.getInt("max") + 1;
		            }
		            
		            System.out.println(id);
		            System.out.println(game_over_text.getText());
		            
		            PreparedStatement SQL2 = Menu.myConn.prepareStatement("insert into highscores"
		            		+ "(id_highscores, nickname, moves, time, colors, duplicates) VALUE (?,?,?,?,?,?)");
		            SQL2.setInt(1, id);
		            SQL2.setString(2, game_over_text.getText());
		            SQL2.setInt(3, current_move);
		            SQL2.setString(4, t1.display_time());
		            SQL2.setInt(5, max_color);
		            SQL2.setString(6, dub);
		            SQL2.executeUpdate();
		            
		            
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				game_over_stage.close();
			}
		});
		game_over_button2.setVisible(false);
		
		Pane game_over_spring1 = new Pane();
		game_over_spring1.setMinHeight(20);
		Pane game_over_spring2 = new Pane();
		game_over_spring2.setMinHeight(60);
		Pane game_over_spring3 = new Pane();
		game_over_spring3.setMinWidth(120);
		
		game_over_grid.add(game_over_label1,  0, 0, 3, 1);
		game_over_grid.add(game_over_spring1, 0, 1, 3, 1);
		game_over_grid.add(game_over_label2,  0, 2, 3, 1);
		game_over_grid.add(game_over_text,    0, 3, 3, 1);
		game_over_grid.add(game_over_label3,  0, 4, 3, 1);
		game_over_grid.add(game_over_spring2, 0, 5, 3, 1);
		game_over_grid.add(game_over_button1, 0, 6);
		game_over_grid.add(game_over_spring3, 1, 6);
		game_over_grid.add(game_over_button2, 2, 6);
		
		
		VBox game_over_layout = new VBox(10);
		game_over_layout.getChildren().add(game_over_grid);
		Scene game_over_scene = new Scene(game_over_layout);
		game_over_layout.setStyle("-fx-padding: 10;" + 
                "-fx-border-style: solid inside;" + 
                "-fx-border-width: 2;" +
                "-fx-border-insets: 5;" + 
                "-fx-border-radius: 5;" + 
                "-fx-border-color: black;");

		//results depend on winning or losing
		if(win)
		{
			results_move(4,0);
			game_over_label1.setText("You won!\nMoves: " + (current_move) + "/" +max_moves +"\n" + "Timer: " +  t1.display_time());
			Menu.game_over.setText("You won!");
			game_over_label2.setVisible(true);
			game_over_text.setVisible(true);
			game_over_button2.setVisible(true);
		}
		else
		{
			game_over_label1.setText("You lost!\nMoves: " + (current_move-1) + "/" +max_moves +"\n" +  "Timer: " + t1.display_time());
			Menu.game_over.setText("You lost!");
		}	
		
		Menu.countDownLatch = new CountDownLatch(1);
		Menu.countDownLatch.await();
		
		if(t1!=null)
		{
			t1.terminate();
		}
		
		game_over_stage.setScene(game_over_scene);
		game_over_stage.show();
	}
	
	public boolean check_duplicates(int number, int l) // used if dupplicates are not allowed
	{
		for(int i=0; i<=l; i++)
		{
			if(correct_code[i]==number) return true;
		}
		return false;
	}
}
