package entities;

import java.io.Serializable;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;

/**
 * The Class User.
 * entity class for keeping a user info.
 */
public class User  implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String userName,lastName,firstName,password,IDNumber,email,phoneNumber,userPermission,LoginType;
	
	private boolean isLogged;    

	/**
  * Instantiates a new user.
  */
 public User() {
	
	}

	/**
	 * Instantiates a new user.
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
	 */
	public User(String userName, String lastName, String firstName, String password, String iDNumber, String email,
			String phoneNumber, boolean isLogged, String userPermission) {
		this.userName = userName;
		this.lastName = lastName;
		this.firstName = firstName;
		this.password = password;
		this.IDNumber = iDNumber;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.isLogged = isLogged;
		this.userPermission = userPermission;
	}

	/**
	 * Gets the user name.
	 *
	 * @return the user name
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * Sets the user name.
	 *
	 * @param userName the new user name
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	/**
	 * Gets the login type.
	 *
	 * @return the login type
	 */
	public String getLoginType() {
		return LoginType;
	}

	/**
	 * Sets the login type.
	 *
	 * @param LoginType the new login type
	 */
	public void setLoginType(String LoginType) {
		this.LoginType = LoginType;
	}
	
	/**
	 * Gets the last name.
	 *
	 * @return the last name
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Sets the last name.
	 *
	 * @param lastName the new last name
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * Gets the first name.
	 *
	 * @return the first name
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Sets the first name.
	 *
	 * @param firstName the new first name
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * Gets the password.
	 *
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Sets the password.
	 *
	 * @param password the new password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Gets the ID number.
	 *
	 * @return the ID number
	 */
	public String getIDNumber() {
		return IDNumber;
	}

	/**
	 * Sets the ID number.
	 *
	 * @param iDNumber the new ID number
	 */
	public void setIDNumber(String iDNumber) {
		IDNumber = iDNumber;
	}

	/**
	 * Gets the email.
	 *
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Sets the email.
	 *
	 * @param email the new email
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Gets the phone number.
	 *
	 * @return the phone number
	 */
	public String getPhoneNumber() {
		return phoneNumber;
	}

	/**
	 * Sets the phone number.
	 *
	 * @param phoneNumber the new phone number
	 */
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	/**
	 * Checks if is logged.
	 *
	 * @return true, if is logged
	 */
	public boolean isLogged() {
		return isLogged;
	}


	/**
	 * Sets the logged.
	 *
	 * @param isLogged the new logged
	 */
	public void setLogged(boolean isLogged) {
		this.isLogged = isLogged;
	}


	/**
	 * Gets the user permission.
	 *
	 * @return the user permission
	 */
	public String getUserPermission() {
		return userPermission;
	}

	

	/**
	 * Sets the user permission.
	 *
	 * @param userPermission the new user permission
	 */
	public void setUserPermission(String userPermission) {
		this.userPermission = userPermission;
	}


	/**
	 * Checks if is exist.
	 *
	 * @return true, if is exist
	 */
	public boolean isExist() {
		return !(this == null);
	}
	
	/**
	 * The Enum UserPermission.
	 */
	enum UserPermission{
		customer, 
		ceo,
		pending, 
		regionalManager, 
		deliveryManager, 
		serviceEmployee, 
		marketingManager,
		marketingEmployee
	};
}
