package entities;

import java.sql.Timestamp;
import java.util.List;

/**
 * The Class TakeAway.
 * entity class for keeping a take away(Pick up) order info.
 */
public class TakeAway extends Order {
	

	private static final long serialVersionUID = 8854912544530626770L;

	private int orderCode;
    
    private boolean collected;

	/**
	 * Instantiates a new take away.
	 *
	 * @param deviceID the device ID
	 * @param orderNumber the order number
	 * @param totalAmount the total amount
	 * @param createdTime the created time
	 * @param itemsInOrder the items in order
	 * @param payment the payment
	 * @param orderCode the order code
	 * @param collected the collected
	 */
	public TakeAway(int deviceID, int orderNumber, float totalAmount, Timestamp createdTime,
			List<ItemInOrder> itemsInOrder, Payment payment, int orderCode, boolean collected) {
		super(deviceID, orderNumber, totalAmount, createdTime, itemsInOrder, payment);
		this.orderCode = orderCode;
		this.collected = collected;
	}

	/**
	 * Instantiates a new take away.
	 */
	public TakeAway() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * Gets the order code.
	 *
	 * @return the order code
	 */
	public int getOrderCode() {
		return orderCode;
	}

	/**
	 * Sets the order code.
	 *
	 * @param orderCode the new order code
	 */
	public void setOrderCode(int orderCode) {
		this.orderCode = orderCode;
	}

	/**
	 * Checks if is collected.
	 *
	 * @return true, if is collected
	 */
	public boolean isCollected() {
		return collected;
	}

	/**
	 * Sets the collected.
	 *
	 * @param collected the new collected
	 */
	public void setCollected(boolean collected) {
		this.collected = collected;
	}

}
