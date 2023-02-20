package entities;

/**
 * The Class Employee.
 * entity class for keeping an employee user information
 */
public class Employee extends User {
	
	private static final long serialVersionUID = -8621861563596905040L;
	
	private int employeeID;// employee number-not the persons id
	
	private String region, position;

	/**
	 * Instantiates a new employee.
	 */
	public Employee() {
	}

	/**
	 * Instantiates a new employee.
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
	 * @param employeeID the employee ID
	 * @param region the region
	 * @param position the position
	 */
	public Employee(String userName, String lastName, String firstName, String password, String iDNumber, String email,
			String phoneNumber, boolean isLogged, String userPermission,int employeeID, String region, String position) {
		super(userName, lastName, firstName, password, iDNumber, email, phoneNumber, isLogged, userPermission);
		this.employeeID = employeeID;
		this.region = region;
		this.position = position;
	}

	/**
	 * Gets the employee ID.
	 *
	 * @return the employee ID
	 */
	public int getEmployeeID() {
		return employeeID;
	}

	/**
	 * Sets the employee ID.
	 *
	 * @param employeeID the new employee ID
	 */
	public void setEmployeeID(int employeeID) {
		this.employeeID = employeeID;
	}

	/**
	 * Gets the region.
	 *
	 * @return the region
	 */
	public String getRegion() {
		return region;
	}

	/**
	 * Sets the region.
	 *
	 * @param region the new region
	 */
	public void setRegion(String region) {
		this.region = region;
	}

	/**
	 * Gets the position.
	 *
	 * @return the position
	 */
	public String getPosition() {
		return position;
	}

	/**
	 * Sets the position.
	 *
	 * @param position the new position
	 */
	public void setPosition(String position) {
		this.position = position;
	}
	
}
