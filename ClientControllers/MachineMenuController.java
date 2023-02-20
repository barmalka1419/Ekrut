package ClientControllers;

import java.net.URL;
import java.sql.PreparedStatement;
import java.util.ResourceBundle;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import common.*;
import entities.Delivery;
import entities.Member;
import entities.User;
import entitiesController.DeliveryEntityController;
import entitiesController.NotificationController;
import entitiesController.userLoginController;
import client.ChatClient;
import client.ClientUI;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.stage.Stage;


/**
 * The Class MachineMenuController.
 * Controller for the machine menu screen.
 * this is the first screen in the machine after logging in- shows user the actions he can do
 */
public class MachineMenuController implements Initializable{

    @FXML
    private Button CreateNewOrderBtn;

    @FXML
    private Button LogoutBtn;

    @FXML
    private Button PickUpOrderBtn;
    
    @FXML
    private Label deviceNum;
    
    @FXML
    private Label NameLable;

    @FXML
    private Label location;

    ObservableList<Delivery> deliveryList;
    
    private String deliveryNotification;
    
    private User user;
    
    Message msg = new Message();
    
    /**
     * Click ON create new order.
     *	moves the customer to ordering screen
     * @param event the event
     */
    //move to order screen
    @FXML
    void ClickONCreateNewOrder(ActionEvent event) {
    	 //scene=new ChangeScene();
    	ChatClient.MyOrder.setOrderType("local");
		((Node)event.getSource()).getScene().getWindow().hide(); 
		ChatClient.currentScreen.changeScreen(new Stage(),"/clientGUI/Ordering.fxml");//changed line
		
    }

	
    /**
     * Click on pick up an order.
     *	moves the customer to pickup screen
     * @param event the event
     */
    @FXML
    void ClickOnPickUpAbOrder(ActionEvent event) {
    	ChangeScene scene=new ChangeScene();
		((Node)event.getSource()).getScene().getWindow().hide(); //hiding primary window
		scene.changeScreen(new Stage(),"/clientGUI/pickUp.fxml");
    }
    
 
    /**
     * Click log out.
     *	logs out the user and also check for relevant SMS/e-mail notifications for the user
     * @param event the event
     */
    @FXML
    void ClickLogOut(ActionEvent event) {  	
   	checkNotifiction();
    	
    	if(!deliveryList.isEmpty()) {
    		
    		
    		deliveryNotification =  user.getPhoneNumber()+ "\n" + user.getEmail() + "\n" + "Your Approved Deliveries:\r\n";
    		
    		for(Delivery d : deliveryList) {

    			deliveryNotification += "OrderID: " + d.getOrderNumber() + ", Estimated Delivery Time: " + d.getEstimatedDeliveryTime() + "\r\n";	
    		}
    		
    		
    	
    		NotificationController.showMemberNotification(false,null,deliveryNotification,"Delivery");
    		
    		
    		
    		
    	}

    
    	if(ChatClient.userloginController.getUser().getUserPermission().equals("member"))  {
    		NotificationController.showMemberNotification(true,event,user.getPhoneNumber() + ":\r\nYou're now a Member!\r\nYour member number is: "+(((Member) user).getMemberNumber()),"Member");
    		
    	}
    	else
    		userLoginController.logoutEKrut(event);
    	
 
    }


	/**
	 * Initialize the screen.
	 *also check for relevant  sms/e-mail notifications.
	 *	shows the user's name and current device information on screen
	 * @param location the location
	 * @param resources the resources
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {//added
		String firstName,LastName;
		firstName= ChatClient.userloginController.getUser().getFirstName();
		LastName=ChatClient.userloginController.getUser().getLastName();
		msg.setTask("Get Current Device");
		msg.setData(ChatClient.myApp.getMachineNumber());
	    ClientUI.chat.accept(msg);	
	    deviceNum.setText("Device Number "+String.valueOf(ChatClient.MyDevice.getDeviceId()));
	    this.location.setText("Location: "+ ChatClient.MyDevice.getMyDevice().getLocation());	
	    NameLable.setText("Hello "+firstName +" "+LastName);
	    NameLable.underlineProperty();
	    
    	
    	user = ChatClient.userloginController.getUser();
    	
    	checkNotifiction();
    	
    		 NotificationController.showMemberNotification(false,null,user.getPhoneNumber() + "\n" + user.getEmail() +  ":\r\nYou're now a Customer!","Customer");
    	
  
    	if(user.getUserPermission().equals("member"))  {
    		
    	    NotificationController.showMemberNotification(false,null,user.getPhoneNumber() + ":\r\nYou're now a Member!\r\nYour member number is: "+(((Member) user).getMemberNumber()),"Member");
    	}
	    
	    
	    
	}
	

    /**
     * Check notifiction.
     */
    void checkNotifiction() {
    	
    	if( ChatClient.userloginController.getUser().getUserPermission().equals("member")) {
    		msg.setTask("Show Member Notification");
    		msg.setData(ChatClient.userloginController.getUser());
    	    ClientUI.chat.accept(msg);		
    	}
    	
    	
	    
		msg.setTask("Show Delivery Notification");
		msg.setData(ChatClient.userloginController.getUser());
	    ClientUI.chat.accept(msg);	  
		deliveryList = DeliveryEntityController.getDeliveryList();
		
		msg.setTask("Show Customer Notification");
		msg.setData(ChatClient.userloginController.getUser());
	    ClientUI.chat.accept(msg);
		 
		

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

