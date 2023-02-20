package db;
import java.sql.Connection;
import java.time.DayOfWeek;
import java.time.temporal.ChronoField;
import java.time.LocalDate;
import java.sql.Date;
import java.util.*;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.time.Year;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import entities.Report;

import common.Message;
import entities.Customer;
import entities.Delivery;
import entities.Device;
import entities.Employee;
import entities.InventoryCall;
import entities.Item;
import entities.ItemInDevice;
import entities.ItemInOrder;
import entities.Member;
import entities.Order;
import entities.Sale;
import entities.TakeAway;
import entities.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import server.EchoServer;
import serverController.CreateReportController;

/**
 * The Class mysqlConnection.
 * this class manages the communication between the server and the sql DB.
 * the class allows executing different queries in the DB.
 */
public class mysqlConnection {

	static Connection conn;	 

	/**
	 * Connection db.
	 * the function connects the server to the database.
	 * @param path the path
	 * @param userName the user name
	 * @param Password the password
	 */
	public static void connectionDb(String path ,String userName, String Password ) 
	{
		try 
		{
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            System.out.println("Driver definition succeed");
        } catch (Exception ex) {
        	/* handle the error*/
        	 System.out.println("Driver definition failed");
        	 }
        
        try 
        {
        	
            conn = DriverManager.getConnection(path,userName,Password);
            System.out.println("SQL connection succeed");
            CreateReportController.main();
			
            GetallApprovedCustomers();
            
            
     	} catch (SQLException ex) 
     	    {/* handle any errors*/
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
            }
   	}
	
	
	/**
	 * function for importing users from external users system simulation.
	 */
	public static void importUsers() {
		

		PreparedStatement ps1;
		ResultSet rs;

		boolean notify,madePurchase,isLogged;
		String region,permission,creditCardNumber;
		
		try {
			
			ps1 = conn.prepareStatement("SELECT * FROM `external_users`");
			rs = ps1.executeQuery();
			
			while(rs.next()) {
				
					
					User user = new User();
					
					notify = rs.getBoolean("notify");
					madePurchase = rs.getBoolean("madePurchase");
					
					user.setIDNumber(rs.getString("id"));
					user.setFirstName(rs.getString("first name"));
					user.setLastName(rs.getString("last name"));
					user.setUserName(rs.getString("username"));
					user.setPassword(rs.getString("password"));
					user.setEmail(rs.getString("email"));
					user.setPhoneNumber(rs.getString("phone number"));
					permission = rs.getString("userPermission");
					user.setUserPermission(permission);
					region = rs.getString("region");
					isLogged = rs.getBoolean("isLogged");
					
					creditCardNumber = rs.getString("credit card number");
					
					
					try {
						ps1 = conn.prepareStatement("INSERT INTO `users` (`id`, `first name`, `last name`, `username`, `password`, `email`, `phone number`,`isLogged`,`userPermission`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);");
						ps1.setString(1,user.getIDNumber() );
						ps1.setString(2,user.getFirstName());
						ps1.setString(3,user.getLastName());
						ps1.setString(4,user.getUserName());
						ps1.setString(5,user.getPassword());
						ps1.setString(6,user.getEmail());
						ps1.setString(7,user.getPhoneNumber());
						ps1.setBoolean(8,isLogged);
						ps1.setString(9,user.getUserPermission());
						ps1.executeUpdate();
					} catch (Exception e) {
						System.out.println("import user failed");
						return;
					}
					
					
					if(permission == null) 
						continue;
						

					if(permission.equals("customer") || permission.equals("pending customer")) {
					
						Customer customer = new Customer();
				
						customer.setCreditCardNumber(creditCardNumber);
						
						ps1 = conn.prepareStatement("INSERT INTO `customers` (`id`, `username`, `credit card number`, `approved customer`, `notify`) VALUES (?, ?, ?, ?, ?);");
						ps1.setString(1, user.getIDNumber());
						ps1.setString(2, user.getUserName());
						ps1.setString(3,customer.getCreditCardNumber());
						
						if(user.getUserPermission().equals("customer"))
								ps1.setBoolean(4,true);
						else 
							ps1.setBoolean(4,false);
							
						ps1.setBoolean(5,notify);
						
			
						ps1.executeUpdate();
					

						
						
					}
					else if(permission.equals("member")) {
						
						Customer customer = new Customer();
						
						customer.setCreditCardNumber(creditCardNumber);
						
						ps1 = conn.prepareStatement("INSERT INTO `customers` (`id`, `username`, `credit card number`, `approved customer`, `notify`) VALUES (?, ?, ?, true, true)");
						ps1.setString(1, user.getIDNumber());
						ps1.setString(2, user.getUserName());
						ps1.setString(3,customer.getCreditCardNumber());
						
						
						
			
						ps1.executeUpdate();
						ps1.close();
						
						Member member = new Member();
						
						member.setFirstPurchase(madePurchase);
						
						ps1 = conn.prepareStatement("INSERT INTO `member` (`username`, `madePurchase`, `notify`) VALUES (?, ?, ?)");
				
						ps1.setString(1, user.getUserName());
						ps1.setBoolean(2,member.isFirstPurchase());
						ps1.setBoolean(3,notify);
						
						ps1.executeUpdate();
						ps1.close();
					}
					else {
						
						Employee employee = new Employee();
						employee.setRegion(region);
						
						ps1 = conn.prepareStatement("INSERT INTO `employees` (`username`, `position`, `region`) VALUES (?, ?, ?)");
				
						ps1.setString(1, user.getUserName());
						ps1.setString(2,user.getUserPermission());
						ps1.setString(3,employee.getRegion());
					
						
						ps1.executeUpdate();
						ps1.close();
						
					}
							
			}
				

			ps1.close();
			GetallApprovedCustomers();
			
			
				
			
		}  catch (SQLException e) {

			e.printStackTrace();
		}
		
	
	}
	
	
	/**
	 * Get a list of all the approved customers in the DB
	 */
	public static void GetallApprovedCustomers() {
		
		PreparedStatement ps1;
    	ArrayList<String>CustomersFromDb =new ArrayList<>();
		
        try {
			ps1 = conn.prepareStatement("SELECT username FROM customers  WHERE `approved customer` = '1' ");
			
			ResultSet rs = ps1.executeQuery();
			while(rs.next())
	 		{
	 			CustomersFromDb.add(rs.getString(1));
			} 
	 		
			rs.close();
			ps1.close();
	        EchoServer.Customer = FXCollections.observableArrayList(CustomersFromDb);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		

	}
	
	
	/**
	 * Gets  delivery notifications.
	 *
	 * @param clientMsg the client msg
	 * @return the delivery notification
	 */
	public static Message getDeliveryNotification(Message clientMsg) {
		
		PreparedStatement ps1;
		Message msg = new Message();
		ArrayList<Delivery> deliveryList = new ArrayList<>();
		Customer customer = (Customer)(clientMsg.getData());
		msg.setTask("Show Delivery Notification");
		
		try {
			
			ps1 = conn.prepareStatement("SELECT d.orderID,d.estimatedDeliveryTime FROM delivery d JOIN orders o ON d.orderID = o.orderID WHERE o.customerID = ? AND d.status = 'PROCESSING' AND isNotifyDeliveryConfirmation = '0'");
			ps1.setString(1,customer.getIDNumber());
			ResultSet rs = ps1.executeQuery();

			while(rs.next())
	 		{
	 			
				Delivery delivery = new Delivery();
						
		        delivery.setOrderNumber(rs.getInt(1));
		        delivery.setEstimatedDeliveryTime(rs.getTimestamp(2));

		        
		        deliveryList.add(delivery);	 
				
			} 
	 		
			rs.close();
			ps1.close();

			
		}  catch (SQLException e) {
			
			
			msg.setData("Failed");
			e.printStackTrace();
			return msg;
		}
		
		msg.setData(deliveryList);
		return msg;
	
	}
	
	
	
	
	

	
	/**
	 * Gets new member notification.
	 *
	 * @param clientMsg the client msg
	 * @return the member notification
	 */
	public static Message getMemberNotification(Message clientMsg) {
		
		PreparedStatement ps1;
		Message msg = new Message();
		boolean isNotify = true;
		
		Member user = (Member)(clientMsg.getData());
		msg.setTask("Show Member Notification");
		
		try {
			
			ps1 = conn.prepareStatement("SELECT `notify` FROM `member` WHERE `member number` = ?");
			ps1.setInt(1,user.getMemberNumber());
			ResultSet rs = ps1.executeQuery();
			
			if(rs.next())
				isNotify = rs.getBoolean(1);
				

			rs.close();
			ps1.close(); 	
			
		}  catch (SQLException e) {
			
			
			msg.setData(isNotify);
			e.printStackTrace();
			return msg;
		}
		
		msg.setData(isNotify);
		return msg;
	
	}
	
	/**
	 * this function Sets the new member notification flag in DB members table to true(seen).
	 *	
	 * @param clientMsg the client msg
	 * @return the message
	 */
	public static Message setNotifyMemberSeen(Message clientMsg) {
		
		PreparedStatement ps1;
		Message msg = new Message();
	
		Member user = (Member)(clientMsg.getData());
		msg.setTask("Member Notification Seen");    
		   
		try {
	
				ps1 = conn.prepareStatement("UPDATE `member` SET `notify` = '1'  WHERE `member number` = ?");
				ps1.setInt(1,user.getMemberNumber());
				ps1.executeUpdate();
				ps1.close();
		
		} catch (SQLException e) {
			
			
			msg.setData("Falied");
			e.printStackTrace();
			return msg;
		}
		
		
		msg.setData("Success");
		
		return msg;
	}
		
/**
 * this function gets new customer notification.
 *
 * @param clientMsg the client msg
 * @return the customer notification
 */
public static Message getCustomerNotification(Message clientMsg) {
		
		PreparedStatement ps1;
		Message msg = new Message();
		boolean isNotify = true;
		
		Customer user = (Customer)(clientMsg.getData());
		msg.setTask("Show Customer Notification");
		
		try {
			
			ps1 = conn.prepareStatement("SELECT `notify` FROM `customers` WHERE `username` = ? AND `approved customer` = '1'");
			ps1.setString(1,user.getUserName());
			ResultSet rs = ps1.executeQuery();
			
			if(rs.next())
				isNotify = rs.getBoolean(1);
				

			rs.close();
			ps1.close();
			
		}  catch (SQLException e) {
			
			
			msg.setData(isNotify);
			e.printStackTrace();
			return msg;
		}
		
		msg.setData(isNotify);
		return msg;
	
	}

/**
 * Sets the notify customer seen flag in the db.
 *
 * @param clientMsg the client msg
 * @return the message
 */
public static Message setNotifyCustomerSeen(Message clientMsg) {
		
		PreparedStatement ps1;
		Message msg = new Message();
	
		Customer user = (Customer)(clientMsg.getData());
		msg.setTask("Customer Notification Seen");    
		   
		try {
			
	
				ps1 = conn.prepareStatement("UPDATE `customers` SET `notify` = '1'  WHERE `username` = ? AND `approved customer` = '1'");
				ps1.setString(1,user.getUserName());
				ps1.executeUpdate();
				ps1.close();
		
		} catch (SQLException e) {
			
			
			msg.setData("Falied");
			e.printStackTrace();
			return msg;
		}
		
		
		msg.setData("Success");
		
		return msg;
	}
		
	
	/**
	 * Sets the notify delivery seen.
	 *
	 * @param clientMsg the client msg
	 * @return the message
	 */
	public static Message setNotifyDeliverySeen(Message clientMsg) {
		
		PreparedStatement ps1;
		Message msg = new Message();
	
		ArrayList<Delivery> deliveryList = (ArrayList<Delivery>)(clientMsg.getData());
		msg.setTask("Delivery Notification Seen");    
		   
		try {
			
			for(Delivery delivery : deliveryList) {
	
				ps1 = conn.prepareStatement("UPDATE `delivery` SET `isNotifyDeliveryConfirmation` = '1'  WHERE `OrderID` = ?");
				ps1.setInt(1,delivery.getOrderNumber());
				ps1.executeUpdate();
				ps1.close();
			}
		
		} catch (SQLException e) {
			
			
			msg.setData("Falied");
			e.printStackTrace();
			return msg;
		}
		
		
		msg.setData("Success");
		
		return msg;
	}
	
	
	
/**
 * Sets the notify threshold seen.
 *
 * @param clientMsg the client msg
 * @return the message
 */
public static Message setNotifyThresholdSeen(Message clientMsg) {
	PreparedStatement ps1;
	Message msg = new Message();

	ArrayList<ItemInDevice> itemInDeviceList = (ArrayList<ItemInDevice>)(clientMsg.getData());
	msg.setTask("threshold Notification Seen");    
	try {
		
		for(ItemInDevice item : itemInDeviceList) {

			ps1 = conn.prepareStatement("UPDATE `item_in_device` SET `thresholdNotify` = '1'  WHERE `deviceID` = ? AND serialNumber = ?");
			ps1.setInt(1,item.getDevice().getDeviceID());
			ps1.setInt(2,item.getItem().getSerialNumber());
			ps1.executeUpdate();
			ps1.close();
		}
	
	} catch (SQLException e) {
		
		
		msg.setData("Falied");
		e.printStackTrace();
		return msg;
	}
	
	
	msg.setData("Success");
	
	return msg;
}
	
	
/**
 * Gets the inventory call notification.
 *
 * @param clientMsg the client msg
 * @return the inventory call notification
 */
public static Message getInventoryCallNotification(Message clientMsg) {
		
		PreparedStatement ps1;
		Message msg = new Message();
		ArrayList<InventoryCall> InventoryCallList = new ArrayList<>();
		User user = (User) clientMsg.getData();
		msg.setTask("Show Inventory Call Notification");
		
		try {
			
			ps1 = conn.prepareStatement("SELECT callID, deviceID FROM inventory_call WHERE creatorUsername = ? AND status = 'DONE' AND isNotify = '0'");
			ps1.setString(1,user.getUserName());
			ResultSet rs = ps1.executeQuery();

			while(rs.next())
	 		{
	 			
				InventoryCall call = new InventoryCall();
						
		        call.setCallID(rs.getInt(1));
		        call.setDeviceID(rs.getInt(2));

		        
		        InventoryCallList.add(call);	 
				
			} 
	 		
			rs.close();
			ps1.close();

			
		}  catch (SQLException e) {
			
			
			msg.setData("Failed");
			e.printStackTrace();
			return msg;
		}
		
		msg.setData(InventoryCallList);
		return msg;
	
	}

/**
 * Sets the notify inventory call seen.
 *
 * @param clientMsg the client msg
 * @return the message
 */
public static Message setNotifyInventoryCallSeen(Message clientMsg) {
	
	PreparedStatement ps1;
	Message msg = new Message();

	ArrayList<InventoryCall> inventoryCallList = (ArrayList<InventoryCall>)(clientMsg.getData());
	msg.setTask("Delivery Notification Seen");    
	   
	try {
		
		for(InventoryCall inventoryCall : inventoryCallList) {

			ps1 = conn.prepareStatement("UPDATE `inventory_call` SET `isNotify` = '1'  WHERE `callID` = ?");
			ps1.setInt(1,inventoryCall.getCallID());
			ps1.executeUpdate();
			ps1.close();
		}
	
	} catch (SQLException e) {
		
		
		msg.setData("Falied");
		e.printStackTrace();
		return msg;
	}
	
	
	msg.setData("Success");
	
	return msg;
}

	

	
	
	
	
	
	/**
	 * Checks if a user registered.
	 *
	 * @param id the id
	 * @return true, if is user registered
	 */
	public static boolean isUserRegistered(String id)
	{

		try 
		{
			
			PreparedStatement ps1 = conn.prepareStatement("SELECT ID FROM subscriber WHERE ID = ?");
			ps1.setString(1,id);
			ResultSet rs = ps1.executeQuery();
			rs.next();
	 		
			try {
				rs.getString(1);
			} catch(SQLException e) {
				
				ps1.close();
				return false;
			}
			
		} catch (SQLException e) {
			
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	
	/**
	 * Gets the pending customers list.
	 *
	 * @return the pending customer
	 */
	public static Message getPendingCustomer()
	{
		 Message msg = new Message();
		 
		 
		ArrayList<User> userList = new ArrayList<>();
		msg.setTask("Get Pending Customer");
		Statement stmt;
		try 
		{
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT `id`,`first name`,`last name`,`email`,`phone number`,`username` FROM `users` WHERE `userPermission` = 'pending customer';");
			
	 		while(rs.next())
	 		{
	 			
	 			User userDetails = new User(); 
	 			
	 			userDetails.setIDNumber(rs.getString(1));
				userDetails.setFirstName(rs.getString(2));
				userDetails.setLastName(rs.getString(3));
				userDetails.setEmail(rs.getString(4));
				userDetails.setPhoneNumber(rs.getString(5));
				userDetails.setUserName(rs.getString(6));
				
	 			
				userList.add(userDetails);	 
			} 
	 		
			rs.close();
			
		} catch (SQLException e) {e.printStackTrace();}
		
		msg.setData(userList);
		return msg;
	}
	

	

	
	/**
	 * returns a list of all the sales stored in sale table in DB.
	 *
	 * @return the message
	 */
	public static Message viewSales() {
		
		 Message msg = new Message();
				
			ArrayList<Sale> sales = new ArrayList<>();
			
			msg.setTask("View Sales");
			Statement stmt;
			try 
			{
				stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT * FROM sales;");
				
		 		while(rs.next())
		 		{
		 			
		 			List<String> daysList = Arrays.asList(rs.getString(6).split(","));
		 			
		 			sales.add(new Sale(rs.getInt(1),
							rs.getDate(2),
							rs.getDate(3),
							rs.getInt(4),
							rs.getString(5),
							daysList,
							rs.getTime(7),
							rs.getTime(8),
							rs.getString(9),
							rs.getBoolean(10) ));	 
				} 
				rs.close();
				
			} catch (SQLException e) {e.printStackTrace();}
			
			msg.setData(sales);
			return msg;
		
		
	}
	
	   
	/**
	 * Gets an active sale in the current device's region.
	 *
	 * @param clientMsg the client msg
	 * @return the message
	 */
	public static Message GetDeviceSales(Message clientMsg) {
		
		 
		String dayAsSrting=null;
		Message msg = new Message();
		long now = System.currentTimeMillis();
		Time sqlTime = new Time(now);
		Date Datenow = Date.valueOf(LocalDate.now());
		Device device = (Device) clientMsg.getData();	  
		dayAsSrting= GetCurrentDayAsString();
		PreparedStatement ps1 = null ;
	    Sale saleForDevice=null;
	    	
	  
	   

	    	try {
				
	    		ps1 = conn.prepareStatement("SELECT * from  sales WHERE region = ? and isActive = true and ? BETWEEN startDate and endDate and days LIKE '%' ? '%'  and ? BETWEEN startHour and endHour  ");
				ps1.setString(1,device.getRegion());
				ps1.setDate(2,Datenow);
				ps1.setString(3,dayAsSrting);
				ps1.setTime(4, sqlTime);
				ResultSet rs =ps1.executeQuery();
				
				if(rs.next()) {
					
					saleForDevice= new Sale(rs.getInt(1),
							rs.getDate(2),
							rs.getDate(3),
							rs.getInt(4),
							rs.getString(5),
							null,
							rs.getTime(7),
							rs.getTime(8),
							rs.getString(9),
							true );	 
				} 
				
				else { // if we not find any sale for are  device 
					msg.setTask("there is no sales to this device "); // check that we implement  that in client chat !!!! &&&& !!!!
					msg.setData(null);
					rs.close();
					ps1.close();
					return msg;
				}
				
				rs.close();
				ps1.close();
				
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
	    	msg.setData(saleForDevice);
	    	
	    	msg.setTask("Return sales for this device");

		return msg;
		   
}
	
	
	/**
	 * Gets the current day as a string in a shortened format.
	 *
	 * @return the string
	 */
	public static String GetCurrentDayAsString() {
		
		 Calendar calendar = Calendar.getInstance();
	        int day = calendar.get(Calendar.DAY_OF_WEEK); 
	        String dayNow = null;
	        
	        switch (day) {
	        case Calendar.SUNDAY:
	        	 return dayNow = "sun";
	           
	        case Calendar.MONDAY:
	        	return dayNow = "mon";
	           
	        case Calendar.TUESDAY:
	        	return dayNow = "tue";

	        case Calendar.WEDNESDAY:
	        	return dayNow = "wed";
	           
	        case Calendar.THURSDAY:
	        	return dayNow = "thu";
	          
	        case Calendar.FRIDAY:
	        	return  dayNow = "fri";
	            
	        case Calendar.SATURDAY:
	        	return dayNow = "sat";
	          
	        }
		
		return "ERROR";
	}
	
/**
 * View sales by region.
 *
 * @param clientMsg the client msg
 * @return the message
 */
public static Message viewSalesByRegion(Message clientMsg) {
		
		 	Message msg = new Message();
			Employee employee = (Employee)clientMsg.getData();
			ArrayList<Sale> sales = new ArrayList<>();
			msg.setTask("View Sales By Region");
			PreparedStatement ps1 = null;
			
			
			try 
			{

				ps1 = conn.prepareStatement("SELECT * FROM sales WHERE `region` = ?;");
				ps1.setString(1, employee.getRegion());
				ResultSet rs = ps1.executeQuery();
				
				while(rs.next())
		 		{

		 			List<String> daysList = Arrays.asList(rs.getString(6).split(","));
		 			
		 			sales.add(new Sale(rs.getInt(1),
							rs.getDate(2),
							rs.getDate(3),
							rs.getInt(4),
							rs.getString(5),
							daysList,
							rs.getTime(7),
							rs.getTime(8),
							rs.getString(9),
							rs.getBoolean(10) ));	 
				} 
				
				rs.close();
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			msg.setData(sales);
			return msg;
		
		
	}	
	
	
	/**
	 * Adds the sale.
	 *
	 * @param clientMsg the client msg
	 * @return the message
	 */
	public static Message addSale(Message clientMsg) {//change salID TO AUTO INCREMENT IN DATABASE
		Message msg = new Message();
		PreparedStatement ps1 = null;
		
		Sale sale = (Sale)clientMsg.getData();
		
		msg.setTask("Define Sale");
    	
    	try {
			ps1 = conn.prepareStatement("INSERT INTO sales (startDate, endDate, discountSize, region, days, starthour, endHour,saleType,isActive) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);");
			ps1.setDate(1, sale.getStartDate());
			ps1.setDate(2,sale.getEndDate());
			ps1.setInt(3,sale.getDiscountSize());
			ps1.setString(4,sale.getRegion());
			ps1.setString(5,String.join(", ", sale.getDays()));
			ps1.setTime(6,sale.getStartHour());
			ps1.setTime(7,sale.getEndHour());
			ps1.setString(8,sale.getSaleType());//make sure that enum names match that in db
			ps1.setBoolean(9, sale.isActive());
			ps1.executeUpdate();
			ps1.close();
			
		} catch (SQLException e) {
			msg.setData("Falied");
			e.printStackTrace();
			return msg;
		}
    	
    	msg.setData("Success");
    	return msg;
    		
	}
	
	
	
	/**
	 * Change user status to log out.
	 *
	 * @param clientMsg the client msg
	 * @return the message
	 */
	public static Message ChangeUserStatusToLogOut(Message clientMsg) {
		
		Message msg = new Message();
		String UserName = String.valueOf(clientMsg.getData());
		final int LogOutFlag = 0 ;		
		PreparedStatement ps1 = null ;
    	
    	try {
			ps1 = conn.prepareStatement("UPDATE users SET `isLogged` = ? WHERE username = ?");
			ps1.setBoolean(1, false);
			ps1.setString(2,UserName);
			ps1.executeUpdate();
			ps1.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	
    	msg.setTask("Back to UserLogin screen");
    	return msg;
    		
	}
	
	
	/**
	 * Gets the product catalog.
	 *
	 * @param Data the data
	 * @return the product catalog
	 */
	public static Message getProductCatalog(Object Data) {
		Message msg =new Message();
		
		//int DeviceId = 1;
		ArrayList<Item> ItemsCatalog = new ArrayList<Item>();
		try {
			
			Device device = new entities.Device();
			device.setDeviceID((int)Data);
			
			PreparedStatement ps = conn.prepareStatement("SELECT t1.* ,t2.amount FROM item t1,item_in_device t2 where t1.serialNumber=t2.serialNumber\r\n"
					+ " AND t2.deviceID = ? And t2.status ='AVAILABLE' And t2.amount>0");	
			ps.setInt(1, device.getDeviceID());
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				Item i = new Item(rs.getInt("serialNumber"), rs.getString("name"), rs.getString("description"),
						 rs.getString("photo"),rs.getFloat("price"));
				
				i.addDevice(device, rs.getInt("amount"),entities.ItemInDevice.Status.AVAILABLE);	
				ItemsCatalog.add(i);
			}
			
			ps.close();
		} catch (Exception e) {
			
			System.out.println("The Catalog Items Importing failed!");
		}
		
		msg.setTask("Display Catalog");
		msg.setData(ItemsCatalog);
		
		return msg;
	}
	
	
	
	
	/**
	 * User login.
	 * changes the login status of the user to islogged=true(if possible) in users tables in DB.
	 * returns a User object(or an object of a User subclass) with the details of the  logged in user.
	 * @param clientMsg the client msg
	 * @return the message
	 */
	public static Message userLogin(Message clientMsg) 
{


	Message msg = new Message();
	boolean getIsLogged;
	User user1=new User();
    user1=(User) clientMsg.getData();
	int usernamepass = 0;
	try 
	{
		
		if(!isUserAndPassExist(user1.getUserName(),user1.getPassword())) {
			
			msg.setData(usernamepass);
			msg.setTask("User details is not exist");
			return msg;
		}

		PreparedStatement ps1 = conn.prepareStatement("SELECT * FROM users WHERE username = ? AND password = ?;");
 		ps1.setString(1,user1.getUserName());
		ps1.setString(2,user1.getPassword());
		ResultSet rs = ps1.executeQuery();
		rs.next();
		getIsLogged = rs.getBoolean("isLogged");
		user1.setIDNumber(rs.getString("id"));
		user1.setFirstName(rs.getString("first name"));
		user1.setLastName(rs.getString("last name"));
		user1.setUserName(rs.getString("username"));
		user1.setEmail(rs.getString("email"));
		user1.setPhoneNumber(rs.getString("phone number"));
		user1.setUserPermission(rs.getString("userPermission"));
		
		if(getIsLogged) {
			msg.setData(usernamepass);
			msg.setTask("User Already Logged in");
			ps1.close();
			return msg;
		}
		if(user1.getUserPermission()==null) {
			msg.setData(user1);
			msg.setTask("User has no permissions");
			return msg;
		}
 		if(user1.getUserPermission().equals("pending customer"))
 		{
			msg.setData(user1);
			msg.setTask("User still in pending position!");
			return msg;
 		}
 		ps1 = conn.prepareStatement("UPDATE users SET `isLogged` = 1 WHERE username = ?");
 		ps1.setString(1,user1.getUserName());
		ps1.executeUpdate();
		ps1.close();
		
	} catch (SQLException e) {
		
		msg.setData(usernamepass);
		msg.setTask("Error Update user to logged in");
		e.printStackTrace();
		return msg;
	}
	
	if(user1.getUserPermission().equals("customer") || user1.getUserPermission().equals("member"))
	{
		Member mem1;
		Customer cus1 = null;
		String Creditcard = null;
		try 
		{
			
			PreparedStatement ps1 = conn.prepareStatement("SELECT * FROM customers WHERE username = ?;");
	 		ps1.setString(1,user1.getUserName());
			ResultSet rs = ps1.executeQuery();
			
			if(rs.next()) {
            Creditcard=rs.getString("credit card number");
            cus1=new Customer(user1.getUserName(),user1.getLastName(),
					user1.getFirstName(),user1.getPassword(),user1.getIDNumber(),
					user1.getEmail(),user1.getPhoneNumber(),
					user1.isLogged(),user1.getUserPermission(),
					Creditcard,true);
			}
			
            msg.setData(cus1);
		    ps1.close();
			
		} catch (SQLException e) {
			
			msg.setData(usernamepass);
			msg.setTask("Error Update user type");
			e.printStackTrace();
			return msg;
		}
		
		try 
		{
			PreparedStatement ps1 = conn.prepareStatement("SELECT * FROM member WHERE username = ?;");
	 		ps1.setString(1,user1.getUserName());
			ResultSet rs = ps1.executeQuery();
			if(rs.next())
			{
			mem1=new Member(user1.getUserName(),user1.getLastName(),
					user1.getFirstName(),user1.getPassword(),user1.getIDNumber(),
					user1.getEmail(),user1.getPhoneNumber(),
					user1.isLogged(),user1.getUserPermission(),
					Creditcard,true,
					rs.getInt("member number"),rs.getBoolean("madePurchase"));
		            msg.setData(mem1);
			}
			else
				msg.setData(cus1);	
		ps1.close();
		} catch (SQLException e) {
			
			msg.setData(usernamepass);
			msg.setTask("Error Update user type");
			e.printStackTrace();
			return msg;
		}
		//user1.setMember(mem1);
	}
	else
	{
		Employee emp1;
		try 
		{
			PreparedStatement ps1 = conn.prepareStatement("SELECT * FROM employees WHERE username = ?;");
	 		ps1.setString(1,user1.getUserName());
			ResultSet rs = ps1.executeQuery();
			rs.next();
			emp1=new Employee(user1.getUserName(),user1.getLastName(),
					user1.getFirstName(),user1.getPassword(),user1.getIDNumber(),
					user1.getEmail(),user1.getPhoneNumber(),
					user1.isLogged(),user1.getUserPermission(),
					rs.getInt("employeeID"),rs.getString("region"),
					rs.getString("position"));
		ps1.close();
		} catch (SQLException e) {
			
			msg.setData(usernamepass);
			msg.setTask("Error Update user type");
			e.printStackTrace();
			return msg;
		}
		//user1.setEmployee(emp1);
		msg.setData(emp1);
	}

	//msg.setData(user1);
	msg.setTask("User Login Succeed");
	
	return msg;

}

	
/**
 * Save order.
 * Saves the customer order in the DB.
 * @param clientMsg the client msg
 * @return the message
 */
public static Message SaveOrder(Message clientMsg) {
		
		Message msg=new Message();
		Order order = (Order) clientMsg.getData();
		String OrderNumber;//maybe remove
		List<ItemInOrder> itemInOrder =order.getItemsInOrder();
		
		//check that all the item find in DB
		if(!(order instanceof Delivery)) {
		for(int i= 0; i < itemInOrder.size();i++) {
		
			try {
				PreparedStatement ps = conn.prepareStatement("SELECT `amount` FROM item_in_device WHERE deviceID = ? and serialNumber=? ");
				ps.setInt(1, order.getDeviceID());
				ps.setInt(2, itemInOrder.get(i).getItem().getItem().getSerialNumber());
				ResultSet rs = ps.executeQuery();
				rs.next();
				if(rs.getInt(1)<itemInOrder.get(i).getAmount()) {
					
					msg.setTask("Item Misssing");
					ps.close();//critical- if you don't close a statement/result there might be a memory leak!
					return msg;
				}
				ps.close();	//critical- if you don't close a statement/result there might be a memory leak!	
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
			
		}
				
		String command="";
		command="UPDATE item_in_device SET amount = amount-? WHERE amount-?>=0 ";
		command+="AND deviceId=? AND serialNumber=?";
		PreparedStatement ps1 = null ;
    	List<ItemInOrder> items=order.getItemsInOrder();
    	for(ItemInOrder i:items) {//update inventory
    	try {
			ps1 = conn.prepareStatement(command);
			ps1.setInt(1, i.getAmount());
			ps1.setInt(2, i.getAmount());
			ps1.setInt(3,i.getOrder().getDeviceID());
			ps1.setInt(4,i.getItem().getItem().getSerialNumber());
			ps1.executeUpdate();
			ps1.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	}
    	
    	try {//update status of unavailable items
			ps1 = conn.prepareStatement("UPDATE item_in_device SET status = 'NOT_AVAILABLE' WHERE amount=0 and deviceId=?");
			ps1.setInt(1, order.getDeviceID());
			ps1.executeUpdate();
			ps1.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}	
		PreparedStatement ps1 = null ;
    	// Set debit date for end of the month for members ! 
    	if(order.getPayment().getCustomer() instanceof Member) {
    		
    		 Calendar cal = Calendar.getInstance();
    	      Timestamp T = getEndTimestamp(cal.get(Calendar.MONTH));
    	     order.getPayment().setDebitDate(T); 
    	     
    	     if(((Member)order.getPayment().getCustomer()).isFirstPurchase()== false) {
    	    	 ((Member)order.getPayment().getCustomer()).setFirstPurchase(true);
    	    	 
    	    		try {//update status of unavailable items
    	    			ps1 = conn.prepareStatement("UPDATE member SET madePurchase = 1 WHERE `member number` = ?");
    	    			ps1.setInt(1, ((Member)order.getPayment().getCustomer()).getMemberNumber());
    	    			ps1.executeUpdate();
    	    			ps1.close();
    	    			
    	    		} catch (SQLException e) {
    	    			e.printStackTrace();
    	    		}
    	    	 
    	     }
    	}
    	
    	
    	try {//insert new order
			ps1 = conn.prepareStatement("INSERT INTO orders (totalAmount,createdTime,"
					+"deviceID, debitDate,customerID) VALUES (?,?,?,?,?)", PreparedStatement.RETURN_GENERATED_KEYS);
		
			ps1.setFloat(1, order.getTotalAmount());
			ps1.setTimestamp(2, order.getCreatedTime());
			ps1.setInt(3, order.getDeviceID());
			ps1.setTimestamp(4, order.getPayment().getDebitDate());
			ps1.setString(5, order.getPayment().getCustomer().getIDNumber());
			ps1.executeUpdate();
			ResultSet r= ps1.getGeneratedKeys();//get generated order number
			if(r.next()) {
				order.setOrderNumber(r.getInt(1));
			}
			ps1.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	
    	if(order instanceof Delivery) { 
    		Delivery delivery = (Delivery)order;
    		try {//insert new order
    			ps1 = conn.prepareStatement("INSERT INTO delivery (orderID,address,"
    					+" status,deviceID,notes) VALUES (?,?,?,?,?)");
    		
    			ps1.setInt(1, delivery.getOrderNumber());
    			ps1.setString(2, delivery.getAddress());
    			ps1.setString(3, "WAITING");
    			ps1.setInt(4,delivery.getDeviceID());
    			ps1.setString(5,delivery.getNotes());
    			ps1.executeUpdate();
    			ps1.close();
    		} catch (SQLException e) {
    			e.printStackTrace();
    		}
    		
    	}
    	
    	
    	//add selecting new order number and only then add items in order
    	SaveOrderItemsInDB(order);
    	if(order instanceof TakeAway) {
    		SavePickUp(order);
    	}
    	msg.setTask("success Order");
    	msg.setData(order);
    	return msg;		
}
	
/**
 * Gets the end end of the  current month timestamp.
 *
 * @param month the month
 * @return the end timestamp
 */
public static Timestamp getEndTimestamp(int month) { 
	  Calendar calendar = Calendar.getInstance();
	  java.util.Date date = new java.util.Date();
	  calendar.setTime(date); 
	  calendar.set(Calendar.MONTH, month);
	  calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH)); 
	  calendar.set(Calendar.HOUR_OF_DAY, 0); 
	  calendar.set(Calendar.MINUTE, 0); 
	  calendar.set(Calendar.SECOND, 0); 
	  calendar.set(Calendar.MILLISECOND, 0); 
	  return new Timestamp(calendar.getTimeInMillis()); 
	}

	/**
	 * Gets a user's password from DB.
	 *
	 * @param user the user
	 * @return the string
	 */
	public static String GetMyPassword(User user) {
		PreparedStatement ps1;
		String password=null;
		
		try {
			ps1 = conn.prepareStatement("SELECT password  FROM users where username = ? ");
			
			ps1.setString(1, user.getUserName());
			ResultSet rs = ps1.executeQuery();
			if(rs.next()) {
				password = rs.getString(1);
			}

			rs.close();
			ps1.close();
	
			
		}  catch (SQLException e) {
			
			e.printStackTrace();
		}
		
		return password;
	}
	
	

/**
 * Save  a pick up(Take away) order in the DB.
 *
 * @param order the order
 */
public static void  SavePickUp(Order order) {

	PreparedStatement ps1 = null ;
	try {
		ps1 = conn.prepareStatement("INSERT INTO takeaway (orderID,orderCode) VALUES (?, ?)");
		ps1.setInt(1, order.getOrderNumber());
		ps1.setInt(2,order.getOrderNumber());
		ps1.executeUpdate();
		ps1.close();
		
	} catch (SQLException e) {
		e.printStackTrace();
	}
}

	/**
	 * Save order items in DB.
	 *
	 * @param order the order
	 */
	public static void  SaveOrderItemsInDB(Order order) {

			PreparedStatement ps1 = null ;
			
			
			
			
			List<ItemInOrder> items=order.getItemsInOrder();
			//INSERT INTO orders (totalAmount,createdTime,"
			//+"deviceID, debitDate,customerID) VALUES (?,?,?,?,?)",
		
	    	for(ItemInOrder i:items) {//update inventory
	    	try {
	    		ps1 = conn.prepareStatement("INSERT INTO item_in_order (orderID,serialNumber,deviceID,amount) VALUES (?, ?,?,?)");
				ps1.setInt(1, order.getOrderNumber());
				ps1.setInt(2,i.getItem().getItem().getSerialNumber());
				ps1.setInt(3,order.getDeviceID());
				ps1.setInt(4, i.getAmount());
				ps1.executeUpdate();
				ps1.close();
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
	    	
	    
	    	}

	}
	


	
		/**
		 * Adds a customer.
		 *
		 * @param clientMsg the client msg
		 * @return the message
		 */
		public static Message addCustomer(Message clientMsg) {
		 
		
		PreparedStatement ps1;
		
		Customer customer = (Customer)clientMsg.getData();
		Message msg = new Message();
		
			
		try {
			ps1 = conn.prepareStatement("UPDATE `users` SET `userPermission` = 'pending customer' WHERE `username` = ?");
			ps1.setString(1,customer.getUserName());
			ps1.executeUpdate();
			ps1.close();
		} catch (SQLException e) {
			
			msg.setTask("Update Credit Card Failed");
			e.printStackTrace();
			return msg;
		}
		
		
		try {
			ps1 = conn.prepareStatement("INSERT INTO `customers` (`username`,`credit card number`,`id`) VALUES (?, ?, ?)");
			ps1.setString(1, customer.getUserName());
			ps1.setString(2,customer.getCreditCardNumber());
			ps1.setString(3,customer.getIDNumber());
			ps1.executeUpdate();
			ps1.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
 			
		
		msg.setTask("Update Credit Card Succeed");
		return msg;		
	}
	
	
	
	/**
	 * Gets the device list in a specific region.
	 *
	 * @param clientMsg the client msg
	 * @return the device list
	 */
	public static Message getDeviceList(Message clientMsg) {
		
		PreparedStatement ps1;
		Message msg = new Message();
		
		Employee employee = (Employee)(clientMsg.getData());
		ArrayList<Device> deviceList = new ArrayList<>();
		msg.setTask("Get Device List");
		
		try {
			
			ps1 = conn.prepareStatement("SELECT `deviceID`,`location`,`threshold` FROM `device` WHERE `region` = ? AND `location` != 'delivery center'");
			ps1.setString(1,employee.getRegion());
			ResultSet rs = ps1.executeQuery();
			
			while(rs.next())
	 		{
	 			
	 			Device device = new Device(); 
	 			
	 			device.setDeviceID(rs.getInt(1));
	 			device.setLocation(rs.getString(2));
	 			device.setThreshold(rs.getInt(3));
				
				deviceList.add(device);	 
				
			} 
	 		
			rs.close();
	
			
		}  catch (SQLException e) {
			
			
			msg.setData("Falied");
			e.printStackTrace();
			return msg;
		}
		
		msg.setData(deviceList);
		return msg;
	
	}
	
	/**
	 * Update installation information for  future clients.
	 *
	 * @param type the type
	 * @param deviceID the device ID
	 */
	public static void updateInstallation(String type,int deviceID) {
		
		PreparedStatement ps1;

		try {
			
			ps1 = conn.prepareStatement("UPDATE `app_installation` SET `installType` = ? , `machineNumber` = ?");
			ps1.setString(1,type);
			ps1.setInt(2,deviceID);
			ps1.executeUpdate();
			ps1.close();
		
		} catch (SQLException e) {
			
			e.printStackTrace();
		
		}

		
	}
	
	
	/**
	 * Gets a list of all the devices.
	 *
	 * @param clientMsg the client msg
	 * @return the pick up device list
	 */
	public static Message getPickUpDeviceList(Message clientMsg) {//added function
		
		PreparedStatement ps1;
		Message msg = new Message();
		
		//Employee employee = (Employee)(clientMsg.getData());
		ArrayList<Device> deviceList = new ArrayList<>();
		msg.setTask("Get Device List");
		
		try {
			ps1 = conn.prepareStatement("SELECT *  FROM `device` where location!='delivery center'");
			ResultSet rs = ps1.executeQuery();
			while(rs.next())
	 		{
	 			Device device = new Device(); 
	 			
	 			device.setDeviceID(rs.getInt(1));
	 			device.setLocation(rs.getString(2));
	 			device.setThreshold(rs.getInt(3));
	 			device.setRegion(rs.getString(4));
				
				deviceList.add(device);	 
				
			} 
	 		
			rs.close();
	
			
		}  catch (SQLException e) {
			
			
			msg.setData("Falied");
			e.printStackTrace();
			return msg;
		}
		
		msg.setData(deviceList);
		return msg;
	
	}
	
	
/**
 * Gets the delivery centers list.
 *
 * @param clientMsg the client msg
 * @return the delivery centers list
 */
public static Message getDeliveryCentersList(Message clientMsg) {//added function
		
		PreparedStatement ps1;
		Message msg = new Message();
		
		//Employee employee = (Employee)(clientMsg.getData());
		ArrayList<Device> centersList = new ArrayList<>();
		msg.setTask("Get Delivery Centers List");
		
		try {
			ps1 = conn.prepareStatement("SELECT *  FROM `device` where location='delivery center'");
			ResultSet rs = ps1.executeQuery();
			while(rs.next())
	 		{
	 			Device device = new Device(); 
	 			
	 			device.setDeviceID(rs.getInt(1));
	 			device.setLocation(rs.getString(2));
	 			device.setThreshold(rs.getInt(3));
	 			device.setRegion(rs.getString(4));
				
	 			centersList.add(device);	 
				
			} 
	 		
			rs.close();
	
			
		}  catch (SQLException e) {
			
			
			msg.setData("Falied");
			e.printStackTrace();
			return msg;
		}
		
		msg.setData(centersList);
		return msg;
	
}
	
	
	/**
	 * Registration mangement.
	 *	updates customer status to approved in DB.
	 * @param clientMsg the client msg
	 * @return the message
	 */
	public static Message registrationMangement(Message clientMsg) {
		
		PreparedStatement ps1;
		Message msg = new Message();
	
		@SuppressWarnings("unchecked")
		ArrayList<User> userList = (ArrayList<User>)clientMsg.getData();
		
		  
		msg.setTask("Return Registration Mangement");
			    
		   
		try {
			
			 for (User user : userList) {
			
					ps1 = conn.prepareStatement("UPDATE `users` SET `userPermission` = 'customer' WHERE `username` = ?");
					ps1.setString(1,user.getUserName());
					ps1.executeUpdate();
					ps1.close();
					
					
					ps1 = conn.prepareStatement("UPDATE `customers` SET `approved customer` = '1' WHERE `username` = ?");
					ps1.setString(1,user.getUserName());
					ps1.executeUpdate();
					ps1.close();
					
			 }
			
		} catch (SQLException e) {
			
			
			msg.setData("Falied");
			e.printStackTrace();
			return msg;
		}
		
		
		msg.setData("Success");
		
		return msg;
	}
	
	
	
/**
 * Sets device(s) threshold.
 *
 * @param clientMsg the client msg
 * @return the message
 */
public static Message setThresholdList(Message clientMsg) {
		
		PreparedStatement ps1;
		Message msg = new Message();
	
		@SuppressWarnings("unchecked")
		ArrayList<Device> deviceList = (ArrayList<Device>)clientMsg.getData();
		
		  
		msg.setTask("Set Threshold");
			    
		   
		try {
			
			 for (Device device : deviceList) {
			
					ps1 = conn.prepareStatement("UPDATE `device` SET `threshold` = ?  WHERE `deviceID` = ?");
					ps1.setInt(1,device.getThreshold());
					ps1.setInt(2,device.getDeviceID());
					ps1.executeUpdate();
					ps1.close();
	
					
			 }
			
		} catch (SQLException e) {
			
			
			msg.setData("Falied");
			e.printStackTrace();
			return msg;
		}
		
		
		msg.setData("Success");
		
		return msg;
	}
	
	
	
	/**
	 * Adds a new member.
	 *
	 * @param clientMsg the client msg
	 * @return the message
	 */
	public static Message addMember(Message clientMsg) {
		 
		
		PreparedStatement ps1;
		
		Member member = (Member)clientMsg.getData();
		Message msg = new Message();
		Member returnMember = new Member();
		int memberID;

		try {
			ps1 = conn.prepareStatement("INSERT INTO `member` (`username`) VALUES (?)");
			ps1.setString(1, member.getUserName());
			ps1.executeUpdate();
			ps1.close();
			
		} catch (SQLException e) {
			
			returnMember.setMemberNumber(0);
			msg.setData(returnMember);
			msg.setTask("Add Member");
			e.printStackTrace();
			return msg;
		}
			
		
		try {
			ps1 = conn.prepareStatement("UPDATE `users` SET `userPermission` = 'member' WHERE `username` = ?");
			ps1.setString(1,member.getUserName());
			ps1.executeUpdate();
			ps1.close();
		} catch (SQLException e) {
			
			msg.setTask("Add Member");
			returnMember.setMemberNumber(0);
			msg.setData(returnMember);
			e.printStackTrace();
			return msg;
		}
		
		
		try {
			
			ps1 = conn.prepareStatement("SELECT `member number` FROM member WHERE `username` = ?");
			ps1.setString(1,member.getUserName());
			ResultSet rs = ps1.executeQuery();
			rs.next();
			memberID = rs.getInt(1);
			
			
		}  catch (SQLException e) {
			
			returnMember.setMemberNumber(0);
			msg.setData(returnMember);
			msg.setTask("Add Member");
			e.printStackTrace();
			return msg;
		}
		
 			
		returnMember.setMemberNumber(memberID);
		msg.setData(returnMember);
		msg.setTask("Add Member");
		return msg;		
	}
	
	
	
	
	/**
	 * Search a username in DB.
	 *
	 * @param clientMsg the client msg
	 * @return the message
	 */
	public static Message searchUsername(Message clientMsg) {
	
		Message msg = new Message();
		msg.setTask("Search Username");
		String username = (String)clientMsg.getData();
		
		
		User userDetails = new User();
		ArrayList<User> user = new ArrayList<>();
		
		
		try 
		{
			
			PreparedStatement ps1 = conn.prepareStatement("SELECT `id`,`first name`,`last name`,`email`,`phone number`,`userPermission`,`username` FROM users WHERE `username` = ?");
			ps1.setString(1,username);
			ResultSet rs = ps1.executeQuery();
			rs.next();
	 		
			try {
				

				userDetails.setIDNumber(rs.getString(1));
				userDetails.setFirstName(rs.getString(2));
				userDetails.setLastName(rs.getString(3));
				userDetails.setEmail(rs.getString(4));
				userDetails.setPhoneNumber(rs.getString(5));
				userDetails.setUserPermission(rs.getString(6));
				userDetails.setUserName(rs.getString(7));

				
				user.add(userDetails);
			
				
			} catch(SQLException e) {
				
				ps1.close();
				msg.setData(user);
				return msg;
				
			}
			
		} catch (SQLException e) {
			
			e.printStackTrace();
			
		}
		
	

		
		msg.setData(user);
		return msg;
		
		
		
	}

	
/**
 * Gets a list of open the inventory calls from DB.
 *
 * @param clientMsg the client msg
 * @return the inventory call logistic
 */
public static Message getInventoryCallLogistic(Message clientMsg) {
		
		PreparedStatement ps1;
		Message msg = new Message();
		Employee employee = (Employee)(clientMsg.getData());
		ArrayList<InventoryCall> inventoryCallList = new ArrayList<>();
		
		
		msg.setTask("Get Inventory Call Logistic");
		
		try {
			
			ps1 = conn.prepareStatement("SELECT `callID`,`deviceID`,`employeeID`,`date`,`status` FROM `inventory_call` WHERE `employeeID` = ? AND `status` = 'OPEN' ");
			ps1.setInt(1,employee.getEmployeeID());
			ResultSet rs = ps1.executeQuery();
			
			while(rs.next())
	 		{
	 			
				InventoryCall inventoryCall = new InventoryCall();
				
				Timestamp t = rs.getTimestamp(4);
		        String date = new SimpleDateFormat("YYYY-MM-dd hh:mm:ss").format(t);
		        t = Timestamp.valueOf(date);

	 			
	 			inventoryCall.setCallID(rs.getInt(1));
	 			inventoryCall.setDeviceID(rs.getInt(2));
	 			inventoryCall.setEmployeeID(rs.getInt(3));
	 			inventoryCall.setDate(t);
	 			inventoryCall.setStatus(rs.getString(5));
	 	
	 			inventoryCallList.add(inventoryCall);	 
				
			} 
	 		
			rs.close();
	
			
		}  catch (SQLException e) {
			
			
			msg.setData("Falied");
			e.printStackTrace();
			return msg;
		}
		
		msg.setData(inventoryCallList);
		return msg;
	
	}
	

	/**
	 * View delivery manager.
	 *	gets a list of deliveries from a specific regional delivery center.
	 * @param clientMsg the client msg
	 * @return the message
	 */
	public static Message viewDeliveryManager(Message clientMsg) {
		
		PreparedStatement ps1;
		Message msg = new Message();
		Employee employee = (Employee)(clientMsg.getData());
		ArrayList<Delivery> deliveryList = new ArrayList<>();
		
		
		msg.setTask("View Delivery Manager");
		
		try {
			
			ps1 = conn.prepareStatement("SELECT * FROM `delivery` WHERE `deviceID` IN (SELECT `deviceID` FROM `device` WHERE location = 'delivery center' AND `region` = ?)");
			ps1.setString(1,employee.getRegion());
			ResultSet rs = ps1.executeQuery();
			
			while(rs.next())
	 		{
	 			
				Delivery delivery = new Delivery();
				
				Timestamp t = rs.getTimestamp(3);
		     //   String date = new SimpleDateFormat("YYYY-MM-dd hh:mm:ss").format(t);
		      //  t = Timestamp.valueOf(date);
	
	 			
		        delivery.setOrderNumber(rs.getInt(1));
		        delivery.setAddress(rs.getString(2));
		        delivery.setEstimatedDeliveryTime(t);
		        delivery.setStatus(rs.getString(4));
		        
		        deliveryList.add(delivery);	 
				
			} 
	 		
			rs.close();
	
			
		}  catch (SQLException e) {
			
			
			msg.setData("Falied");
			e.printStackTrace();
			return msg;
		}
		
		msg.setData(deliveryList);
		return msg;
	
	}


	/**
	 * Gets delivery orders of a specific customer.
	 *
	 * @param clientMsg the client msg
	 * @return the message
	 */
	public static Message viewDeliveryOrders(Message clientMsg) {
		
		PreparedStatement ps1;
		Message msg = new Message();
		Customer customer = (Customer)(clientMsg.getData());
		ArrayList<Delivery> deliveryList = new ArrayList<>();
		
		
		msg.setTask("View Delivery Orders");
		
		try {
			
			ps1 = conn.prepareStatement("SELECT d.* FROM delivery d JOIN orders o ON d.orderID = o.orderID WHERE o.customerID = ? ");
			ps1.setString(1,customer.getIDNumber());
			ResultSet rs = ps1.executeQuery();
			
			while(rs.next())
	 		{
	 			
				Delivery delivery = new Delivery();
				
				Timestamp t = rs.getTimestamp(3);
		     //   String date = new SimpleDateFormat("YYYY-MM-dd hh:mm:ss").format(t);
		      //  t = Timestamp.valueOf(date);
	
	 			
		        delivery.setOrderNumber(rs.getInt(1));
		        delivery.setAddress(rs.getString(2));
		        delivery.setEstimatedDeliveryTime(t);
		        delivery.setStatus(rs.getString(4));
		        delivery.setDeviceID(rs.getInt(5));
		        
		        deliveryList.add(delivery);	 
				
			} 
	 		
			rs.close();
	
			
		}  catch (SQLException e) {
			
			
			msg.setData("Falied");
			e.printStackTrace();
			return msg;
		}
		
		msg.setData(deliveryList);
		return msg;
	
	}


	
	







	
	/**
	 * Gets a list of inventory calls created by a specific user(employee).
	 *
	 * @param clientMsg the client msg
	 * @return the inventory call manager
	 */
	public static Message getInventoryCallManager(Message clientMsg) {
		
		PreparedStatement ps1;
		Message msg = new Message();
		Employee employee = (Employee)(clientMsg.getData());
		ArrayList<InventoryCall> inventoryCallList = new ArrayList<>();
		
		
		msg.setTask("Get Inventory Call Manager");
		
		try {
			
			ps1 = conn.prepareStatement("SELECT `callID`,`deviceID`,`employeeID`,`date`,`status` FROM `inventory_call` WHERE `creatorUsername` = ?");
			ps1.setString(1,employee.getUserName());
			ResultSet rs = ps1.executeQuery();
			
			while(rs.next())
	 		{
	 			
				InventoryCall inventoryCall = new InventoryCall();
				
				Timestamp t = rs.getTimestamp(4);
		        String date = new SimpleDateFormat("YYYY-MM-dd hh:mm:ss").format(t);
		        t = Timestamp.valueOf(date);

	 			
	 			inventoryCall.setCallID(rs.getInt(1));
	 			inventoryCall.setDeviceID(rs.getInt(2));
	 			inventoryCall.setEmployeeID(rs.getInt(3));
	 			inventoryCall.setDate(t);
	 			inventoryCall.setStatus(rs.getString(5));
	 	
	 			inventoryCallList.add(inventoryCall);	 
				
			} 
	 		
			rs.close();
	
			
		}  catch (SQLException e) {
			
			
			msg.setData("Falied");
			e.printStackTrace();
			return msg;
		}
		
		msg.setData(inventoryCallList);
		return msg;
	
	}
	
	
	/**
	 * Gets a list of items that went below the thresholds in devices of a specific region.
	 *
	 * @param clientMsg the client msg
	 * @return the alerted items
	 */
	public static Message getAlertedItems(Message clientMsg) {
		
		PreparedStatement ps1;
		Message msg = new Message();
		Employee employee = (Employee)(clientMsg.getData());
		
		ArrayList<ItemInDevice> ItemInDeviceList = new ArrayList<>();
		
		
		
		
		try {
			
			ps1 = conn.prepareStatement("SELECT it.serialNumber, it.name, it.price,i.amount,d.threshold ,d.deviceID FROM "
					+ "item_in_device i JOIN device d ON i.deviceID = d.deviceID JOIN item it ON i.serialNumber = it.serialNumber "
					+ "WHERE d.region=? AND d.threshold > i.amount AND i.thresholdNotify = '0'");
			
			ps1.setString(1,employee.getRegion());
			ResultSet rs = ps1.executeQuery();
			
			while(rs.next())
	 		{
	 			
				ItemInDevice itemInDevice = new ItemInDevice();
				Item item = new Item();
				
				item.setSerialNumber(rs.getInt(1));
				item.setName(rs.getString(2));
				item.setPrice(rs.getFloat(3));
				itemInDevice.setDevice(new Device());
				itemInDevice.getDevice().setDeviceID(rs.getInt(6));//
				itemInDevice.setAmount(rs.getInt(4));
				itemInDevice.getDevice().setThreshold(rs.getInt(5));
				itemInDevice.setItem(item);
				ItemInDeviceList.add(itemInDevice);	 
				
			} 
	 		
			rs.close();
	
			
		}  catch (SQLException e) {
			
			
			msg.setData("Falied");
			e.printStackTrace();
			return msg;
		}
		msg.setTask("threshold notify");
		msg.setData(ItemInDeviceList);
		return msg;
		
	}
	
	
	/**
	 * Gets a list of ItemInDevice objects of items that are below the threshold in a specific device.
	 *
	 * @param clientMsg the client msg
	 * @return the item in device
	 */
	public static Message getItemInDevice(Message clientMsg) {
		
		PreparedStatement ps1;
		Message msg = new Message();
		InventoryCall inventoryCall = (InventoryCall)(clientMsg.getData());
		
		ArrayList<ItemInDevice> ItemInDeviceList = new ArrayList<>();
		
		
		msg.setTask("Get Item In Device");
		
		try {
			
			ps1 = conn.prepareStatement("SELECT it.serialNumber, it.name, it.price, it.photo,i.amount,d.threshold FROM item_in_device i JOIN device d ON i.deviceID = d.deviceID JOIN item it ON i.serialNumber = it.serialNumber WHERE d.deviceID = ? AND d.threshold > i.amount");
			ps1.setInt(1,inventoryCall.getDeviceID());
			ResultSet rs = ps1.executeQuery();
			
			while(rs.next())
	 		{
	 			
				ItemInDevice itemInDevice = new ItemInDevice();
				Item item = new Item();
				
				item.setSerialNumber(rs.getInt(1));
				item.setName(rs.getString(2));
				item.setPrice(rs.getFloat(3));
				item.setPhoto(rs.getString(4));
				
				itemInDevice.setDevice(new Device());
				
				itemInDevice.setAmount(rs.getInt(5));
				itemInDevice.getDevice().setDeviceID(inventoryCall.getDeviceID());
				itemInDevice.getDevice().setThreshold(rs.getInt(6));
				itemInDevice.setAddedAmount(0);
				itemInDevice.setItem(item);

	 	
				ItemInDeviceList.add(itemInDevice);	 
				
			} 
	 		
			rs.close();
	
			
		}  catch (SQLException e) {
			
			
			msg.setData("Falied");
			e.printStackTrace();
			return msg;
		}
		
		msg.setData(ItemInDeviceList);
		return msg;
}	
	
	/**
	 * Sets the sale status.
	 *
	 * @param clientMsg the client msg
	 * @return the message
	 */
	public static Message setSaleStatus(Message clientMsg) {
		
		PreparedStatement ps1;
		Message msg = new Message();
		ArrayList<Sale> saleList = (ArrayList<Sale>)(clientMsg.getData());
		
		
		msg.setTask("Set Sale Status");
		
		try {
			
			 for (Sale sale : saleList) {
					
					ps1 = conn.prepareStatement("UPDATE `sales` SET `isActive` = ? WHERE `saleID` = ?");
					ps1.setBoolean(1,sale.isActive());
					ps1.setInt(2,sale.getSaleID());
					ps1.executeUpdate();
					ps1.close();
					
					
			 }
	 		
			
		}  catch (SQLException e) {
			
			
			msg.setData("Falied");
			e.printStackTrace();
			return msg;
		}
		
		msg.setData("Success");
		return msg;
	
	}
	
	
	/**
	 * Sets the delivery status.
	 *
	 * @param clientMsg the client msg
	 * @return the message
	 */
	public static Message setDeliveryStatus(Message clientMsg) {
		
		PreparedStatement ps1;
		Message msg = new Message();
		ArrayList<Delivery> deliveryList = (ArrayList<Delivery>)(clientMsg.getData());
		
		
	   int distanceKM = 10;
       int droneSpeedKmh = 75;
       int loadingMinute = 5; 
       
       
       Calendar calendar = Calendar.getInstance();
       calendar.add(Calendar.DAY_OF_MONTH, 7); // drone will be available
       calendar.add(Calendar.MINUTE,loadingMinute); // add loading time
       calendar.add(Calendar.MINUTE,(int)(((float)distanceKM/droneSpeedKmh)*60)); // add delivery time 
       java.util.Date estimatedDatetime = calendar.getTime();
       Timestamp estimatedTimestamp = new Timestamp(estimatedDatetime.getTime());
       
		
		msg.setTask("Set Delivery Status");
		
		try {
			
			 for (Delivery delivery : deliveryList) {
				
				 	if(delivery.getEstimatedDeliveryTime() == null && delivery.getStatus().equals("PROCESSING")) {
				 		
				 		
						ps1 = conn.prepareStatement("UPDATE `delivery` SET `status` = ?,estimatedDeliveryTime = ? WHERE `orderID` = ?");
						ps1.setString(1,delivery.getStatus());
						ps1.setTimestamp(2,estimatedTimestamp);
						ps1.setInt(3,delivery.getOrderNumber());
						ps1.executeUpdate();
						ps1.close();
				 			
				 		
				 	}
				 	else  {
				 		
				 		
				 		ps1 = conn.prepareStatement("UPDATE `delivery` SET `status` = ? WHERE `orderID` = ?");
						ps1.setString(1,delivery.getStatus());
						ps1.setInt(2,delivery.getOrderNumber());
						ps1.executeUpdate();
						ps1.close();
				 				
				 		
				 	}
				 					
					
			 }
	 		
			
		}  catch (SQLException e) {
			
			
			msg.setData("Falied");
			e.printStackTrace();
			return msg;
		}
		
		msg.setData("Success");
		return msg;
	
	}

/**
 * Update inventory.
 * updates the inventory(adds to the inventory) after the logistics worker executed an inventory call.
 * @param clientMsg the client msg
 * @return the message
 */
public static Message updateInventory(Message clientMsg) {
		
		PreparedStatement ps1;
		Message msg = new Message();
		ArrayList<ItemInDevice> ItemInDeviceList = (ArrayList<ItemInDevice>)(clientMsg.getData());
		
		
		msg.setTask("Update Inventory");
		
		try {
			
			 for (ItemInDevice itemInDevice : ItemInDeviceList) {
					
					ps1 = conn.prepareStatement("UPDATE `item_in_device` SET thresholdNotify ='0', `amount` = `amount` + ?,`status` = 'AVAILABLE' WHERE `serialNumber` = ? AND `deviceID` = ?");
					ps1.setInt(1,itemInDevice.getAddedAmount());
					ps1.setInt(2,itemInDevice.getItem().getSerialNumber());
					ps1.setInt(3,itemInDevice.getDevice().getDeviceID());
					ps1.executeUpdate();
					ps1.close();
					
					
			 }
	 		
			
		}  catch (SQLException e) {
			
			
			msg.setData("Falied");
			e.printStackTrace();
			return msg;
		}
		
		msg.setData("Success");
		return msg;
	
	}
	
	
	
	
	
	
	/**
	 * Close inventory call.
	 *
	 * @param clientMsg the client msg
	 * @return the message
	 */
	public static Message closeInventoryCall(Message clientMsg) {
		
		PreparedStatement ps1;
		Message msg = new Message();
		InventoryCall inventoryCall = (InventoryCall)(clientMsg.getData());
		
		msg.setTask("Close Inventory Call");
		
		try {
			
			
					ps1 = conn.prepareStatement("UPDATE `inventory_call` SET `status` =  'DONE' WHERE `callID` = ?");
					ps1.setInt(1,inventoryCall.getCallID());
					ps1.executeUpdate();
					ps1.close();
					
	 		
			
		}  catch (SQLException e) {
			
			
			msg.setData("Falied");
			e.printStackTrace();
			return msg;
		}
		
		msg.setData("Success");
		return msg;
	
	}

/**
 * Gets a list of logistics employees.
 *
 * @param clientMsg the client msg
 * @return the logistics employee
 */
public static Message getLogisticsEmployee(Message clientMsg) {
		
		PreparedStatement ps1;
		Message msg = new Message();
		
		Employee employee = (Employee)(clientMsg.getData());
		ArrayList<Employee> employeeList = new ArrayList<>();
		msg.setTask("Get Logistics Employee");
		
		try {
		
			ps1 = conn.prepareStatement("SELECT `username`,`employeeID` FROM employees WHERE `position` = 'logistics employee'");
			ResultSet rs = ps1.executeQuery();
			
			while(rs.next())
	 		{
	 			
	 			Employee employeeNew = new Employee();
	 		
	 			employeeNew.setUserName(rs.getString(1));
	 			employeeNew.setEmployeeID(rs.getInt(2));

	 			employeeList.add(employeeNew);	 
				
			} 
	 		
			rs.close();
	
			
		}  catch (SQLException e) {
			
			
			msg.setData("Falied");
			e.printStackTrace();
			return msg;
		}
		
		msg.setData(employeeList);
		return msg;
	
	}

	
	
	/**
	 * Creates an inventory call.
	 *
	 * @param clientMsg the client msg
	 * @return the message
	 */
	public static Message createInventoryCall(Message clientMsg) {//change salID TO AUTO INCREMENT IN DATABASE
		Message msg = new Message();
		PreparedStatement ps1 = null;
		
		InventoryCall inventoryCall = (InventoryCall)(clientMsg.getData());
		msg.setTask("Create Inventory Call");
    	
    	try {
    		
			ps1 = conn.prepareStatement("INSERT INTO `inventory_call` (deviceID, employeeID, date,status,creatorUsername) VALUES (?, ?, ?, ?, ?);");
			ps1.setInt(1, inventoryCall.getDeviceID());
			ps1.setInt(2,inventoryCall.getEmployeeID());
			ps1.setTimestamp(3,inventoryCall.getDate());
			ps1.setString(4,inventoryCall.getStatus());
			ps1.setString(5,inventoryCall.getCreatorUsername());
			ps1.executeUpdate();
			ps1.close();
			
		} catch (SQLException e) {
			msg.setData("Failed");
			e.printStackTrace();
		}
    	
    	msg.setData("Success");
    	return msg;
    		
	}
	
	
	/**
	 * Checks if a user and  exists in db.
	 *
	 * @param usern the usern
	 * @param passw the passw
	 * @return true, if is user and pass exist
	 */
	public static boolean isUserAndPassExist(String usern,String passw)
{

	try 
	{
		
		PreparedStatement ps1 = conn.prepareStatement("SELECT `username` , `password` FROM users WHERE username = ? AND password = ?");
		ps1.setString(1,usern);
		ps1.setString(2,passw);
		ResultSet rs = ps1.executeQuery();
		rs.next();
 		
		try {
			rs.getString(1);
		} catch(SQLException e) {
			
			ps1.close();
			return false;
		}
		
	} catch (SQLException e) {
		
		e.printStackTrace();
		return false;
	}
	
	return true;
}

/**
 * User logout.
 *
 * @param clientMsg the client msg
 * @return the message
 */
public static Message userLogout(Message clientMsg) 
{
	Message msg = new Message();
	User user1=new User();
    user1=(User) clientMsg.getData();
	int usernamepass = 0;
	try 
	{
		
		PreparedStatement ps1 = conn.prepareStatement("UPDATE users SET `isLogged` = false WHERE username = ?");
 		ps1.setString(1,user1.getUserName());
		ps1.executeUpdate();
		ps1.close();
		
	} catch (SQLException e) {
		
		msg.setData(usernamepass);
		msg.setTask("Error Update user to logged out");
		e.printStackTrace();
		return msg;
	}
	
	msg.setData(usernamepass);
	msg.setTask("User Logout Success");
	return msg;

}

/**
 * imports a report of a specific type from the DB.
 *
 * @param clientMsg the client msg
 * @return the report
 */
public static Message getReport(Message clientMsg) 
{
	Message msg = new Message();
	Report repo=new Report();
	Report repoReturn=null;
	repo=(Report) clientMsg.getData();

	try 
	{
		PreparedStatement ps1;
		if(repo.getType().equals("order"))
		   ps1 = conn.prepareStatement("SELECT `reportID`,`year` , `month`,`region`,`totalOrders` , `topSeller`,`leastSeller` FROM orders_report WHERE year = ? AND month = ? AND region = ?");
		else 
		 if(repo.getType().equals("customer"))
		   ps1 = conn.prepareStatement("SELECT * FROM customer_report WHERE year = ? AND month = ? AND region = ?");
		else
			ps1 = conn.prepareStatement("SELECT * FROM invetory_report WHERE year = ? AND month = ? AND region = ? AND deviceID = ?");
		ps1.setInt(1,repo.getYear());
 		ps1.setInt(2,repo.getMonth());
 		ps1.setString(3,repo.getRegion());
 		if(repo.getType().equals("inventory"))
 	 		ps1.setInt(4,repo.getDeviceID());
 		ResultSet rs = ps1.executeQuery();;
 		while(rs.next()){
 			repoReturn= new	Report(rs.getInt("reportID"),rs.getInt("year"),rs.getInt("month"),rs.getString("region")); 
 			if(repo.getType().equals("order"))
 			{
 				repoReturn.settotalOrders(rs.getInt("totalOrders"));
 				repoReturn.settopSeller(rs.getString("topSeller"));
 				repoReturn.setleastSeller(rs.getString("leastSeller"));
 			}
 			else 
 				 if(repo.getType().equals("customer"))
 				 {
 	 				repoReturn.setactivityLevel(rs.getInt("activityLevel"));
 	 				repoReturn.setorderAverage(rs.getInt("orderAverage"));
 	 				repoReturn.setnumberOfClientOrdered(rs.getInt("numberOfClientOrdered")); 
 				 }
 				 else
 			 		{
 	 				repoReturn.setDeviceID(rs.getInt("deviceID"));
 	 				repoReturn.setthreshold(rs.getInt("threshold"));
 	 				repoReturn.setitemsInShortage(rs.getInt("itemsInShortage"));
 	 				repoReturn.setitemsNotInShortage(rs.getInt("itemsNotInShortage"));
 			 		}
 		}
 		ps1.close();
		
	} catch (SQLException e) {
		
		msg.setData(repoReturn);
		msg.setTask("Report Not Found");
		e.printStackTrace();
		return msg;
	}
	
	
	
	ArrayList<Report> reportList = new ArrayList<>();
	PreparedStatement ps1;
	if(repo.getType().equals("order")) {
	try 
	{
		ps1 = conn.prepareStatement("SELECT `deviceName`,`totalOrders` FROM ordergeneratereport WHERE year = ? AND month = ? AND region = ?;");
		ps1.setInt(1,repo.getYear());
 		ps1.setInt(2,repo.getMonth());
 		ps1.setString(3,repo.getRegion());
 		ResultSet rs = ps1.executeQuery();
 		while(rs.next())
 		{
 			reportList.add(new Report(rs.getString("deviceName"),rs.getInt("totalOrders")));
		} 
		rs.close();
		
	} catch (SQLException e) {e.printStackTrace();}
	}
	else
		if(repo.getType().equals("customer"))
		{
			try 
			{
				ps1 = conn.prepareStatement("SELECT * FROM customergeneratereport WHERE year = ? AND month = ? AND region = ?;");
				ps1.setInt(1,repo.getYear());
		 		ps1.setInt(2,repo.getMonth());
		 		ps1.setString(3,repo.getRegion());
		 		ResultSet rs = ps1.executeQuery();
		 		while(rs.next())
		 		{
		 			repoReturn.setbelow3orders(rs.getInt("below3orders"));
		 			repoReturn.set4to8orders(rs.getInt("4to8orders"));
		 			repoReturn.set9to12orders(rs.getInt("9to12orders"));
		 			repoReturn.set13to15orders(rs.getInt("13to15orders"));
		 			repoReturn.set16oraboveorders(rs.getInt("16oraboveorders"));
				} 
				rs.close();
				
			} catch (SQLException e) {e.printStackTrace();}
		}
		else
		{
			try 
			{
				ps1 = conn.prepareStatement("SELECT `item`,`quantity` FROM inventorygeneratereport WHERE year = ? AND month = ? AND deviceID = ?;");
				ps1.setInt(1,repo.getYear());
		 		ps1.setInt(2,repo.getMonth());
		 		ps1.setInt(3,repo.getDeviceID());
		 		ResultSet rs = ps1.executeQuery();
		 		while(rs.next())
		 		{
		 			reportList.add(new Report(rs.getString("item"),rs.getInt("quantity")));
				} 
				rs.close();
				
			} catch (SQLException e) {e.printStackTrace();}
		}
	msg.setData(repoReturn);
	if(repoReturn==null)
		msg.setTask("Report Not Found");
	else
	{
		repoReturn.setreportList(reportList);
	    msg.setTask("get Report Success");
	}
	return msg;
}


/**
 * Request to pick up order.
 * checks in the DB if an order is a pick up(take away) order that was not collected yet and belongs to a
 * specific device and customer.
 * updates the pickup collected status in the DB if necessary.
 * @param clientMsg the client msg
 * @return the message
 */
public static Message RequestToPickUpOrder(Message clientMsg) {
	Message msg=new Message();
	ArrayList<Object> OrderPickUpDetails= (ArrayList<Object>) clientMsg.getData();
	User user= (User) OrderPickUpDetails.get(0);
	String OrderNumber = (String) OrderPickUpDetails.get(1);
	Device device = (Device) OrderPickUpDetails.get(2);
	try {
		PreparedStatement ps = conn.prepareStatement("Select t.* from takeaway t where orderID=? and collected=?");//check if pending takeaway order
		ps.setString(1, OrderNumber);
		ps.setBoolean(2, false);
		ResultSet rs = ps.executeQuery();
		if(!rs.next()) {
			msg.setTask("Pickup Request");
			msg.setData("The order number entered is not a pending pick up order");
			ps.close();
			return msg;
		}
		ps.close();	
		ps = conn.prepareStatement("Select t.* from orders t where orderID=? and deviceID=? and customerID=?;");//check if the order belongs to device and logged customer
		ps.setString(1, OrderNumber);
		ps.setInt(2, device.getDeviceID());
		ps.setString(3, user.getIDNumber());
		rs = ps.executeQuery();
		if(!rs.next()) {
			msg.setTask("Pickup Request");
			msg.setData("The order number entered belongs to another device and/or customer");
			ps.close();
			return msg;
		}
		ps.close();
		ps = conn.prepareStatement("UPDATE takeaway SET collected = true where orderID=?");//check if the order belongs to device and logged customer
		ps.setString(1, OrderNumber);
		ps.executeUpdate();
		ps.close();
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		msg.setTask("Pickup Request");
		msg.setData("pickup request failed");
		e.printStackTrace();
		return msg;
	}
	
	msg.setTask("Pickup Request");
	msg.setData("pickup success");
	return msg;
}



/**
 * Request to install.
 * import installation data from DB for a new client- install simulation.
 *
 * @param clientMsg the client msg
 * @return the message
 */
public static Message RequestToInstall(Message clientMsg) {
	Message msg=new Message();
	ArrayList<Object> InstallDetails= new ArrayList<Object>();

	try {
		Statement st= conn.createStatement();
		ResultSet rs = st.executeQuery("Select * from app_installation");
		if(rs.next()) {
			InstallDetails.add(rs.getString(1));
			InstallDetails.add(rs.getInt(2));
		}
		st.close();	
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		return msg;
	}
	
	msg.setTask("Install");
	msg.setData(InstallDetails);
	return msg;
}

/**
 * Creates the order report.
 *
 * @param year the year
 * @param month the month
 */
public static void CreateOrderReport(int year,int month) 
{
	String startDate=year+"-"+month+"-01 00:00:00";
	String EndDate=year+"-"+month+"-31 23:59:59";
	String region = null,deviceName=null,topseller = null,leastseller = null;
	int totalorders=0,deviceID=0,top=0,least=10000,orderbd=0;
	try 
	{
		PreparedStatement ps1;
		ps1 = conn.prepareStatement("SELECT * FROM ordergeneratereport WHERE year = ? AND month = ?");
		ps1.setInt(1,year);
		ps1.setInt(2,month);
 		ResultSet rs = ps1.executeQuery();
		if(rs.next())
			return;
 		ps1.close();
	} catch (SQLException e) {
		e.printStackTrace();
	}
	try 
	{
		PreparedStatement ps1;
		PreparedStatement ps2;
		PreparedStatement ps3;
		PreparedStatement ps4;
		//ps1 = conn.prepareStatement("SELECT * FROM orders WHERE createdTime > ? AND createdTime < ? AND deviceID in (SELECT deviceID FROM device WHERE region = ?)");
		ps1 = conn.prepareStatement("SELECT * FROM orders WHERE createdTime > ? AND createdTime < ?");
		ps1.setString(1,startDate);
 		ps1.setString(2,EndDate);
 		ResultSet rs = ps1.executeQuery();
 		while(rs.next())
 		{ 
     		ps2 = conn.prepareStatement("SELECT * FROM device WHERE deviceID = ?");
    		ps2.setInt(1,rs.getInt("deviceID"));
     		ResultSet rs1 = ps2.executeQuery();
     		rs1.next();
     		ps3 = conn.prepareStatement("SELECT * FROM ordergeneratereport WHERE deviceID = ? AND year = ? AND month = ?");
    		ps3.setInt(1,rs.getInt("deviceID"));
    		deviceID=rs.getInt("deviceID");
 			ps3.setInt(2,year);
 			ps3.setInt(3,month);
     		ResultSet rs2 = ps3.executeQuery();
     		region=rs1.getString("region");
     		deviceName=rs1.getString("location");
     		if(rs2.next())
     		{
     			ps4 = conn.prepareStatement("UPDATE ordergeneratereport SET `totalOrders` = ? WHERE deviceID = ? AND year = ? AND month = ?");
     			ps4.setInt(1,rs2.getInt("totalOrders")+1);
     			ps4.setInt(2,rs2.getInt("deviceID"));
     			ps4.setInt(3,year);
     			ps4.setInt(4,month);
     			ps4.executeUpdate();
     		}
     		else
     		{
     			ps4 = conn.prepareStatement("INSERT INTO ordergeneratereport (deviceID, totalOrders, region,year,month,deviceName) VALUES (?, ?, ?, ?, ?,?);");
     			ps4.setInt(1,rs.getInt("deviceID"));
     			ps4.setInt(2,1);
     			ps4.setString(3,region);
     			ps4.setInt(4,year);
     			ps4.setInt(5,month);
     			ps4.setString(6,deviceName);
     			ps4.executeUpdate();
     	 		ps4.close();
     		}
     		ps2.close();
     		ps3.close();
 	 		ps4.close();
		} 
 		ps1.close();
	} catch (SQLException e) {
		e.printStackTrace();
	}
	try 
	{
		PreparedStatement ps1;
		PreparedStatement ps2;
		ps1 = conn.prepareStatement("SELECT * FROM ordergeneratereport WHERE region = ? AND year = ? AND month = ?");
		ps1.setString(1,region);
		ps1.setInt(2,year);
		ps1.setInt(3,month);
 		ResultSet rs = ps1.executeQuery();
 		while(rs.next())
 		{ 
     		totalorders=totalorders+rs.getInt("totalOrders");
            if(rs.getInt("totalOrders")>top)
            {
            	top=rs.getInt("totalOrders");
            	topseller=rs.getString("deviceName");
            	orderbd=rs.getInt("deviceID");
            }
            if(rs.getInt("totalOrders")<least)
            {
            	least=rs.getInt("totalOrders");
            	leastseller=rs.getString("deviceName");
            }
		} 
 		ps2 = conn.prepareStatement("INSERT INTO orders_report (region,year,month,orderByDevice,topSeller,leastSeller,totalOrders) VALUES (?, ?, ?, ?, ?, ?, ?);");
		ps2.setString(1,region);
		ps2.setInt(2,year);
		ps2.setInt(3,month);
		ps2.setInt(4,orderbd);
		ps2.setString(5,topseller);
		ps2.setString(6,leastseller);
		ps2.setInt(7,totalorders);
 		ps2.executeUpdate();
 		ps1.close();
 		ps2.close();
	} catch (SQLException e) {
		e.printStackTrace();
	}
}

/**
 * Creates the customer report.
 *
 * @param year the year
 * @param month the month
 */
public static void CreateCustomerReport(int year,int month) 
{
	String startDate=year+"-"+month+"-01 00:00:00";
	String EndDate=year+"-"+month+"-31 23:59:59";
	int clients=0,orderavg=0,count=0,c1=0,b3=0,t48=0,t912=0,t1315=0,t16=0;
	try 
	{
		PreparedStatement ps1;
		ps1 = conn.prepareStatement("SELECT * FROM customergeneratereport WHERE year = ? AND month = ?");
		ps1.setInt(1,year);
		ps1.setInt(2,month);
 		ResultSet rs = ps1.executeQuery();
		if(rs.next())
			return;
 		ps1.close();
	} catch (SQLException e) {
		e.printStackTrace();
	}
	try 
	{
		PreparedStatement ps1;
		PreparedStatement ps2;
		PreparedStatement ps4;
		ps1 = conn.prepareStatement("SELECT * FROM customers");
 		ResultSet rs = ps1.executeQuery();
			ps4 = conn.prepareStatement("INSERT INTO customergeneratereport (year, month, below3orders,4to8orders,9to12orders,13to15orders,16oraboveorders,region) VALUES (?, ?, ?, ?, ?,?,?,?);");
			ps4.setInt(1,year);
			ps4.setInt(2,month);
			ps4.setInt(3,0);
			ps4.setInt(4,0);
			ps4.setInt(5,0);
			ps4.setInt(6,0);
			ps4.setInt(7,0);
			ps4.setString(8,"region");
			ps4.executeUpdate();
	 		ps4.close();
 		while(rs.next())
 		{ 
 			clients++;
     		ps2 = conn.prepareStatement("SELECT COUNT(customerID) AS rowcount FROM orders WHERE customerID = ? AND createdTime > ? AND createdTime < ?");
     		ps2.setString(1,rs.getString("id"));
    		ps2.setString(2,startDate);
     		ps2.setString(3,EndDate);
     		ResultSet rs1 = ps2.executeQuery();
     		rs1.next();
     		c1=rs1.getInt("rowcount");
 			count=count+c1;
            if(c1<4)
            {
            	b3++;
    			ps4 = conn.prepareStatement("UPDATE customergeneratereport SET `below3orders` = ? WHERE year = ? AND month = ?");
    			ps4.setInt(1,b3);
    			ps4.setInt(2,year);
    			ps4.setInt(3,month);
    			ps4.executeUpdate();
    	 		ps4.close();
            }
            else
                if(c1<9)
                {
                	t48++;
        			ps4 = conn.prepareStatement("UPDATE customergeneratereport SET `4to8orders` = ? WHERE year = ? AND month = ?");
        			ps4.setInt(1,t48);
        			ps4.setInt(2,year);
        			ps4.setInt(3,month);
        			ps4.executeUpdate();
        	 		ps4.close();
                }
                else
                    if(c1<13)
                    {
                    	t912++;
            			ps4 = conn.prepareStatement("UPDATE customergeneratereport SET `9to12orders` = ? WHERE year = ? AND month = ?");
            			ps4.setInt(1,t912);
            			ps4.setInt(2,year);
            			ps4.setInt(3,month);
            			ps4.executeUpdate();
            	 		ps4.close();
                    }
                    else
                        if(c1<15)
                        {
                        	t1315++;
                			ps4 = conn.prepareStatement("UPDATE customergeneratereport SET `13to15orders` = ? WHERE year = ? AND month = ?");
                			ps4.setInt(1,t1315);
                			ps4.setInt(2,year);
                			ps4.setInt(3,month);
                			ps4.executeUpdate();
                	 		ps4.close();
                        }
                        else
                        {
                        	t16++;
                			ps4 = conn.prepareStatement("UPDATE customergeneratereport SET `16oraboveorders` = ? WHERE year = ? AND month = ?");
                			ps4.setInt(1,t16);
                			ps4.setInt(2,year);
                			ps4.setInt(3,month);
                			ps4.executeUpdate();
                	 		ps4.close();
                        }
            	
     		ps2.close();
		} 
 		ps1.close();
	} catch (SQLException e) {
		e.printStackTrace();
	}
	try 
	{
		orderavg=count/clients;
		PreparedStatement ps2;
 		ps2 = conn.prepareStatement("INSERT INTO customer_report (region,year,month,deviceID,activityLevel,orderAverage,numberOfClientOrdered) VALUES (?,?, ?, ?, ?, ?,?);");
		ps2.setString(1, "region");
 		ps2.setInt(2,year);
		ps2.setInt(3,month);
		ps2.setInt(4,0);
		ps2.setInt(5,(b3+t48+t912+t1315+t16));
		ps2.setInt(6,orderavg);
		ps2.setInt(7,clients);
 		ps2.executeUpdate();
 		ps2.close();
	} catch (SQLException e) {
		e.printStackTrace();
	}
}

/**
 * Creates the inventory report.
 *
 * @param year the year
 * @param month the month
 */
public static void CreateInventoryReport(int year,int month) 
{
	String startDate=year+"-"+month+"-01 00:00:00";
	String EndDate=year+"-"+month+"-31 23:59:59";
	String region="";
	int inshortage=0,notinshortage=0,orderavg=0,deviceID=0;
	try 
	{
		PreparedStatement ps1;
		ps1 = conn.prepareStatement("SELECT * FROM inventorygeneratereport WHERE year = ? AND month = ?");
		ps1.setInt(1,year);
		ps1.setInt(2,month);
 		ResultSet rs = ps1.executeQuery();
		if(rs.next())
			return;
 		ps1.close();
	} catch (SQLException e) {
		e.printStackTrace();
	}
	try 
	{
		PreparedStatement ps1;
		PreparedStatement ps2;
		PreparedStatement ps3;
		PreparedStatement ps4;
		PreparedStatement ps5;
		PreparedStatement ps6;
		ps1 = conn.prepareStatement("SELECT * FROM device");
 		ResultSet rs = ps1.executeQuery();
 		ResultSet rs1;
 		while(rs.next())
 		{ 
 	 		ps6 = conn.prepareStatement("SELECT COUNT(serialNumber) AS rowcount FROM item_in_device WHERE deviceID = ?");
 	 		ps6.setInt(1, rs.getInt("deviceID"));
 	 		ResultSet rs2 = ps6.executeQuery();
 	 		rs2.next();
 	 		inshortage=rs2.getInt("rowcount");
 	 		ps6.close();
			ps2 = conn.prepareStatement("SELECT * FROM item_in_device WHERE deviceID = ? AND status = 'AVAILABLE'");
			ps2.setInt(1, rs.getInt("deviceID"));
	 		rs1 = ps2.executeQuery();
	 		while(rs1.next())
	 		{
	 			if(rs1.getInt("amount")>3)
	 			notinshortage++;
				ps4 = conn.prepareStatement("INSERT INTO inventorygeneratereport (year, month, region,item,quantity,deviceID) VALUES (?, ?, ?, ?, ?,?);");
				ps4.setInt(1,year);
				ps4.setInt(2,month);
				ps4.setString(3,rs.getString("region"));
				region=rs.getString("region");
				ps3 = conn.prepareStatement("SELECT * FROM item WHERE serialNumber = ?");
				ps3.setInt(1, rs1.getInt("serialNumber"));
		 		ResultSet rs3 = ps3.executeQuery();
		 		rs3.next();
				ps4.setString(4,rs3.getString("name"));
				ps4.setInt(5,rs1.getInt("amount"));
				ps4.setInt(6,rs.getInt("deviceID"));
				deviceID=rs.getInt("deviceID");
				ps4.executeUpdate();
				ps3.close();
		 		ps4.close();
	 		}   
	 		inshortage=Math.abs(notinshortage-inshortage);
			ps5 = conn.prepareStatement("INSERT INTO invetory_report (region,year,month,deviceID,threshold,items,itemsInShortage,itemsNotInShortage) VALUES (?,?, ?, ?, ?, ?,?,?);");
			ps5.setString(1, region);
			ps5.setInt(2,year);
			ps5.setInt(3,month);
			ps5.setInt(4,deviceID);
			ps5.setInt(5,4);
			ps5.setString(6,"items");
			ps5.setInt(7,inshortage);
			ps5.setInt(8,notinshortage);
			ps5.executeUpdate();
			ps5.close();
	 		ps2.close();
	 		notinshortage=0;
	 		inshortage=0;
		} 

 		ps1.close();
	} catch (SQLException e) {
		e.printStackTrace();
	}
}

/**
 * Gets the current device.
 *
 * @param clientMsg the client msg
 * @return the current device
 */
public static Message getCurrentDevice(Message clientMsg) {//added function
	
	PreparedStatement ps1;
	Message msg = new Message();
	Device device=new Device();
	//Employee employee = (Employee)(clientMsg.getData());
	msg.setTask("Get Device");
	
	try {
		ps1 = conn.prepareStatement("SELECT *  FROM `device` where deviceID=?");
		ps1.setInt(1, (int)(clientMsg.getData()));
		ResultSet rs = ps1.executeQuery();
		if(rs.next())
 		{
 			
 			device.setDeviceID(rs.getInt(1));
 			device.setLocation(rs.getString(2));
 			device.setThreshold(rs.getInt(3));
 			device.setRegion(rs.getString(4));
			
		} 
 		
		rs.close();

		
	}  catch (SQLException e) {
		
		
		msg.setData("Falied");
		e.printStackTrace();
		return msg;
	}
	
	msg.setData(device);
	return msg;

}

}

	

	



