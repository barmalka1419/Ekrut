package entities;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * The Class InventoryCall.
 * entity class for keeping an inventory call info
 */
public class InventoryCall implements Serializable {

	private static final long serialVersionUID = 2096384424211230289L;
	
	private int callID,deviceID,employeeID;
	
	private Timestamp date;
	
	private String status,creatorUsername;
	


	/**
	 * Instantiates a new inventory call.
	 */
	public InventoryCall() {
	}
	
	/**
	 * Instantiates a new inventory call.
	 *
	 * @param callID the call ID
	 * @param deviceID the device ID
	 * @param employeeID the employee ID
	 * @param date the date
	 * @param status the status
	 * @param creatorUsername the creator username
	 */
	public InventoryCall(int callID, int deviceID, int employeeID, Timestamp date, String status,String creatorUsername) {
		this.callID = callID;
		this.deviceID = deviceID;
		this.employeeID = employeeID;
		this.date = date;
		this.status = status;
		this.creatorUsername = creatorUsername;
	}

	/**
	 * Gets the creator username.
	 *
	 * @return the creator username
	 */
	public String getCreatorUsername() {
		return creatorUsername;
	}

	/**
	 * Sets the creator username.
	 *
	 * @param creatorUsername the new creator username
	 */
	public void setCreatorUsername(String creatorUsername) {
		this.creatorUsername = creatorUsername;
	}
	
	/**
	 * Gets the call ID.
	 *
	 * @return the call ID
	 */
	public int getCallID() {
		return callID;
	}

	/**
	 * Sets the call ID.
	 *
	 * @param callID the new call ID
	 */
	public void setCallID(int callID) {
		this.callID = callID;
	}

	/**
	 * Gets the device ID.
	 *
	 * @return the device ID
	 */
	public int getDeviceID() {
		return deviceID;
	}

	/**
	 * Sets the device ID.
	 *
	 * @param deviceID the new device ID
	 */
	public void setDeviceID(int deviceID) {
		this.deviceID = deviceID;
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
	 * Gets the date.
	 *
	 * @return the date
	 */
	public Timestamp getDate() {
		return date;
	}

	/**
	 * Sets the date.
	 *
	 * @param date the new date
	 */
	public void setDate(Timestamp date) {
		this.date = date;
	}

	/**
	 * Gets the status.
	 *
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * Sets the status.
	 *
	 * @param status the new status
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * The Enum CallStatus.
	 */
	enum CallStatus{
		OPEN, 
		DONE	};

}
