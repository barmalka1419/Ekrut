package entities;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * The Class Report.
 * entity class for keeping a report info.
 */
public class Report implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private int reportId,year,month,deviceId,threshold,
	itemsInShortage,totalOrders,totalSales,itemsNotInShortage,
	activityLevel,orderAverage,numberOfClientOrdered,below3orders,n4to8orderse,n9to12orders,n13to15orders
	,n16oraboveorders;
	
	private String region,type,topSeller,leastSeller,ReportName;
	
	ArrayList<Report> reportList;
	
	/**
	 * Instantiates a new report.
	 */
	public Report() {
	}
	
	/**
	 * Instantiates a new report.
	 *
	 * @param reportId the report id
	 * @param year the year
	 * @param month the month
	 * @param region the region
	 */
	public Report(int reportId, int year, int month, String region) {
		this.reportId = reportId;
		this.year = year;
		this.month = month;
		this.region = region;
	}
	
	/**
	 * Instantiates a new report.
	 *
	 * @param year the year
	 * @param month the month
	 * @param type the type
	 */
	public Report(int year, int month,String type) {
		this.year = year;
		this.month = month;
		this.type=type;
	}
	
	/**
	 * Instantiates a new report.
	 *
	 * @param ReportName the report name
	 * @param totalSales the total sales
	 */
	public Report(String ReportName, int totalSales) {
		this.ReportName=ReportName;
        this.totalSales=totalSales;
	}
	
	/**
	 * Sets the activity level.
	 *
	 * @param activityLevel the new activity level
	 */
	public void setactivityLevel(int activityLevel) {
		this.activityLevel = activityLevel;
	}
	
	/**
	 * Gets the activity level.
	 *
	 * @return the activity level
	 */
	public int getactivityLevel() {
		return activityLevel;
	}
	
	/**
	 * Sets the order average.
	 *
	 * @param orderAverage the new order average
	 */
	public void setorderAverage(int orderAverage) {
		this.orderAverage = orderAverage;
	}
	
	/**
	 * Gets the order average.
	 *
	 * @return the order average
	 */
	public int getorderAverage() {
		return orderAverage;
	}
	
	/**
	 * Sets the number of client ordered.
	 *
	 * @param numberOfClientOrdered the new number of client ordered
	 */
	public void setnumberOfClientOrdered(int numberOfClientOrdered) {
		this.numberOfClientOrdered = numberOfClientOrdered;
	}
	
	/**
	 * Gets the number of client ordered.
	 *
	 * @return the number of client ordered
	 */
	public int getnumberOfClientOrdered() {
		return numberOfClientOrdered;
	}
	
	/**
	 * Sets the report name.
	 *
	 * @param ReportName the new report name
	 */
	public void setReportName(String ReportName) {
		this.ReportName = ReportName;
	}
	
	/**
	 * Gets the report name.
	 *
	 * @return the report name
	 */
	public String getReportName() {
		return ReportName;
	}
	
	/**
	 * Sets the total sales.
	 *
	 * @param totalSales the new total sales
	 */
	public void settotalSales(int totalSales) {
		this.totalSales = totalSales;
	}
	
	/**
	 * Gets the total sales.
	 *
	 * @return the total sales
	 */
	public int gettotalSales() {
		return totalSales;
	}
	
	/**
	 * Sets the total orders.
	 *
	 * @param totalOrders the new total orders
	 */
	public void settotalOrders(int totalOrders) {
		this.totalOrders = totalOrders;
	}
	
	/**
	 * Gets the total orders.
	 *
	 * @return the total orders
	 */
	public int gettotalOrders() {
		return totalOrders;
	}
	
	/**
	 * Sets the top seller.
	 *
	 * @param topSeller the new top seller
	 */
	public void settopSeller(String topSeller) {
		this.topSeller = topSeller;
	}
	
	/**
	 * Gets the top seller.
	 *
	 * @return the top seller
	 */
	public String gettopSeller() {
		return topSeller;
	}
	
	/**
	 * Sets the least seller.
	 *
	 * @param leastSeller the new least seller
	 */
	public void setleastSeller(String leastSeller) {
		this.leastSeller = leastSeller;
	}
	
	/**
	 * Gets the least seller.
	 *
	 * @return the least seller
	 */
	public String getleastSeller() {
		return leastSeller;
	}
	
	/**
	 * Sets the report list.
	 *
	 * @param reportList the new report list
	 */
	public void setreportList(ArrayList<Report> reportList) {
		this.reportList = reportList;
	}
	
	/**
	 * Gets the report list.
	 *
	 * @return the report list
	 */
	public ArrayList<Report> getreportList() {
		return reportList;
	}
	
	/**
	 * Gets the device ID.
	 *
	 * @return the device ID
	 */
	public int getDeviceID() {
		return deviceId;
	}
	
	/**
	 * Sets the device ID.
	 *
	 * @param deviceId the new device ID
	 */
	public void setDeviceID(int deviceId) {
		this.deviceId = deviceId;
	}
	
	/**
	 * Gets the threshold.
	 *
	 * @return the threshold
	 */
	public int getthreshold() {
		return threshold;
	}
	
	/**
	 * Sets the threshold.
	 *
	 * @param threshold the new threshold
	 */
	public void setthreshold(int threshold) {
		this.threshold = threshold;
	}
	
	/**
	 * Gets the items in shortage.
	 *
	 * @return the items in shortage
	 */
	public int getitemsInShortage() {
		return itemsInShortage;
	}
	
	/**
	 * Sets the items in shortage.
	 *
	 * @param itemsInShortage the new items in shortage
	 */
	public void setitemsInShortage(int itemsInShortage) {
		this.itemsInShortage = itemsInShortage;
	}
	
	/**
	 * Gets the items not in shortage.
	 *
	 * @return the items not in shortage
	 */
	public int getitemsNotInShortage() {
		return itemsNotInShortage;
	}
	
	/**
	 * Sets the items not in shortage.
	 *
	 * @param itemsNotInShortage the new items not in shortage
	 */
	public void setitemsNotInShortage(int itemsNotInShortage) {
		this.itemsNotInShortage = itemsNotInShortage;
	}
	
	/**
	 * Gets the report id.
	 *
	 * @return the report id
	 */
	public int getReportId() {
		return reportId;
	}
	
	/**
	 * Sets the report id.
	 *
	 * @param reportId the new report id
	 */
	public void setReportId(int reportId) {
		this.reportId = reportId;
	}
	
	/**
	 * Gets the year.
	 *
	 * @return the year
	 */
	public int getYear() {
		return year;
	}
	
	/**
	 * Sets the year.
	 *
	 * @param year the new year
	 */
	public void setYear(int year) {
		this.year = year;
	}
	
	/**
	 * Gets the type.
	 *
	 * @return the type
	 */
	public String getType() {
		return type;
	}
	
	/**
	 * Sets the type.
	 *
	 * @param type the new type
	 */
	public void setType(String type) {
		this.type = type;
	}
	
	/**
	 * Gets the month.
	 *
	 * @return the month
	 */
	public int getMonth() {
		return month;
	}
	
	/**
	 * Sets the month.
	 *
	 * @param month the new month
	 */
	public void setMonth(int month) {
		this.month = month;
	}
	
	/**
	 * Gets the region.
	 *
	 * @return the region
	 */
	public String getRegion() {
		return region;
	}
	
	/**
	 * Sets the region.
	 *
	 * @param region the new region
	 */
	public void setRegion(String region) {
		this.region = region;
	}
	
	/**
	 * Sets the below 3 orders.
	 *
	 * @param below3orders the new below 3 orders
	 */
	public void setbelow3orders(int below3orders) {
		this.below3orders = below3orders;
	}
	
	/**
	 * Gets the below 3 orders.
	 *
	 * @return the below 3 orders
	 */
	public int getbelow3orders() {
		return below3orders;
	}
	
	/**
	 * Sets the 4 to 8 orders.
	 *
	 * @param n4to8orderse the new 4 to 8 orders
	 */
	public void set4to8orders(int n4to8orderse) {
		this.n4to8orderse = n4to8orderse;
	}
	
	/**
	 * Gets the 4 to 8 orders.
	 *
	 * @return the 4 to 8 orders
	 */
	public int get4to8orders() {
		return n4to8orderse;
	}
	
	/**
	 * Sets the 9 to 12 orders.
	 *
	 * @param n9to12orders the new 9 to 12 orders
	 */
	public void set9to12orders(int n9to12orders) {
		this.n9to12orders = n9to12orders;
	}
	
	/**
	 * Gets the 9 to 12 orders.
	 *
	 * @return the 9 to 12 orders
	 */
	public int get9to12orders() {
		return n9to12orders;
	}
	
	/**
	 * Sets the 13 to 15 orders.
	 *
	 * @param n13to15orders the new 13 to 15 orders
	 */
	public void set13to15orders(int n13to15orders) {
		this.n13to15orders = n13to15orders;
	}
	
	/**
	 * Gets the 13 to 15 orders.
	 *
	 * @return the 13 to 15 orders
	 */
	public int get13to15orders() {
		return n13to15orders;
	}
	
	/**
	 * Sets the 16 oraboveorders.
	 *
	 * @param n16oraboveorders the new 16 oraboveorders
	 */
	public void set16oraboveorders(int n16oraboveorders) {
		this.n16oraboveorders = n16oraboveorders;
	}
	
	/**
	 * Gets the 16 oraboveorders.
	 *
	 * @return the 16 oraboveorders
	 */
	public int get16oraboveorders() {
		return n16oraboveorders;
	}
}
