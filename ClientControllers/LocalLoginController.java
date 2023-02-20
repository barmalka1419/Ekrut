package ClientControllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.scene.image.ImageView;
import java.net.URL;
import java.util.ResourceBundle;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import client.ChatClient;
import client.ClientUI;
import common.ChangeScene;
import common.Message;
import common.Message;


/**
 * The Class LocalLoginController.
 * Controller for local login screen- the main screen of a device(machine)
 * lets members login  to a machine with the smartphone.
 * regular customers can move from here to regula login
 */
public class LocalLoginController implements Initializable {
	
	@FXML
    private Button PhoneBtn;


    @FXML
    private ImageView BackBtn;
    
    /**
     * Click on move to login form.
     *	moves the user to regular login form screen(MachineLogin)
     * @param event the event
     */
    @FXML
    void ClickOnMoveToLoginForm(MouseEvent event) {
    	ChangeScene scene=new ChangeScene();
		((Node)event.getSource()).getScene().getWindow().hide(); //hiding primary window
		ChatClient.currentScreen.changeScreen(new Stage(),"/clientGUI/MachineLogin.fxml");
		ChatClient.userloginController.setLocalLogin(true);
    }
    
    
    /**
     * Click on back btn.
     *	moves the user to EkrutLogin Screen
     * @param event the event
     */
    @FXML
    void ClickOnBackBtn(MouseEvent event) {
    	ChangeScene scene=new ChangeScene();
		((Node)event.getSource()).getScene().getWindow().hide(); //hiding primary window
		ChatClient.currentScreen.changeScreen(new Stage(),"/clientGUI/EkrutLogin.fxml");
    }

    /**
     * Click on exit btn.
     *	closes the app
     * @param event the event
     */
    @FXML
    void ClickOnExitBtn(MouseEvent event) {
    	System.exit(0);
    }


	/**
	 * Initialize.
	 * disable back button if the install type is "machine"(device)
	 * @param arg0 the arg 0
	 * @param arg1 the arg 1
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// if that machine not needed a back button 
		if(ChatClient.myApp.getInstallType()!=null &&ChatClient.myApp.getInstallType().equals("machine")) {
			BackBtn.setVisible(false);
		}	
		ChatClient.userloginController.setLocalLogin(true);
	}
	
	
	   /**
   	 * Click on phone attach image.
   	 *	tries to login the customer that is loaded in smartphone login simulation
   	 *	if the customer isn't a member logs him out and shows a relevant message
   	 * @param event the event
   	 */
   	@FXML
	    void ClickOnPhoneAttachImage(MouseEvent event) {
		 
			Message msg = new Message();
			msg.setTask("get smart phone simulation user data");
			client.ClientUI.chat.accept(msg);
			
			if(ChatClient.userloginController.getUser()==null) {
				ChangeScene.showInformationAlert("App login fail!");
				return;
			}
			
			
			if(ChatClient.userloginController.getUser().getUserPermission()!=null &&(ChatClient.userloginController.getUser()).getUserPermission().equals("member")) {
		   ChangeScene scene=new ChangeScene();
			((Node)event.getSource()).getScene().getWindow().hide(); //hiding primary window
			ChatClient.currentScreen.changeScreen(new Stage(),"/clientGUI/MachineMenu.fxml");
			}
			else {
				Message disconnect = new Message();				
				disconnect.setTask("User wants To Logout");
				disconnect.setData(ChatClient.userloginController.getUser());
		    	ClientUI.chat.accept(disconnect);
				ChangeScene.showInformationAlert(" hey  you are not a member Therefore you can't login with the phone,\r\n"
						+ "please click on the button 'Move To Login Form' \r\n"
						+ "or contact with customer service to register as a member");
				
			}
	    }
	
}
