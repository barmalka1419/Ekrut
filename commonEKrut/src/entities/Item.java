package entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import entities.ItemInDevice.Status;

/**
 * The Class Item.
 * entity class for keeping an item info
 */
public class Item  implements Serializable{
	
	/**
	 * Hash code.
	 *
	 * @return the int
	 */
	@Override
	public int hashCode() {
		return Objects.hash(description, itemsInDevice, name, photo, price, serialNumber);
	}

	/**
	 * Equals.
	 *
	 * @param obj the obj
	 * @return true, if successful
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Item other = (Item) obj;
		return Objects.equals(description, other.description) && Objects.equals(itemsInDevice, other.itemsInDevice)
				&& Objects.equals(name, other.name) && Objects.equals(photo, other.photo)
				&& Float.floatToIntBits(price) == Float.floatToIntBits(other.price)
				&& serialNumber == other.serialNumber;
	}


	private int serialNumber; // primry key
	
	private String name, description,photo;
	
	private float price;
	
	private List<ItemInDevice> itemsInDevice=new ArrayList<ItemInDevice>();;
	
	/**
	 * Instantiates a new item.
	 */
	public Item() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Instantiates a new item.
	 *
	 * @param serialNumber the serial number
	 * @param name the name
	 * @param description the description
	 * @param photo the photo
	 * @param price the price
	 */
	public Item(int serialNumber, String name, String description, String photo, float price) {
		this.serialNumber = serialNumber;
		this.name = name;
		this.description = description;
		this.photo = photo;
		this.price = price;
	}

	
	
	/**
	 * Instantiates a new item.
	 *
	 * @param serialNumber the serial number
	 * @param name the name
	 * @param description the description
	 * @param photo the photo
	 * @param price the price
	 * @param itemsInDevice the items in device
	 */
	public Item(int serialNumber, String name, String description, String photo, float price,
			List<ItemInDevice> itemsInDevice) {
		this.serialNumber = serialNumber;
		this.name = name;
		this.description = description;
		this.photo = photo;
		this.price = price;
		this.itemsInDevice = itemsInDevice;
	}

	
	/**
	 * Adds the device.
	 *
	 * @param device the device
	 * @param amount the amount
	 * @param status the status
	 */
	public void addDevice(Device device, int amount, Status status) {//add association class
		itemsInDevice.add(new ItemInDevice(this,device,amount,status));//add check if device is in the list
	}


	/**
	 * Gets the serial number.
	 *
	 * @return the serial number
	 */
	public int getSerialNumber() {
		return serialNumber;
	}


	/**
	 * Sets the serial number.
	 *
	 * @param serialNumber the new serial number
	 */
	public void setSerialNumber(int serialNumber) {
		this.serialNumber = serialNumber;
	}


	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}


	/**
	 * Sets the name.
	 *
	 * @param name the new name
	 */
	public void setName(String name) {
		this.name = name;
	}


	/**
	 * Gets the description.
	 *
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}


	/**
	 * Sets the description.
	 *
	 * @param description the new description
	 */
	public void setDescription(String description) {
		this.description = description;
	}


	/**
	 * Gets the photo.
	 *
	 * @return the photo
	 */
	public String getPhoto() {
		return photo;
	}


	/**
	 * Sets the photo.
	 *
	 * @param photo the new photo
	 */
	public void setPhoto(String photo) {
		this.photo = photo;
	}


	/**
	 * Gets the price.
	 *
	 * @return the price
	 */
	public float getPrice() {
		return price;
	}


	/**
	 * Sets the price.
	 *
	 * @param price the new price
	 */
	public void setPrice(float price) {
		this.price = price;
	}


	/**
	 * Gets the items in device.
	 *
	 * @return the items in device
	 */
	public List<ItemInDevice> getItemsInDevice() {
		return itemsInDevice;
	}


	/**
	 * Sets the selling devices.
	 *
	 * @param sellingDevices the new selling devices
	 */
	public void setSellingDevices(List<ItemInDevice> sellingDevices) {
		this.itemsInDevice = sellingDevices;
	}

}
