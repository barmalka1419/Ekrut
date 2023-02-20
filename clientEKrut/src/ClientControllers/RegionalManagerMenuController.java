package ClientControllers;

import client.ChatClient;
import client.ClientUI;
import common.ChangeScene;
import common.Message;
import entities.Employee;
import entities.InventoryCall;
import entities.ItemInDevice;
import entities.User;
import entitiesController.InventoryCallEntityController;
import entitiesController.ItemInDeviceEntityController;
import entitiesController.NotificationController;
import entitiesController.userLoginController;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.fxml.FXML;

/**
 * The Class RegionalManagerMenuController.
 * Controller for Regional Manager Menu screen
 * Let the regional manager option to manage registration request,view reports, create inventory calls and update threshold
 */
public class RegionalManagerMenuController {



    @FXML
    private Text txtRegion;

    @FXML
    private Text welcomeMessage;

    Message msg = new Message();
    User user = ChatClient.userloginController.getUser();
    
    /**
     * Initialize.
     * Sets welcome message and employee's region, also check for inventory call and threshold Notification
     */
    @FXML
	public void initialize() {
    	int  requireAmount;
    	Employee employee = (Employee)ChatClient.userloginController.getUser();
    	
    	welcomeMessage.setText("Hello " + employee.getFirstName() + " " + employee.getLastName());
    	txtRegion.setText("Region: " + employee.getRegion());
    	
    	msg.setTask("Show Inventory Call Notification");
		msg.setData(user);
	    ClientUI.chat.accept(msg);	  
	     
	    ObservableList<InventoryCall> inventoryCallList = InventoryCallEntityController.getInvetoryCallList();
	    
			if(!inventoryCallList.isEmpty()) {
		    		
		    		
					String inventoryCallNotification =   user.getEmail() + "\n" + "The call that had been done:\r\n";
		    		
		    		for(InventoryCall ic : inventoryCallList) {
		
		    			inventoryCallNotification += "Call ID: " + ic.getCallID() + ", Device ID: " + ic.getDeviceID() + "\r\n";	
		    		}
		    		
		
		    		NotificationController.showMemberNotification(false,null,inventoryCallNotification,"InventoryCall");
		    	
		    	}
	
	
	
			msg.setTask("Show threshold notification");
			msg.setData(ChatClient.userloginController.getUser());
		    ClientUI.chat.accept(msg);	
		    
		    ObservableList<ItemInDevice> ItemList = ItemInDeviceEntityController.getItemInDeviceList();
		    
		    if(!ItemList.isEmpty()) {
			String ItemListNotification =   user.getEmail() + "\n" +" Phone number:"+ user.getPhoneNumber() + "\n"+" the following items are below the threshold: \n" ;
				
				for(ItemInDevice ic : ItemList) {
					requireAmount = (ic.getDevice().getThreshold() - ic.getAmount());
					ItemListNotification += " Device: "+ ic.getDevice().getDeviceID() + " item :" + ic.getItem().getName() +" require amount: "+ requireAmount +"\n";
					
				}
				NotificationController.showMemberNotification(false,null,ItemListNotification,"threshold");
			
			}

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
     * Click registration request: go to Registration Request Management screen.
     * 
     * @param event ActionEvent
     */
    @FXML
    void clickRegistrationRequest(ActionEvent event) {
     	ChangeScene scene=new ChangeScene();
		scene.changeScreen(new Stage(),"/clientGUI/RegistrationRequestManagement.fxml");
		((Node)event.getSource()).getScene().getWindow().hide(); //hiding primary window
    }


    /**
     * Click create inventory call: go to Inventory Call management screen.
     *
     * @param event ActionEvent
     */
    @FXML
    void clickCreateInventoryCall(ActionEvent event) {
    	ChangeScene scene=new ChangeScene();
		scene.changeScreen(new Stage(),"/clientGUI/InventoryCall.fxml");
		((Node)event.getSource()).getScene().getWindow().hide(); //hiding primary window
    	
    }

    
    /**
     * Click update threshold: to go Update Threshold management screen.
     *
     * @param event ActionEvent
     */
    @FXML
    void clickUpdateThreshold(ActionEvent event) {

     	ChangeScene scene=new ChangeScene();
		scene.changeScreen(new Stage(),"/clientGUI/UpdateThreshold.fxml");
		((Node)event.getSource()).getScene().getWindow().hide(); //hiding primary window
    	
    }
    

    /**
     * Click view report: go to view reports screen.
     *
     * @param event ActionEvent
     */
    @FXML
    void clickViewReport(ActionEvent event) {
    	ChangeScene scene=new ChangeScene();
		scene.changeScreen(new Stage(),"/clientGUI/ReportType.fxml");
		((Node)event.getSource()).getScene().getWindow().hide(); //hiding primary window
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
