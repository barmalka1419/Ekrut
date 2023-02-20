package ClientControllers;

import java.sql.Timestamp;
import java.util.ArrayList;

import client.ChatClient;
import client.ClientUI;
import common.ChangeScene;
import common.Message;
import entities.Customer;
import entities.Delivery;
import entities.Employee;
import entitiesController.DeliveryEntityController;
import entitiesController.userLoginController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 * The Class DeliveryUserManagementController.
 * Controller for user delivery management screen
 * Let the user view and control the delivery status 
 */
public class DeliveryUserManagementController {

	@FXML
	private TableView<Delivery> deliveryTable;

	@FXML
	private TableColumn<Delivery, Integer> orderID;

	@FXML
	private TableColumn<Delivery, String> address;

	@FXML
	private TableColumn<Delivery, Timestamp> estimatedDeliveryTime;

	@FXML
	private TableColumn<Delivery, String> status;

	@FXML
	private TableColumn<Delivery, String> manage;

	@FXML
	private TableColumn<Delivery, Integer> deviceID;

	Message msg = new Message();
	ObservableList<Delivery> deliveryList;

	ObservableList<String> processingToArrived = FXCollections.observableArrayList("PROCESSING", "ARRIVED");
	ObservableList<String> processing = FXCollections.observableArrayList("PROCESSING");
	ObservableList<String> arrived = FXCollections.observableArrayList("ARRIVED");
	ObservableList<String> waiting = FXCollections.observableArrayList("WAITING");
	ArrayList<Delivery> changedDeliveryList = new ArrayList<>();

	/**
	 * Initialize.
	 * 
	 * Set the deliveries table that include: orderNumber,address,estimated delivery time and status
	 */
	@FXML
	public void initialize() {

		msg.setData(ChatClient.userloginController.getUser());
		msg.setTask("View Delivery Orders");
		ClientUI.chat.accept(msg);

		deliveryList = DeliveryEntityController.getDeliveryList();

		orderID.setCellValueFactory(new PropertyValueFactory<>("orderNumber"));
		address.setCellValueFactory(new PropertyValueFactory<>("address"));
		estimatedDeliveryTime.setCellValueFactory(new PropertyValueFactory<>("estimatedDeliveryTime"));

		deviceID.setCellValueFactory(new PropertyValueFactory<>("deviceID"));

		estimatedDeliveryTime
				.setCellFactory(new Callback<TableColumn<Delivery, Timestamp>, TableCell<Delivery, Timestamp>>() {
					@Override
					public TableCell<Delivery, Timestamp> call(TableColumn<Delivery, Timestamp> column) {
						return new TableCell<Delivery, Timestamp>() {

							@Override
							protected void updateItem(Timestamp item, boolean empty) {
								super.updateItem(item, empty);
								if (empty) {
									setGraphic(null);
								} else {

									Label text = new Label("Waiting for manager approval..");

									if (this.getTableRow().getItem().getEstimatedDeliveryTime() == null) {

										setGraphic(text);
									} else {

										Label date = new Label(
												this.getTableRow().getItem().getEstimatedDeliveryTime().toString());
										setGraphic(date);

									}

								}
							}

						};
					}
				});

		status.setCellFactory(new Callback<TableColumn<Delivery, String>, TableCell<Delivery, String>>() {
			@Override
			public TableCell<Delivery, String> call(TableColumn<Delivery, String> column) {
				return new TableCell<Delivery, String>() {

					@Override
					protected void updateItem(String item, boolean empty) {
						super.updateItem(item, empty);
						if (empty) {
							setGraphic(null);
						} else {

							ComboBox<String> comboBox = new ComboBox<>();
							comboBox.setMaxWidth(Double.MAX_VALUE);
							comboBox.setPrefWidth(Double.MAX_VALUE);

							String rowStatus = this.getTableRow().getItem().getStatus();

							switch (rowStatus) {

							case "PROCESSING":
								if (this.getTableRow().getItem().getEstimatedDeliveryTime() == null)
									comboBox.setItems(processing);
								else
									comboBox.setItems(processingToArrived);
								comboBox.setValue(rowStatus);
								break;
							case "ARRIVED":
								comboBox.setItems(arrived);
								comboBox.setValue(rowStatus);
								break;
							case "WAITING":
								comboBox.setItems(waiting);
								comboBox.setValue(rowStatus);
								break;
							case "DONE":
								comboBox.setItems(arrived);
								comboBox.setValue("ARRIVED");
								break;

							}

							setGraphic(comboBox);

							comboBox.setOnAction(event -> {

								if (!changedDeliveryList.contains(getTableRow().getItem())) {

									getTableRow().getItem().setStatus(comboBox.getValue());
									changedDeliveryList.add(getTableRow().getItem());
								} else {

									changedDeliveryList.get(changedDeliveryList.indexOf(getTableRow().getItem()))
											.setStatus(comboBox.getValue());

								}

								

							});

						}
					}

				};
			}
		});

		deliveryTable.setItems(deliveryList);

		deliveryTable.setEditable(true);
	}

	/**
	 * Save the deliveries that their status changed by the user and send it to db
     * The user can change the status from PROCESSING to ARRIVED
	 * @param event ActionEvent
	 */
	@FXML
	void clickSave(ActionEvent event) {

		
		if (changedDeliveryList.isEmpty()) {
			ChangeScene.showInformationAlert("None of the delivery status changed");
		} else {

			msg.setData(changedDeliveryList);
			msg.setTask("Set Delivery Status");
			ClientUI.chat.accept(msg);

			String returnMessage = ChatClient.getReturnMessage();

			if (returnMessage.equals("Falied"))
				ChangeScene.showInformationAlert("Delivery Set Status Failed");
			else
				ChangeScene.showInformationAlert("Delivery Set Status Succeeded");

			ChangeScene scene = new ChangeScene();
			scene.changeScreen(new Stage(), "/clientGUI/RemoteOrderMenu.fxml");
			((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window

		}

	}

	/**
	 * Close app and logout EKrut.
	 *
	 * @param event the event
	 */
	@FXML
	void closeApp(MouseEvent event) {
		userLoginController.logoutEKrut(event);
		Message connect = new Message();
		connect.setTask("Disconnect Server");
		ClientUI.chat.accept(connect);
		System.exit(0);
	}

	/**
	 * Go back to remote order menu.
	 *
	 * @param event MouseEvent
	 */
	@FXML
	void goBack(MouseEvent event) {
		ChangeScene scene = new ChangeScene();
		scene.changeScreen(new Stage(), "/clientGUI/RemoteOrderMenu.fxml");
		((Node) event.getSource()).getScene().getWindow().hide(); // hiding primary window
	}

}
