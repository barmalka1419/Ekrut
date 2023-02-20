package entities;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * The Class Sale.
 * entity class for keeping a sale info.
 */
public class Sale implements Serializable {
	
	private static final long serialVersionUID = 1505134694328867515L;
	
	private int saleID,discountSize;
	
	private Date startDate, endDate;
	
	private Time startHour,endHour;
	
	private String region;
	
	private List<String> days;
	
	public String saleType;
	
	private boolean isActive;
	
  


	/**
	 * Instantiates a new sale.
	 */
	public Sale() {
	}

	/**
	 * Instantiates a new sale.
	 *
	 * @param saleID the sale ID
	 * @param startDate the start date
	 * @param endDate the end date
	 * @param discountSize the discount size
	 * @param region the region
	 * @param days the days
	 * @param startHour the start hour
	 * @param endHour the end hour
	 * @param saleType the sale type
	 * @param isActive the is active
	 */
	public Sale(int saleID, Date startDate, Date endDate, int discountSize,String region,List<String> days, Time startHour, Time endHour, 
			 String saleType, boolean isActive) {
		this.saleID = saleID;
		this.discountSize = discountSize;
		this.startDate = startDate;
		this.endDate = endDate;
		this.startHour = startHour;
		this.endHour = endHour;
		this.region = region;
		this.days = days;
		this.saleType = saleType;
		this.isActive = isActive;
	}
	
	/**
	 * Gets the sale ID.
	 *
	 * @return the sale ID
	 */
	public int getSaleID() {
		return saleID;
	}

	/**
	 * Sets the sale ID.
	 *
	 * @param saleID the new sale ID
	 */
	public void setSaleID(int saleID) {
		this.saleID = saleID;
	}

	/**
	 * Gets the discount size.
	 *
	 * @return the discount size
	 */
	public int getDiscountSize() {
		return discountSize;
	}

	/**
	 * Sets the discount size.
	 *
	 * @param discountSize the new discount size
	 */
	public void setDiscountSize(int discountSize) {
		this.discountSize = discountSize;
	}

	/**
	 * Gets the start date.
	 *
	 * @return the start date
	 */
	public Date getStartDate() {
		return startDate;
	}

	/**
	 * Sets the start date.
	 *
	 * @param startDate the new start date
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	/**
	 * Gets the end date.
	 *
	 * @return the end date
	 */
	public Date getEndDate() {
		return endDate;
	}

	/**
	 * Sets the end date.
	 *
	 * @param endDate the new end date
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	/**
	 * Gets the start hour.
	 *
	 * @return the start hour
	 */
	public Time getStartHour() {
		return startHour;
	}

	/**
	 * Sets the start hour.
	 *
	 * @param startHour the new start hour
	 */
	public void setStartHour(Time startHour) {
		this.startHour = startHour;
	}

	/**
	 * Gets the end hour.
	 *
	 * @return the end hour
	 */
	public Time getEndHour() {
		return endHour;
	}

	/**
	 * Sets the end hour.
	 *
	 * @param endHour the new end hour
	 */
	public void setEndHour(Time endHour) {
		this.endHour = endHour;
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
	 * Gets the days.
	 *
	 * @return the days
	 */
	public List<String> getDays() {
		return days;
	}

	/**
	 * Sets the days.
	 *
	 * @param days the new days
	 */
	public void setDays(List<String> days) {
		this.days = days;
	}

	/**
	 * Gets the sale type.
	 *
	 * @return the sale type
	 */
	public String getSaleType() {
		return saleType;
	}

	/**
	 * Sets the sale type.
	 *
	 * @param saleType the new sale type
	 */
	public void setSaleType(String saleType) {
		this.saleType = saleType;
	}

	/**
	 * Checks if is active.
	 *
	 * @return true, if is active
	 */
	public boolean isActive() {
		return isActive;
	}
	
	/**
	 * Checks if is active property.
	 *
	 * @return the string property
	 */
	public StringProperty isActiveProperty() {
		
		if(isActive())
			return new SimpleStringProperty("1");
		
		return new SimpleStringProperty("0");
	       
	 }

	/**
	 * Sets the active.
	 *
	 * @param isActive the new active
	 */
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	/**
	 * The Enum SaleType.
	 */
	enum SaleType{
		
		discountSize, 
		twofor1, 
		discountByTime
		};
}
