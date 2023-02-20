// This file contains material supporting section 3.7 of the textbook:
// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 

package client;

import ocsf.client.*;
import common.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.util.ArrayList;

import common.Message;
import entities.Delivery;
import entities.Device;
import entities.Employee;
import entities.InventoryCall;
import entities.Item;
import entities.ItemInDevice;
import entities.Member;
import entities.Order;
import entities.Sale;
import entities.Report;
import entities.User;
import entitiesController.CartController;
import entitiesController.DeliveryController;
import entitiesController.DeliveryEntityController;
import entitiesController.DeviceController;
import entitiesController.DeviceEntityController;
import entitiesController.EkrutCatalogController;
import entitiesController.InventoryCallEntityController;
import entitiesController.ItemInDeviceEntityController;
import entitiesController.MemberEntityController;
import entitiesController.NotificationController;
import entitiesController.SaleController;
import entitiesController.UserEntityController;
import entitiesController.UserOrderController;
import entitiesController.userLoginController;
//import entityControllers.userLoginController; FIX!!!
/**
 * This class overrides some of the methods defined in the abstract
 * superclass in order to give more functionality to the client.
 *
 * @author Dr Timothy C. Lethbridge
 * @author Dr Robert Lagani&egrave;
 * @author Fran&ccedil;ois B&eacute;langer
 * @version July 2000
 *
 */
public class ChatClient extends AbstractClient
{
  //Instance variables **********************************************
	public static DeviceController MyDevice = new DeviceController();
	public static CartController cartController = new CartController();
	public static userLoginController myUser= new userLoginController(new User());
	public static UserOrderController MyOrder=new UserOrderController();
	public static AppInstallation myApp= new AppInstallation();
	public static DeliveryController deliveryCont= new DeliveryController();
  /**
   * The interface type variable.  It allows the implementation of 
   * the display method in the client.
   */
  ChatIF clientUI; 

  

  
  	public static EkrutCatalogController catlogController = new EkrutCatalogController();
	
	public static Report specificReport = new Report();
	public static String messageSubscribe,messageCreditCard,messageLogin,messageLogout,messageReport,msgpending;
  	
  	
  	public static boolean awaitResponse = false;
	
	public static String returnMessage;
	
	public static SaleController saleController = new SaleController();
	public static userLoginController userloginController = new userLoginController();

	public static UserEntityController userEntityController = new UserEntityController();
	public static MemberEntityController memberEntityController = new MemberEntityController();
	public static DeviceEntityController deviceEntityController = new DeviceEntityController();
	public static User user = new User();
	public static ChangeScene currentScreen =new ChangeScene();
	public static userLoginController specificUser=new userLoginController();
	public static String getReturnMessage() {
		return returnMessage;
	}
	
	public  void setReturnMessage(String returnMessage1) {
		returnMessage = returnMessage1;
	}
	
	

	public static String getMessageCreditCard() {
		return messageCreditCard;
	}
	
	public static String setMessageCreditCard(String msgCard) {
		return messageCreditCard = msgCard;
	}
	
	
	public static String getMessageSubscribe() {
		return messageSubscribe;
	}
	
	public static String setMessageSubscribe(String msgSubsribe) {
		return messageSubscribe = msgSubsribe;
	}
	public static String setMessageLogin(String msgSubsribe) {
		return messageLogin = msgSubsribe;
	}
	public static String getMessageLogin() {
		return messageLogin;
	}
	public static String setMessageLogout(String msgSubsribe) {
		return messageLogout = msgSubsribe;
	}
	public static String getMessageLogout() {
		return messageLogout;
	}
	public static String setMessageReport(String msgreport) {
		return messageReport = msgreport;
	}
	public static String setMessageNotApprovedYet(String msgpending) {
		return messageReport = msgpending;
	}
	public static String getMessageReport() {
		return messageReport;
	}
	public static void setUser(userLoginController specificUser1) {
		  specificUser=specificUser1;
	}
	public static userLoginController getUser() {
		  return specificUser;
	}

	public static Report getReport() {
		  return specificReport;
	}
	
	public static void setReport(Report newReport) {
		specificReport = newReport;
	}
	 

  //Constructors ****************************************************
  
  /**
   * Constructs an instance of the chat client.
   *
   * @param host The server to connect to.
   * @param port The port number to connect on.
   * @param clientUI The interface type variable.
   */
  public ChatClient(String host, int port, ChatIF clientUI) 
    throws IOException 
  {
    super(host, port); //Call the superclass constructor
    this.clientUI = clientUI;
    openConnection();
  }

  
  //Instance methods ************************************************
    
