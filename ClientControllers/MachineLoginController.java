package ClientControllers;

import common.Message;
import entities.Customer;
import entities.Employee;
import entities.Member;
import entities.User;
import entitiesController.userLoginController;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import client.ChatClient;
import client.ClientUI;
import common.ChangeScene;
import javafx.collections.FXCollections;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import javafx.scene.text.Text;

/**
 * The Class MachineLoginController is controller for the screen MachineLogin.fxml.
 * This class is setup the screen at first and than gives the user option to enter username and password
 * to login into the system according his permission it will take him to the correct area in the system
 */
public class MachineLoginController implements Initializable{

	Message connect = new Message();
	
    @FXML
    private Button LgnBTN;
    @FXML
    private Button LogoutBTN;
	@FXML
	private TextField UserField;
	@FXML
	private PasswordField PassField;    
	@FXML
	private Text ErrTxt;   

	private User user1;
	private  String nextScreen;

    @FXML
    private ImageView backBtn;
	

 
    /**
     * Sets the return message.
     *
     * @param rMessage
     */
    public void setReturnMessage(String rMessage) {
    	ErrTxt.setText(rMessage);
    }
    
    
    /**
     * This function is Login to system checking the textboxes that everything
     * is correctly entered and with correct values.
     *
     * @param event the event
     */
    @FXML
    void loginToSystem(ActionEvent event) {

    	user1=new User();
    	
      	if(UserField.getText().isEmpty()) {
	    	ErrTxt.setText("Username is Empty!");
	        return;
    	}
        if(PassField.getText().isEmpty()) {
        	ErrTxt.setText("Password is Empty!");
            return;
        }
    	
    	
    	user1.setUserName(UserField.getText());
		user1.setPassword(PassField.getText());
  
	    	connect.setTask("User wants To Login");
	    	connect.setData(user1);
	    	ClientUI.chat.accept(connect);
	    	setReturnMessage(ChatClient.getMessageLogin());
	    	String rMessage =ChatClient.getMessageLogin();
	    	if(rMessage.equals("User has no permissions")|| rMessage.equals("User still in pending position!"))
	    	{
	    		ErrTxt.setText(rMessage);
	    		return;
	    	}
	    	user1=ChatClient.userloginController.getUser();
	    	if(rMessage.equals("User Login Succeed"))
	    	{
		    user1.setLoginType("Local");
		    switch(user1.getUserPermission()) {
			  
	  		case "ceo":
	  			nextScreen="/clientGUI/CEOReportOption.fxml";
	  			break;
	  		case "customer":
	  			nextScreen="/clientGUI/RemoteOrderMenu.fxml";
	  			break;
	  		case "member":
	  			nextScreen="/clientGUI/RemoteOrderMenu.fxml";
	  			break;
	  		case "regional manager":
	  			nextScreen="/clientGUI/RegionalManagerMenu.fxml";
	  			break;
	  		case "delivery manager":
	  			nextScreen="/clientGUI/DeliveryManagerMenu.fxml";
	  			break;  
	  		case "service employee":
	  			nextScreen="/clientGUI/ServiceEmployeeMenu.fxml";
	  			break;
	  		case "marketing manager":
	  			nextScreen="/clientGUI/MarketingManagerMenu.fxml";
	  			break;
	  		case "marketing employee":
	  			nextScreen="/clientGUI/MarketingWorkerMenu.fxml";
	  			break;
	  		case "logistics employee":
	  			nextScreen="/clientGUI/LogisticsWorkerMenu.fxml";
	  			break;
		    }	
	  		    if(ChatClient.userloginController.getLocalLogin()) {
			    	if(ChatClient.userloginController.getUser()!=null && !(ChatClient.userloginController.getUser() instanceof Customer)) {
			    		ErrTxt.setText("User is not a Customer");
			    		connect.setTask("User wants To Logout");
			        	connect.setData(user1);
			        	ClientUI.chat.accept(connect);
			        	return;
			    	}
			    	else {
			    		nextScreen="/clientGUI/MachineMenu.fxml";
			    		//ChatClient.userloginController.setLocalLogin(false);
			    	}
			    }	  		
    		ChangeScene scene=new ChangeScene();
			((Node)event.getSource()).getScene().getWindow().hide(); //hiding primary window
			scene.changeScreen(new Stage(),nextScreen);
	    	}
		}


    /**
     * User logout.
     *
     * @param event the ActionEvent
     */
    @FXML
    void UserLogout(ActionEvent event) {
        connect.setTask("User wants To Logout");
    	connect.setData(user1);
    	ClientUI.chat.accept(connect);
    	System.exit(0);
    }

    /**
     * Click on Go back change screen for the user to the previous screen he was in.
     *
     * @param event the ActionEvent
     */
    @FXML
    void clickOnBackBtn(MouseEvent event) {
    	if(ChatClient.userloginController.getLocalLogin()==true) {
    		ChangeScene scene=new ChangeScene();
			((Node)event.getSource()).getScene().getWindow().hide(); //hiding primary window
			scene.changeScreen(new Stage(),"/clientGUI/LocalLogin.fxml");
    	}
    	else {
    		ChangeScene scene=new ChangeScene();
			((Node)event.getSource()).getScene().getWindow().hide(); //hiding primary window
			scene.changeScreen(new Stage(),"/clientGUI/EkrutLogin.fxml");
    	}

    }
    
    /**
     * This function is to Click exit and logout and terminate the app completely.
     *
     * @param event the ActionEvent
     */
    @FXML
    void clickOnExitBtn(MouseEvent event) {
    	Message connect = new Message();
		connect.setTask("Disconnect Server");
		ClientUI.chat.accept(connect);
		System.exit(0);

    }

	/**
	 * Initialize the machine login screen.
	 *
	 * @param arg0 the URL
	 * @param arg1 the ResourceBundle 1
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		if(ChatClient.myApp.getInstallType().equals("remote")) {
			backBtn.setVisible(false);
		}
		else {
			backBtn.setVisible(true);
		}
	}


}