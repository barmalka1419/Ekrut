package ClientControllers;

import java.util.ArrayList;

import client.ChatClient;
import client.ClientUI;
import common.ChangeScene;
import common.Message;
import entities.Device;
import entities.Employee;
import entities.User;
import entitiesController.DeviceEntityController;
import entitiesController.UserEntityController;
import entitiesController.userLoginController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;

/**
 * The Class ThresholdMangementController.
 * Controller for Threshold Management screen
 * Let the regional manager option to set device threshold 
 */
public class ThresholdMangementController {

    @FXML
    private TableView<Device> deviceTable;
    
    @FXML
    private TableColumn<Device, Integer> deviceID;

    @FXML
    private TableColumn<Device, String> locationCol;

    @FXML
    private TableColumn<Device, Integer> threshold;
	
    @FXML
    private Text txtRegion;
    
    
	Message msg = new Message();
	ArrayList<Device> thresholdChanged = new ArrayList<>();
    
	/**
	 * Initialize.
	 * Sets employee's region, also get device list and sets to table
	 */
	@FXML
	public void initialize() {
		

		
		Employee employee = (Employee)ChatClient.userloginController.getUser();
		txtRegion.setText("Region: " + employee.getRegion());
		
		
		msg.setData(employee);
		msg.setTask("Get Device List");
    	ClientUI.chat.accept(msg);
		
		
		ObservableList<Device> deviceList = DeviceEntityController.getDeviceList();
		
		
		deviceTable.setItems(deviceList);
		
	  	deviceID.setCellValueFactory(new PropertyValueFactory<>("deviceID"));
	  	locationCol.setCellValueFactory(new PropertyValueFactory<>("location"));
    	threshold.setCellValueFactory(new PropertyValueFactory<>("threshold"));
    	threshold.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
    	
    	threshold.setOnEditCommit(event -> {
  
    		// if threshold is empty or negative value
    		if(event.getNewValue() != null && event.getNewValue() < 0) {
    			
    			// if Device already in changed list, then remove it 
    			if(thresholdChanged.contains(event.getRowValue()))
    				thresholdChanged.remove(event.getRowValue());
    			
    			ChangeScene.showInformationAlert("Threshold should be non negative value");
 
    			return;
   
    			
    		}
    		// if threshold is not null and value is non-negative
    		if(event.getNewValue() != null && event.getNewValue() >= 0) {
    			
    			// if it's new device, add it to changed list and update threshold
    			if(!thresholdChanged.contains(event.getRowValue())) {
    				event.getRowValue().setThreshold(event.getNewValue());
    				thresholdChanged.add(event.getRowValue());
    			}
    			// if it's not new device in the changed list
    			else {
    				
    				// set new threshold to the device in the changed list
    				thresholdChanged.get(thresholdChanged.indexOf(event.getRowValue())).setThreshold(event.getNewValue());
    				
    			}
    			
    			
    			
    			
    		}
    		
    		
		});
    	
    	
    	
    	deviceTable.setEditable(true);
    	
		
	}
	
    
    
    /**
     * Click save: if none of the devices threshold changed then shows error,
     * otherwise, send devices let to DB and update the threshold.
     *
     * @param event the event
     */
    @FXML
    void clickSave(ActionEvent event) {
    	
 
    	
    	if(thresholdChanged.isEmpty()) {
    		ChangeScene.showInformationAlert("None of the devices threshold changed");
    	}
    	else {
    		
    		msg.setData(thresholdChanged);
    		msg.setTask("Set Threshold");
        	ClientUI.chat.accept(msg);
        	
        	
        	
        	String returnMessage = ChatClient.getReturnMessage();
        	
        	if(returnMessage.equals("Falied"))
        		ChangeScene.showInformationAlert("Threshold Update Failed");
        	else 
        		ChangeScene.showInformationAlert("Threshold Update Succeeded");
        	
        	ChangeScene scene=new ChangeScene();
    		scene.changeScreen(new Stage(),"/clientGUI/RegionalManagerMenu.fxml");
    		((Node)event.getSource()).getScene().getWindow().hide(); //hiding primary window
    		
    		
    	}
    	
    	
    }

    /**
     * Close app.
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

    /**
     * Go back to Regional Manager Menu.
     *
     * @param event MouseEvent
     */
    @FXML
    void goBack(MouseEvent event) {
    	ChangeScene scene=new ChangeScene();
		scene.changeScreen(new Stage(),"/clientGUI/RegionalManagerMenu.fxml");
		((Node)event.getSource()).getScene().getWindow().hide(); //hiding primary window
    }

}
