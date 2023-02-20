package entities;

/**
 * The Class InventoryReport.
 * entity class for keeping an inventory report info
 */
public class InventoryReport extends Report {
	
	private int deviceID,threshold,itemsInshortage;
	
	private String items;
	
	/**
	 * Instantiates a new inventory report.
	 */
	public InventoryReport() {}
	
	/**
	 * Instantiates a new inventory report.
	 *
	 * @param reportId the report id
	 * @param year the year
	 * @param month the month
	 * @param region the region
	 * @param deviceID the device ID
	 * @param threshold the threshold
	 * @param itemsInshortage the items inshortage
	 * @param items the items
	 */
	public InventoryReport(int reportId, int year, int month, String region, int deviceID, int threshold,
			int itemsInshortage, String items) {
		super(reportId, year, month, region);
		this.deviceID = deviceID;
		this.threshold = threshold;
		this.itemsInshortage = itemsInshortage;
		this.items = items;
	}
	
	/**
	 * Gets the device ID.
	 *
	 * @return the device ID
	 */
	public int getDeviceID() {
		return deviceID;
	}
	
	/**
	 * Sets the device ID.
	 *
	 * @param deviceID the new device ID
	 */
	public void setDeviceID(int deviceID) {
		this.deviceID = deviceID;
	}
	
	/**
	 * Gets the threshold.
	 *
	 * @return the threshold
	 */
	public int getThreshold() {
		return threshold;
	}
	
	/**
	 * Sets the threshold.
	 *
	 * @param threshold the new threshold
	 */
	public void setThreshold(int threshold) {
		this.threshold = threshold;
	}
	
	/**
	 * Gets the items inshortage.
	 *
	 * @return the items inshortage
	 */
	public int getItemsInshortage() {
		return itemsInshortage;
	}
	
	/**
	 * Sets the items inshortage.
	 *
	 * @param itemsInshortage the new items inshortage
	 */
	public void setItemsInshortage(int itemsInshortage) {
		this.itemsInshortage = itemsInshortage;
	}
	
	/**
	 * Gets the items.
	 *
	 * @return the items
	 */
	public String getItems() {
		return items;
	}
	
	/**
	 * Sets the items.
	 *
	 * @param items the new items
	 */
	public void setItems(String items) {
		this.items = items;
	}
}
