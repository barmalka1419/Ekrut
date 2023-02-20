package ClientControllers;

import common.Message;
import entities.Employee;
import entitiesController.userLoginController;
import client.ChatClient;
import client.ClientUI;
import common.ChangeScene;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * The Class ReportTypeController is controller for the screen ReportType.fxml.
 * This class is setup the screen at first and than gives the CEO or to the Regional Manager option select report type
 * after the user is choose report type it will take him to the correct report type to choose year and month
 */
public class ReportTypeController {

	Message connect = new Message();
	
    @FXML
    private Button ordersBTN;
    @FXML
    private Button customerBTN;
    @FXML
    private Button inventoryBTN; 
	@FXML
	private Text areaTXT; 
    @FXML
    private Button backBTN;
	@FXML
	private static userLoginController user1=new userLoginController();
	private  String nextScreen;
    
    /**
     * In the Initialize we setup the current screen with elements user and area.
     */

    @FXML
    public void initialize() {
    	user1=ChatClient.getUser();
    	areaTXT.setText(((Employee) ChatClient.userloginController.getUser()).getRegion());
    }
    
    /**
     * By clicking button -> screen change to report type the user choose
     *
     * @param event the ActionEvent
     */
    @FXML
    void ToCustomerReportScreen(ActionEvent event) {
    	
    	ChangeScene scene=new ChangeScene();
		((Node)event.getSource()).getScene().getWindow().hide(); //hiding primary window
		scene.changeScreen(new Stage(),"/clientGUI/CustomerReportOption.fxml");
    }
    
    /**
     * By clicking button -> screen change to report type the user choose
     *
     * @param event the ActionEvent
     */
    @FXML
    void ToInventoryReportScreen(ActionEvent event) {
    	
    	ChangeScene scene=new ChangeScene();
		((Node)event.getSource()).getScene().getWindow().hide(); //hiding primary window
		scene.changeScreen(new Stage(),"/clientGUI/InventoryReportOption.fxml");
    }
    
    /**
     * By clicking button -> screen change to report type the user choose
     *
     * @param event the ActionEvent
     */
    @FXML
    void ToOrdersReportScreen(ActionEvent event) {
    	
    	ChangeScene scene=new ChangeScene();
		((Node)event.getSource()).getScene().getWindow().hide(); //hiding primary window
		scene.changeScreen(new Stage(),"/clientGUI/OrdersReportOption.fxml");
    }

    /**
     * This function Close the App completely and logout from the system.
     *
     * @param event the ActionEvent
     */
    @FXML
    void closeApp(ActionEvent event) {
    	userLoginController.logoutEKrut(event);
	  	Message connect = new Message();
		connect.setTask("Disconnect Server");
		ClientUI.chat.accept(connect);
		System.exit(0);
    }
    
    /**
     * Click on Go back change screen for the user to the previous screen he was in.
     *
     * @param event the ActionEvent
     */
    @FXML
    void goBack(ActionEvent event) {
    		    switch(ChatClient.userloginController.getUser().getUserPermission()) {
  			  
    	  		case "ceo":
    	  			nextScreen="/clientGUI/CEOReportOption.fxml";
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
    		    }	
        		ChangeScene scene=new ChangeScene();
    			((Node)event.getSource()).getScene().getWindow().hide(); //hiding primary window
    			scene.changeScreen(new Stage(),nextScreen);
    }
}