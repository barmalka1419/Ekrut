package entitiesController;

import java.util.Observable;

import entities.Item;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;

/**
 * The Class EkrutCatalogController.
 * class for keeping the items shown in the items catalog in ordering screen
 */
public class EkrutCatalogController {
	
	private static ObservableList<Item> DeviceItem = FXCollections.observableArrayList();
	
	private boolean changedInventory;
	
	/**
	 * Gets the device items.
	 *
	 * @return the observable list
	 */
	public ObservableList<Item> GetDeviceItems(){
		
		return DeviceItem;
	}
	
	/**
	 * Checks if is changed inventory.
	 *
	 * @return true, if is changed inventory
	 */
	public boolean isChangedInventory() {
		return changedInventory;
	}

	/**
	 * Sets the changed inventory.
	 *
	 * @param changedInventory the new changed inventory
	 */
	public void setChangedInventory(boolean changedInventory) {
		this.changedInventory = changedInventory;
	}

	/**
	 * Sets the item in device.
	 *
	 * @param Items the items
	 */
	public void SetItemInDevice(ObservableList<Item> Items) {
		DeviceItem = Items;
		
	}

}
