package entitiesController;

import entities.InventoryCall;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * The Class InventoryCallEntityController.
 * class for keeping inventory calls information
 */
public class InventoryCallEntityController {

	
	private static ObservableList<InventoryCall> inventoryCallList = FXCollections.observableArrayList();
	
	private static InventoryCall inventoryCall;
	
	/**
	 * Gets the inventory call.
	 *
	 * @return the inventory call
	 */
	public static InventoryCall getInventoryCall() {
		return inventoryCall;
	}

	/**
	 * Sets the inventory call.
	 *
	 * @param inventoryCall the new inventory call
	 */
	public static void setInventoryCall(InventoryCall inventoryCall) {
		InventoryCallEntityController.inventoryCall = inventoryCall;
	}

	/**
	 * Gets the invetory call list.
	 *
	 * @return the invetory call list
	 */
	public static ObservableList<InventoryCall> getInvetoryCallList() {
		  return inventoryCallList;
	}
	
	/**
	 * Sets the invetory call list.
	 *
	 * @param invetoryCall the new invetory call list
	 */
	public static void setInvetoryCallList(ObservableList<InventoryCall> invetoryCall) {
		inventoryCallList = invetoryCall;
	}	
	
}
