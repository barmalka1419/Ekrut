package entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * The Class ItemInOrder.
 * entity class for keeping an item in order info.
 * an association class between ItemInDevice and Order entity classes.
 */
public class ItemInOrder implements Serializable{
	
	private ItemInDevice item;
	
	private Order order;
	
	private int amount;
	
	/**
	 * Instantiates a new item in order.
	 */
	public ItemInOrder() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Instantiates a new item in order.
	 *
	 * @param item the item
	 * @param order the order
	 * @param amount the amount
	 */
	public ItemInOrder(ItemInDevice item, Order order, int amount) {//constructor for the relation
		this.item = item;
		this.order = order;
		this.amount = amount;
	}
	
	/**
	 * Gets the item.
	 *
	 * @return the item
	 */
	public ItemInDevice getItem() {
		return item;
	}
	
	/**
	 * Sets the item.
	 *
	 * @param item the new item
	 */
	public void setItem(ItemInDevice item) {
		this.item = item;
	}
	
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

}
