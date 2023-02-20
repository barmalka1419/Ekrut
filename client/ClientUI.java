package client;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormatSymbols;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Calendar;

import common.ChangeScene;

import javafx.application.Application;
import javafx.stage.Stage;

/*
 * Test Elior && Ofir 1111
 */

public class ClientUI extends Application {

	/*
	 * static ClientConsole instance that let the UI communicate with ChatClient
	 * 
	 */
	public static ClientConsole chat; 

	/*
	 *  JavaFX method that start the ClientUI 
	 *  
	 */
	@Override
	public void start(Stage primaryStage) {	
		
		ChangeScene scene = new ChangeScene();
		scene.changeScreen(primaryStage, "/clientGUI/ConnectToServer.fxml");
		//scene.changeScreen(primaryStage, "/clientGUI/OrderFailPopUp.fxml");
	}
	
	/*
	 * setChat creates ClientConsole instance that let the client GUI communicate with ClientChat, 
	 * that communicate with the server
	 */
	public static void setChat(String ip, int port) {
		chat = new ClientConsole(ip, port);
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
}