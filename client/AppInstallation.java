package client;


/**
 * The Class AppInstallation.
 * class that keeps the installation information of the client
 */
public class AppInstallation {
	
	String installType;
	
	int machineNumber;
	
	/**
	 * Gets the install type.
	 *
	 * @return the install type
	 */
	public String getInstallType() {
		return installType;
	}
	
	/**
	 * Sets the install type.
	 *
	 * @param installType the new install type
	 */
	public void setInstallType(String installType) {
		this.installType = installType;
	}
	
	/**
	 * Gets the machine number.
	 *
	 * @return the machine number
	 */
	public int getMachineNumber() {
		return machineNumber;
	}
	
	/**
	 * Sets the machine number.
	 *
	 * @param machineNumber the new machine number
	 */
	public void setMachineNumber(int machineNumber) {
		this.machineNumber = machineNumber;
	}
}
