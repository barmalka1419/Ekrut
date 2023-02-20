package ClientControllers;

import common.Message;
import client.ChatClient;
import client.ClientUI;
import common.ChangeScene;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * The Class ClientConnectionController.
 * Controller for connection to server screen
 */
public class ClientConnectionController {

	Message connect = new Message();
	

    @FXML
    private TextField ip;

    
    /**
     * Gets the ip.
     *
     * @return the ip
     */
    public TextField getIP() {
    	return ip;
    }
    
    /**
     * Close app.
     *
     * @param event the event
     */
    /*
     * exit the app on click
     */
    @FXML
    void closeApp(MouseEvent event) {
   
   
    	System.exit(0);
    }

    /**
     * Connect to server.
     *
     * @param event the event
     */
    /*
     * connect to server by clicking the button
     */
    @FXML
    void connectToServer(MouseEvent event) {
    	
    	/*
    	 * calling the setChat method that create ClientConsole with the IP and Port
    	 */
    	ClientUI.setChat(getIP().getText(), 5555); 
    	connect.setTask("Connect To Server"); // set Task for the server
    	ClientUI.chat.accept(connect); // Send object of Message to the server
    	Message appInstall= new Message();
    	appInstall.setTask("Install App");//added line
    	ClientUI.chat.accept(appInstall);
    	String screen="";
    	String installType=ChatClient.myApp.getInstallType();
    	if(installType.equals("machine")) {
    		screen="/clientGUI/LocalLogin.fxml";
    	}
    	else if(installType.equals("remote")) {
    		screen="/clientGUI/MachineLogin.fxml";
    	}
    	else if(installType.equals("merged")) {
    		screen="/clientGUI/EkrutLogin.fxml";
    	}
    	ChangeScene scene=new ChangeScene(); 
		((Node)event.getSource()).getScene().getWindow().hide(); //hiding primary window
		//scene.changeScreen(new Stage(),"/clientGUI/EkrutLogin.fxml"); // change screen to User Login
		scene.changeScreen(new Stage(),screen);
		//scene.changeScreen(new Stage(),"/clientGUI/RemoteOrderMenu.fxml");
    }

}
