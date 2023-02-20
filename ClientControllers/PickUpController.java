package ClientControllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import client.ChatClient;
import client.ClientUI;
import common.ChangeScene;
import common.Message;
import entities.Device;
import entities.User;
import entitiesController.NotificationController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;


/**
 * The Class PickUpController.
 * Controller for pickup screen.
 * lets the user pick his order from the machine
 */
public class PickUpController implements Initializable{

    @FXML
    private Text ErrorText;

    @FXML
    private TextField OrderNumberText;

    
    // found in data base
    // type --> pickup
    //check that is no already pickup 
    //device location === pickup location 
    /**
     * Click on pick up now btn.
     *	check the order number entered by the user and shows a relevant message.
     *	if the order can be picked up also updates the pickup order status.
     *  after picking up the order moves back to the machine menu
     * @param event the event
     */
    //order ----> user that login (check)
    @FXML
    void ClickOnPickUpNowBtn(ActionEvent event) {
    	
    	
    	
    	
    	if((OrderNumberText.getText()).length() ==0) {
    		ErrorText.setText("Must Enter a Order Number");
    		return;
    	}
    	
    	for(int i = 0 ; i < (OrderNumberText.getText()).length();i++) {
    		
    		if(i==0) {
    			if((OrderNumberText.getText()).charAt(i)== '0') {
    				ErrorText.setText("Order Number Cant begin with 0");
        			return;
    			}
    		}
    		
    		if((OrderNumberText.getText()).charAt(i) > '9' || (OrderNumberText.getText()).charAt(i)<'0') {
    			ErrorText.setText("The Order Number must Contain Only Digits");
    			return;
    		}

    	}
    	ArrayList<Object>pickUpDetails=new ArrayList<>();
    	pickUpDetails.add(client.ChatClient.userloginController.getUser());
    	pickUpDetails.add(OrderNumberText.getText());
    	pickUpDetails.add(client.ChatClient.MyDevice.getMyDevice()); // make sure we know the device that the user use to pick up his order
    	Message msg = new Message();
    	msg.setTask("Check Order Number");
    	msg.setData(pickUpDetails);
    	ClientUI.chat.accept(msg);
    	String returnMessage = ChatClient.getReturnMessage();
    	if(!returnMessage.equals("pickup success")) {
    		ErrorText.setText(returnMessage);
    	}
    	else {
    
    		
	
			    ChangeScene.showInformationAlert("you can pick up your order ");
    	        
    	        ChangeScene scene=new ChangeScene(); // need to change that we want to logout the user ! guy  said that 
    			((Node)event.getSource()).getScene().getWindow().hide(); //hiding primary window
    			scene.changeScreen(new Stage(),"/clientGUI/MachineMenu.fxml");
    	    }
    	
    	        
    	}

    
    
    /**
     * Click on back btn.
     *moves the user back to the machine menu
     * @param event the event
     */
    @FXML
    void ClickOnBackBtn(MouseEvent event) {
    	ChangeScene scene=new ChangeScene();
		((Node)event.getSource()).getScene().getWindow().hide(); //hiding primary window
		scene.changeScreen(new Stage(),"/clientGUI/MachineMenu.fxml");
    }

	/**
	 * Initialize.
	 *
	 * @param arg0 the arg 0
	 * @param arg1 the arg 1
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		
	}

}
