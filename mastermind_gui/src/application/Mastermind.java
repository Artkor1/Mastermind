package application;

import java.util.Random;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

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
	
	private Timer t1;
	
	private boolean is_over;
	
	public void Initialize()
	{
		t1 = new Timer();
    	t1.start();

		Create_code();
    	
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
				t1.Terminate();
			}
		});
    	
    	Menu.game_again.setOnAction(e ->
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
    			Menu.game_correct[i].setStyle("-fx-background-color: gray");
    			Menu.game_correct[i].setText("?");
    		}
    		current_move=1;
    		current_element=0;
    		Create_code();
    		Menu.game_over.setVisible(false);
    		if(t1!=null)
    		{
    			t1.Start_again();
    		}
    		
    	});
    	Menu.game_cancel.setOnAction(e ->
    	{
    		if(current_element>0 && (current_move-1)*code_length+current_element < max_moves*code_length && !is_over)
    		{
    			current_element--;
    			current_code[current_element]=0;
    			Menu.game_answers[(current_move-1)*code_length+current_element].setStyle("-fx-background-color:lightgrey");
    		}
    	});
    	
    	Menu.game_choices[0].setOnAction(e ->
    	{
    		if((current_move-1)*code_length+current_element < max_moves*code_length && !is_over)
    		{
    			current_code[current_element]=1;
    			Menu.game_answers[(current_move-1)*code_length+current_element].setStyle("-fx-background-color:red");
	    		if(Is_finished()) Play();
	    		else current_element++;
    		}
    	});
    	Menu.game_choices[1].setOnAction(e ->
    	{
    		if((current_move-1)*code_length+current_element < max_moves*code_length && !is_over)
    		{
    			current_code[current_element]=2;
    			Menu.game_answers[(current_move-1)*code_length+current_element].setStyle("-fx-background-color:royalblue");
	    		if(Is_finished()) Play();
	    		else current_element++;
    		}
    		
    	});
    	Menu.game_choices[2].setOnAction(e ->
    	{
    		if((current_move-1)*code_length+current_element < max_moves*code_length && !is_over)
    		{
    			current_code[current_element]=3;
    			Menu.game_answers[(current_move-1)*code_length+current_element].setStyle("-fx-background-color:lime");
	    		if(Is_finished()) Play();
	    		else current_element++;
    		}
    	});
    	Menu.game_choices[3].setOnAction(e ->
    	{
    		if((current_move-1)*code_length+current_element < max_moves*code_length && !is_over)
    		{
    			current_code[current_element]=4;
    			Menu.game_answers[(current_move-1)*code_length+current_element].setStyle("-fx-background-color:gold");
	    		if(Is_finished()) Play();
	    		else current_element++;
    		}
    	});
    	Menu.game_choices[4].setOnAction(e ->
    	{
    		if((current_move-1)*code_length+current_element < max_moves*code_length && !is_over)
    		{
    			current_code[current_element]=5;
    			Menu.game_answers[(current_move-1)*code_length+current_element].setStyle("-fx-background-color:blueviolet");
	    		if(Is_finished()) Play();
	    		else current_element++;
    		}
    	});
    	Menu.game_choices[5].setOnAction(e ->
    	{
    		if((current_move-1)*code_length+current_element < max_moves*code_length && !is_over)
    		{
    			current_code[current_element]=6;
    			Menu.game_answers[(current_move-1)*code_length+current_element].setStyle("-fx-background-color:black");
	    		if(Is_finished()) Play();
	    		else current_element++;
    		}
    	});
    	Menu.game_choices[6].setOnAction(e ->
    	{
    		if((current_move-1)*code_length+current_element < max_moves*code_length && !is_over)
    		{
    			current_code[current_element]=7;
    			Menu.game_answers[(current_move-1)*code_length+current_element].setStyle("-fx-background-color:darkgreen");
	    		if(Is_finished()) Play();
	    		else current_element++;
    		}
    	});
    	Menu.game_choices[7].setOnAction(e ->
    	{
    		if((current_move-1)*code_length+current_element < max_moves*code_length && !is_over)
    		{
    			current_code[current_element]=8;
    			Menu.game_answers[(current_move-1)*code_length+current_element].setStyle("-fx-background-color: sienna");
	    		if(Is_finished()) Play();
	    		else current_element++;
    		}
    	});
		
	}
	
	public Mastermind(int code_length, int max_moves, int max_number, boolean duplicates) {
		this.code_length=code_length;
		this.max_moves=max_moves;
		this.max_number=max_number;
		this.duplicates=duplicates;
		correct_code=new int[code_length];
		current_code=new int[code_length];
		is_over=false;
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
		Menu.game_over.setVisible(true);
		if(t1!=null)
		{
			t1.Terminate();
		}
		
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
		
		for(int i=0;i<max_moves*code_length;i++)
		{
			Menu.game_results[i].setVisible(false);
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
			Menu.game_over.setText("You won!");
		}
		else
		{
			game_over_label.setText("You lost!\nMoves: " + (current_move-1) + "/" +max_moves +"\n" + t1.Display_time());
			Menu.game_over.setText("You lost!");
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
