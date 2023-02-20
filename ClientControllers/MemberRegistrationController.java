package ClientControllers;

import client.ClientUI;
import common.ChangeScene;
import common.Message;
import entities.Customer;
import entities.Member;
import entities.User;
import entitiesController.MemberEntityController;
import entitiesController.UserEntityController;
import entitiesController.userLoginController;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * The Class MemberRegistrationController.
 * Controller for Member Registration screen
 * Let the service employee register a customer as a member
 */
public class MemberRegistrationController {

	Message msg = new Message();
	
    @FXML
    private Button btnAddMember;

    @FXML
    private TextField username;

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
    private Text usernameErrorMessage;

    /**
     * set error if username is not a customer
     *
     * @param msgError error message
     */
    void setUsernameError(String msgError) {
    	
    	usernameErrorMessage.setText(msgError);
    }
    
    /**
     * Click add member: send the user name details to the DB and set user name permission to member
     *
     * @param event ActionEvent
     */
    @FXML
    void clickAddMember(ActionEvent event) {

    	
    	Member member = new Member();
    	member.setUserName(username.getText());
 
		msg.setData(member);
		msg.setTask("Add Member");
    	ClientUI.chat.accept(msg);
    	
    	Member returnMember = MemberEntityController.getMember();
    	if(returnMember.getMemberNumber() == 0)
    		ChangeScene.showInformationAlert("Member not added!");
    	else 
    		ChangeScene.showInformationAlert("Member added!");
    	
    	ChangeScene scene=new ChangeScene();
		scene.changeScreen(new Stage(),"/clientGUI/ServiceEmployeeMenu.fxml");
		((Node)event.getSource()).getScene().getWindow().hide(); //hiding primary window
    	
    	
    	
    }

    /**
     * Click search user name: search for user name in DB, if user name permission is not null, 
     * then shows error message.
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
    		btnAddMember.setDisable(true);
    		
    	}
    	// if user name is already customer or employee
    	else if(userList.get(0).getUserPermission() == null) {
    		
    		
    		setUsernameError("Username is not a approved customer");
    		
    	}
    	else if(!userList.get(0).getUserPermission().equals("customer")) {
    		setUsernameError("Username is " + userList.get(0).getUserPermission());
    	}
    	else {
    		
    		btnAddMember.setDisable(false);
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

    /**
     * Go back to Service Employee Menu.
     *
     * @param event the event
     */
    @FXML
    void goBack(MouseEvent event) {
      	ChangeScene scene=new ChangeScene();
		scene.changeScreen(new Stage(),"/clientGUI/ServiceEmployeeMenu.fxml");
		((Node)event.getSource()).getScene().getWindow().hide(); //hiding primary window
    }

}
