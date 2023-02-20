package ClientControllers;

import java.sql.Date;
import java.sql.Timestamp;

import client.ChatClient;
import client.ClientUI;
import common.ChangeScene;
import common.Message;
import entities.Device;
import entities.Employee;
import entities.InventoryCall;
import entities.Member;
import entities.User;
import entitiesController.DeviceEntityController;
import entitiesController.InventoryCallEntityController;
import entitiesController.UserEntityController;
import entitiesController.userLoginController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;


/**
 * The Class InventoryCallMangementController.
 * Controller for inventory call management screen
 * Let the regional manager view and create inventory calls 
 */
public class InventoryCallMangementController {

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
    private TableColumn<InventoryCall, String> status;
    
    @FXML
    private ComboBox<Integer> comboDevice;

    @FXML
    private ComboBox<String> comboEmployee;

    @FXML
    private Text txtRegion;
    

    Message msg = new Message();
    ObservableList<Integer> deviceIDList = FXCollections.observableArrayList();
    ObservableList<String> employeeDetailsList = FXCollections.observableArrayList();
    ObservableList<Employee> employeeUsernameList;
    ObservableList<InventoryCall> inventoryCallList;
    Employee employee = (Employee)ChatClient.userloginController.getUser();
    
    /**
     * Gets the inventory calls from DB, and set it to the table.
     *
     */
    public void getInventoryCall() {
    	
    	
		
		txtRegion.setText("Region: " + employee.getRegion());
    	

  		
  		msg.setData(employee);
		msg.setTask("Get Inventory Call Manager");
    	ClientUI.chat.accept(msg);
    	
    	
		inventoryCallList = InventoryCallEntityController.getInvetoryCallList();
		
		
		inventoryCallTable.setItems(inventoryCallList);
		
		callID.setCellValueFactory(new PropertyValueFactory<>("callID"));
		DeviceID.setCellValueFactory(new PropertyValueFactory<>("deviceID"));
		EmployeeID.setCellValueFactory(new PropertyValueFactory<>("employeeID"));
		dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
		status.setCellValueFactory(new PropertyValueFactory<>("status"));
    	
    }
    
    
  	/**
     * Initialize: 
     * get device list by region and logistics employee list
     * in order to allow the manager create new inventory call
     * 
     */
	  @FXML
  	public void initialize() {
  		
  		
  		getInventoryCall();

		
		/*
		 *  Get device list by regional employee location
		 * 
		 */
		
		msg.setData(employee);
		msg.setTask("Get Device List");
    	ClientUI.chat.accept(msg);
		
    	ObservableList<Device> deviceList = DeviceEntityController.getDeviceList();
		
    	for(Device device : deviceList) {
    		
    		deviceIDList.add(device.getDeviceID());
    		
    	}
    	
    	comboDevice.setItems(deviceIDList);
    	
    	if(!deviceIDList.isEmpty())
    		comboDevice.getSelectionModel().select(0);
    	
    	
    	/*
		 *  Get Logistics employee list 
		 * 
		 */
    	
		msg.setData(employee);
		msg.setTask("Get Logistics Employee");
    	ClientUI.chat.accept(msg);
    	
    	employeeUsernameList = UserEntityController.getEmployeeList();
		
    	for(User user : employeeUsernameList) {
    		
    		employeeDetailsList.add(user.getUserName());
    		
    	}
    	
    	comboEmployee.setItems(employeeDetailsList);
    	
    	if(!deviceIDList.isEmpty())
    		comboEmployee.getSelectionModel().select(0);
    	
    	
    	

  		
  	}
    

    /**
     * Click create call: let the manager create new inventory call, 
     * also check if there's already open call for the specific device
     *
     * @param event ActionEvent
     */
    @FXML
    void clickCreateCall(ActionEvent event) {

    	
    	
    	for(InventoryCall c : inventoryCallList) {
    		
    		int deviceIDCombo = Integer.valueOf(comboDevice.getValue()); 
    		
    		if(c.getDeviceID() == deviceIDCombo && c.getStatus().equals("OPEN")) {
    			
    			
    			ChangeScene.showInformationAlert("There's already opened call for this device");
    			return;
    			
    		}
    		
    	}
    	
    	
    	
    	
    	InventoryCall inventoryCall = new InventoryCall();
    	
    	inventoryCall.setDeviceID(comboDevice.getValue());

    	inventoryCall.setEmployeeID(employeeUsernameList.get(employeeDetailsList.indexOf(comboEmployee.getValue())).getEmployeeID());
    	inventoryCall.setDate(new Timestamp(System.currentTimeMillis()));
    	inventoryCall.setStatus("OPEN");
    	
    	inventoryCall.setCreatorUsername(employee.getUserName());
    	
		msg.setData(inventoryCall);
		msg.setTask("Create Inventory Call");
    	ClientUI.chat.accept(msg);
    	
    	
     	String returnMessage = ChatClient.getReturnMessage();
    	
    	if(returnMessage.equals("Falied"))
    		ChangeScene.showInformationAlert("Create Call Failed");
    	else 
    		ChangeScene.showInformationAlert("Create Call Succeeded");
    	
    	getInventoryCall();
    	
    	
    }
    
  	
  	
    /**
     *  Close app and logout EKrut.
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
     * Go back to Regional Manager Menu.
     *
     * @param event the event
     */
    @FXML
    void goBack(MouseEvent event) {
    	ChangeScene scene=new ChangeScene();
		scene.changeScreen(new Stage(),"/clientGUI/RegionalManagerMenu.fxml");
		((Node)event.getSource()).getScene().getWindow().hide(); //hiding primary window
    }

}
