package entitiesController;

import java.util.ArrayList;
import java.util.EventObject;
import java.util.Timer;
import java.util.TimerTask;

import client.ChatClient;
import client.ClientUI;
import common.ChangeScene;
import common.Message;
import entities.Delivery;
import entities.InventoryCall;
import entities.ItemInDevice;
import entities.Member;
import entities.User;
import javafx.application.Platform;

/**
 * The Class NotificationController.
 * controller class for managing SMS/E-mail notifications simulations.
 */
public class NotificationController {
	
	static User user;
    
    static Message msg = new Message();
	
	private static boolean memberNotification,deliveryNotification, customerNotification;

	/**
	 * Checks if is customer notification.
	 *
	 * @return true, if is customer notification
	 */
	public static boolean isCustomerNotification() {
		return customerNotification;
	}

	/**
	 * Sets the customer notification.
	 *
	 * @param customerNotification the new customer notification
	 */
	public static void setCustomerNotification(boolean customerNotification) {
		NotificationController.customerNotification = customerNotification;
	}

	/**
	 * Checks if is delivery notification.
	 *
	 * @return true, if is delivery notification
	 */
	public static boolean isDeliveryNotification() {
		return deliveryNotification;
	}

	/**
	 * Sets the delivery notification.
	 *
	 * @param deliveryNotification the new delivery notification
	 */
	public static void setDeliveryNotification(boolean deliveryNotification) {
		NotificationController.deliveryNotification = deliveryNotification;
	}

	/**
	 * Checks if is member notification.
	 *
	 * @return true, if is member notification
	 */
	public static boolean isMemberNotification() {
		return memberNotification;
	}

	/**
	 * Sets the member notification.
	 *
	 * @param memberNotification the new member notification
	 */
	public static void setMemberNotification(boolean memberNotification) {
		NotificationController.memberNotification = memberNotification;
	}
	
	/**
	 * Shows users sms/E-mail notification and updates notifications flags in the DB.
	 * 
	 * @param logout flag- if true logouts the user
	 * @param event the event
	 * @param message the message that will be shown to the user
	 * @param notificationType the notification type
	 */
	public static void showMemberNotification(boolean logout,EventObject event,String message,String notificationType) {
	    	
	   
	    	    boolean memberNotify = NotificationController.isMemberNotification();
	    	    boolean deliveryNotify = NotificationController.isDeliveryNotification();
	    	    boolean customerNotify = NotificationController.isCustomerNotification();

	    		Timer time = new Timer();
	    		TimerTask timeTask = new TimerTask() {
	    			public void run() {
	    				// The task you want to do
	    				Platform.runLater(new Runnable() {
	    					@Override
	    					public void run() {
	    						
	    						switch(notificationType) {
	    						
	    							
	    						case "Member":
	    							if(!memberNotify) {
	    	    	    	    		
		    					    	ChangeScene.showInformationAlert(message,"Simulation");
		    	    	    	    	
		    	    	    	    	msg.setTask("Member Notification Seen");
		    	    	        		msg.setData(ChatClient.userloginController.getUser());
		    	    	        	    ClientUI.chat.accept(msg);	
		     	    	        		
		    					    }
	    							break;
	    						case "Delivery":
	    							if(!DeliveryEntityController.getDeliveryList().isEmpty()) {
			    						
			    						ChangeScene.showInformationAlert(message,"Simulation");
			    						ArrayList <Delivery> changedDeliveryList = new ArrayList<>();
			    				        for (Delivery d : DeliveryEntityController.getDeliveryList()) {
			    				        	changedDeliveryList.add(d);
			    				        }
			    					
			    						
		    	    	    	    	msg.setTask("Delivery Notification Seen");
		    	    	        		msg.setData(changedDeliveryList);
		    	    	        	    ClientUI.chat.accept(msg);	
			    						
			    						
			    					}
	    							break;
	    						case "Customer":
	    							if(!customerNotify) {
	    	    	    	    		
		    					    	ChangeScene.showInformationAlert(message,"Simulation");
		    	    	    	    	
		    	    	    	    	msg.setTask("Customer Notification Seen");
		    	    	        		msg.setData(ChatClient.userloginController.getUser());
		    	    	        	    ClientUI.chat.accept(msg);	
	    						
	    						}
	    						break; 
	    						case "InventoryCall":
	    							if(!InventoryCallEntityController.getInvetoryCallList().isEmpty()) {
			    						
			    						ChangeScene.showInformationAlert(message,"Simulation");
			    						ArrayList <InventoryCall> changedInvetoryCallList = new ArrayList<>();
			    				        for (InventoryCall ic : InventoryCallEntityController.getInvetoryCallList()) {
			    				        	changedInvetoryCallList.add(ic);
			    				        }
			    		
			    						
		    	    	    	    	msg.setTask("Inventory Call Notification Seen");
		    	    	        		msg.setData(changedInvetoryCallList);
		    	    	        	    ClientUI.chat.accept(msg);	
			    						
			    						
			    					}
	    							break;
	    							
	    							
	    							
	    						case "threshold":
	    							if(!ItemInDeviceEntityController.getItemInDeviceList().isEmpty()) {
			    						
			    						ChangeScene.showInformationAlert(message,"Simulation");
			    						ArrayList <ItemInDevice> thresholdCallList = new ArrayList<>();
			    				        for (ItemInDevice ic : ItemInDeviceEntityController.getItemInDeviceList()) {
			    				        	thresholdCallList.add(ic);
			    				        }
		    	    	    	    	msg.setTask("threshold Notification Seen");
		    	    	        		msg.setData(thresholdCallList);
		    	    	        	    ClientUI.chat.accept(msg);	
			    						
			    						
			    					}
	    							break;	
	    						}
	    					    
		    			

	    					    if(logout) {
		    	        	    	
		    	        	    	userLoginController.logoutEKrut(event);
		    	  	
		    	        	    }
	    					    
	    					
	    					};
	    			});
	    			};		
	 	    
	   };	
	   time.schedule(timeTask, 500);

}
}
