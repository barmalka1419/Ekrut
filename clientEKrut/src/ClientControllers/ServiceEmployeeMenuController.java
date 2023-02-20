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
 * The Class ServiceEmployeeMenuController.
 * Controller for Service Employee Menu screen
 * Let the service employee option to register customers and members
 */
public class ServiceEmployeeMenuController {



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
     * Click customer registration: go to Customer Registration screen.
     *
     * @param event the event
     */
    @FXML
    void clickCustomerRegistration(ActionEvent event) {
    	ChangeScene scene=new ChangeScene();
		((Node)event.getSource()).getScene().getWindow().hide(); //hiding primary window
		scene.changeScreen(new Stage(),"/clientGUI/CustomerRegistration.fxml");
    }

    /**
     * Click logout.
     *
     * @param event the event
     */
    @FXML
    void clickLogout(ActionEvent event) {
    	userLoginController.logoutEKrut(event);
    }

    /**
     * Click member registration: go to Member Registration screen.
     *
     * @param event the event
     */
    @FXML
    void clickMemberRegistration(ActionEvent event) {
    	ChangeScene scene=new ChangeScene();
		((Node)event.getSource()).getScene().getWindow().hide(); //hiding primary window
		scene.changeScreen(new Stage(),"/clientGUI/MemberRegistration.fxml");
    }

    /**
     * Close app and logout EKrut.
     *
     * @param event the event
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
