package entities;

/**
 * The Class OrdersReport.
 * entity class for keeping an orders report info.
 */
public class OrdersReport extends Report {
	
	private String ordersByDevice,topSeller,leastSeller;
	
	private int totalOrders;

	/**
	 * Instantiates a new orders report.
	 *
	 * @param reportId the report id
	 * @param year the year
	 * @param month the month
	 * @param region the region
	 * @param ordersByDevice the orders by device
	 * @param topSeller the top seller
	 * @param leastSeller the least seller
	 * @param totalOrders the total orders
	 */
	public OrdersReport(int reportId, int year, int month, String region, String ordersByDevice, String topSeller,
			String leastSeller, int totalOrders) {
		super(reportId, year, month, region);
		this.ordersByDevice = ordersByDevice;
		this.topSeller = topSeller;
		this.leastSeller = leastSeller;
		this.totalOrders = totalOrders;
	}
	
	/**
	 * Instantiates a new orders report.
	 */
	public OrdersReport() {
	}
	
	/**
	 * Gets the orders by device.
	 *
	 * @return the orders by device
	 */
	public String getOrdersByDevice() {
		return ordersByDevice;
	}
	
	/**
	 * Sets the orders by device.
	 *
	 * @param ordersByDevice the new orders by device
	 */
	public void setOrdersByDevice(String ordersByDevice) {
		this.ordersByDevice = ordersByDevice;
	}
	
	/**
	 * Gets the top seller.
	 *
	 * @return the top seller
	 */
	public String getTopSeller() {
		return topSeller;
	}
	
	/**
	 * Sets the top seller.
	 *
	 * @param topSeller the new top seller
	 */
	public void setTopSeller(String topSeller) {
		this.topSeller = topSeller;
	}
	
	/**
	 * Gets the least seller.
	 *
	 * @return the least seller
	 */
	public String getLeastSeller() {
		return leastSeller;
	}
	
	/**
	 * Sets the least seller.
	 *
	 * @param leastSeller the new least seller
	 */
	public void setLeastSeller(String leastSeller) {
		this.leastSeller = leastSeller;
	}
	
	/**
	 * Gets the total orders.
	 *
	 * @return the total orders
	 */
	public int getTotalOrders() {
		return totalOrders;
	}
	
	/**
	 * Sets the total orders.
	 *
	 * @param totalOrders the new total orders
	 */
	public void setTotalOrders(int totalOrders) {
		this.totalOrders = totalOrders;
	}
	
}
