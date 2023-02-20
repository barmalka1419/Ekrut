package ClientControllers;

import client.ChatClient;
import client.ClientUI;
import common.ChangeScene;
import common.Message;
import entities.Employee;
import entitiesController.userLoginController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * The Class DeliveryManagerMenuController.
 * Controller for delivery manager menu screen
 * Manager has a option to go to delivery management 
 *   
 */
public class DeliveryManagerMenuController {

    @FXML
    private Text txtRegion;

    @FXML
    private Text welcomeMessage;

	
    Employee employee = (Employee)ChatClient.userloginController.getUser();
	
	/**
	 * Initialize.
	 * Set welcome message and region for employee
	 */
	@FXML
	public void initialize() {
		
		
	  	welcomeMessage.setText("Hello " + employee.getFirstName() + " " + employee.getLastName());
    	txtRegion.setText("Region: " + employee.getRegion());
    		
	  
	}
	
	
    /**
     * Click manage deliveries, move the employee to delivery management screen
     *
     * @param event ActionEvent
     */
    @FXML
    void clickManageDeliveries(ActionEvent event) {

      	ChangeScene scene=new ChangeScene();
		((Node)event.getSource()).getScene().getWindow().hide(); //hiding primary window
		scene.changeScreen(new Stage(),"/clientGUI/DeliveryManagerView.fxml");
    	
    	
    }


    /**
     * Close app and logout EKrut.
     *
     * @param event ActionEvent
     */
    @FXML
    void closeApp(MouseEvent event) {
    	userLoginController.logoutEKrut(event);
     	Message connect = new Message();
		connect.setTask("Disconnect Server");
		ClientUI.chat.accept(connect);
		System.exit(0);
    }

    
    /**
     * Click logout.
     *
     * @param event ActionEvent
     */
    @FXML
    void clickLogout(ActionEvent event) {
    	userLoginController.logoutEKrut(event);
    	
    }
}
