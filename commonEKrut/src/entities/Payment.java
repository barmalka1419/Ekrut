package entities;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * The Class Payment.
 * entity class for keeping an order payment info.
 * an association class between Order and Customer entity classes.
 */
public class Payment implements Serializable{
	
	private Order order;
	
	private Customer customer;
	
	private float totalAmount;
	
	private Timestamp debitDate;
	
	/**
	 * Instantiates a new payment.
	 */
	public Payment() {
		
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Instantiates a new payment.
	 *
	 * @param order the order
	 * @param customer the customer
	 * @param totalAmount the total amount
	 * @param debitDate the debit date
	 */
	public Payment(Order order, Customer customer, float totalAmount, Timestamp debitDate) {
		this.order = order;
		this.customer = customer;
		this.totalAmount = totalAmount;
		this.debitDate = debitDate;
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
	 * Gets the customer.
	 *
	 * @return the customer
	 */
	public Customer getCustomer() {
		return customer;
	}
	
	/**
	 * Sets the customer.
	 *
	 * @param customer the new customer
	 */
	public void setCustomer(Customer customer) {
		this.customer = customer;
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
	 * Gets the debit date.
	 *
	 * @return the debit date
	 */
	public Timestamp getDebitDate() {
		return debitDate;
	}
	
	/**
	 * Sets the debit date.
	 *
	 * @param debitDate the new debit date
	 */
	public void setDebitDate(Timestamp debitDate) {
		this.debitDate = debitDate;
	}

}
