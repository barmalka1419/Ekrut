package entitiesController;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ClientControllers.CartPatternController;
import ClientControllers.OrderingController;
import ClientControllers.PickUpController;
import client.ChatClient;
import entities.Customer;
import entities.Delivery;
import entities.Member;
import entities.Order;
import entities.Payment;
import entities.TakeAway;

/**
 * The Class UserOrderController.
 * class for keeping the user's current order information
 */
public class UserOrderController {
	
	private Order order=new Order();
	
	private String orderType="local";
	
	/**
	 * Gets the order.
	 *
	 * @return the order
	 */
	public Order getOrder() {
		
		return order;
	}
	
	/**
	 * Sets the order.
	 *
	 * @param order the new order
	 */
	public void setOrder(Order order) {
		this.order = order;
	}
	
	/**
	 * Creates the payment.
	 *  creates a payment object for the user's order
	 * @param payment the payment
	 * @param total the total
	 * @return the payment
	 */
	public Payment createPayment(Payment payment, float total) {
		payment.setOrder(order);
		payment.setTotalAmount(total);
		
		if(ChatClient.userloginController.getUser() instanceof Member) {
			payment.setCustomer((Member)ChatClient.userloginController.getUser());
		}
		else {
			payment.setCustomer((Customer)ChatClient.userloginController.getUser());

		}
		Date date = new Date();  
        Timestamp ts=new Timestamp(date.getTime());  
        payment.setDebitDate(ts);
        order.setCreatedTime(ts);
		return payment;
	}
	
	/**
	 * Save order.
	 * prepares the user's order for saving in the database
	 */
	public void saveOrder() {
		if(orderType.equals("pick up")) {
			order=new TakeAway();
		}
		if(orderType.equals("delivery")) {
			Delivery newDel= new Delivery();
			newDel.setAddress(ChatClient.deliveryCont.getDelivery().getAddress());
			newDel.setNotes(ChatClient.deliveryCont.getDelivery().getNotes());
			order=newDel;
		}
		List<CartPatternController> cart=ChatClient.cartController.getOrderingController().cartLines;
		for( CartPatternController c : cart) {//ADD ITEMS TO ORDER
			order.addItem(c.getCartItem().getItemsInDevice().get(0), order, c.getCartItemQuantity());
		}
		float total= OrderingController.TotalPrice;
		order.setTotalAmount(total);
		order.setDeviceID(ChatClient.MyDevice.getDeviceId());
		order.addPayment(createPayment(new Payment(), total));
	}
	
	/**
	 * Gets the order type.
	 *
	 * @return the order type
	 */
	public String getOrderType() {
		return orderType;
	}
	
	/**
	 * Sets the order type.
	 *
	 * @param orderType the new order type
	 */
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}


}
