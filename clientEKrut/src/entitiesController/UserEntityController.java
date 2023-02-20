package entitiesController;


import entities.Employee;
import entities.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


/**
 * The Class UserEntityController.
 * controller class for storing users and employees information
 * 
 */
public class UserEntityController {

	private static User user;
	
	private static ObservableList<User> userList = FXCollections.observableArrayList();
	
	private static ObservableList<Employee> employeeList = FXCollections.observableArrayList();
	
	
	/**
	 * Gets the employee list.
	 *
	 * @return the employee list
	 */
	public static ObservableList<Employee> getEmployeeList() {
		return employeeList;
	}

	/**
	 * Sets the employee list.
	 *
	 * @param employeeList the new employee list
	 */
	public static void setEmployeeList(ObservableList<Employee> employeeList) {
		UserEntityController.employeeList = employeeList;
	}

	/**
	 * Gets the user list.
	 *
	 * @return the user list
	 */
	public static ObservableList<User> getUserList() {
		
		  return userList;
	}
	
	/**
	 * Sets the user list.
	 *
	 * @param userL the new user list
	 */
	public void setUserList(ObservableList<User> userL) {
		userList = userL;
	}
	
	
	/**
	 * Gets the user.
	 *
	 * @return the user
	 */
	public static User getUser() {
		  return user;
	}
	
	/**
	 * Sets the user.
	 *
	 * @param userDB the new user
	 */
	public void setUser(User userDB) {
		user = userDB;
	}
	
	
}
