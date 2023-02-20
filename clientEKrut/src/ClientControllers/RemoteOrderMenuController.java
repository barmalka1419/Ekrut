package ClientControllers;

import java.util.ArrayList;
import java.util.EventObject;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import client.ChatClient;
import client.ClientUI;
import common.ChangeScene;
import common.Message;
import entities.Delivery;
import entities.Device;
import entities.Member;
import entities.User;
import entitiesController.DeliveryController;
import entitiesController.DeliveryEntityController;
import entitiesController.DeviceEntityController;
import entitiesController.NotificationController;
import entitiesController.userLoginController;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;


/**
 * The Class RemoteOrderMenuController.
 * controller for the remote order menu.
 * lets user to move to make delivery/pickup orders
 * also lets user to move the orders management screen
 */
public class RemoteOrderMenuController  {

    @FXML
    private ComboBox<String> selectDevice;
    
    @FXML
    private ComboBox<String> selectDelivery;

    @FXML
    private Label errorLabel;
    
    @FXML
    private Label NameLable;
    
    Message msg = new Message();
    
    private User user;
    
    private String deliveryNotification;
    
    private ObservableList<String> device, region;
    
    ObservableList<Delivery> deliveryList;
    
    /**
     * Initializes the screen- loads devices and delivery centers list for orders.
     * also shows relevant notifications
     */
    @FXML
    public void initialize() {
    errorLabel.setVisible(false);
    msg.setTask("Get Pick Up Devices");
    ClientUI.chat.accept(msg);	
    List<String> devices= new ArrayList<String>();
   for( Device d: DeviceEntityController.getDeviceList()) {
	   devices.add(d.getDeviceID()+ "-" +d.getLocation());
   }
   	device=FXCollections.observableArrayList(devices);
   	selectDevice.setItems(device);  
    msg.setTask("Get Delivery Centers");
    ClientUI.chat.accept(msg);	
    List<String> deliveryCenter= new ArrayList<String>();
    for( Device d: DeliveryController.getdeliveryCentersList()) {
    	deliveryCenter.add(d.getRegion());//for south and north regions add 'of Israel' to improve interface
    }
    	region=FXCollections.observableArrayList(deliveryCenter);
    	selectDelivery.setItems(region);  
    	
    	
    	user = ChatClient.userloginController.getUser();
    	
    	checkNotifiction();
    	
    		 NotificationController.showMemberNotification(false,null,user.getPhoneNumber() + "\n" + user.getEmail() +  ":\r\nYou're now a Customer!","Customer");
    	
  
    	if(user.getUserPermission().equals("member"))  {
    		
    	    NotificationController.showMemberNotification(false,null,user.getPhoneNumber() + ":\r\nYou're now a Member!\r\nYour member number is: "+(((Member) user).getMemberNumber()),"Member");
    	}
    	
    	String firstName,LastName;
    	firstName= ChatClient.userloginController.getUser().getFirstName();
    	LastName=ChatClient.userloginController.getUser().getLastName();
    	NameLable.setText("Hello "+firstName +" "+LastName);
        NameLable.underlineProperty();
    

   }
    
  
    /**
     * check for relevant sms/e-mail simulation notifications
     */
    void checkNotifiction() {
    	
    	if(ChatClient.userloginController.getUser().getUserPermission().equals("member")) {
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
     * Click delivery order.
     * checks if the customer chose a delivery center and moves him to delivery address form
     * @param event the event
     */
    @FXML
    void clickDeliveryOrder(ActionEvent event) {
    	if(selectDelivery.getValue()==null) {
    		errorLabel.setText("*Error: Please choose Region");
    		errorLabel.setVisible(true);
    		return;
    	}
    	String choice= selectDelivery.getValue();
    	ChatClient.MyDevice.setMyDevice((DeliveryController.getdeliveryCentersList()).get(region.indexOf(choice)));
    	ChatClient.MyDevice.setDeviceId(ChatClient.MyDevice.getMyDevice().getDeviceID());
    	ChatClient.MyOrder.setOrderType("delivery");
    	ChatClient.deliveryCont.setDelivery(new Delivery());
    	ChangeScene scene=new ChangeScene();
		((Node)event.getSource()).getScene().getWindow().hide(); //hiding primary window
		scene.changeScreen(new Stage(),"/clientGUI/DeliveryAddressForm.fxml"); // change screen to user login
    }

    /**
     * Click logout.
     * logs out the customer and check for notifications
     * @param event the event
     */
    @FXML
    void clickLogout(ActionEvent event) {
    		
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
     * Click manage order.
     *	moves the customer to delivery user management screen
     * @param event the event
     */
    @FXML
    void clickManageOrder(ActionEvent event) {

     	ChangeScene scene=new ChangeScene();
    		((Node)event.getSource()).getScene().getWindow().hide(); //hiding primary window
    		scene.changeScreen(new Stage(),"/clientGUI/DeliveryUserManagement.fxml"); // change screen to user login
    	
    	
    	
    	
    }

    /**
     * Click pickup order.
     * checks if the customer chose a device to order from.
     * if the customer chose a device moves him to the ordering screen to order from the chosen device.
     * @param event the event
     */
    @FXML
    void clickPickupOrder(ActionEvent event) {
    	if(selectDevice.getValue()==null) {
    		errorLabel.setText("*Error: Please choose Device");
    		errorLabel.setVisible(true);
    		return;
    	}
    	String choice= selectDevice.getValue();
    	ChatClient.MyDevice.setMyDevice((DeviceEntityController.getDeviceList()).get(device.indexOf(choice)));
    	ChatClient.MyDevice.setDeviceId(ChatClient.MyDevice.getMyDevice().getDeviceID());
    	ChatClient.MyOrder.setOrderType("pick up");
		((Node)event.getSource()).getScene().getWindow().hide(); 
		ChatClient.currentScreen.changeScreen(new Stage(),"/clientGUI/Ordering.fxml");
    }

    /**
     * Close app.
     *	closes the app
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

}
