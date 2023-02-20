package ClientControllers;

import client.ClientUI;
import common.ChangeScene;
import common.Message;
import entities.Customer;
import entities.Sale;
import entities.User;
import entitiesController.SaleController;
import entitiesController.UserEntityController;
import entitiesController.userLoginController;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.TilePane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import util.FormUtil;


/**
 * The Class CustomerRegistrationController.
 * Controller for customer registration screen
 * Let the Service Employee option to search username and register as a customer 
 * 
 */
public class CustomerRegistrationController {


	Message msg = new Message();
	

    @FXML
    private TextField username;


    @FXML
    private Button btnAddCustomer;
    
   
    @FXML
    private TableView<User> userTable;

  
    @FXML
    private TableColumn<User, String> ID;


    @FXML
    private TableColumn<User, String> firstName;


    @FXML
    private TableColumn<User, String> lastName;

    @FXML
    private TableColumn<User, String> email;


    @FXML
    private TableColumn<User, String> phoneNumber;

  
    @FXML
    private TextField creditCardNumber;
    

    @FXML
    private Text usernameErrorMessage;


    @FXML
    private Text creditCardErrorMessage;
    
    /** The customer. */
    Customer customer = new Customer();
    
    
    /**
     * Add customer to the db as pending customer, waiting for regional manager approval
     *
     * @param event ActionEvent
     */
    @FXML
    void clickAddCustomer(ActionEvent event) {
    	
    	
      	int creditCardLength = (creditCardNumber.getText()).length();
      	
     	// if the credit card number not contain only digits
    	if(!FormUtil.CheckContainOnlyDigit(creditCardNumber.getText())) {
    		setCreditCardError("credit card number must contain  only digits!");
    		return;
    		
    	}
    	// if the credit card number is not between 8-19
    	if(creditCardLength < 8 || creditCardLength > 19) {		
    		setCreditCardError("Credit card number length should be between 8-19 digits");
    		return;
    		
    	}
    	
    	customer.setCreditCardNumber(creditCardNumber.getText());
		msg.setData(customer);
		msg.setTask("Add Customer");
    	ClientUI.chat.accept(msg);
    	
    	ChangeScene.showInformationAlert("Customer added, request was sent to Regional Manager");
    	
    	ChangeScene scene=new ChangeScene();
		scene.changeScreen(new Stage(),"/clientGUI/ServiceEmployeeMenu.fxml");
		((Node)event.getSource()).getScene().getWindow().hide(); //hiding primary window
    	

    }
    
    /**
     * Sets the username error.
     *
     * @param msgError error message for wrong username
     */
    void setUsernameError(String msgError) {
    	
    	usernameErrorMessage.setText(msgError);
    }
    
    /**
     * Sets the credit card error.
     *
     * @param msgError  error message for wrong credit card
     */
    void setCreditCardError(String msgError) {
    	
    	creditCardErrorMessage.setText(msgError);
    }
    

    /**
     * Search for username in the db, shows error if the user has already permission
     *
     * @param event MouseEvent
     */
    @FXML
    void clickSearchUsername(MouseEvent event) {
    		
    	
		msg.setData(username.getText());
		msg.setTask("Search Username");
    	ClientUI.chat.accept(msg);
       	
    	
    	ObservableList<User> userList = UserEntityController.getUserList();
    	
    	// if user name doesn't exist
    	if(userList.isEmpty()) {
    		
    		setUsernameError("Username doesn't exist");
    		btnAddCustomer.setDisable(true);
    		creditCardNumber.setDisable(true);
    		
    	}
    	// if user name is already customer or employee
    	else if(userList.get(0).getUserPermission() != null) {
    		
    		
    		setUsernameError("Username is " + userList.get(0).getUserPermission());
    		
    	}
    	else {
    		
    		customer.setIDNumber(userList.get(0).getIDNumber());
    		customer.setUserName(userList.get(0).getUserName());
    		
    		btnAddCustomer.setDisable(false);
    		creditCardNumber.setDisable(false);
    		setUsernameError("");
    		
    	 	userTable.setItems(userList);
        	ID.setCellValueFactory(new PropertyValueFactory<>("IDNumber"));
        	firstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        	lastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        	email.setCellValueFactory(new PropertyValueFactory<>("email"));
        	phoneNumber.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
    				
    		
    	}
    	
   
    	

    }

    /**
     * Go back to Service Employee Menu.
     *
     * @param event MouseEvent
     */
    @FXML
    void goBack(MouseEvent event) {
    	ChangeScene scene=new ChangeScene();
		scene.changeScreen(new Stage(),"/clientGUI/ServiceEmployeeMenu.fxml");
		((Node)event.getSource()).getScene().getWindow().hide(); //hiding primary window
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
