package entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import entities.ItemInDevice.Status;

/**
 * The Class Device.
 * entity class for keeping a device/delivery center info
 */
public class Device implements Serializable {
	
	private static final long serialVersionUID = 7850324229072013613L;
	
	private int deviceID,threshold;
	
	private String location, region;
	
	private List<ItemInDevice> AvilableItems=new ArrayList<ItemInDevice>();
	
	
	/**
	 * Instantiates a new device.
	 */
	public Device() {
		// TODO Auto-generated constructor stub
	}
	
    /**
     * Instantiates a new device.
     *
     * @param deviceID the device ID
     * @param threshold the threshold
     * @param location the location
     * @param region the region
     * @param soldItems the sold items
     */
    public Device(int deviceID, int threshold, String location, String region, List<ItemInDevice> soldItems) {
		this.deviceID = deviceID;
		this.threshold = threshold;
		this.location = location;
		this.region = region;
		this.AvilableItems = soldItems;
	}

	/**
	 * Adds the item.
	 *
	 * @param item the item
	 * @param amount the amount
	 * @param status the status
	 */
	public void addItem(Item item, int amount, Status status) {//add association class
		AvilableItems.add(new ItemInDevice(item,this,amount,status));//check that item is added only once-add code
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
	 * Gets the location.
	 *
	 * @return the location
	 */
	public String getLocation() {
		return location;
	}

	/**
	 * Sets the location.
	 *
	 * @param location the new location
	 */
	public void setLocation(String location) {
		this.location = location;
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
	 * Gets the sold items.
	 *
	 * @return the sold items
	 */
	public List<ItemInDevice> getSoldItems() {
		return AvilableItems;
	}

	/**
	 * Sets the sold items.
	 *
	 * @param soldItems the new sold items
	 */
	public void setSoldItems(List<ItemInDevice> soldItems) {
		this.AvilableItems = soldItems;
	}

}
