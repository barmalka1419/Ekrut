package ClientControllers;

import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.mysql.cj.xdevapi.Client;

import client.ChatClient;
import client.ClientUI;
import common.ChangeScene;
import common.Message;
import entities.Device;
import entities.Item;
import entities.Member;
import entities.Order;
import entitiesController.userLoginController;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Tooltip;


/**
 * The Class OrderingController.
 * Controller for the Ordering screen
 * Shows the user the available items and lets him make an order
 */
public class OrderingController implements Initializable {
	
	static int i = 0;// needs to reset every new order
	
	static Timer time = null;
	
	static TimerTask timeTask;
	
	private int cartRows = 0;
	
	static int RowIndex = 0;// needs to reset every new order
	
	public static float TotalPrice = 0;// needs to reset every new order-because is static

	private List<Item> items = new ArrayList<>();

	@FXML
	private ScrollPane CatalogScrollpane;

	@FXML
	private Label NoAvilableItemS;
	

    @FXML
    private Tooltip tooltip;

	@FXML
	private GridPane grid;

	@FXML
	private GridPane GridForMyCart;

	@FXML
	public Label TotalPriceLable;

	@FXML
	private Label cartIsEmptyLable;

	@FXML
	private Button GetBtn;

	@FXML
	private ScrollPane ScrollpaneCart;
	
	public List<CartPatternController> cartLines = new ArrayList<CartPatternController>();

	/**
	 * disable cart rows' buttons for order summary view in the next screen.
	 */
	@FXML

	public void SetDisableImageForOrderSummary() {
		for (int i = 0; i < cartLines.size(); i++) {
			cartLines.get(i).SetDisableImages();
		}

	}

	/**
	 * Enables cart rows' buttons.
	 */
	public void SetEnabledImageButtons() {// make cart buttons appear again when back from order summary
		for (int i = 0; i < cartLines.size(); i++) {
			cartLines.get(i).SetEnableImages();
			;
		}
	}

