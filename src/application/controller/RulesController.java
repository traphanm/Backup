
package application.controller;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;

public class RulesController implements EventHandler<ActionEvent> {
	
	@FXML
	private Button backBtn;

	@Override
	public void handle( ActionEvent event ) {
				
		if( ( (Button) event.getSource() ).equals(backBtn) ) {
			System.out.println( "Back button pressed" );
			
			try {
				Parent root = FXMLLoader.load( getClass().getResource( "/application/view/Menu.fxml" ) );
				application.Main.primaryStage.getScene().setRoot( root );
			} catch ( IOException e ) {
				e.printStackTrace();
			}
			
		}
	}
}
