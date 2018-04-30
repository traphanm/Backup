
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
 * @author Donovan Medina - (4/9/2018 generated SettingController to be in charge of Setting.fxml)
 *
 */
public class SettingController implements EventHandler< ActionEvent >{

	// class variables
	@FXML
	private Button backBtn;
	@FXML
	private Button ruleBtn;
	
	@Override
	public void handle( ActionEvent event) {
		
		Button selected = (Button) event.getSource();
		
		
		if ( selected.equals(backBtn) ) {
			System.out.println( "Back button pressed" );
			
			try {
				Parent root = FXMLLoader.load( getClass().getResource( "/application/view/Menu.fxml" ) );
				application.Main.primaryStage.getScene().setRoot( root );
			} catch ( IOException e ) {
				e.printStackTrace();
			}
			
		}
		
		if( selected.equals(ruleBtn) ) {
			System.out.println( "Back button pressed" );
			
			try {
				Parent root = FXMLLoader.load( getClass().getResource( "/application/view/Rules.fxml" ) );
				application.Main.primaryStage.getScene().setRoot( root );
			} catch ( IOException e ) {
				e.printStackTrace();
			}
			
		}
		
	}
	
}
