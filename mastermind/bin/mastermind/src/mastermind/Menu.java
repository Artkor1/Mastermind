package mastermind;

import java.util.Scanner;

public class Menu {		// this class represents the main menu of game, player can select different options here (like starting the game)
	
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
	
	public void Start()
	{
		boolean exit=false;
		Display_menu();
		while(!exit)	// the whole menu is based on an infinite loop, player can leave only by selecting "exit game"
		{
			Scanner input = new Scanner(System.in);
		    int choice = input.nextInt();
		    switch(choice) {
			    case 1: //START GAME
			    	Mastermind game = new Mastermind(code_length, max_moves, max_number, duplicates);
			    	Display_menu();
			    	break;
			    case 2: //SETTING
			    	//TO DO, options to change code length, maximum moves, maximum number, duplicates on/off etc.
			    	System.out.println("Not yet ready");
			    	break;
			    case 3: //TUTORIAL
			    	Tutorial();
			    	Display_menu();
			    	break;
			    case 4: //EXIT GAME
			    	System.out.println("Thanks for playing");
			    	exit=true;
			    	break;
			    default:
			    	System.out.println("Choose a number from 1 to 4 to proceed");    	
		  }
		}
	}
	
	public void Display_menu()	//display basic starting options for player
	{
    	System.out.println("\nWelcome to Mastermind!");
		System.out.println("[1]-Start game");
		System.out.println("[2]-Settings");
		System.out.println("[3]-Tutorial");
		System.out.println("[4]-Exit game");
	}
	
	
	public void Tutorial()     //basic tutorial for the game
	{
		System.out.println("The game of Mastermind is a simple (yet intriguing) game of logic. The computer generates a " + code_length
				+ "-digit long code, consisting of numbers from 1 to " + max_number + ". \nYour role is to crack that code. You have " + max_moves
				+ " moves. On each move, you try to guess the correct code by typing your own " + code_length + "-digit long code."
			    + "\nEach time you do it, You get the information about how many digits are in the right place, and how many are in the code but in the wrong place."
			    + "\nBy analyzing these results, excluding the incorrect numbers and confirming the place of correct numbers, you can crack the code!"
			    + "\nRemember: Your time and moves are being counted!");
		System.out.println("[1]-Go back");
		Scanner input2 = new Scanner(System.in);
		int choice = input2.nextInt();
		while(choice!=1)
		{
			choice=input2.nextInt();
		}
	}

	public static void main(String[] args) {
		Menu m=new Menu();
		m.Start();
	}

}
