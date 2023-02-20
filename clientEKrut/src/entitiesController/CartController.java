package entitiesController;

import java.util.ArrayList;

import ClientControllers.ItemPatternController;
import ClientControllers.OrderingController;
import javafx.scene.layout.AnchorPane;


/**
 * The Class CartController.
 * class for keeping cart information
 */
public class CartController {
	
	OrderingController orderingcontroller;
	
	public ArrayList<ItemPatternController>itemPattern= new ArrayList<>();
	
	public ArrayList<AnchorPane> anchorpaneList = new ArrayList<>();
	
	public ArrayList<AnchorPane> anchorpaneCatalogList = new ArrayList<>();
	
	public boolean resetCart=true;//true if the cart needs to be reset(cleared)
	
	/**
	 * Gets the anchorpane catalog list.
	 *
	 * @return the anchorpane catalog list
	 */
	public ArrayList<AnchorPane> getAnchorpaneCatalogList() {
		return anchorpaneCatalogList;
	}
	
	/**
	 * Sets the anchorpane catalog list.
	 *
	 * @param anchorpaneCatalogList the new anchorpane catalog list
	 */
	public void setAnchorpaneCatalogList(ArrayList<AnchorPane> anchorpaneCatalogList) {
		this.anchorpaneCatalogList = anchorpaneCatalogList;
	}
	
	/**
	 * Adds the catalog anchor pane.
	 *
	 * @param anchor the anchor
	 */
	public void addCatalogAnchorPane (AnchorPane anchor) {
		anchorpaneCatalogList.add(anchor);
		
	}
	
	
	/**
	 * Checks if is reset cart.
	 *
	 * @return true, if is reset cart
	 */
	public boolean isResetCart() {
		return resetCart;
	}
	
	/**
	 * Sets the reset cart.
	 *
	 * @param resetCart the new reset cart
	 */
	public void setResetCart(boolean resetCart) {
		this.resetCart = resetCart;
	}
	
	/**
	 * Gets the anchorpane list.
	 *
	 * @return the anchorpane list
	 */
	public ArrayList<AnchorPane> getAnchorpaneList() {
		return anchorpaneList;
	}
	
	/**
	 * Delete index in anchorpane list.
	 *
	 * @param index the index
	 */
	public void DeleteIndexInAnchorpaneList(int index) {
		anchorpaneList.remove(index);
	}

	/**
	 * Adds the anchorpane list.
	 *
	 * @param anchorpane the anchorpane
	 */
	public void addAnchorpaneList(AnchorPane anchorpane) {
		anchorpaneList.add(anchorpane) ;
	}
	
	/**
	 * Sets the anchorpane list.
	 *
	 * @param anchorpaneList the anchorpane list
	 */
	public void SetAnchorpaneList(ArrayList<AnchorPane> anchorpaneList) {
		this.anchorpaneList=anchorpaneList;
	}

	/**
	 * Instantiates a new cart controller.
	 */
	public  CartController () {};
	
	/**
	 * Sets the.
	 *
	 * @param orderingcontroller the orderingcontroller
	 */
	public void set(OrderingController orderingcontroller) {
		this.orderingcontroller= orderingcontroller;
	}
	
	/**
	 * Gets the ordering controller.
	 *
	 * @return the ordering controller
	 */
	public OrderingController getOrderingController() {
		return orderingcontroller;
	}
	
	/**
	 * Adds the item pattern.
	 *
	 * @param itemPatternController the item pattern controller
	 */
	public void addItemPattern (ItemPatternController itemPatternController) {
		itemPattern.add(itemPatternController);
		
	}
	
	/**
	 * Delet item pattern.
	 *
	 * @param index the index
	 */
	public void DeletItemPattern(int index) {
		itemPattern.remove(index);
	}
	
	/**
	 * Gets the item pattern.
	 *
	 * @return the item pattern
	 */
	public ArrayList<ItemPatternController> getItemPattern() {
		return itemPattern;
	}

}

