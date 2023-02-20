package entities;

import java.io.Serializable;
import java.util.List;

/**
 * The Class Member.
 * entity class for keeping a member user info.
 */
public class Member extends Customer implements Serializable {
	
	private static final long serialVersionUID = 497075175800142741L;
	
	private int memberNumber;
	
	private boolean madePurcheas = false;
	
	/**
	 * Instantiates a new member.
	 */
	public Member() {}
	
	/**
	 * Instantiates a new member.
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
	 * @param memberNumber the member number
	 * @param firstPurchase the first purchase
	 */
	public Member(String userName, String lastName, String firstName, String password, String iDNumber, String email,
			String phoneNumber, boolean isLogged, String userPermission, String creditCardNumber,
			boolean approvedCostumer, List<Payment> orders, int memberNumber, boolean firstPurchase) {
		super(userName, lastName, firstName, password, iDNumber, email, phoneNumber, isLogged, userPermission,
				creditCardNumber, approvedCostumer, orders);
		this.memberNumber = memberNumber;
		this.madePurcheas = firstPurchase;
	}
	
	/**
	 * Instantiates a new member.
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
	 * @param memberNumber the member number
	 * @param firstPurchase the first purchase
	 */
	public Member(String userName, String lastName, String firstName, String password, String iDNumber, String email,
			String phoneNumber, boolean isLogged, String userPermission, String creditCardNumber,
			boolean approvedCostumer ,int memberNumber, boolean firstPurchase) {
		super(userName, lastName, firstName, password, iDNumber, email, phoneNumber, isLogged, userPermission,
				creditCardNumber, approvedCostumer);
		this.memberNumber = memberNumber;
		this.madePurcheas = firstPurchase;
	}
	
	
	/**
	 * Gets the member number.
	 *
	 * @return the member number
	 */
	public int getMemberNumber() {
		return memberNumber;
	}

	/**
	 * Sets the member number.
	 *
	 * @param memberNumber the new member number
	 */
	public void setMemberNumber(int memberNumber) {
		this.memberNumber = memberNumber;
	}

	/**
	 * Checks if is first purchase.
	 *
	 * @return true, if is first purchase
	 */
	public boolean isFirstPurchase() {
		return madePurcheas;
	}

	/**
	 * Sets the first purchase.
	 *
	 * @param firstPurchase the new first purchase
	 */
	public void setFirstPurchase(boolean firstPurchase) {
		this.madePurcheas = firstPurchase;
	}
	
	
}
