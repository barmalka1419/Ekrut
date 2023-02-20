package entitiesController;

import client.ClientUI;
import common.Message;
import entities.Device;


/**
 * The Class DeviceController.
 * class for keeping information of the current device in use
 */
public class  DeviceController {
	
	private  int DeviceId = 1;
	
	private Device device;
	
	
	
	
	/**
	 * Instantiates a new device controller.
	 */
	public DeviceController() {

	}
	
	
	/**
	 * Instantiates a new device controller.
	 *
	 * @param MyDevice the new current device
	 */
	public DeviceController(Device MyDevice) {
		this.device=MyDevice;
	}
	

	/**
	 * Gets the device id.
	 *
	 * @return the device id
	 */
	public int getDeviceId() {
		return DeviceId;
	}
	

	/**
	 * Sets the device id.
	 *
	 * @param deviceId the new device id
	 */
	public void setDeviceId(int deviceId) {
		
		DeviceId = deviceId;
	}




	/**
	 * Gets the  device.
	 *
	 * @return the current device
	 */
	public Device getMyDevice() {
		return device;
	}




	/**
	 * Sets the device.
	 *
	 * @param myDevice the new current device
	 */
	public void setMyDevice(Device myDevice) {
		device = myDevice;
	}

}
