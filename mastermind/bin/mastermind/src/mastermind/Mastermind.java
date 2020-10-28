package mastermind;

import java.util.Random;
import java.util.Scanner;
import java.util.Arrays;


public class Mastermind {
	Random rand=new Random();
	
	private int code_length; 		// amount of digits in a code, usually between 4 and 8
	private int max_moves;   		// amount of moves (guesses) player has before he loses
	private int max_number;  		// range of digits in a code, from 1 to max_number
	private boolean duplicates; 	// are duplicated digits in a code allowed
	
	private int current_move=1;		// index for moves, if it reaches max_moves player loses
	private int[] correct_code;		// array that holds the digits of a correct code
	
	private Timer t1;
	
	public Mastermind(int code_length, int max_moves, int max_number, boolean duplicates) {
		this.code_length=code_length;
		this.max_moves=max_moves;
		this.max_number=max_number;
		this.duplicates=duplicates;
		correct_code=new int[code_length];
		
		t1 = new Timer();
    	t1.start();
		
		Create_code();
		Play();
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
		System.out.println("\nI have generated a " + code_length +" digits long code, consisting of numbers from 1 to " + max_number +".");
		if(duplicates) System.out.println("Duplicates are allowed.");
		else System.out.println("Duplicates are not allowed.");
		System.out.println("You have " + max_moves +" moves to break the code. Good luck.");
		
		Scanner input=new Scanner(System.in);
		
		while(current_move<=max_moves)		// main loop, where the game takes place
		{
			System.out.print("\nMove #" + current_move + ": ");
			String move=input.nextLine();
			while(!Is_correct(move))		// if the move is incorrect, the player has to type it again
			{
				System.out.println("Error. Incorrect move");
				move=input.nextLine();
			}
			if(Is_won(move))				// if the code is fully correct, the game is won
			{
				Game_won();
				break;
			}
			else
			{
				Check_move(move);			// compare the player code to the winning code
				current_move++;
			}
		}
		if(current_move>max_moves)	Game_lost();		// if the player used all his moves and didn't guess the code, the game is lost
	}
	
	public boolean Is_correct(String move) // checks if the code that player typed is correct
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
	}
	
	public void Check_move(String move) // this method returns the amount of perfect digits (in the right place) and wrongly placed digits (they are in code, but in different place)
	{
		int perfect=0;
		int wrong_place=0;
		for(int i=0;i<move.length();i++)
		{
			if(!Check_duplicates_move(move,i))
			{
				for(int j=0;j<code_length;j++)
				{
					if(Character.getNumericValue(move.charAt(i)) == correct_code[j])
					{
						if(i==j) perfect++;
						else wrong_place++;
					}
				}
			}
		}
		System.out.println("Perfect: " + perfect + " Wrong place: " + wrong_place);
	}
	
	public boolean Check_duplicates_move(String move, int l)   // this method supplements the Check_move(String move) method so that it gives the correct answers
	{
		for(int i=0;i<l;i++)
		{
			if(move.charAt(i) == move.charAt(l))
			{
				return true;
			}
		}
		return false;
	}
	
	public boolean Is_won(String move)	//simple method, that checks if the code typed by the player equals winning code
	{
		for(int i=0;i<code_length;i++)
		{
			if(correct_code[i]!=Character.getNumericValue(move.charAt(i)))
			{
				return false;
			}
		}
		return true;
	}
	
	public void Game_won()		// game results
	{
		System.out.println("\nCongratulations! You have won in " + current_move + " moves. The code was: " + Correct_code_to_string());
		t1.Display_time();
	}
	
	public void Game_lost()		// game results
	{
		System.out.println("\nYou lost, correct code was: " + Correct_code_to_string());
		t1.Display_time();
	}
	
	public String Correct_code_to_string() // change code from array of ints to string
	{
		String code="";
		for(int i=0;i<code_length;i++)
		{
			code+=Integer.toString(correct_code[i]);
		}
		return code;
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
