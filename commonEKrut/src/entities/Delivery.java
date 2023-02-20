package entities;

import java.sql.Timestamp;
import java.util.List;

/**
 * The Class Delivery.
 * class for keeping a delivery order info
 */
public class Delivery extends Order {
	
	private static final long serialVersionUID = 9189909896002176129L;
	
	private String address,status,notes;
	
	private Timestamp estimatedDeliveryTime;
	
	private Boolean confirmationNotified;
	
	/**
	 * Instantiates a new delivery.
	 */
	public Delivery() {
	}
	
	/**
	 * Instantiates a new delivery.
	 *
	 * @param deviceID the device ID
	 * @param orderNumber the order number
	 * @param totalAmount the total amount
	 * @param createdTime the created time
	 * @param itemsInOrder the items in order
	 * @param payment the payment
	 * @param address the address
	 * @param estimatedDeliveryTime the estimated delivery time
	 * @param status the status
	 */
	public Delivery(int deviceID, int orderNumber, float totalAmount, Timestamp createdTime,
			List<ItemInOrder> itemsInOrder, Payment payment, String address, Timestamp estimatedDeliveryTime,
			String status) {
		super(deviceID, orderNumber, totalAmount, createdTime, itemsInOrder, payment);
		this.address = address;
		this.estimatedDeliveryTime = estimatedDeliveryTime;
		this.status = status;
	}

	/**
	 * Gets the address.
	 *
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}
	
	/**
	 * Sets the address.
	 *
	 * @param address the new address
	 */
	public void setAddress(String address) {
		this.address = address;
	}
	
	/**
	 * Gets the estimated delivery time.
	 *
	 * @return the estimated delivery time
	 */
	public Timestamp getEstimatedDeliveryTime() {
		return estimatedDeliveryTime;
	}
	
	/**
	 * Sets the estimated delivery time.
	 *
	 * @param estimatedDeliveryTime the new estimated delivery time
	 */
	public void setEstimatedDeliveryTime(Timestamp estimatedDeliveryTime) {
		this.estimatedDeliveryTime = estimatedDeliveryTime;
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
	 * Gets the confirmation notified.
	 *
	 * @return the confirmation notified
	 */
	public Boolean getConfirmationNotified() {
		return confirmationNotified;
	}
	
	/**
	 * Sets the confirmation notified.
	 *
	 * @param confirmationNotified the new confirmation notified
	 */
	public void setConfirmationNotified(Boolean confirmationNotified) {
		this.confirmationNotified = confirmationNotified;
	}

	/**
	 * Gets the notes.
	 *
	 * @return the notes
	 */
	public String getNotes() {
		return notes;
	}
	
	/**
	 * Sets the notes.
	 *
	 * @param notes the new notes
	 */
	public void setNotes(String notes) {
		this.notes = notes;
	}

	/**
	 * The Enum DeliveryStatus.
	 */
	enum DeliveryStatus{	
		PROCESSING, 
        ON_TRANSIT, 
        ARRIVED	};
}
