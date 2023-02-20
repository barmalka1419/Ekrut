package entitiesController;

import entities.Device;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * The Class DeviceEntityController.
 * class for keeping device(s) information
 */
public class DeviceEntityController {

	private static ObservableList<Device> deviceList = FXCollections.observableArrayList();
	
	
	/**
	 * Gets the device list.
	 *
	 * @return the device list
	 */
	public static ObservableList<Device> getDeviceList() {
		  return deviceList;
	}
	
	/**
	 * Sets the device list.
	 *
	 * @param deviceL the new device list
	 */
	public void setDeviceList(ObservableList<Device> deviceL) {
		deviceList = deviceL;
	}
	
	
	
}
