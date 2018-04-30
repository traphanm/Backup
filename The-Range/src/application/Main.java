
package application;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCombination;
import javafx.stage.Stage;

/**
 * The Range
 * 
 * <p>A static-frame shooting range simulator where users will race against the clock to
 * eliminate targets (positioning and amount will be determined by a selected level’s design)
 * of varying ranges (depth) by moving a pair of crosshairs around the screen with a mouse.
 * Users will be able to make profiles in which the game can save progression and log scores to.</p>
 * 
 * <p>The Main class for The Range handles launching the application and starting the user at a
 * menu scene where they can either choose to start the game, view the game's high score record,
 * or make changes to the game's settings.</p>
 * 
 * @author Matthew Traphan - Lead Engineer 
 * @author Donovan Medina - GUI/UI with FXML 
 * @author Jordan Garza - User object/Save System
 * @author Long Trac - Game Play mechanics when targets are shot
 * @author Alberto Guillen - HighScore and Timer
 */
public class Main extends Application {
	
	// static class variable created to give the application's
	// related classes easy access to changing the scene on the
	// application's stage
	public static Stage primaryStage;
	
	/**
	 * Sets the initial scene to the stage and displays the stage to the user.
	 * 
	 * @param primaryStage The Stage where the application's scenes are displayed on.
	 */
	@Override
	public void start( Stage primaryStage ) {
		try {
			Main.primaryStage = primaryStage;
			Main.primaryStage.setTitle( "The Range" );
			
			// Load the FXML document (we created with SceneBuilder)
			Parent root = FXMLLoader.load( getClass().getResource( "/application/view/Menu.fxml" ) );
			
			// Set the scene (loaded with the FXML) to stage and show the stage to the user
			Main.primaryStage.setScene( new Scene( root ) );
			Main.primaryStage.setFullScreen( true );
			Main.primaryStage.setFullScreenExitHint( "" );
			Main.primaryStage.setFullScreenExitKeyCombination( KeyCombination.NO_MATCH );
			Main.primaryStage.show();
		} catch( Exception e ) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Calls the function to launch the application.
	 * 
	 * @param args A string array containing the command line arguments.
	 */
	public static void main( String[] args ) {
		// Launches the application (calls start method in the process)
		launch( args );
	}
	
}
