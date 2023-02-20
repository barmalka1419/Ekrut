package ClientControllers;

import common.Message;
import entities.Employee;
import entities.Report;
import entities.User;
import entitiesController.userLoginController;

import java.util.ArrayList;
import java.util.Observable;

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
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * The Class CustomerReportOptionController is controller for the screen CustomerReportOption.fxml.
 * This class is setup the screen at first and than gives the CEO or to the Regional Manager options to choose specific
 * report to look at
 */
public class CustomerReportOptionController {

	Message connect = new Message();
	
    @FXML
    private Button getReportBTN;
    @FXML
    private Button backBTN;
	@FXML
	private Text ErrTXT;   
    
    @FXML
    private ComboBox<String> comboBoxYear;
    @FXML
    private ComboBox<String> comboBoxMonth;
	@FXML
	private static Report repo=new Report();

	@FXML
	private Text areaTXT; 
	@FXML
	private static userLoginController user1=new userLoginController();
    
    /**
     * In the Initialize we setup the current screen with elements in the combobox and user and area.
    */

    @FXML
    public void initialize() {
    	user1=ChatClient.getUser();
    
    	areaTXT.setText(((Employee) ChatClient.userloginController.getUser()).getRegion());
    ObservableList<String> Year=FXCollections.observableArrayList("2023", "2022","2021","2020","2019");
    ObservableList<String> Month=FXCollections.observableArrayList("01", "02","03","04","05","06","07","08","09","10","11","12");
    comboBoxYear.setItems(Year);
    comboBoxMonth.setItems(Month);

    }


    
    /**
     * Sets the return message.
     *
     * @param rMessage
     */
    public void setReturnMessage(String rMessage) {
    	ErrTXT.setText(rMessage);
    }
    
    /**
     * Gets the customerReport to look at in the next screen.
     * also in this function we check that the report exist and if
     * not exist showing the appropriate message in the screen
     * @param event the ActionEvent
     */
    @FXML
    void getCustomerReport(ActionEvent event) {
    	if(comboBoxYear.getValue()==null||comboBoxMonth.getValue()==null) {
    		ErrTXT.setText("*Error: Please choose year and month!");
        return;
    	}
    	// add the year and the month to ArrayList
    	repo.setYear(Integer.parseInt(comboBoxYear.getValue()));
    	repo.setMonth(Integer.parseInt(comboBoxMonth.getValue()));
    	repo.setType("customer");
    	repo.setRegion("region");
    	connect.setTask("User wants report");
    	connect.setData(repo);
    	ClientUI.chat.accept(connect);
    	repo=ChatClient.getReport();
    	setReturnMessage(ChatClient.getMessageReport());
    	String rMessage =ChatClient.getMessageReport();
    	if(rMessage.equals("get Report Success"))
    	{
        	ChangeScene scene=new ChangeScene();
    		((Node)event.getSource()).getScene().getWindow().hide(); //hiding primary window
    		scene.changeScreen(new Stage(),"/clientGUI/ViewSpecificCustomerReport.fxml");
    	}
    }
    
    /**
     * Click on Go back change screen for the user to the previous screen he was in.
     *
     * @param event the ActionEvent
     */
    @FXML
    void goBack(ActionEvent event) {
        	ChangeScene scene=new ChangeScene();
    		((Node)event.getSource()).getScene().getWindow().hide(); //hiding primary window
    		scene.changeScreen(new Stage(),"/clientGUI/ReportType.fxml");
    }
    
    /**
     * This function is to Click exit and logout and terminate the app completely.
     *
     * @param event the ActionEvent
     */
    @FXML
    void clickLogout(ActionEvent event) {
    	userLoginController.logoutEKrut(event);
	  	Message connect = new Message();
		connect.setTask("Disconnect Server");
		ClientUI.chat.accept(connect);
		System.exit(0);
    }
}