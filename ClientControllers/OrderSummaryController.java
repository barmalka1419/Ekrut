package ClientControllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import client.ChatClient;
import client.ClientUI;
import common.ChangeScene;
import common.Message;
import entities.Customer;
import entities.Member;
import entities.Order;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 * The Class OrderSummaryController.
 * shows the customer the oreder summary
 */
public class OrderSummaryController implements Initializable {
	
	private OrderingController OrderScreen;

	@FXML
	private ImageView BackBtn;

	@FXML
	private Button ConfirmBtn;

	@FXML
	private ImageView ExitBtn;

	@FXML
	private Label TotalPriceLable;

	@FXML
	private Button cancelOrderBtn;

	@FXML
	private GridPane gridPane;

	private OrderingController orderingController;

	List<CartPatternController> CartItems;

	/**
	 * Organzies the screen.
	 */
	public void OrginaizeScreen() {

	}

	/**
	 * Initialize the screen and builds the order summary view of the customer's cart.
	 * if the customer is a member and it's his first purchase also applies 20% off on the total price
	 * @param arg0 the arg 0
	 * @param arg1 the arg 1
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		ArrayList<AnchorPane> itemInCart;

		if (ChatClient.userloginController.getUser() instanceof Member) {
			if (((Member) (ChatClient.userloginController.getUser())).isFirstPurchase() == false) {
				OrderingController.TotalPrice = (float) (OrderingController.TotalPrice * 0.8);
			}
		}

		TotalPriceLable
				.setText(String.valueOf(String.valueOf(String.format("%.2f", OrderingController.TotalPrice)) + "$"));

		if (client.ChatClient.cartController.getOrderingController() != null) {
			itemInCart = client.ChatClient.cartController.getAnchorpaneList();
			for (int i = 0; i < itemInCart.size(); i++) {
				gridPane.add(itemInCart.get(i), 0, i);

			}

		}

	}

	/**
	 * Click on back.
	 *	moves the customer to the previous screen
	 * @param event the event
	 */
	@FXML
	void ClickOnBack(MouseEvent event) {

		if (ChatClient.userloginController.getUser() instanceof Member) {
			if (((Member) (ChatClient.userloginController.getUser())).isFirstPurchase() == false) {
				OrderingController.TotalPrice = (float) ((OrderingController.TotalPrice) / 0.800);
			}
		}

		client.ChatClient.cartController.setResetCart(false);// we want to save previous screen's state
		ChangeScene scene = new ChangeScene();
		((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
		ChatClient.currentScreen.changeScreen(new Stage(), "/clientGUI/Ordering.fxml");
		client.ChatClient.cartController.set(client.ChatClient.cartController.getOrderingController());

	}

	/**
	 * Click on cancel order.
	 *	cancels the current order and moves the client to ordering screen to start a new order
	 * @param event the event
	 */
	@FXML
	void ClickOnCancelOrder(MouseEvent event) {
		OrderingController.timeTask.cancel();// cancel time window
		OrderingController.ClearAll();
		ChangeScene scene = new ChangeScene();
		((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
		ChatClient.currentScreen.changeScreen(new Stage(), "/clientGUI/Ordering.fxml");

	}

	/**
	 * Click on confirm and pay.
	 *	saves the order and make payment.
	 *	if an item is missing moves to a screen containing a relevant message and options
	 *	if everything is fine shows the customer the order number and moves him to the machine/ remote orders menu
	 * @param event the event
	 */
	@FXML
	void ClickOnConfirmAndPay(MouseEvent event) {

		OrderingController.timeTask.cancel();// cancel time window

		ChatClient.MyOrder.saveOrder();
		Message msg = new Message();
		msg.setTask("Save Order");
		msg.setData((ChatClient.MyOrder).getOrder());
		ClientUI.chat.accept(msg);
		if (ChatClient.catlogController.isChangedInventory()) {

			OrderingController.ClearAll();
			ChangeScene scene = new ChangeScene();
			((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
			ChatClient.currentScreen.changeScreen(new Stage(), "/clientGUI/OrderFailPopUp.fxml");
		} else {

			int OrderNumber = ChatClient.MyOrder.getOrder().getOrderNumber();
			
			ChangeScene.showInformationAlert("Order Number : " + OrderNumber + " Payment succsess ");
			ChatClient.MyOrder.setOrder(new Order());
			// add here condition for remote orders
			ChangeScene scene = new ChangeScene();
			((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
			if (ChatClient.MyOrder.getOrderType().equals("local")) {
				ChatClient.currentScreen.changeScreen(new Stage(), "/clientGUI/MachineMenu.fxml");
			} else if (ChatClient.MyOrder.getOrderType().equals("pick up")
					|| ChatClient.MyOrder.getOrderType().equals("delivery")) {
				ChatClient.currentScreen.changeScreen(new Stage(), "/clientGUI/RemoteOrderMenu.fxml");
			}
		}

	}
}