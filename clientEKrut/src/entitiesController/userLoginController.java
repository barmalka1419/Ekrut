package entitiesController;
import java.util.EventObject;

import client.ChatClient;
import client.ClientUI;
import common.ChangeScene;
import common.Message;
import entities.Employee;
import entities.User;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.stage.Stage;

/**
 * The Class userLoginController.
 * controller class for storing logged-in user information and login type
 */
public class userLoginController {
	
	private User user = null;
	
	private boolean localLogin;
	
	/**
	 * Instantiates a new user login controller.
	 */
	public userLoginController() {}
	
	/**
	 * Instantiates a new user login controller.
	 *
	 * @param user1 the user 1
	 */
	public userLoginController(User user1) {user = user1;}
	
	/**
	 * Gets the user.
	 *
	 * @return the user
	 */
	public  User getUser() {
		return user;
	}
	
	/**
	 * Sets the user.
	 *
	 * @param user the new user
	 */
	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * Checks if is exist.
	 *
	 * @return true, if is exist
	 */
	public boolean isExist() {
		return !(user == null);
	}
	
	/**
	 * Gets the local login.
	 *
	 * @return the local login
	 */
	public boolean getLocalLogin() {
		return localLogin;
	}
	
	/**
	 * Sets the local login.
	 *
	 * @param localLogin the new local login
	 */
	public void setLocalLogin(Boolean localLogin) {
		this.localLogin = localLogin;
	}
	
	/**
	 * Logout E krut.
	 * logouts the users and moves to a screen according to the login type
	 * @param event the event
	 */
	public static void logoutEKrut(EventObject event) {
		
		
		
		Message disconnect = new Message();
		
		String gotoScreen; 
		disconnect.setTask("User wants To Logout");
		disconnect.setData(ChatClient.userloginController.getUser());
    	ClientUI.chat.accept(disconnect);
    	ChangeScene scene=new ChangeScene();
		((Node)event.getSource()).getScene().getWindow().hide(); //hiding primary window
		
		if(ChatClient.userloginController.getLocalLogin()) 
			gotoScreen = "/clientGUI/LocalLogin.fxml";
		else	
			gotoScreen = "/clientGUI/MachineLogin.fxml";
		
	
		scene.changeScreen(new Stage(),gotoScreen);
		
		
		
	}

}