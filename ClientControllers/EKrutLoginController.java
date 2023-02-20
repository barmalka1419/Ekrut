package ClientControllers;

import common.Message;
import entitiesController.userLoginController;
import client.ChatClient;
import client.ClientUI;
import common.ChangeScene;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class EKrutLoginController {

	Message connect = new Message();
	
    @FXML
    private Button LocalLoginBTN;
	
    /*
     * By clicking login button -> screen change to service worker menu
     */
    @FXML
    void LoginByuserAndPass(ActionEvent event) {
    	
    	ChangeScene scene=new ChangeScene();
		((Node)event.getSource()).getScene().getWindow().hide(); //hiding primary window
		scene.changeScreen(new Stage(),"/clientGUI/LocalLogin.fxml");
		
    }
    
    @FXML
    void remoteLogin(ActionEvent event) {
    	ChatClient.userloginController.setLocalLogin(false);
    	ChangeScene scene=new ChangeScene();
		((Node)event.getSource()).getScene().getWindow().hide(); //hiding primary window
		scene.changeScreen(new Stage(),"/clientGUI/MachineLogin.fxml");
    }
    
    @FXML
    void ClickIOnExitBtn(MouseEvent event) {
	  	Message connect = new Message();
		connect.setTask("Disconnect Server");
		ClientUI.chat.accept(connect);
		System.exit(0);    
		}
    


}