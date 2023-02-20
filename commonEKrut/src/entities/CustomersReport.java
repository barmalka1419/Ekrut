package entities;

/**
 * The Class CustomersReport.
 * class for keeping a customers report info
 */
public class CustomersReport extends Report {
		
		private String activityLevel;
		
		private int numberOfClientsOrdered;
		
		private double orderAverage;
	
	/**
	 * Instantiates a new customers report.
	 */
	public CustomersReport() {
	}
	
	/**
	 * Instantiates a new customers report.
	 *
	 * @param reportId the report id
	 * @param year the year
	 * @param month the month
	 * @param region the region
	 * @param activityLevel the activity level
	 * @param numberOfClientsOrdered the number of clients ordered
	 * @param orderAverage the order average
	 */
	public CustomersReport(int reportId, int year, int month, String region, String activityLevel,
			int numberOfClientsOrdered, double orderAverage) {
		super(reportId, year, month, region);
		this.activityLevel = activityLevel;
		this.numberOfClientsOrdered = numberOfClientsOrdered;
		this.orderAverage = orderAverage;
	}
	
	/**
	 * Gets the activity level.
	 *
	 * @return the activity level
	 */
	public String getActivityLevel() {
		return activityLevel;
	}
	
	/**
	 * Sets the activity level.
	 *
	 * @param activityLevel the new activity level
	 */
	public void setActivityLevel(String activityLevel) {
		this.activityLevel = activityLevel;
	}
	
	/**
	 * Gets the number of clients ordered.
	 *
	 * @return the number of clients ordered
	 */
	public int getNumberOfClientsOrdered() {
		return numberOfClientsOrdered;
	}
	
	/**
	 * Sets the number of clients ordered.
	 *
	 * @param numberOfClientsOrdered the new number of clients ordered
	 */
	public void setNumberOfClientsOrdered(int numberOfClientsOrdered) {
		this.numberOfClientsOrdered = numberOfClientsOrdered;
	}
	
	/**
	 * Gets the order average.
	 *
	 * @return the order average
	 */
	public double getOrderAverage() {
		return orderAverage;
	}
	
	/**
	 * Sets the order average.
	 *
	 * @param orderAverage the new order average
	 */
	public void setOrderAverage(double orderAverage) {
		this.orderAverage = orderAverage;
	}

}
