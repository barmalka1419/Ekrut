package ClientControllers;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;

import client.ChatClient;
import client.ClientUI;
import common.ChangeScene;
import common.Message;
import entities.Delivery;
import entities.Employee;
import entities.Sale;
import entities.User;
import entitiesController.DeliveryEntityController;
import entitiesController.UserEntityController;
import entitiesController.userLoginController;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 * The Class DeliveryManagerViewController.
 * Controller for delivery management screen
 * Let the manager control the delivery status 
 */
public class DeliveryManagerViewController {


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
    private Text txtRegion;

    Message msg = new Message();
    ObservableList<Delivery> deliveryList;
    ObservableList<String> waiting = FXCollections.observableArrayList("WAITING","PROCESSING");
    ObservableList<String> processing = FXCollections.observableArrayList("PROCESSING");
    ObservableList<String> arrived = FXCollections.observableArrayList("ARRIVED","DONE");
    ObservableList<String> done = FXCollections.observableArrayList("DONE");
    ArrayList<Delivery> changedDeliveryList = new ArrayList<>();
    Employee employee = (Employee)ChatClient.userloginController.getUser();
    
    /**
     * Initialize.
     * 
     * Set the deliveries table that include: orderNumber,address,estimated delivery time and status
     * 
     */
    @FXML
	public void initialize() {
		
    	txtRegion.setText("Region: " + employee.getRegion());
    	
		msg.setData(employee);
		msg.setTask("View Delivery Manager");
    	ClientUI.chat.accept(msg);
    	
    	deliveryList = DeliveryEntityController.getDeliveryList();
    	
    	orderID.setCellValueFactory(new PropertyValueFactory<>("orderNumber"));
    	address.setCellValueFactory(new PropertyValueFactory<>("address"));
    	estimatedDeliveryTime.setCellValueFactory(new PropertyValueFactory<>("estimatedDeliveryTime"));
    	status.setCellValueFactory(new PropertyValueFactory<>("status"));   	
     	
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
			                    comboBox.setValue(item);
			                    String rowStatus = this.getTableRow().getItem().getStatus();
			                
			                    
			                    switch(rowStatus) {
				                    
				                    case "WAITING":
				                    	comboBox.setItems(waiting);
				                    	break;
				                    case "PROCESSING":
				                    	comboBox.setItems(processing);
				                    	break;
				                    case "ARRIVED":
				                    	comboBox.setItems(arrived);
				                    	break;
				                    case "DONE":
				                    	comboBox.setItems(done);
				                    	break;
				                    
			                    
			                    }

			                    
			                    setGraphic(comboBox);
			                   
			                    
			                    comboBox.setOnAction(event -> {
			                    	
			                    	
			                    	if(!changedDeliveryList.contains(getTableRow().getItem())) {
				            			
				            			getTableRow().getItem().setStatus(comboBox.getValue());
				            			changedDeliveryList.add(getTableRow().getItem());
				            		}
				            		else {
				            			
				            			
				            			changedDeliveryList.get(changedDeliveryList.indexOf(getTableRow().getItem())).setStatus(comboBox.getValue());
				            			
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
     * Save the deliveries that their status changed by the manager and send it to db
     * The manager can change the status from WAITING to PROCESSING and ARRIVED to DONE
     * 
     * @param event ActionEvent
     */
    @FXML
    void clickSave(ActionEvent event) {

    	
    	if(changedDeliveryList.isEmpty()) {
    		ChangeScene.showInformationAlert("None of the delivery status changed");
    	}
    	else {
    		
    		msg.setData(changedDeliveryList);
    		msg.setTask("Set Delivery Status");
        	ClientUI.chat.accept(msg);
        	
        	
        	
        	String returnMessage = ChatClient.getReturnMessage();
        	
        	if(returnMessage.equals("Falied"))
        		ChangeScene.showInformationAlert("Delivery Set Status Failed");
        	else 
        		ChangeScene.showInformationAlert("Delivery Set Status Succeeded");
        	
        	ChangeScene scene=new ChangeScene();
    		scene.changeScreen(new Stage(),"/clientGUI/DeliveryManagerMenu.fxml");
    		((Node)event.getSource()).getScene().getWindow().hide(); //hiding primary window
    		
    		
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
     * Go back to delivery manager menu.
     *
     * @param event the event
     */
    @FXML
    void goBack(MouseEvent event) {
    	ChangeScene scene=new ChangeScene();
		scene.changeScreen(new Stage(),"/clientGUI/DeliveryManagerMenu.fxml");
		((Node)event.getSource()).getScene().getWindow().hide(); //hiding primary window
    }

}
