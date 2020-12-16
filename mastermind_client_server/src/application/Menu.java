package application;
	

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.concurrent.CountDownLatch;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;

import client.*;
import java.sql.*;

public class Menu extends Application{
	
	static Client client;
	
	static Stage window;
	static Scene main_scene, tutorial_scene, game_scene, settings_scene, highscores_scene;
	static VBox main_menu, tutorial, game_menu, settings, highscores;
	static GridPane game_grid, settings_grid;
	static GridPane[] results_grid;
	static ScrollPane game_pane;
	static Button[] menu_buttons;
	static Button[] game_choices;
	static Button tut1, set1, set2, set3, set4, set8, set9, game_exit, game_cancel, game_again, server_check, high1;
	static Label tut2, set5, set6, set7, set10, set11, game_timer, game_over, server_status;
	static Label[] game_answers, game_correct, game_results, game_numbers;
	static Line[] lines;
	
	static CountDownLatch countDownLatch;
	
	static Connection myConn;
	static Statement myStmt;
	
	private ObservableList<ObservableList> highscores_data;
    private TableView highscores_table;
	
	
	private int code_length;
	private int max_moves;
	private int max_color;
	private boolean duplicates;
	public Menu()
	{
		code_length=4;
		max_moves=10;
		max_color=8;
		duplicates=false;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void start(Stage primaryStage) {  //setting the whole GUI
		try {
			window=primaryStage;
			window.setTitle("Mastermind");
			window.setWidth(400);
			window.setHeight(620);
			window.setResizable(false);
			window.setOnCloseRequest(e->
			{
				Platform.exit();
				System.exit(0);
			});
			
			//main menu
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
			
			
			
			//Send a packet to check connection at the opening of the game and whenever the button server_check is clicked
			Pane spring_menu1 = new Pane();
			spring_menu1.setMinHeight(60);
			server_check = new Button("Check connection");
			server_status = new Label("Connecting...");
			
			if(client.send("testing packet..."))	server_status.setText("Connected");
			else	server_status.setText("Not connected");
			server_check.setOnAction(e ->
			{
				if(client.send("testing packet..."))	server_status.setText("Connected");
				else	server_status.setText("Not connected");
			});
			
			main_menu = new VBox(20);
			main_menu.getChildren().addAll(menu_buttons[0], menu_buttons[1], menu_buttons[2], menu_buttons[3], menu_buttons[4], spring_menu1, server_status, server_check);
			main_menu.setStyle("-fx-padding: 10;" + 
                     "-fx-border-style: solid inside;" + 
                     "-fx-border-width: 2;" +
                     "-fx-border-insets: 5;" + 
                     "-fx-border-radius: 5;" + 
                     "-fx-border-color: black;");
			main_scene = new Scene(main_menu,620,400);
			
			//most important button - creates game object and initializes the game
			menu_buttons[0].setOnAction(e ->	
			{
				window.setScene(game_scene);
				Mastermind game = new Mastermind(code_length, max_moves, max_color, duplicates);
				game.initialize();
			});
			menu_buttons[1].setOnAction(e -> window.setScene(settings_scene));
			
			menu_buttons[2].setOnAction(e ->
			{
				highscores_table = new TableView();
				highscores_data = FXCollections.observableArrayList();
		        try{
		        	String SQL = "select nickname, moves, time, colors, duplicates from highscores";
		            ResultSet rs = myConn.createStatement().executeQuery(SQL);
		            
		            //table column added dynamically
		            for(int i=0 ; i<rs.getMetaData().getColumnCount(); i++)
		            {
		                final int j = i;             
						TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i+1));
		                col.setCellValueFactory(new Callback<CellDataFeatures<ObservableList,String>,ObservableValue<String>>()
		                {                    
		                    public ObservableValue<String> call(CellDataFeatures<ObservableList, String> param)
		                    {   
		                        return new SimpleStringProperty(param.getValue().get(j).toString());
		                    }                    
		                });
		                highscores_table.getColumns().addAll(col); 
		            }
		            
		            //data added to ObservableList
		            while(rs.next())	//iterate through rows
		            {
		            	//if(myRs.next)
		                ObservableList<String> row = FXCollections.observableArrayList();
		                for(int i=1 ; i<=rs.getMetaData().getColumnCount(); i++) //iterate through columns
		                {
		                    row.add(rs.getString(i));
		                }
		                highscores_data.add(row);
		            }
		            
		            //added to TableView
		            highscores_table.setItems(highscores_data);
		          }catch(Exception e2){
		              e2.printStackTrace();
		             System.out.println("Error on Building Data");             
		        }
		        
		        //cleaning and adding to VBox
		        highscores.getChildren().clear();
		        highscores.getChildren().addAll(high1, highscores_table);
				window.setScene(highscores_scene);
				
			});
			
			menu_buttons[3].setOnAction(e -> window.setScene(tutorial_scene));
			menu_buttons[4].setOnAction(e ->
			{
				System.exit(0);
				Platform.exit();
			});
			
			
			//settings menu
			set1 = new Button("<-");
			set2 = new Button("->");
			set3 = new Button("OFF");
			set4 = new Button("Go back");
			set5 = new Label("Maximum moves:");
			set6 = new Label("Duplicates allowed:");
			set7 = new Label(Integer.toString(max_moves));
			set8 = new Button("<-");
			set9 = new Button("->");
			set10 = new Label("Colors: ");
			set11 = new Label(Integer.toString(max_color));
			
			
			set1.setPrefWidth(40);
			set2.setPrefWidth(40);
			set3.setPrefWidth(60);
			set7.setPrefWidth(60);
			set8.setPrefWidth(40);
			set9.setPrefWidth(40);
			set11.setPrefWidth(60);
			
			set1.setFont(new Font(15.0));
			set2.setFont(new Font(15.0));
			set3.setFont(new Font(15.0));
			set5.setFont(new Font(15.0));
			set6.setFont(new Font(15.0));
			set7.setFont(new Font(20.0));
			set8.setFont(new Font(15.0));
			set9.setFont(new Font(15.0));
			set10.setFont(new Font(15.0));
			set11.setFont(new Font(20.0));
			
			
			set7.setAlignment(Pos.BASELINE_CENTER);
			set11.setAlignment(Pos.BASELINE_CENTER);
			
			set4.setFont(new Font(25.0));
			set4.setPrefSize(400, 60);
			
			
			set1.setOnAction(e ->
			{
				if(max_moves>6)
				{
					max_moves--;
					set7.setText(Integer.toString(max_moves));
				}
			});
			set2.setOnAction(e ->
			{
				if(max_moves<10)
				{
					max_moves++;
					set7.setText(Integer.toString(max_moves));
				}
			});
			set8.setOnAction(e ->
			{
				if(max_color>4)
				{
					max_color--;
					set11.setText(Integer.toString(max_color));
				}
			});
			set9.setOnAction(e ->
			{
				if(max_color<8)
				{
					max_color++;
					set11.setText(Integer.toString(max_color));
				}
			});
			set3.setOnAction(e->
			{
				if(duplicates)
				{
					duplicates=false;
					set3.setText("OFF");
				}
				else
				{
					duplicates=true;
					set3.setText("ON");
				}
			});
			
			set4.setOnAction(e ->
			{
				window.setScene(main_scene);
			});
			
			Pane spring_settings1 = new Pane();
			Pane spring_settings2 = new Pane();
			Pane spring_settings3 = new Pane();
			Pane spring_settings4 = new Pane();
			spring_settings1.setMinWidth(20);
			spring_settings2.setMinHeight(10);
			spring_settings3.setMinHeight(100);
			spring_settings4.setMinHeight(10);
			
			
			settings_grid = new GridPane();
			settings_grid.setHgap(5);
			settings_grid.setVgap(5);
			
			settings_grid.add(set5, 0, 0);
			settings_grid.add(spring_settings1, 1, 0, 1, 5);
			settings_grid.add(set1, 2, 0);
			settings_grid.add(set7, 3, 0);
			settings_grid.add(set2, 4, 0);
			settings_grid.add(spring_settings2, 0, 1, 5, 1);
			settings_grid.add(set10, 0, 2);
			settings_grid.add(set8, 2, 2);
			settings_grid.add(set11, 3, 2);
			settings_grid.add(set9, 4, 2);
			settings_grid.add(spring_settings4, 0, 3, 5, 1);
			settings_grid.add(set6, 0, 4);
			settings_grid.add(set3, 3, 4);
			settings_grid.add(spring_settings3, 0, 5, 5, 1);
			settings_grid.add(set4, 0, 6, 5, 1);
			
			settings = new VBox();
			settings.getChildren().add(settings_grid);
			settings.setStyle("-fx-padding: 10;" + 
                     "-fx-border-style: solid inside;" + 
                     "-fx-border-width: 2;" +
                     "-fx-border-insets: 5;" + 
                     "-fx-border-radius: 5;" + 
                     "-fx-border-color: black;");
			
			settings_scene = new Scene(settings, 620, 400);
			
			
			//tutorial menu
			tut1 = new Button("Go back");
			tut1.setFont(new Font(25.0));
			tut1.setPrefSize(400, 60);
			tut1.setOnAction(e -> window.setScene(main_scene));
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
		
			
			//highscores menu
			highscores_table = new TableView();
	        high1 = new Button("Go back");
			high1.setFont(new Font(25.0));
			high1.setPrefSize(400, 60);
	        high1.setOnAction(e -> window.setScene(main_scene));
	        
	        highscores=new VBox(20);
			highscores.getChildren().addAll(high1, highscores_table);
			highscores.setStyle("-fx-padding: 10;" + 
                    "-fx-border-style: solid inside;" + 
                    "-fx-border-width: 2;" +
                    "-fx-border-insets: 5;" + 
                    "-fx-border-radius: 5;" + 
                    "-fx-border-color: black;");
	        
			highscores_scene = new Scene(highscores,620,400);
			

			
			
			//game menu
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
			
			lines = new Line[max_moves-1];
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
			

			for(int i=0; i<max_color; i++)
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
		//create new client and start him up
		client = new Client("localhost", 4999);
		Thread t = new Thread(client);
		t.start();
		
		
		//connect to database
		try {
			//get a conection to database
			myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/mastermind?serverTimezone=UTC","student","student");
			//create a statement
			myStmt = myConn.createStatement();
		}
		catch (Exception exc){
			exc.printStackTrace();
		}
		
		
		
		//create menu
		Menu m=new Menu();
		launch(args);
	}
}
