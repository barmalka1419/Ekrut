package entities;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * The Class Customer.
 * entity class for keeping a customer's information
 * extends User class
 */
public class Customer  extends User{
	
	private static final long serialVersionUID = 2589491211308269216L;
	
	private String creditCardNumber;
	
	private boolean approvedCostumer;
	
	private List<Payment> orders=new ArrayList<Payment>();
	
	/**
	 * Instantiates a new customer.
	 */
	public Customer() {
	}
	
	/**
	 * Instantiates a new customer.
	 *
	 * @param userName the user name
	 * @param lastName the last name
	 * @param firstName the first name
	 * @param password the password
	 * @param iDNumber the i D number
	 * @param email the email
	 * @param phoneNumber the phone number
	 * @param isLogged the is logged
	 * @param userPermission the user permission
	 * @param creditCardNumber the credit card number
	 * @param approvedCostumer the approved costumer
	 * @param orders the orders
	 */
	public Customer(String userName, String lastName, String firstName, String password, String iDNumber, String email,
			String phoneNumber, boolean isLogged, String userPermission, String creditCardNumber,
			boolean approvedCostumer, List<Payment> orders) {
		super(userName, lastName, firstName, password, iDNumber, email, phoneNumber, isLogged, userPermission);
		this.creditCardNumber = creditCardNumber;
		this.approvedCostumer = approvedCostumer;
		this.orders = orders;
	}
	
	/**
	 * Instantiates a new customer.
	 *
	 * @param userName the user name
	 * @param lastName the last name
	 * @param firstName the first name
	 * @param password the password
	 * @param iDNumber the i D number
	 * @param email the email
	 * @param phoneNumber the phone number
	 * @param isLogged the is logged
	 * @param userPermission the user permission
	 * @param creditCardNumber the credit card number
	 * @param approvedCostumer the approved costumer
	 */
	public Customer(String userName, String lastName, String firstName, String password, String iDNumber, String email,
			String phoneNumber, boolean isLogged, String userPermission, String creditCardNumber,
			boolean approvedCostumer) {
		super(userName, lastName, firstName, password, iDNumber, email, phoneNumber, isLogged, userPermission);
		this.creditCardNumber = creditCardNumber;
		this.approvedCostumer = approvedCostumer;
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
		orders.add(new Payment(order,this,totalAmount,debitDate));
	}

	/**
	 * Gets the credit card number.
	 *
	 * @return the credit card number
	 */
	public String getCreditCardNumber() {
		return creditCardNumber;
	}

	/**
	 * Sets the credit card number.
	 *
	 * @param creditCardNumber the new credit card number
	 */
	public void setCreditCardNumber(String creditCardNumber) {
		this.creditCardNumber = creditCardNumber;
	}

	/**
	 * Checks if is approved costumer.
	 *
	 * @return true, if is approved costumer
	 */
	public boolean isApprovedCostumer() {
		return approvedCostumer;
	}

	/**
	 * Sets the approved costumer.
	 *
	 * @param approvedCostumer the new approved costumer
	 */
	public void setApprovedCostumer(boolean approvedCostumer) {
		this.approvedCostumer = approvedCostumer;
	}

	/**
	 * Gets the orders.
	 *
	 * @return the orders
	 */
	public List<Payment> getOrders() {
		return orders;
	}

	/**
	 * Sets the orders.
	 *
	 * @param orders the new orders
	 */
	public void setOrders(List<Payment> orders) {
		this.orders = orders;
	}
	
	

}
