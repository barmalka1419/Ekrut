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
 * The Class MarketingManagerMenuController.
 * Controller for Marketing Manager Menu screen
 * Let the  Marketing Manager option to view and define sales
 */
public class MarketingManagerMenuController {

	
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
     * Click define sale, go to Define Sale screen
     *
     * @param event ActionEvent
     */
    @FXML
    void clickDefineSale(ActionEvent event) {
    	ChangeScene scene=new ChangeScene();
		((Node)event.getSource()).getScene().getWindow().hide(); //hiding primary window
		scene.changeScreen(new Stage(),"/clientGUI/DefineSale.fxml");
    }
    
	 
    /**
     * Click view sale, go to view sale screen.
     *
     * @param event ActionEvent
     */
    @FXML
    void clickViewSale(ActionEvent event) {
    	ChangeScene scene=new ChangeScene();
		((Node)event.getSource()).getScene().getWindow().hide(); //hiding primary window
		scene.changeScreen(new Stage(),"/clientGUI/ViewSales.fxml");
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
	
}
