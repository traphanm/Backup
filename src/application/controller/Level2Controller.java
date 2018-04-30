
package application.controller;

import com.sun.javafx.tk.Toolkit;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.ImageCursor;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.media.AudioClip;
import javafx.scene.shape.Circle;
import java.util.*;

/**
 * 
 * @author  Long Trac - (4/9/2018 minor change and clean up the process of moving 2 targets around) 
 * 
 * Matthew - possible name for level1 could be "learning the basics" or "the basics"
 * Matthew - might want to change hitmarker outline to white
 */
public class Level2Controller implements Initializable, EventHandler<MouseEvent> {

	// class variables and fxml
	
	@FXML private Circle outerTarg1, innerTarg1, bullTarg1;
	@FXML private Circle outerTarg2, innerTarg2, bullTarg2;
	@FXML private GridPane grid;
	@FXML private Label scoreLabel;
	@FXML private Label endGameLabel;
	@FXML private Label profileLabel;
	@FXML private Label timerLabel;
	private int column1, column2, row1, row2, oldRow1, oldRow2, oldCol1, oldCol2;
	
	public int score;
	public int time;

	
	public void initialize( URL location, ResourceBundle resources ) {
		// listens for ESC key pressed to go back to selectLevel (TBD)
		application.Main.primaryStage.getScene().setOnKeyPressed( ke -> {
			if ( ke.getCode() == KeyCode.ESCAPE ) {
				System.out.println( "Key Pressed: " + ke.getCode() );

				try {
					application.Main.primaryStage.getScene().setOnKeyPressed( null );
					Parent root = FXMLLoader.load( getClass().getResource( "/application/view/SelectLevel.fxml" ) );
					application.Main.primaryStage.getScene().setRoot( root );
				} catch ( IOException e ) {
					e.printStackTrace();
				}
			}
		} );
		
		grid.getParent().setOnMouseClicked(me -> {
            if( me.getButton() == MouseButton.PRIMARY )
            	new Thread(new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                    	new AudioClip( new File( "gunshot.mp3" ).toURI().toString() ).play();         
                        return null;
                    }
                }).start();	
        } );
		
		profileLabel.setPadding( new Insets(-Toolkit.getToolkit().getFontLoader().getFontMetrics( profileLabel.getFont() ).getDescent(), 0, 0, 0) );
		timerLabel.setPadding( new Insets(-Toolkit.getToolkit().getFontLoader().getFontMetrics( timerLabel.getFont() ).getDescent() * .5, 0, 0, 0) );
		scoreLabel.setPadding( new Insets(-Toolkit.getToolkit().getFontLoader().getFontMetrics( scoreLabel.getFont() ).getDescent(), 0, 0, 0) );
		
		new Thread(new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                time = 30;
                Platform.runLater(() -> timerLabel.setText( String.format( "Time Left\n00:%02d", time ) ) );
                	
                while ( time > 0 ) {
                	Thread.sleep( 1000 );
					time--;
					Platform.runLater(() -> timerLabel.setText( String.format( "Time Left\n00:%02d", time ) ) );
                }
                
                endGameLabel.setOpacity(1);
    			endGameLabel.setDisable(false);
    			outerTarg1.setVisible(false);
    			outerTarg2.setVisible(false);
    			innerTarg1.setVisible(false);
    			innerTarg2.setVisible(false);
    			bullTarg1.setVisible(false);
    			bullTarg2.setVisible(false);
    			
    			outerTarg1.setDisable(true);
    			outerTarg2.setDisable(true);
    			innerTarg1.setDisable(true);
    			innerTarg2.setDisable(true);
    			bullTarg1.setDisable(true);
    			bullTarg2.setDisable(true);
    			
    			System.out.println("Adding to file");
    			File file = new File("users.txt");
    			Path p = Paths.get(file.toURI());
    			List<String> fileContent = new ArrayList<>(Files.readAllLines(p, StandardCharsets.UTF_8));
    			
    			for (int i = 0; i < fileContent.size(); i++) {
    				String[] seperated = fileContent.get(i).split(",");
    				if(seperated[2].isEmpty()) {
    					seperated[2] = "0";
    				}
    				if(seperated[0].equals(PlayerSelectController.selected.getUserN())) {
    					System.out.println("names match");
    					System.out.println(seperated[2]);
    					
    					if(Integer.parseInt(seperated[2])< score) {
    						fileContent.set(i, PlayerSelectController.selected.getUserN()+","+seperated[1]+","+score+","+seperated[3]);
    			
    					}
    				}
    			}

    			Files.write(p, fileContent, StandardCharsets.UTF_8);
 
                return null;
            }
        }).start();
		
		//-----------------------------------------------------------------
		//
		//Threat to move target automatically to increase the difficulty
		//
		//-----------------------------------------------------------------
		Thread th = new Thread(new Task<Void>(){
			int time  = 30;
			@Override
			protected Void call() throws Exception {
				Random rand = new Random();
				
				while(time > 0){ 
					
					System.out.println("here");
				    column1 = rand.nextInt(5);
				    row1 = rand.nextInt(5);
					column2 = rand.nextInt(5);
					row2 = rand.nextInt(5);
					 
					while ( (column1 == column2&& row1  == row2)||
							(oldRow1 == row1 && oldCol1 == column1)||
							(oldRow2 == row2 && oldCol2 == column2)||
							(oldRow2 == row1 && oldCol2 == column1)||
							(oldRow1 == row2 && oldCol1 == column2))
							{
								column1 = rand.nextInt(5);
								row1 = rand.nextInt(5);
								column2 = rand.nextInt(5);
								row2 = rand.nextInt(5);
							}
					
					Platform.runLater(()->GridPane.setColumnIndex(outerTarg1, column1));
					Platform.runLater(()->GridPane.setColumnIndex(innerTarg1, column1));
					Platform.runLater(()->GridPane.setColumnIndex(bullTarg1, column1));
					Platform.runLater(()->GridPane.setRowIndex(outerTarg1, row1));
					Platform.runLater(()->GridPane.setRowIndex(innerTarg1, row1));
					Platform.runLater(()->GridPane.setRowIndex(bullTarg1, row1));
					oldCol1 = column1;
					oldRow1 = row1;
						
					Platform.runLater(()->GridPane.setColumnIndex(outerTarg2, column2));
					Platform.runLater(()->GridPane.setColumnIndex(innerTarg2, column2));
					Platform.runLater(()->GridPane.setColumnIndex(bullTarg2, column2));
					Platform.runLater(()->GridPane.setRowIndex(outerTarg2, row2));
					Platform.runLater(()->GridPane.setRowIndex(innerTarg2, row2));
					Platform.runLater(()->GridPane.setRowIndex(bullTarg2, row2));
					oldCol2 = column2;
					oldRow2 = row2;	
					
					Thread.sleep(1000);
					time --;
				}
				
				return null;	
			}
		});
		th.start();
	}	
	
	public void handle( MouseEvent event ) {			 
		Circle selected = (Circle) event.getSource();
		if( event.getButton() == MouseButton.PRIMARY ) {
			new Thread(new Task<Void>() {
	            @Override
	            protected Void call() throws Exception {
	            	Platform.runLater(() -> grid.getParent().setCursor( new ImageCursor( new Image( "file:hitmarker.png"), new Image( "file:hitmarker.jpg").getWidth() / 2, new Image( "file:hitmarker.jpg").getHeight() / 2 ) ) );
	        		Thread.sleep( 150 );
	            	Platform.runLater(() -> grid.getParent().setCursor( Cursor.CROSSHAIR ) );      
	                return null;
	            }
	        }).start();
			
			
			if(selected.equals(outerTarg1) || selected.equals(outerTarg2)) {
				score += 10;
				scoreLabel.setText("SCORE  " + score);
			}		
			if(selected.equals(innerTarg1) || selected.equals(innerTarg2)) {
				score += 25;
				scoreLabel.setText("SCORE  " + score);
			}		
			if(selected.equals(bullTarg1) || selected.equals(bullTarg2)) {
				score += 50;
				scoreLabel.setText("SCORE  " + score);
			}
			
					
		}		
	
	}// end of handle(ActionEvent event)	
	
}// end of StaticLevelController implementing event handler