  /**
   * This method handles all data that comes in from the server.
   *
   * @param msg The message from the server.
   */
  @SuppressWarnings("unchecked")
public void handleMessageFromServer(Object msg) 
  {
	  
	  // set awaitResponse to false and release the thread to continue working
	  awaitResponse = false;
	  
	  
	  // casting the message from server to Message object
	  Message messageFromServer = (Message)msg;
	  Object data = ((Message)msg).getData();
	  
	 
	  // get the task from the message received from server
	  String getTaskFromServer = String.valueOf(messageFromServer.getTask()); 
	  
	  // convert received message to relevante method
	  switch(getTaskFromServer) {
	  		case "User ID is not exist":
	  			setMessageSubscribe(getTaskFromServer);
	  			break;  
	  		case "Update Credit Card Succeed":
	  			setMessageCreditCard(getTaskFromServer);
	  			break;
	  		case "Update Credit Card Failed":
	  			setMessageCreditCard(getTaskFromServer);
	  			break;
	  		case "Can't update creadit card: User ID is not exist":
	  			setMessageCreditCard(getTaskFromServer);
	  			break;
	  			
	  		case "Display Catalog":		
	  			catlogController.SetItemInDevice(FXCollections.observableArrayList((ArrayList<Item>) messageFromServer.getData()));	
	  			break;
	  			
	  		case "success Order":
	  			MyOrder.setOrder((Order)data);
	  			ChatClient.userloginController.setUser(ChatClient.MyOrder.getOrder().getPayment().getCustomer());
	  			break;
	  			
	  		case "Item Misssing":
	  			catlogController.setChangedInventory(true);
				break;
				
				case "View Sales":
				SaleController.setSaleList(FXCollections.observableArrayList((ArrayList<Sale>)messageFromServer.getData()));
	  			break;	
	 		case "Search Username":
	 			userEntityController.setUserList(FXCollections.observableArrayList((ArrayList<User>)messageFromServer.getData()));
	 			break;
	 		case "Add Member":
	 			memberEntityController.setMember((Member)data);
	 			break;
	 		case "Get Pending Customer":
	 			userEntityController.setUserList(FXCollections.observableArrayList((ArrayList<User>)messageFromServer.getData()));
	 			break;
	 		case "Return Registration Mangement":
	 			setReturnMessage((String)data);
	 			break;
	 		case "Get Device List":
	 			deviceEntityController.setDeviceList(FXCollections.observableArrayList((ArrayList<Device>)messageFromServer.getData()));
	 			break;
	 		case "Set Threshold":
	 			setReturnMessage((String)data);
	 			break;
	 		case "Get Inventory Call Manager":
	 			InventoryCallEntityController.setInvetoryCallList(FXCollections.observableArrayList((ArrayList<InventoryCall>)messageFromServer.getData()));
	 			break;
	 		case "Get Logistics Employee":
	 			UserEntityController.setEmployeeList(FXCollections.observableArrayList((ArrayList<Employee>)messageFromServer.getData()));
	 			break;
	 		case "Create Inventory Call":
	 			setReturnMessage((String)data);
	 			break;
	 		case "Get Inventory Call Logistic":
	 			InventoryCallEntityController.setInvetoryCallList(FXCollections.observableArrayList((ArrayList<InventoryCall>)messageFromServer.getData()));
	 			break;
	 			
	 		case "Get Item In Device":
	 			ItemInDeviceEntityController.setItemInDeviceList(FXCollections.observableArrayList((ArrayList<ItemInDevice>)messageFromServer.getData()));
	 			break;
	 			
	 		case "Update Inventory":
	 			setReturnMessage((String)data);
	 			break;
	 			
	 		case "View Sales By Region":
	 			SaleController.setSaleList(FXCollections.observableArrayList((ArrayList<Sale>)messageFromServer.getData()));
	  			break;	
	 			
	 		case "Set Sale Status":
	 			setReturnMessage((String)data);
	 			break;
	 			
			case "Define Sale":
	 			setReturnMessage((String)data);
	 			break;
	 			
	 		case "View Delivery Manager":
	 			DeliveryEntityController.setDeliveryList(FXCollections.observableArrayList((ArrayList<Delivery>)messageFromServer.getData()));
	 			break;
	 			
	 			
	 		case "Set Delivery Status":
	 			setReturnMessage((String)data);
	 			break;
	  			
	 		case "View Delivery Orders":
	 			DeliveryEntityController.setDeliveryList(FXCollections.observableArrayList((ArrayList<Delivery>)messageFromServer.getData()));
	 			break;
	 			
				
					case "User Already Logged in":
	  			setMessageLogin(getTaskFromServer);
	  			userloginController.setUser((User) messageFromServer.getData());
	  			break;
	  		case "Error Update user to logged in":
	  			setMessageLogin(getTaskFromServer);
	  			break;
	  		case "User Login Succeed":
	  			setMessageLogin(getTaskFromServer);
				//User user = (User) messageFromServer.getData();
                //setUser(user);
			//	if(messageFromServer.getData() instanceof Member)
				    userloginController.setUser((User) messageFromServer.getData());
				break;
	  		case "User details is not exist":
	  			userloginController.setUser(null);
	  			setMessageLogin(getTaskFromServer);
	  			break;
	  		case "User Logout Success":
	  			ChatClient.userloginController.setUser(new User());
	  			setMessageLogout(getTaskFromServer);
				break;
	  		case "User still in pending position!":
	  			setMessageLogin(getTaskFromServer);
				break;
	  		case "get Report Success":
	  			setReport((Report)messageFromServer.getData());
	  			setMessageReport(getTaskFromServer);
				break;
	  		case "Report Not Found":
	  			setMessageReport(getTaskFromServer);
				break;
	  		case "Pickup Request":
	 			setReturnMessage((String)data);
	 			break;
	 			
	  		case "Return sales for this device":
	  			
	  			saleController.setSalesInDevice((Sale) data);
	  			break;
	  		
	  		case "Install":
	  			ArrayList<Object> InstallDetails= (ArrayList<Object>)data;
	  			ChatClient.myApp.setInstallType((String)(InstallDetails.get(0)));
	  			ChatClient.myApp.setMachineNumber((int)InstallDetails.get(1));
	  			break;
	  		case "Get Delivery Centers List":
	  			ChatClient.deliveryCont.setdeliveryCentersList(FXCollections.observableArrayList((ArrayList<Device>)messageFromServer.getData()));
	  			break;
	  		case "Get Device"://added
	  			ChatClient.MyDevice.setDeviceId(((Device)(messageFromServer.getData())).getDeviceID());
	  			ChatClient.MyDevice.setMyDevice((Device)(messageFromServer.getData()));
	  			break;
	  		case "User has no permissions":
	  			setMessageLogin(getTaskFromServer);
	  			break;
	  			
	  		case "Show Member Notification":
	  			NotificationController.setMemberNotification((boolean)data);
	  			break;
	  			
	  		case "Member Notification Seen":
	 			setReturnMessage((String)data);
	 			break;
	 			
	  		case "Show Delivery Notification":
	  			DeliveryEntityController.setDeliveryList(FXCollections.observableArrayList((ArrayList<Delivery>)messageFromServer.getData()));
	  			break;
	  			
	  		case "Show Customer Notification":
	  			NotificationController.setCustomerNotification((boolean)data);
	  			break;
	  		case "Show Inventory Call Notification":
	  			InventoryCallEntityController.setInvetoryCallList(FXCollections.observableArrayList((ArrayList<InventoryCall>)messageFromServer.getData()));
	  			break;
	  			
	  			
	  		case "threshold notify":
	  			ItemInDeviceEntityController.setItemInDeviceList(FXCollections.observableArrayList((ArrayList<ItemInDevice>)messageFromServer.getData()));
	  			break;
	  			
	  			
	  		case "Error":
	  			setMessageLogin(getTaskFromServer);
	  			break;
	  			
	  			  			
	  }
  }
  
  public void handleMessageFromClientUI(Object message)  
  {
	  try
	    {
	    	openConnection();//in order to send more than one message
	       	awaitResponse = true;
	    	sendToServer(message);
			// wait for response
			while (awaitResponse) {
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
	    }
	    catch(IOException e)
	    {
	    	e.printStackTrace();
	      clientUI.display("Could not send message to server: Terminating client."+ e);
	      quit();
	    }
  }
  
  /**
   * This method terminates the client.
   */
  public void quit()
  {
    try
    {
      closeConnection();
    }
    catch(IOException e) {}
    System.exit(0);
  }
}
//End of ChatClient class
