
package application.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import application.model.User;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.stage.StageStyle;

/**
 * @author Matthew Traphan (4/8/2018 - changed controller to use a single handle method instead of
 * multiple and added class variables for the view nodes the controller will be handling)
 * 
 * @author Donovan Medina (4/9/2018 - changed "Menu.fxml", "PlayerSelect.fxml", "HighScore.fxml", "Settings.fxml")
 */
public class MenuController implements Initializable, EventHandler<ActionEvent> {
	
	// class variables (names subject to change)
	@FXML private Button play;
	@FXML private Button settings;
	@FXML private Button scores;
	@FXML private Button exit;

	//call to the Users class
	private static User user;
	
	@Override
	public void initialize( URL location, ResourceBundle resources ) {
		user = new User();
	}

	/**
	 * 
	 */
	@Override
	public void handle( ActionEvent event ) {
		if ( ( (Button) event.getSource() ).equals( play ) ) {
			System.out.println( "Play button clicked" );
			user.readFile();
			try {
				Parent root = FXMLLoader.load( getClass().getResource( "/application/view/PlayerSelect.fxml" ) );
				application.Main.primaryStage.getScene().setRoot( root );
			} catch ( IOException e ) {
				e.printStackTrace();
			}
		}
		else if ( ( (Button) event.getSource() ).equals( settings ) ) {
			System.out.println( "Settings button clicked" );
			
			try {
				Parent root = FXMLLoader.load( getClass().getResource( "/application/view/Setting.fxml" ) );
				application.Main.primaryStage.getScene().setRoot( root );
			} catch ( IOException e ) {
				e.printStackTrace();
			}
		}
		else if ( ( (Button) event.getSource() ).equals( scores ) ) {
			System.out.println( "Scores button clicked" );
			user.readFile();
			try {
				Parent root = FXMLLoader.load( getClass().getResource( "/application/view/HighScore.fxml" ) );
				//TODO: some sort of ObservableList view to see HighScores centered on the black background
				application.Main.primaryStage.getScene().setRoot( root );
			} catch ( IOException e ) {
				e.printStackTrace();
			}
		}
		else {
			System.out.println( "Exit button clicked" );
			Alert alert = new Alert( AlertType.CONFIRMATION, "Do you really want to exit The Range?", ButtonType.YES, ButtonType.NO );
			alert.setTitle( "Confirm" );
			alert.setHeaderText( null );
			alert.initStyle( StageStyle.UNDECORATED );
			alert.initOwner( application.Main.primaryStage );
			alert.getDialogPane().setStyle( "-fx-background-color: green" );

			if ( alert.showAndWait().get() == ButtonType.YES )
				application.Main.primaryStage.close();
		}
		
	}


	/**
	 * @return the user
	 */
	public static User getUser() {
		return user;
	}


	/**
	 * @param user the user to set
	 */
	public static void setUser( User user ) {
		MenuController.user = user;
	}

}
