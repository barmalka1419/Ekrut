package server;


import java.io.IOException;

import EntityController.ClientStatusController;
import common.Message;
import db.mysqlConnection;
import entities.Customer;
import entities.User;
import javafx.collections.ObservableList;
import ocsf.server.ConnectionToClient;

/**
 * The Class MessageHandler.
 * class for handling messages(requests) sent from a client to the server
 */
public class MessageHandler {
	

	/**
	 * Handle message.
	 * function for handling messages(requests) sent from a client to the server
	 * @param msg the msg
	 * @param client the client
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static void HandleMessage(Object msg, ConnectionToClient client) throws IOException {	
		
			String getTaskFromClient = String.valueOf(((Message)msg).getTask()); 
			
			if(msg instanceof Message)  {
				
				switch(getTaskFromClient) {
				
					case "Connect To Server":
						ClientStatusController.updateClientsList(client,"Connected");
						client.sendToClient("Connected");
						break;
						
					case "Disconnect Server":
						ClientStatusController.updateClientsList(client,"Disconnected");
						client.sendToClient("Disconnected");
						break;
									
					case "Define Sale":		
						client.sendToClient(mysqlConnection.addSale((Message)msg));
						break;
						
					case "View Sales":		
						client.sendToClient(mysqlConnection.viewSales());
						break;
						
					case "Search Username":		
						client.sendToClient(mysqlConnection.searchUsername((Message)msg));
						break;
						
					case "Load Catalog":
						client.sendToClient(mysqlConnection.getProductCatalog(((Message)msg).getData()));
						break;
					case "Save Order":
						client.sendToClient(mysqlConnection.SaveOrder(((Message)msg)));
						break;
						
			
					case "Add Customer":		
						client.sendToClient(mysqlConnection.addCustomer((Message)msg));
						break;
					case "Add Member":		
						client.sendToClient(mysqlConnection.addMember((Message)msg));
						break;
						
					case "Get Pending Customer":		
						client.sendToClient(mysqlConnection.getPendingCustomer());
						break;

					case "Registration Mangement":
						client.sendToClient(mysqlConnection.registrationMangement((Message)msg));
						break;

						
						
						
					case "Get Device List":
						client.sendToClient(mysqlConnection.getDeviceList((Message)msg));
						break;	
							
					case "Set Threshold":
						client.sendToClient(mysqlConnection.setThresholdList((Message)msg));
						break;	
						
					case "Get Inventory Call Manager":
						client.sendToClient(mysqlConnection.getInventoryCallManager((Message)msg));
						break;	
						
					case "Get Inventory Call Logistic":
						client.sendToClient(mysqlConnection.getInventoryCallLogistic((Message)msg));
						break;	
						
					case "Get Item In Device":
						client.sendToClient(mysqlConnection.getItemInDevice((Message)msg));
						break;	
						
					case "Update Inventory":
						client.sendToClient(mysqlConnection.updateInventory((Message)msg));
						break;		
					
					case "Close Inventory Call":
						client.sendToClient(mysqlConnection.closeInventoryCall((Message)msg));
						break;	
						
						
					case "Get Logistics Employee":
						client.sendToClient(mysqlConnection.getLogisticsEmployee((Message)msg));
						break;	
						
					case "Create Inventory Call":
						client.sendToClient(mysqlConnection.createInventoryCall((Message)msg));
						break;	
						
					case "View Sales By Region":
						client.sendToClient(mysqlConnection.viewSalesByRegion((Message)msg));
						break;	
						
					case "Set Sale Status":
						client.sendToClient(mysqlConnection.setSaleStatus((Message)msg));
						break;	
					
					case "View Delivery Manager":
						client.sendToClient(mysqlConnection.viewDeliveryManager((Message)msg));
						break;	
						
					case "Set Delivery Status":
						client.sendToClient(mysqlConnection.setDeliveryStatus((Message)msg));
						break;	
						
					case "View Delivery Orders":
						client.sendToClient(mysqlConnection.viewDeliveryOrders((Message)msg));
						break;		
						
					case "User wants To Login":	
						client.sendToClient(mysqlConnection.userLogin((Message)msg));
						break;
					case "User wants To Logout":	
						client.sendToClient(mysqlConnection.userLogout((Message)msg));
						break;
					case "User wants report":	
						client.sendToClient(mysqlConnection.getReport((Message)msg));
						break;
					case "User wants Inventory report":	
						client.sendToClient(mysqlConnection.getReport((Message)msg));
						break;
					case "User wants Customer report":	
						client.sendToClient(mysqlConnection.getReport((Message)msg));
						break;
						
					case "Check Order Number":
						client.sendToClient(mysqlConnection.RequestToPickUpOrder((Message)msg));
						break;
						
					case "Look for sales in my device":
						client.sendToClient(mysqlConnection.GetDeviceSales((Message)msg));
						break;
					case "Install App":
						client.sendToClient(mysqlConnection.RequestToInstall((Message)msg));
						break;	
					case "Get Pick Up Devices":
						client.sendToClient(mysqlConnection.getPickUpDeviceList((Message)msg));
						break;	
					case "Get Delivery Centers":
						client.sendToClient(mysqlConnection.getDeliveryCentersList((Message)msg));
						break;
					case "Get Current Device":
						client.sendToClient(mysqlConnection.getCurrentDevice((Message)msg));
						break;
					case "Show Member Notification":
						client.sendToClient(mysqlConnection.getMemberNotification((Message)msg));
						break;	
						
					case "Member Notification Seen":
						client.sendToClient(mysqlConnection.setNotifyMemberSeen((Message)msg));
						break;	
						
					case "Show Delivery Notification":
						client.sendToClient(mysqlConnection.getDeliveryNotification((Message)msg));
						break;	
					
					case "Delivery Notification Seen":
						client.sendToClient(mysqlConnection.setNotifyDeliverySeen((Message)msg));
						break;	
					case "Customer Notification Seen":
						client.sendToClient(mysqlConnection.setNotifyCustomerSeen((Message)msg));
						break;	
					case "Show Customer Notification":
						client.sendToClient(mysqlConnection.getCustomerNotification((Message)msg));
						break;	
					case "Show Inventory Call Notification":
						client.sendToClient(mysqlConnection.getInventoryCallNotification((Message)msg));
						break;	
					case "Inventory Call Notification Seen":
						client.sendToClient(mysqlConnection.setNotifyInventoryCallSeen((Message)msg));
						break;	
						
					case "get smart phone simulation user data":
						if((EchoServer.CustomerPhoneSimulation).getUserName() !=null) {
								EchoServer.CustomerPhoneSimulation.setPassword(mysqlConnection.GetMyPassword(EchoServer.CustomerPhoneSimulation));
								Message Logmessage = new Message();
								Logmessage.setData(EchoServer.CustomerPhoneSimulation);
								client.sendToClient(mysqlConnection.userLogin(Logmessage));
						}
						else {
							Message Error = new Message();
							Error.setTask("Error");
							Error.setData("this customer is not autorized to use this option");
							client.sendToClient(Error);
						}
						
						break;
						
					case "Show threshold notification":
						client.sendToClient(mysqlConnection.getAlertedItems(((Message)msg)));
						break;
						
					case "threshold Notification Seen":
						client.sendToClient(mysqlConnection.setNotifyThresholdSeen(((Message)msg)));
						break;
						
						
					
						
					
						
						
				}//switch
				
				
				
				
				
				
				
			}//if
		
	}
	


}
