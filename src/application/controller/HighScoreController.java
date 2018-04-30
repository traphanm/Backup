
package application.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

/**
 * 
 * @author Donovan Medina (4/9/2018 - generated HighScore Controller which will be in charge of the HighScore.fxml)
 *
 */
public class HighScoreController implements Initializable, EventHandler<ActionEvent> {

	// class variables
	@FXML
	private Button backBtn;
	@FXML
	private ListView<String> highScores;
		
	private ObservableList<String> highScore;
	
	@Override
	public void handle( ActionEvent event ) {
		System.out.println("Back button pressed");
		try {
			MenuController.setUser( null );
			Parent root = FXMLLoader.load( getClass().getResource( "/application/view/Menu.fxml" ) );
			application.Main.primaryStage.getScene().setRoot( root );
		} catch ( IOException e ) {
			e.printStackTrace();
		}
	}

	@Override
	public void initialize( URL location, ResourceBundle resources ) {
		highScore = FXCollections.observableArrayList();
		for( int i = 0; i < MenuController.getUser().getLevelThree().size(); i++ ) {
			if( MenuController.getUser().getLevelThree().get(i).getL3() > 0 ) {			
				highScore.add( String.format("%-30s Level 3:	 %32s",MenuController.getUser().getLevelThree().get(i).getUserN(),MenuController.getUser().getLevelThree().get(i).getL3() ) );
			}
		}
		
		highScore.add("\n");
		
		for( int i = 0; i < MenuController.getUser().getLevelTwo().size(); i++ ) {
			if( MenuController.getUser().getLevelTwo().get(i).getL2() > 0 ) {
				highScore.add( String.format("%-30s Level 2:	 %32s",MenuController.getUser().getLevelTwo().get(i).getUserN(), MenuController.getUser().getLevelTwo().get(i).getL2() ) );

			}
		}

		highScore.add("\n");

		for( int i = 0; i < MenuController.getUser().getLevelOne().size(); i++ ) {
			if( MenuController.getUser().getLevelTwo().get(i).getL1() > 0 ) {
				highScore.add( String.format("%-30s Level 1:	 %32s",MenuController.getUser().getLevelOne().get(i).getUserN(), MenuController.getUser().getLevelOne().get(i).getL1() ) );


			}
		}
		highScores.setItems(highScore);
	}
}
