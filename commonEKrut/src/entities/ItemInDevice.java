package entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * The Class ItemInDevice.
 * an entity class for storing item in device info.
 * an association class between item and device entity classes.
 */
public class ItemInDevice implements Serializable {
	
	private Item item;
	
	private Device device;
	
	private int amount,addedAmount;
	
	private Status status;
	
	private List<ItemInOrder> orders=new ArrayList<ItemInOrder>();
	
	/**
	 * Instantiates a new item in device.
	 */
	public ItemInDevice() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Gets the added amount.
	 *
	 * @return the added amount
	 */
	public int getAddedAmount() {
		return addedAmount;
	}

	/**
	 * Sets the added amount.
	 *
	 * @param addedAmount the new added amount
	 */
	public void setAddedAmount(int addedAmount) {
		this.addedAmount = addedAmount;
	}

	/**
	 * Instantiates a new item in device.
	 *
	 * @param item the item
	 * @param device the device
	 * @param amount the amount
	 * @param status the status
	 */
	//constructor for relation between item and device
	public ItemInDevice(Item item, Device device, int amount, Status status) {
		this.item = item;
		this.device = device;
		this.amount = amount;
		this.status = status;
	}
	
	/**
	 * Adds the item.
	 *
	 * @param item the item
	 * @param order the order
	 * @param amount the amount
	 */
	public void addItem(ItemInDevice item, Order order, int amount) {//add association class instance
		orders.add(new ItemInOrder(this,order,amount));//need to check if the order was already added;
	}

	/**
	 * Gets the orders.
	 *
	 * @return the orders
	 */
	public List<ItemInOrder> getOrders() {
		return orders;
	}

	/**
	 * Sets the orders.
	 *
	 * @param orders the new orders
	 */
	public void setOrders(List<ItemInOrder> orders) {
		this.orders = orders;
	}

	/**
	 * Gets the item.
	 *
	 * @return the item
	 */
	public Item getItem() {
		return item;
	}

	/**
	 * Sets the item.
	 *
	 * @param item the new item
	 */
	public void setItem(Item item) {
		this.item = item;
	}

	/**
	 * Gets the device.
	 *
	 * @return the device
	 */
	public Device getDevice() {
		return device;
	}

	/**
	 * Sets the device.
	 *
	 * @param device the new device
	 */
	public void setDevice(Device device) {
		this.device = device;
	}

	/**
	 * Gets the amount.
	 *
	 * @return the amount
	 */
	public int getAmount() {
		return amount;
	}

	/**
	 * Sets the amount.
	 *
	 * @param amount the new amount
	 */
	public void setAmount(int amount) {
		this.amount = amount;
	}

	/**
	 * Gets the status.
	 *
	 * @return the status
	 */
	public Status getStatus() {
		return status;
	}

	/**
	 * Sets the status.
	 *
	 * @param status the new status
	 */
	public void setStatus(Status status) {
		this.status = status;
	}

	/**
	 * The Enum Status.
	 */
	public enum Status{
			NOT_AVAILABLE, 
			AVAILABLE
			};
}
