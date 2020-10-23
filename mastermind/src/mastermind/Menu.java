package mastermind;

import java.util.Scanner;

public class Menu {
	
	public void Start()
	{
		int code_length=4;
		int max_moves=10;
		int max_number=8;
		boolean duplicates=false;
		System.out.println("Welcome to Mastermind!");
		System.out.println("[1]-Start game");
		System.out.println("[2]-Settings");
		System.out.println("[3]-Tutorial");
		System.out.println("[4]-Exit game");
		boolean exit=false;
		while(!exit)
		{
			Scanner input = new Scanner(System.in);
		    int choice = input.nextInt();
		    switch(choice) {
			    case 1:
			    	Mastermind game=new Mastermind(code_length, max_moves, max_number, duplicates);
			    	
			    	System.out.println("\nWelcome to Mastermind!");
					System.out.println("[1]-Start game");
					System.out.println("[2]-Settings");
					System.out.println("[3]-Tutorial");
					System.out.println("[4]-Exit game");
			    	break;
			    case 2:
			    	//TO DO
			    	System.out.println("Not yet ready");
			    	break;
			    case 3:
			    	//TO DO
			    	System.out.println("Not yet ready");
			    	break;
			    case 4:
			    	System.out.println("Thanks for playing");
			    	exit=true;
			    	break;
			    default:
			    	System.out.println("Choose a number from 1 to 4 to proceed");    	
		  }
		  if(exit) input.close();
		}
	}

	public static void main(String[] args) {
		Menu m=new Menu();
		m.Start();
	}

}
