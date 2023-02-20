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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * The Class CEOReportOptionController is controller for the screen CEOReportOption.fxml.
 * This class is setup the screen at first and than gives the CEO option to choose specific area
 * for next ReportType screen
 */
public class CEOReportOptionController {
	Message connect = new Message();
	@FXML
	private Text UserNameTXT; 
	@FXML
	private Text areaTXT; 
	@FXML
	private Button getReportBTN;
    @FXML
    private ComboBox<String> comboBoxArea;
	@FXML
	
    private Label ErorLabel;
	private static userLoginController user1=new userLoginController();
    
    /**
     * In the Initialize we setup the current screen with elements in the combobox.
     */

    @FXML
    public void initialize() {
    	user1=ChatClient.getUser();
    	UserNameTXT.setText(ChatClient.userloginController.getUser().getUserName());
        ObservableList<String> area=FXCollections.observableArrayList("UAE","north","south");
        comboBoxArea.setItems(area);
    }
    
    /**
     * In this function the user choose from combobox the specific area for reportType
     * after the user choose area he moved to the next Report option screen.
     *
     * @param event the ActionEvent
     */
    @FXML
    void getReportType(ActionEvent event) {
    	

    	((Employee) ChatClient.userloginController.getUser()).setRegion(comboBoxArea.getValue());
    	if(comboBoxArea.getValue()!=null) {
    		ChangeScene scene=new ChangeScene();
    		((Node)event.getSource()).getScene().getWindow().hide(); //hiding primary window
    		scene.changeScreen(new Stage(),"/clientGUI/ReportType.fxml");
    	}
    	else {
    		ErorLabel.setText("you have to choose area to continue");
    	}
    	
    }
    
    /**
     * This function is to Click logout and transfer the user back
     * to the login page.
     *
     * @param event the ActionEvent
     */
    @FXML
    void clickLogout(ActionEvent event) {
    	userLoginController.logoutEKrut(event);
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
    
}