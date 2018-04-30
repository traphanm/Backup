
package application.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;
import application.Main;
import application.model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


/**
 * 
 * @author	Matthew Traphan (4/8/2018 - changed controller to use a single handle method instead of
 * multiple and added class variables for the view nodes the controller will be handling)
 */
public class PlayerSelectController implements Initializable, EventHandler<ActionEvent> {
	
	// class variables (names subject to change)
	@FXML private Button backBtn;
	@FXML private ListView<String> players;
	@FXML private Button createNewBtn;
	@FXML private Button contNoSaveBtn;
	public static User selected;
	
	/**
	 * 
	 */
	@Override
	public void initialize( URL location, ResourceBundle resources ) {
		ObservableList<String> items = FXCollections.observableArrayList();
		for ( User user : User.userList )
			items.add( user.getUserN() );
		players.setItems( items );
		players.setCellFactory( ( ListView<String> listview ) -> new ListCell<String>() {
			private GridPane cell;
			private Button user;
						
			{
				cell = new GridPane();
				cell.setStyle( "-fx-background-color: black; -fx-background-insets: 0 0 -1" );
				cell.setPrefHeight( players.getHeight() * .148 );
				user = new Button();
				ColumnConstraints col = new ColumnConstraints(), col2 = new ColumnConstraints();
				col.setPercentWidth( 95 );
				col2.setPercentWidth( 5 );
				RowConstraints row = new RowConstraints();
				row.setPercentHeight( 100 );
				cell.getColumnConstraints().addAll( col, col2 );
				cell.getRowConstraints().addAll( row );
				cell.add( user, 0, 0 );
				GridPane deleteCell = new GridPane();
				ColumnConstraints col3 = new ColumnConstraints();
				col3.setPercentWidth( 100 );
				RowConstraints row2 = new RowConstraints();
				row2.setPercentHeight( 30 );
				deleteCell.getColumnConstraints().addAll( col3 );
				deleteCell.getRowConstraints().addAll( row2 );
				Button delete = new Button();
				delete.setMinSize( 0, 0 );
				delete.setMaxSize( Double.MAX_VALUE, Double.MAX_VALUE );
				delete.setCursor( Cursor.HAND );
				delete.setId( "delete" );
				delete.setOnAction( ae -> {
					Alert alert = new Alert( AlertType.CONFIRMATION, String.format( "Are you sure you want to DELETE Player '%s'?", user.getText() ), ButtonType.YES, ButtonType.NO );
					alert.setTitle( "Confirm" );
					alert.setHeaderText( null );
					alert.initStyle( StageStyle.UNDECORATED );
					alert.initOwner( application.Main.primaryStage );
					alert.getDialogPane().setStyle( "-fx-background-color: red" );
					alert.getDialogPane().lookup( ".content" ).setStyle( "-fx-text-fill: white" );
					// alert.getDialogPane().lookup( ".content" ).lookup( ".label" ).getStyleClass().add( "outline" );
					// alert.getDialogPane().getStylesheets().addAll( getClass().getResource( "/application/view/Outline.css" ).toExternalForm() );
					
					if ( alert.showAndWait().get() == ButtonType.YES ) {
						System.out.println( "Deleted!" );
							
						FileWriter writer = null;
						Scanner scan = null;
						
						try {
							// open the file for reading
							scan = new Scanner( new File( "users.txt" ) );
							writer = new FileWriter( "usersTmp.txt" );
							
							while( scan.hasNextLine() ) {
								// format: userName,password
								String line = scan.nextLine();
								if ( !line.split( "," )[0].equals( user.getText() ) ) {
									writer.write( String.format( "%s%n", line ) );
								}
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
						
						User.userList.remove( getIndex() );
						items.clear();
						for ( User user : User.userList )
							items.add( user.getUserN() );
					}
				} );
				deleteCell.add( delete, 0, 0 );
				cell.add( deleteCell, 1, 0 );
				
				user.setMaxSize( Double.MAX_VALUE, Double.MAX_VALUE );
				user.setCursor( Cursor.HAND );
				user.setStyle( "-fx-background-color: black; -fx-font-family: Book Antiqua Bold Italic" );
				user.setTextFill( Paint.valueOf( "linear-gradient(to bottom, #07ff2e 38%, #ffffff 55%, #000000 61%, #07ff2e 68%)" ) );
		        user.setOnAction( new EventHandler<ActionEvent>() {
		            @Override
		            public void handle( ActionEvent event ) {
		            	System.out.println( "Saved Player button clicked" + getItem() );
		            	selected = User.userList.get( getIndex() );
		    			try {
		    				Parent root = FXMLLoader.load( getClass().getResource( "/application/view/SelectLevel.fxml" ) );
		    				application.Main.primaryStage.getScene().setRoot( root );
		    			} catch ( IOException e ) {
		    				e.printStackTrace();
		    			}
		            }
		        } );
		        setText(null);
			}
			
			@Override protected void updateItem( String item, boolean empty ) {
				super.updateItem( item, empty );
				if ( empty || item == null ) {
    				setText( null );
    				setGraphic( null );
                }
                else {
                	user.setText( item ); 
                    setGraphic( cell );
                }
			}
		} );
	}
	
	/**
	 * 
	 */
	@Override
	public void handle( ActionEvent event ) {		
		
		if ( ( (Button) event.getSource() ).equals( createNewBtn ) ) {
			System.out.println( "Create New button clicked" );
			
			TextInputDialog dialog = new TextInputDialog( null );
			 
			dialog.setTitle( "Create Player" );
			dialog.setHeaderText( null );
			dialog.initStyle( StageStyle.UNDECORATED );
			dialog.initOwner( application.Main.primaryStage );
			dialog.setContentText("Enter a player name:");
			( (Button) dialog.getDialogPane().lookupButton( ButtonType.OK ) ).addEventFilter( ActionEvent.ACTION, ae -> {
				if ( dialog.getEditor().getText() == null || dialog.getEditor().getText().equals( "" ) )
					ae.consume();
				for ( User user : User.userList )
					if ( dialog.getEditor().getText().equals( user.getUserN() ) ) {
						new Alert( AlertType.ERROR ) { 
							{
								initOwner( Main.primaryStage );
								initStyle( StageStyle.UNDECORATED );
								( (Stage) getDialogPane().getScene().getWindow() ).setAlwaysOnTop( true );
								setTitle( "Error Dialog" );
								setHeaderText( null );
								setContentText( String.format( "Player name '%s' already exists. Try Again!", dialog.getEditor().getText() ) );
							} 
						}.showAndWait();
						ae.consume();
					}
			} );
			
			dialog.showAndWait().ifPresent( name -> {
			    System.out.println( name );
			    User.userList.add( new User( name, -1, -1, -1 ) );
			    selected = User.userList.get( User.userList.size() - 1 );
			    
			    try {
			    	FileWriter writer = null;
					
					try {
						writer = new FileWriter( "users.txt", true );
						
						writer.write( String.format( "%s,-1,-1,-1%n", name ) );
						
						writer.close();
					} catch ( IOException e ) {
						e.printStackTrace();
					} finally {
						if ( writer != null ) {
							writer.close();
							writer = null;
						}
					}
					AnchorPane root = FXMLLoader.load( getClass().getResource( "/application/view/Level1.fxml" ) );
					application.Main.primaryStage.getScene().setRoot( root );
				} catch ( IOException e ) {
					e.printStackTrace();
				}
			} );
			
		}
		else if ( ( (Button) event.getSource() ).equals( contNoSaveBtn ) ) {
			System.out.println( "Continue Without Saving button clicked" );
			
			try {
				AnchorPane root = FXMLLoader.load( getClass().getResource( "/application/view/Level1.fxml" ) );
				application.Main.primaryStage.getScene().setRoot( root );
			} catch ( IOException e ) {
				e.printStackTrace();
			}
		}
		else if ( ( (Button) event.getSource() ).equals( backBtn ) ) {
			System.out.println( "Back button clicked" );
			
			try {
				MenuController.getUser().getLevelThree().clear();
				MenuController.getUser().getLevelTwo().clear();
				MenuController.getUser().getLevelOne().clear();
				Parent root = FXMLLoader.load( getClass().getResource( "/application/view/Menu.fxml" ) );
				application.Main.primaryStage.getScene().setRoot( root );
			} catch ( IOException e ) {
				e.printStackTrace();
			}
		}
		else {
			
		}
	}

}

