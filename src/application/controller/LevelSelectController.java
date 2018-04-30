
package application.controller;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;

/**
 * 
 * @author Mathew Traphan
 */
public class LevelSelectController implements EventHandler<ActionEvent>  {
	
	// class variables
	@FXML private Button backBtn, level1, level2, level3;
	
	private int maxScore1 = 1000; // maxScore2 = 2000;
	/**
	 * 
	 */
	@Override
	public void handle( ActionEvent event ) {
		// this method will be for the buttons to load chosen level
		if ( ( (Button) event.getSource() ).equals( backBtn ) ) {
			try {
				PlayerSelectController.selected = null;
				Parent root = FXMLLoader.load( getClass().getResource( "/application/view/PlayerSelect.fxml" ) );
				application.Main.primaryStage.getScene().setRoot( root );
			} catch ( IOException e ) {
				e.printStackTrace();
			}
		}
		if ( ( (Button) event.getSource() ).equals( level1 ) ) {
			try {
				Parent root = FXMLLoader.load( getClass().getResource( "/application/view/Level1.fxml" ) );
				application.Main.primaryStage.getScene().setRoot( root );
			} catch ( IOException e ) {
				e.printStackTrace();
			}
		}
		if ( ( (Button) event.getSource() ).equals( level2 ) && (PlayerSelectController.selected.getL1()>=maxScore1) ) {
			try {
				Parent root = FXMLLoader.load( getClass().getResource( "/application/view/Level2.fxml" ) );
				application.Main.primaryStage.getScene().setRoot( root );
			} catch ( IOException e ) {
				e.printStackTrace();
			}
		}
	}
	
}
