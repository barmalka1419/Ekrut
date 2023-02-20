package server;

import common.*;
import common.ChangeScene;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;
import serverController.CreateReportController;

public class ServerUI extends Application {

	
	final public static int DEFAULT_PORT = 5555;
	public static EchoServer echoServer;
	public static boolean isConnected = false;
	 
	@Override
	public void start(Stage primaryStage) {
		
		@SuppressWarnings("unused")		
		ChangeScene scene = new ChangeScene();
		scene.changeScreen(primaryStage, "/serverGUI/serverGui.fxml");

	}

	public static void startServer() {
		
		 int port = 0; //Port to listen on

		    try
		    {
		      port = 5555; //Integer.parseInt(DEFAULT_PORT); //Get port from command line
		    }
		    catch(Throwable t)
		    {
		      port = DEFAULT_PORT; //Set port to 5555
		    }
			
		    echoServer = new EchoServer(port);
		    
		    try 
		    {
		    	echoServer.listen(); //Start listening for connections
		    	isConnected = true;
		    } 
		    catch (Exception ex) 
		    {
		    	
		      System.out.println("ERROR - Could not listen for clients!");
		    }
		
	}
	
	

	public static void main(String[] args) {
		launch(args);
	}
	
}