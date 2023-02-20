package entitiesController;


import entities.ItemInDevice;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * The Class ItemInDeviceEntityController.
 * class for keeping items in device information
 */
public class ItemInDeviceEntityController {

	private static ObservableList<ItemInDevice> itemInDeviceList = FXCollections.observableArrayList();

	/**
	 * Gets the item in device list.
	 *
	 * @return the item in device list
	 */
	public static ObservableList<ItemInDevice> getItemInDeviceList() {
		return itemInDeviceList;
	}

	/**
	 * Sets the item in device list.
	 *
	 * @param itemInDeviceList the new item in device list
	 */
	public static void setItemInDeviceList(ObservableList<ItemInDevice> itemInDeviceList) {
		ItemInDeviceEntityController.itemInDeviceList = itemInDeviceList;
	}

	

}
