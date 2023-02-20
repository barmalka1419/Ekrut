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
 * The Class MarketingWorkerMenuController.
 * Controller for Marketing Worker Menu screen
 * Let the  Marketing Worker option to view and manage sales operation
 */
public class MarketingWorkerMenuController {

    @FXML
    private Text txtRegion;

    @FXML
    private Text welcomeMessage;

	
    Employee employee = (Employee)ChatClient.userloginController.getUser();
	
	/**
	 * Initialize.
	 * Sets welcome message and employee's region
	 */
	@FXML
	public void initialize() {
		
		
	  	welcomeMessage.setText("Hello " + employee.getFirstName() + " " + employee.getLastName());
    	txtRegion.setText("Region: " + employee.getRegion());
    		
	  
	}

    /**
     * Click sale management, go to sale management screen.
     *
     * @param event ActionEvent
     */
    @FXML
    void clickSaleManagement(ActionEvent event) {

    	ChangeScene scene=new ChangeScene();
		((Node)event.getSource()).getScene().getWindow().hide(); //hiding primary window
		scene.changeScreen(new Stage(),"/clientGUI/MarketingWorkerSaleManagement.fxml");
    	
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


    /**
     * Close app and logout EKrut.
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