	/**
	 * Click on back btn.
	 *	moves to previous screen according to the order type
	 * @param event the event
	 */
	@FXML
	void ClickOnBackBtn(MouseEvent event) {
		OrderingController.timeTask.cancel();
		ClearAll();
		((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
		if (ChatClient.MyOrder.getOrderType().equals("pick up")) {//maybe change so delivery will go back to address
			ChatClient.currentScreen.changeScreen(new Stage(), "/clientGUI/RemoteOrderMenu.fxml");
		} 
		else if( ChatClient.MyOrder.getOrderType().equals("delivery")) {
			ChatClient.currentScreen.changeScreen(new Stage(), "/clientGUI/DeliveryAddressForm.fxml");
		}
		else {
			ChatClient.currentScreen.changeScreen(new Stage(), "/clientGUI/MachineMenu.fxml");
		}
	}

	/**
	 * Click on get btn.
	 * moves to order summary screen if the cart isn't empty
	 * @param event the event
	 */
	@FXML
	void ClickOnGetBtn(ActionEvent event) {

		if (TotalPrice != 0 && cartLines.size() > 0) {
			client.ChatClient.cartController.set(this);
			ChangeScene scene = new ChangeScene();
			((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
			ChatClient.currentScreen.changeScreen(new Stage(), "/clientGUI/orderSummary.fxml");
			SetDisableImageForOrderSummary();
		}

		else {
			cartIsEmptyLable.setVisible(true);
		}
	}

	/**
	 * Clear all.
	 * clears the cart and catalog for a new order
	 */
	public static void ClearAll() {
		client.ChatClient.cartController.anchorpaneList.clear();
		ChatClient.saleController.setSalesInDevice(null);//added line- to fix sales bug
		client.ChatClient.cartController.resetCart = true;
		client.ChatClient.cartController.anchorpaneCatalogList.clear();
		client.ChatClient.cartController.itemPattern.clear();
		ClientControllers.OrderingController.TotalPrice = 0;
		client.ChatClient.MyOrder.setOrder(new Order());
	}

	/**
	 * Initialize the screen.
	 * loads the items catalog, search for sales, create a time window for the new order process.
	 * initializes some relevant variables
	 */
	public void initialize() {
		OrderingController.ClearAll();
		time = new Timer();
		timeTask = new TimerTask() {
			public void run() {
				// The task you want to do
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						OrderingController.ClearAll();
						ChatClient.currentScreen.screen.getScene().getWindow().hide();
						if (ChatClient.MyOrder.getOrderType().equals("local")) {
							ChatClient.currentScreen.changeScreen(new Stage(), "/clientGUI/MachineMenu.fxml");
						} else if (ChatClient.MyOrder.getOrderType().equals("pick up") || ChatClient.MyOrder.getOrderType().equals("delivery")) {
							ChatClient.currentScreen.changeScreen(new Stage(), "/clientGUI/RemoteOrderMenu.fxml");
						}
					}
				});
			}
		};

		try {
			time.schedule(timeTask, 300000l);
			OrderingController.i = 0;
			OrderingController.RowIndex = 0;
			OrderingController.TotalPrice = 0;
			cartIsEmptyLable.setVisible(false);
			NoAvilableItemS.setVisible(false);
			ChatClient.cartController.setResetCart(true);// need new cart, maybe update the flag before click on new
															// order in previous screen
			Message msg = new Message();
			msg.setTask("Load Catalog");
	
			msg.setData((ChatClient.MyDevice).getDeviceId());
			ClientUI.chat.accept(msg);
			items = ChatClient.catlogController.GetDeviceItems();

		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		if (items.size() == 0) {
			NoAvilableItemS.setVisible(true);
		} else {

			if (ChatClient.userloginController.getUser() != null
					&& (ChatClient.userloginController.getUser().getUserPermission()).equals("member")) {

				try {
					Message msg1 = new Message();
					msg1.setTask("Look for sales in my device");
					msg1.setData(ChatClient.MyDevice.getMyDevice()); // in the real system that
					// need to be
					
					ClientUI.chat.accept(msg1);
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {

				try {
					if (ChatClient.saleController.getSalesInDevice() != null) {
						ChatClient.saleController.setSalesInDevice(null);
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			ScreenOrganization();

		}

	}

	/**
	 * Sets the current total price of the order, shown under the cart.
	 */
	public void SetPrice() {

		// Deviation error correction
		if (TotalPrice < 0 && TotalPrice > -1) {
			TotalPrice = 0;
		}

		TotalPriceLable.setText(String.valueOf(String.format("%.2f", TotalPrice)) + "$");
	}

	/**
	 * Adds an item to the cart- triggered by clicking on an "Add to Cart" button in the catalog.
	 *
	 * @param item the item
	 * @param mainScreen the main screen
	 * @param needToAdd the need to add
	 * @param itemPatternScreen the item pattern screen
	 */
	public static void AddItemToCart(Item item, OrderingController mainScreen, boolean needToAdd,
			ItemPatternController itemPatternScreen) {// added parameter, changed name

		if (needToAdd == true) {
			mainScreen.set(item, itemPatternScreen);
			OrderingController.TotalPrice += item.getPrice();
			mainScreen.SetPrice();

		} else {
			mainScreen.update(item);
		}
	}

	/**
	 * makes the "cart is empty" label visible/invisible.
	 *
	 * @param condition the condition
	 */
	public void SetcartIsEmptyLable(boolean condition) {
		cartIsEmptyLable.setVisible(condition);
	}

	/**
	 * add new row to the cart
	 *
	 * @param item the item
	 * @param itemPatternScreen the item pattern screen
	 */
	public void set(Item item, ItemPatternController itemPatternScreen) {// adding new row to cart
		AnchorPane anchorPane = null;
		FXMLLoader fxmlLoader2 = new FXMLLoader();
		fxmlLoader2.setLocation(getClass().getResource("/clientGUI/CartPattern.fxml"));
		try {
			anchorPane = fxmlLoader2.load();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		CartPatternController cartpattermcontroller = fxmlLoader2.getController();// fixed bug
		cartpattermcontroller.OrginaizeScreen(item, this, itemPatternScreen);// added paramters
		cartLines.add(cartpattermcontroller);
		GridForMyCart.add(anchorPane, 0, cartRows);
		client.ChatClient.cartController.addAnchorpaneList(anchorPane);
		cartRows++;
	}

	/**
	 * Update an existing cart row.
	 *
	 * @param item the item
	 */
	public void update(Item item) {
		for (CartPatternController c : cartLines) {
			if (c.getCartItem() == item)
				c.addToCartFromCatalog();
		}
	}

	/**
	 * Delete  a cart row from the cart Gridpane.
	 * also updates the indexes of the rows after the deleted row
	 * @param index the index
	 */
	public void deleteRow(int index) {
		cartRows--;
		RowIndex--;
		ArrayList<Node> childrenInRow = new ArrayList<Node>();// problem removing when there is only one line after we
																// removed all
		ObservableList<Node> children = GridForMyCart.getChildren();
		for (int i = 0; i < children.size(); ++i) {

			if (GridPane.getRowIndex(children.get(i)) == index) {
				childrenInRow.add(children.get(i));
				children.remove(i);
				cartLines.remove(index);// probably fixes deletion bug-moved line from line 129
				client.ChatClient.cartController.DeleteIndexInAnchorpaneList(index);

				for (int j = i; j < children.size(); j++) {// update grid index of following rows
					GridPane.setRowIndex(children.get(j), j);
					cartLines.get(j).moveRowUp();// added line to update Myindex in cart Items
				}
				break;
			}
		}
	}

	/**
	 * building the catalog view- putting up to 3 items in a row
	 */
	void ScreenOrganization() {

		int row = 0;
		int column = 0;
		int i = 0;
		for (i = 0; i < items.size(); i++) {

			AnchorPane anchorPane = null;
			try {

				FXMLLoader PatternFxml = new FXMLLoader();
				PatternFxml.setLocation(getClass().getResource("/clientGUI/ITEMPATTERN.fxml"));
				anchorPane = PatternFxml.load();
				ItemPatternController itemPatternController = PatternFxml.getController();
				itemPatternController.OrganizePattern(items.get(i), this);
				client.ChatClient.cartController.addItemPattern(itemPatternController);
			} catch (IOException e) {

				e.printStackTrace();
			}

			grid.add(anchorPane, column % 3, row);
			client.ChatClient.cartController.addCatalogAnchorPane(anchorPane);
			if (column % 3 == 2) {
				row++;
			}
			column++;

		}

	}

	/**
	 * Restore screen state when going back from the order summary and not cancelling/completing the order.
	 * restores all the cart rows
	 */
	public void restoreScreenState() {// function for restoring the screen state when coming back from order summary
		cartIsEmptyLable.setVisible(false);
		NoAvilableItemS.setVisible(false);
		SetPrice();
		OrderingController lastScreenInstance = client.ChatClient.cartController.getOrderingController();
		this.cartLines = lastScreenInstance.cartLines;
		SetEnabledImageButtons();
		this.items = lastScreenInstance.items;
		this.cartRows = lastScreenInstance.cartRows;
		int row = 0;
		int column = 0;
		for (int i = 0; i < client.ChatClient.cartController.anchorpaneCatalogList.size(); i++) {
			client.ChatClient.cartController.getItemPattern().get(i).setMainScreen(this);// make sure the item sees new
																							// screen
			grid.add(client.ChatClient.cartController.anchorpaneCatalogList.get(i), column % 3, row);
			if (column % 3 == 2) {
				row++;
			}
			column++;
		}
		for (int i = 0; i < client.ChatClient.cartController.anchorpaneList.size(); i++) {
			cartLines.get(i).setPrimaryScreen(this);
			;// make sure the cart item sees new screen
			GridForMyCart.add(client.ChatClient.cartController.anchorpaneList.get(i), 0, i);
		}
		client.ChatClient.cartController.set(this);// make cart controller keep current instance
	}

	/**
	 * Changed inventroy.
	 */
	public void changedInventroy() {//maybe remove

		List<Item> updatedInventory = ChatClient.catlogController.GetDeviceItems();
		for (int j = 0; j < updatedInventory.size(); j++) {// search for out of stock items
			if (updatedInventory.get(j).getItemsInDevice().get(0).getAmount() == 0) {
				ChatClient.cartController.getItemPattern().get(j).SetAddToCartButton(true);
				ChatClient.cartController.getItemPattern().get(j).SetVisibleText(true);
			} else {
				ChatClient.cartController.getItemPattern().get(j).SetAddToCartButton(false);
				// add cart check, restocked items, update amounts

			}
		}
	}

	/**
	 * Initialize the screen /restores previous screen state and cart if needed.
	 * also shows a member a pop up if it's his first purchase about 20% off 
	 * 
	 * @param arg0 the arg 0
	 * @param arg1 the arg 1
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		
		if (ChatClient.cartController.isResetCart()) {
			client.ChatClient.catlogController.setChangedInventory(false);
			ChatClient.userloginController.getUser();
			initialize();

			if (ChatClient.userloginController.getUser() != null
					&& ChatClient.userloginController.getUser() instanceof Member) {

				if (((Member) (ChatClient.userloginController.getUser())).isFirstPurchase() == false) {
					String userName = ChatClient.userloginController.getUser().getFirstName();
					String lastName = ChatClient.userloginController.getUser().getLastName();
					
					ChangeScene.showInformationAlert("Hello " + userName + " " + lastName
							+ "\n Get 20% from Us for your first purchase !"
							+ "\n you will see the price with the discount after moving to the Order summary page!"
							+ "\n click on OK button to move Order Screen ");
					
				}
			}
		}

		else {
			restoreScreenState();
		}

	}
	
    /**
     * Close app and logout EKrut.
     *
     * @param event MouseEvent
     */
    @FXML
    void closeApp(MouseEvent event) {
    	userLoginController.logoutEKrut(event);
	  	Message connect = new Message();
		connect.setTask("Disconnect Server");
		ClientUI.chat.accept(connect);
		System.exit(0);
    }
}
