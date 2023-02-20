package entitiesController;

import entities.Delivery;
import entities.Device;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;



/**
 * The Class DeliveryController.
 * class for keeping user delivery  information and list of the delivery centers
 */
public class DeliveryController {
	
	private Delivery delivery;
	
	private static ObservableList<Device> deliveryCentersList = FXCollections.observableArrayList();
	
	/**
	 * Gets the delivery centers list.
	 *
	 * @return the delivery centers list
	 */
	public static ObservableList<Device> getdeliveryCentersList() {
		  return deliveryCentersList;
	}
	
	/**
	 * Sets the delivery centers list.
	 *
	 * @param deliveryCentersL the new delivery centers list
	 */
	public void setdeliveryCentersList(ObservableList<Device> deliveryCentersL) {
		deliveryCentersList = deliveryCentersL;
	}

	/**
	 * Gets the delivery.
	 *
	 * @return the delivery
	 */
	public Delivery getDelivery() {
		return delivery;
	}

	/**
	 * Sets the delivery.
	 *
	 * @param delivery the new delivery
	 */
	public void setDelivery(Delivery delivery) {
		this.delivery = delivery;
	}
	
}
