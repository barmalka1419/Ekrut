package entitiesController;

import entities.Delivery;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * The Class DeliveryEntityController.
 * class for keeping delivery(ies) information
 */
public class DeliveryEntityController {

	private static ObservableList<Delivery> deliveryList = FXCollections.observableArrayList();

	/**
	 * Gets the delivery list.
	 *
	 * @return the delivery list
	 */
	public static ObservableList<Delivery> getDeliveryList() {
		return deliveryList;
	}

	/**
	 * Sets the delivery list.
	 *
	 * @param deliveryList the new delivery list
	 */
	public static void setDeliveryList(ObservableList<Delivery> deliveryList) {
		DeliveryEntityController.deliveryList = deliveryList;
	}
	

	
}
