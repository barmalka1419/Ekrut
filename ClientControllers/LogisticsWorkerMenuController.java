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
 * The Class LogisticsWorkerMenuController.
 * Controller for Logistics Employee Menu screen
 * Let the logistics employee option to view inventory calls
 */
public class LogisticsWorkerMenuController {

	
    @FXML
    private Text welcomeMessage;

	
    Employee employee = (Employee)ChatClient.userloginController.getUser();
	
	/**
	 * Initialize.
	 * Sets welcome message
	 */
	@FXML
	public void initialize() {
		
		
	  	welcomeMessage.setText("Hello " + employee.getFirstName() + " " + employee.getLastName());
    	
	  
	}
	
	
	
    /**
     * Click logout, go to login screen.
     *
     * @param event ActionEvent
     */
    @FXML
    void clickLogout(ActionEvent event) {
    	userLoginController.logoutEKrut(event);
    	
    }

    /**
     * Click view calls, move to view calls screen.
     *
     * @param event ActionEvent
     */
    @FXML
    void clickViewCalls(ActionEvent event) {
    	
     	ChangeScene scene=new ChangeScene();
		((Node)event.getSource()).getScene().getWindow().hide(); //hiding primary window
		scene.changeScreen(new Stage(),"/clientGUI/LogisticsWorkerViewCalls.fxml");
    	
    }

    /**
     * Close app and logout Ekrut.
     *
     * @param event MouseEvent
     */
    @FXML
    void closeApp(MouseEvent event) {
    	userLoginController.logoutEKrut(event);
     	Message connect = new Message();
    		connect.setTask("Disconnect Server");
    		ClientUI.chat.accept(connect);
    		System.exit(0);
    }

}
