package entities;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;


/**
 * The Class Order.
 * entity class for keeping an order info.
 */
public class Order implements Serializable{
	
	private int DeviceID,orderNumber;
	
	private float totalAmount;
	
	private Timestamp createdTime; 
	
	private List<ItemInOrder> itemsInOrder=new ArrayList<ItemInOrder>();
	
	private Payment payment;
	
	

	/**
	 * Instantiates a new order.
	 *
	 * @param deviceID the device ID
	 * @param orderNumber the order number
	 * @param totalAmount the total amount
	 * @param createdTime the created time
	 * @param itemsInOrder the items in order
	 * @param payment the payment
	 */
	public Order(int deviceID, int orderNumber, float totalAmount, Timestamp createdTime,
			List<ItemInOrder> itemsInOrder, Payment payment) {
		DeviceID = deviceID;
		this.orderNumber = orderNumber;
		this.totalAmount = totalAmount;
		this.createdTime = createdTime;
		this.itemsInOrder = itemsInOrder;
		this.payment = payment;
	}

	/**
	 * Instantiates a new order.
	 */
	public Order() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Adds the item.
	 *
	 * @param item the item
	 * @param order the order
	 * @param amount the amount
	 */
	public void addItem(ItemInDevice item, Order order, int amount) {//add association class instance
		itemsInOrder.add(new ItemInOrder(item,this,amount));//need to check if the item was already added;
	}
	
	/**
	 * Adds the payment.
	 *
	 * @param order the order
	 * @param customer the customer
	 * @param totalAmount the total amount
	 * @param debitDate the debit date
	 */
	public void addPayment(Order order, Customer customer, float totalAmount, Timestamp debitDate) {//add association class instance
		payment=new Payment(this,customer,totalAmount,debitDate);
	}
	
	/**
	 * Adds the payment.
	 *
	 * @param payment the payment
	 */
	public void addPayment(Payment payment) {//added function
		this.payment=payment;
	}

	/**
	 * Gets the device ID.
	 *
	 * @return the device ID
	 */
	public int getDeviceID() {
		return DeviceID;
	}

	/**
	 * Sets the device ID.
	 *
	 * @param deviceID the new device ID
	 */
	public void setDeviceID(int deviceID) {
		DeviceID = deviceID;
	}

	/**
	 * Gets the order number.
	 *
	 * @return the order number
	 */
	public int getOrderNumber() {
		return orderNumber;
	}

	/**
	 * Sets the order number.
	 *
	 * @param orderNumber the new order number
	 */
	public void setOrderNumber(int orderNumber) {
		this.orderNumber = orderNumber;
	}

	/**
	 * Gets the total amount.
	 *
	 * @return the total amount
	 */
	public float getTotalAmount() {
		return totalAmount;
	}

	/**
	 * Sets the total amount.
	 *
	 * @param totalAmount the new total amount
	 */
	public void setTotalAmount(float totalAmount) {
		this.totalAmount = totalAmount;
	}

	/**
	 * Gets the created time.
	 *
	 * @return the created time
	 */
	public Timestamp getCreatedTime() {
		return createdTime;
	}

	/**
	 * Sets the created time.
	 *
	 * @param createdTime the new created time
	 */
	public void setCreatedTime(Timestamp createdTime) {
		this.createdTime = createdTime;
	}

	/**
	 * Gets the items in order.
	 *
	 * @return the items in order
	 */
	public List<ItemInOrder> getItemsInOrder() {
		return itemsInOrder;
	}

	/**
	 * Sets the items in order.
	 *
	 * @param itemsInOrder the new items in order
	 */
	public void setItemsInOrder(List<ItemInOrder> itemsInOrder) {
		this.itemsInOrder = itemsInOrder;
	}

	/**
	 * Gets the payment.
	 *
	 * @return the payment
	 */
	public Payment getPayment() {
		return payment;
	}

	/**
	 * Sets the payment.
	 *
	 * @param payment the new payment
	 */
	public void setPayment(Payment payment) {
		this.payment = payment;
	}

}
