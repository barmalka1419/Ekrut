package entitiesController;

import entities.Sale;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * The Class SaleController.
 * controller class for storing sales information
 */
public class SaleController {
	
	private Sale SalesInDevice;
	
	private static ObservableList<Sale> saleList = FXCollections.observableArrayList();

	/**
	 * Gets the sale list.
	 *
	 * @return the sale list
	 */
	public static ObservableList<Sale> getSaleList() {
		  return saleList;
	}
	
	/**
	 * Sets the sale list.
	 *
	 * @param sales the new sale list
	 */
	public static void setSaleList(ObservableList<Sale> sales) {
		saleList = sales;
	}
	
	/**
	 * Gets the sales in device.
	 *
	 * @return the sales in device
	 */
	public Sale getSalesInDevice() {
		return SalesInDevice;
	}

	/**
	 * Sets the sales in device.
	 *
	 * @param salesInDevice the new sales in device
	 */
	public void setSalesInDevice(Sale salesInDevice) {
		SalesInDevice = salesInDevice;
	}

	
	

}
