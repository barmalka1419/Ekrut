package ClientControllers;

import client.ChatClient;
import common.ChangeScene;
import entitiesController.userLoginController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.stage.Stage;


/**
 * The Class OrderFailPopUpController.
 * a controller for a message shown where the customer tries to complete an order but some of 
 * the items are missing.
 */
public class OrderFailPopUpController {

    @FXML
    private Button GetBtn1;

    /**
     * Click on continue btn.
     *	lets the user start a new order
     * @param event the event
     */
    @FXML
    void ClickOnContinueBtn(ActionEvent event) {
    	ChatClient.cartController.setResetCart(true);
    	
    	ChangeScene scene=new ChangeScene();
		((Node)event.getSource()).getScene().getWindow().hide(); //hiding primary window
		ChatClient.currentScreen.changeScreen(new Stage(),"/clientGUI/Ordering.fxml");
    }

    /**
     * Click on logout btn.
     *logs out the user
     * @param event the event
     */
    @FXML
    void ClickOnLogoutBtn(ActionEvent event) {
    	userLoginController.logoutEKrut(event);
    }
    
    

}
