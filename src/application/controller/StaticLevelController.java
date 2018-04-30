
package application.controller;

import com.sun.javafx.tk.Toolkit;
import application.model.Ammo;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.Scanner;
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

/**
 * 
 * @author  Long Trac - (4/9/2018 minor change and clean up the process of moving 2 targets around) 
 * 
 * Matthew - possible name for level1 could be "learning the basics" or "the basics"
 * Matthew - might want to change hitmarker outline to white
 */
public class StaticLevelController implements Initializable, EventHandler<MouseEvent> {

	// class variables and fxml
	
	@FXML private Circle outerTarg1, innerTarg1, bullTarg1;
	@FXML private Circle outerTarg2, innerTarg2, bullTarg2;
	@FXML private GridPane grid;
	@FXML private Label scoreLabel;
	@FXML private Label endGameLabel;
	@FXML private Label profileLabel;
	@FXML private Label timerLabel;
	@FXML private Label ammoLabel;
	private int column1, column2, row1, row2, oldRow1, oldRow2, oldCol1, oldCol2;
	private Ammo ammo;
	public int score;
	public int time;
	
	
	public void initialize( URL location, ResourceBundle resources ) {
		ammo = new Ammo(10);
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
			else if ( ke.getCode() == KeyCode.R ) {
				System.out.println( "Key Pressed: " + ke.getCode() );
				
				ammo.reload();
				Platform.runLater( () -> ammoLabel.setStyle( "-fx-text-fill: #0085ff" ) );
				Platform.runLater( () -> ammoLabel.setText( String.format( "Ammo: %2d", ammo.getRemaining() ) ) );
			}
		} );
		
		grid.getParent().setOnMouseClicked( me -> {
            if( me.getButton() == MouseButton.PRIMARY )
            	new Thread(new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                                      	
                    	if ( ammo.getRemaining() > 0 ) {
                    		new AudioClip( new File( "./res/gunshot.mp3" ).toURI().toString() ).play();
                    		ammo.reduceAmmo();
                    		if (ammo.getRemaining() <= 0 )
                    			Platform.runLater( () -> ammoLabel.setStyle( "-fx-text-fill: red" ) );
                    		Platform.runLater( () -> ammoLabel.setText( String.format( "Ammo: %2d", ammo.getRemaining() ) ) );
                    	}
                    	
                    	
                        return null;
                    }
                }).start();	
        } );
	
		if ( PlayerSelectController.selected != null )
			profileLabel.setText( "@" + PlayerSelectController.selected.getUserN() );
		profileLabel.setPadding( new Insets( -Toolkit.getToolkit().getFontLoader().getFontMetrics( profileLabel.getFont() ).getDescent(), 0, 0, 0 ) );
		timerLabel.setPadding( new Insets( -Toolkit.getToolkit().getFontLoader().getFontMetrics( timerLabel.getFont() ).getDescent() * .5, 0, 0, 0 ) );
		scoreLabel.setPadding( new Insets( -Toolkit.getToolkit().getFontLoader().getFontMetrics( scoreLabel.getFont() ).getDescent(), 0, 0, 0 ) );
		
		Thread thread = new Thread( new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                time = 30;
                Platform.runLater( () -> timerLabel.setText( String.format( "Time Left\n00:%02d", time ) ) );
                Platform.runLater( () -> ammoLabel.setText( String.format( "Ammo: %2d", ammo.getRemaining() ) ) );
                	
                while ( time > 0 ) {
                	Thread.sleep( 1000 );
					time--;
					Platform.runLater( () -> timerLabel.setText( String.format( "Time Left\n00:%02d", time ) ) );
                }
                
                endGameLabel.setOpacity( 1 );
    			endGameLabel.setDisable( false );
    			outerTarg1.setVisible( false );
    			outerTarg2.setVisible( false );
    			innerTarg1.setVisible( false );
    			innerTarg2.setVisible( false );
    			bullTarg1.setVisible( false );
    			bullTarg2.setVisible( false );
    			
    			outerTarg1.setDisable( true );
    			outerTarg2.setDisable( true );
    			innerTarg1.setDisable( true );
    			innerTarg2.setDisable( true );
    			bullTarg1.setDisable( true );
    			bullTarg2.setDisable( true );
    			
    			if ( PlayerSelectController.selected != null ) {
	    			System.out.println( "Adding to file" );
	
	    			FileWriter writer = null;
					Scanner scan = null;
					
					try {
						// open the file for reading
						scan = new Scanner( new File( "users.txt" ) );
						writer = new FileWriter( "usersTmp.txt" );
						
						while( scan.hasNextLine() ) {
							// format: userName,password
							String line = scan.nextLine();
							String[] tokens = line.split( "," );
							if ( PlayerSelectController.selected.getUserN().equals( tokens[0] ) &&  Integer.valueOf( tokens[1] ) < score )
								writer.write( String.format( "%s,%d,%s,%s%n", tokens[0], score, tokens[2], tokens[3] ) );
							else
								writer.write( String.format( "%s%n", line ) );
						}
						
						// close the files
						writer.close();
						scan.close();
					} catch( FileNotFoundException ex1 ) {
						System.out.println( "File could not be found!" );
					} catch( IOException ex2 ) {
						ex2.printStackTrace();
					} finally {
						if ( scan != null ) {
							scan.close();
							scan = null;
						}
						if ( writer != null ) {
							try {
								writer.close();
							} catch ( Exception e ) {
								e.printStackTrace();
							}
							writer = null;
						}
						if ( new File( "usersTmp.txt" ).exists() ) {
							new File( "users.txt" ).delete();
							new File( "usersTmp.txt" ).renameTo( new File( "users.txt" ) );
						}
					}
    			}

                return null;
            }
        });
		thread.setDaemon( true );
		thread.start();
	}	
	
	public void handle( MouseEvent event ) {
		if( event.getButton() == MouseButton.PRIMARY && ammo.getRemaining() > 0 ) {
			new Thread(new Task<Void>() {
	            @Override
	            protected Void call() throws Exception {
	            	Platform.runLater( () -> grid.getParent().setCursor( new ImageCursor( new Image( "file:./res/hitmarker.png" ), new Image( "file:./res/hitmarker.jpg" ).getWidth() / 2, new Image( "file:./res/hitmarker.jpg" ).getHeight() / 2 ) ) );
	        		Thread.sleep( 150 );
	            	Platform.runLater( () -> grid.getParent().setCursor( Cursor.CROSSHAIR ) );      
	                return null;
	            }
	        }).start();
			Circle selected = (Circle) event.getSource();
			
			//TODO: two targets cannot be in the same column and row. 
			 Random rand = new Random();
			
			 column1 = rand.nextInt( 5 );
			 row1 = rand.nextInt( 5 );
			 column2 = rand.nextInt( 5 );
			 row2 = rand.nextInt( 5 );
		
			 //testing if the generation is the old position or on top of each other or switch places
			while ( ( column1 == column2 && row1 == row2 ) ||
					( oldRow1 == row1 && oldCol1 == column1 ) ||
					( oldRow2 == row2 && oldCol2 == column2 ) ||
					( oldRow2 == row1 && oldCol2 == column1 ) ||
					( oldRow1 == row2 && oldCol1 == column2 ) )
			{
				column1 = rand.nextInt( 5 );
				row1 = rand.nextInt( 5 );
				column2 = rand.nextInt( 5 );
				row2 = rand.nextInt( 5 );
			}
			System.out.println( "C: " + column1 + " R: " + row1 );
			
			
			if ( selected.equals( outerTarg1 ) || selected.equals( outerTarg2 ) ) {
				score += 10;
				scoreLabel.setText( "SCORE  " + score );
				
			}		
			if ( selected.equals( innerTarg1 ) || selected.equals( innerTarg2 ) ) {
				score += 25;
				scoreLabel.setText( "SCORE  " + score );
			}		
			if ( selected.equals( bullTarg1 ) || selected.equals( bullTarg2 ) ) {
				score += 50;
				scoreLabel.setText( "SCORE  " + score );
			}
			
			// Note: right now the only nodes that call this method are the circles so if statement is redundant in view's current state
			// if any layer of either targets is pressed move both of them
			if ( selected.equals( outerTarg1) || selected.equals( innerTarg1 ) || selected.equals( bullTarg1 ) ||
			   selected.equals( outerTarg2 ) || selected.equals( innerTarg2 ) || selected.equals( bullTarg2 ) ) 
			{
				System.out.println( "Outer circle target hit" );
	
				//TODO: increment score
				GridPane.setColumnIndex( outerTarg1, column1 );
				GridPane.setColumnIndex( innerTarg1, column1 );
				GridPane.setColumnIndex( bullTarg1, column1 );
				GridPane.setRowIndex( outerTarg1, row1 );
				GridPane.setRowIndex( innerTarg1, row1 );
				GridPane.setRowIndex( bullTarg1, row1 );
				oldCol1 = column1;
				oldRow1 = row1;
				
				GridPane.setColumnIndex( outerTarg2, column2 );
				GridPane.setColumnIndex( innerTarg2, column2 );
				GridPane.setColumnIndex( bullTarg2, column2 );
				GridPane.setRowIndex( outerTarg2, row2 );
				GridPane.setRowIndex( innerTarg2, row2 );
				GridPane.setRowIndex( bullTarg2, row2 );
				oldCol2 = column2;
				oldRow2 = row2;		
			}					
		}		
	
	}
	
}
