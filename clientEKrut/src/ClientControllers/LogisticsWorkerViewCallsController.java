package ClientControllers;

import java.sql.Date;

import client.ChatClient;
import client.ClientUI;
import common.ChangeScene;
import common.Message;
import entities.Employee;
import entities.InventoryCall;
import entitiesController.InventoryCallEntityController;
import entitiesController.userLoginController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.util.Callback;

/**
 * The Class LogisticsWorkerViewCallsController.
 * Controller for logistics employee view calls screen
 * Let the logistics employee view and execute inventory calls 
 */
public class LogisticsWorkerViewCallsController {

    @FXML
    private TableView<InventoryCall> inventoryCallTable;

    @FXML
    private TableColumn<InventoryCall, Integer> callID;

    @FXML
    private TableColumn<InventoryCall, Integer> DeviceID;

    @FXML
    private TableColumn<InventoryCall, Integer> EmployeeID;

    @FXML
    private TableColumn<InventoryCall, Date> dateCol;

    @FXML
    private TableColumn<InventoryCall, String> manage;
    
    @FXML
    private Text txtCallCount;

    
    Message msg = new Message();
    ObservableList<Integer> deviceIDList = FXCollections.observableArrayList();
    ObservableList<String> employeeDetailsList = FXCollections.observableArrayList();
    ObservableList<Employee> employeeUsernameList;
    Employee employee = (Employee)ChatClient.userloginController.getUser();
    
    /**
     * Gets the inventory calls for the specific employee from the DB and sets it to table 
     * 
     *
     */
    public void getInventoryCall() {
    	
    	
  		msg.setData(employee);
		msg.setTask("Get Inventory Call Logistic");
    	ClientUI.chat.accept(msg);
    	
    	
		ObservableList<InventoryCall> inventoryCallList = InventoryCallEntityController.getInvetoryCallList();
		
		txtCallCount.setText(String.valueOf(inventoryCallList.size()));
		
		inventoryCallTable.setItems(inventoryCallList);
		
		callID.setCellValueFactory(new PropertyValueFactory<>("callID"));
		DeviceID.setCellValueFactory(new PropertyValueFactory<>("deviceID"));
		EmployeeID.setCellValueFactory(new PropertyValueFactory<>("employeeID"));
		dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));


		manage.setCellFactory(new Callback<TableColumn<InventoryCall, String>, TableCell<InventoryCall, String>>() {
		    @Override
		    public TableCell<InventoryCall, String> call(TableColumn<InventoryCall, String> column) {
		        return new TableCell<InventoryCall, String>() {
		            // Create a button for the cell
		            Button button = new Button("Update");
		          

		            {
		            	
		            	 button.setStyle("-fx-background-color: #493dc2; -fx-text-fill: white;");
				         button.setMaxWidth(Double.MAX_VALUE);
		                // Add an action event for the button
		                button.setOnAction((event) -> {
		                	
		             
		                	  TableRow<InventoryCall> row = this.getTableRow();
		                	  InventoryCallEntityController.setInventoryCall(row.getItem());
		                	 	ChangeScene scene=new ChangeScene();
		                	 	
		                		scene.changeScreen(new Stage(),"/clientGUI/LogisticsUpdateInventory.fxml");
		                		((Node)event.getSource()).getScene().getWindow().hide(); //hiding primary window
		            
		                });
		            }

		            @Override
		            protected void updateItem(String item, boolean empty) {
		                super.updateItem(item, empty);
		                if (empty) {
		                    setGraphic(null);
		                } else {
		                    // Set the button as the graphic for the cell
		                    setGraphic(button);
		                }
		            }
		        };
		    }
		});
		

    	
    }
    
    
	/**
	 * Initialize: Gets the inventory calls
	 * 
	 */
	@FXML
  	public void initialize() {
  		
  		
  		getInventoryCall();
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
     * Go back.
     *
     * @param event the event
     */
    @FXML
    void goBack(MouseEvent event) {
    	ChangeScene scene=new ChangeScene();
		scene.changeScreen(new Stage(),"/clientGUI/LogisticsWorkerMenu.fxml");
		((Node)event.getSource()).getScene().getWindow().hide(); //hiding primary window
    }

}
