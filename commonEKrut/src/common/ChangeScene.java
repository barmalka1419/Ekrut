package common;

import java.util.EventObject;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * The Class ChangeScene.
 * class for managing changing between the app's screens
 */
public class ChangeScene {
	
	public Stage screen ;//added variable to keep current screen
	
	/**
	 * Change screen.
	 * function for changing the screen
	 * @param primaryStage the primary stage
	 * @param path the path
	 */
	public void changeScreen(Stage primaryStage,String path)
	{
		try {
		
			Parent root = FXMLLoader.load(getClass().getResource(path));	
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.initStyle(StageStyle.UNDECORATED);
		
			root.setOnMousePressed(pressEvent -> {
			    root.setOnMouseDragged(dragEvent -> {
			        primaryStage.setX(dragEvent.getScreenX() - pressEvent.getSceneX());
			        primaryStage.setY(dragEvent.getScreenY() - pressEvent.getSceneY());
			    });
			});
			
			screen=primaryStage;//added Line
			primaryStage.show();
			} catch(Exception e) {
				e.printStackTrace();
			}
	}

	
	/**
	 * Show information alert.
	 *  shows sms/ e-mail simulation pop up
	 * @param text the text
	 * @param header the header
	 */
	public static void showInformationAlert(String text,String header) {
		
	 	  // create a alert
			  Alert info = new Alert(AlertType.NONE);
			    // set alert type
			
			  info.setTitle("Simulation");
			
			info.setAlertType(AlertType.INFORMATION);

			info.setHeaderText(text);
	        
	        // show the dialog
			info.showAndWait();
		
	}
	

	/**
	 * Show information alert.
	 * shows a pop up window for notification(not simulation)
	 * @param text the text
	 */
	public static void showInformationAlert(String text) {
		
	 	  // create a alert
			  Alert info = new Alert(AlertType.NONE);
			    // set alert type
			
			info.setAlertType(AlertType.INFORMATION);

			info.setHeaderText(text);
	        
	        // show the dialog
			info.showAndWait();
		
	}
	
	
	/**
	 * Confirmation alert.
	 * function for creating pop up with confirmation and cancel options
	 * @param gotoscreen the gotoscreen
	 * @param event the event
	 */
	public static void confirmationAlert(String gotoscreen,EventObject event) {
		
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		alert.setTitle("Define Sale Succeeded!");
		alert.setContentText("Would you like to define another sale?");
		ButtonType okButton = new ButtonType("Yes", ButtonData.YES);
		ButtonType noButton = new ButtonType("No", ButtonData.NO);
		alert.getButtonTypes().setAll(okButton, noButton);
		alert.showAndWait().ifPresent(type -> {
		        if (type.getText().equals("No")) {
		
		           	ChangeScene scene=new ChangeScene();
		           	scene.changeScreen(new Stage(),"/clientGUI/"+gotoscreen+".fxml");
		    		((Node)event.getSource()).getScene().getWindow().hide(); //hiding primary window
		        	
		        }
		});
		
		
 
    
	}

}
