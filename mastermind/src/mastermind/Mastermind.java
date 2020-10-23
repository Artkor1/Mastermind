package mastermind;

import java.util.Random;
import java.util.Scanner;
import java.util.Arrays;


public class Mastermind {
	Random rand=new Random();
	
	private int code_length;
	private int max_moves;
	private int max_number;
	private boolean duplicates;
	
	private int current_move=1;
	private int[] correct_code;
	
	public Mastermind(int code_length, int max_moves, int max_number, boolean duplicates) {
		this.code_length=code_length;
		this.max_moves=max_moves;
		this.max_number=max_number;
		this.duplicates=duplicates;
		correct_code=new int[code_length];
		Create_code();
		Play();
	}
	
	public void Create_code()
	{
		for(int i=0;i<code_length;i++)
		{
		    int random=rand.nextInt(max_number)+1;
			while(!duplicates && Check_duplicates(random,i))
			{
				random=rand.nextInt(max_number)+1;
			}
			correct_code[i]=random;
		}
		
		
		//show code
		/*for(int i=0;i<code_length;i++)
		{
			System.out.print(correct_code[i]);
		}*/
	}
	
	public void Play()
	{
		System.out.println("\nI have generated a " + code_length +" digits long code, consisting of numbers from 1 to " + max_number +".");
		if(duplicates) System.out.println("Duplicates are allowed.");
		else System.out.println("Duplicates are not allowed.");
		System.out.println("You have " + max_moves +" moves to break the code. Good luck.");
		
		Scanner input=new Scanner(System.in);
		
		while(current_move<=max_moves)
		{
			System.out.print("\nMove #" + current_move +": ");
			String move=input.nextLine();
			while(!Is_correct(move))
			{
				System.out.println("Error. Incorrect move");
				move=input.nextLine();
			}
			if(Is_won(move))
			{
				Game_won();
				break;
			}
			else
			{
				Check_move(move);
				current_move++;
			}
		}
		if(current_move>max_moves) Game_lost();
	}
	
	public void Game_won()
	{
		System.out.println("\nCongratulations! You have won in " + current_move + " moves. The code was: " + Arrays.toString(correct_code));
	}
	
	public void Game_lost()
	{
		System.out.println("\nYou lost, correct code was: " + Arrays.toString(correct_code));
	}
	
	public void Check_move(String move)
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
	
	public boolean Check_duplicates_move(String move, int l)
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
	
	public boolean Is_won(String move)
	{
		for(int i=0;i<code_length;i++)
		{
			//if(correct_code[i]!=move.charAt(i)-48)
			if(correct_code[i]!=Character.getNumericValue(move.charAt(i)))
			{
				return false;
			}
		}
		return true;
	}
	
	public boolean Is_correct(String move)
	{
		if(move.length()!=code_length) return false;
		for(int i=0;i<move.length();i++)
		{
			//if(move.charAt(i)<49 || move.charAt(i)>(49+max_number-1))
			if(Character.getNumericValue(move.charAt(i))<1 || Character.getNumericValue(move.charAt(i))>max_number)
			{
				return false;
			}
		}
		return true;
	}
	
	public boolean Check_duplicates(int number, int l)
	{
		for(int i=0; i<=l; i++)
		{
			if(correct_code[i]==number) return true;
		}
		return false;
	}
}
