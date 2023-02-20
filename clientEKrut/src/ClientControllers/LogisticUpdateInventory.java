package ClientControllers;

import java.util.ArrayList;
import java.util.List;

import client.ChatClient;
import client.ClientUI;
import common.ChangeScene;
import common.Message;
import entities.Device;
import entities.InventoryCall;
import entities.Item;
import entities.ItemInDevice;
import entitiesController.InventoryCallEntityController;
import entitiesController.ItemInDeviceEntityController;
import entitiesController.userLoginController;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.converter.IntegerStringConverter;

/**
 * The Class LogisticUpdateInventory.
 * Controller for logistics update inventory screen
 * Let the logistics employee view specific call and update the inventory
 */
public class LogisticUpdateInventory {

    @FXML
    private TableView<ItemInDevice> itemInDeviceTable;

    @FXML
    private TableColumn<ItemInDevice, String> name;

    @FXML
    private TableColumn<ItemInDevice, String> photo;

    @FXML
    private TableColumn<ItemInDevice, Number> serialNumber;

    @FXML
    private TableColumn<ItemInDevice, Number> price;

    @FXML
    private TableColumn<ItemInDevice, Integer> amount;

    @FXML
    private TableColumn<ItemInDevice, Integer> addedAmount;

    @FXML
    private Text deviceIDText;

    @FXML
    private Text thresholdText;

    @FXML
    private Text callIDText;
    
    
	ObservableList<ItemInDevice> itemInDeviceList;
	InventoryCall inventoryCall = InventoryCallEntityController.getInventoryCall();
	ArrayList<ItemInDevice> addedAmountChanged = new ArrayList<>();
    Message msg = new Message();

    
	/**
	 * Initialize.
	 * Gets list of items that require inventory update of specific device and sets it to the table 
	 */
	@FXML
  	public void initialize() {
  		
		
		deviceIDText.setText(String.valueOf(inventoryCall.getDeviceID()));
		callIDText.setText(String.valueOf(inventoryCall.getCallID()));
		
  		msg.setData(inventoryCall);
		msg.setTask("Get Item In Device");
    	ClientUI.chat.accept(msg);
    	
    	
    	itemInDeviceList = ItemInDeviceEntityController.getItemInDeviceList();
		
    	/*
    	 * 
    	 *  check if it's possible to create call without that any item in device will be below threshold
    	 *  
    	 *  in that case, thresholdText can be empty and a fix will be needed
    	 * 
    	 */
    	
    	if(!itemInDeviceList.isEmpty())
    		thresholdText.setText(String.valueOf(itemInDeviceList.get(0).getDevice().getThreshold()));
    	
    	
    	itemInDeviceTable.setItems(itemInDeviceList);
		
    	name.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getItem().getName()));
   
    	
    	photo.setCellFactory(new Callback<TableColumn<ItemInDevice, String>, TableCell<ItemInDevice, String>>() {
		    @Override
		    public TableCell<ItemInDevice, String> call(TableColumn<ItemInDevice, String> column) {
		        return new TableCell<ItemInDevice, String>() {	
		        	
		            private ImageView imageView;
		   
		            @Override
		            public void updateItem(String item, boolean empty) {
		                
		                if (empty) {
		                    setGraphic(null);
		                } else {
		  
		    
		                	String row = this.getTableRow().getItem().getItem().getPhoto();
		                	imageView = new ImageView(new Image(row));
		                    imageView.setFitHeight(40);
		                    imageView.setFitWidth(40);
		                    setGraphic(imageView);
		                }
		            }
		        	
		        	
		        	
				        
		        };
		    }
    	});
    	

    	
    	serialNumber.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getItem().getSerialNumber()));
    	price.setCellValueFactory(cellData -> new SimpleFloatProperty(cellData.getValue().getItem().getPrice()));
    	amount.setCellValueFactory(new PropertyValueFactory<>("amount"));
    
    	addedAmount.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
    	
    	addedAmount.setCellValueFactory(new PropertyValueFactory<>("addedAmount"));
    	  	
    	addedAmount.setOnEditCommit(event -> {

	    		// if amount is empty or non-positive value 
	    		if(event.getNewValue() != null && event.getNewValue() <= 0) {
	    			
	    			// if ItemInDevice already in changed list, then remove it 
	    			if(addedAmountChanged.contains(event.getRowValue()))
	    				addedAmountChanged.remove(event.getRowValue());
	    			
	    			if(event.getNewValue() < 0)
	    				ChangeScene.showInformationAlert("Added Amount should be non negative value");
	 
	    			return;
	   
	    			
	    		}
	   		
	    		
	    		// if amount is not null and value is non-negative
	    		if(event.getNewValue() != null && event.getNewValue() > 0) {
	    			
	    			if(event.getRowValue().getAmount()+event.getNewValue() < event.getRowValue().getDevice().getThreshold()) {
	    				
	    				ChangeScene.showInformationAlert("The sum of Added Amount and Amount should be above or equal to threshold: " + event.getRowValue().getDevice().getThreshold());
	    				return;
	    				
	    				
	    			}
	    			
	    			// if it's new ItemInDevice, add it to changed list and update amount
	    			if(!addedAmountChanged.contains(event.getRowValue())) {
	    				
	    				event.getRowValue().setAddedAmount(event.getNewValue());
	    				addedAmountChanged.add(event.getRowValue());
	    			}
	    			// if it's not new ItemInDevice in the changed list
	    			else {
	    				
	    				// set new amount to the item in the changed list
	    				addedAmountChanged.get(addedAmountChanged.indexOf(event.getRowValue())).setAddedAmount(event.getNewValue());
	    				
	    			}
	    			
	    		}
    	});
    	
    	itemInDeviceTable.setEditable(true);
    	
    	
    	
    	
	}
    
	
    /**
     * Click save, get all items with the new amount and update the DB accordingly, 
     * also check that all the items were updated and the new amount + current amount is above threshold,
     * In addition, close inventory call 
     *
     * @param event ActionEvent
     */
    @FXML
    void clickSave(ActionEvent event) {

    	
    	if(addedAmountChanged.isEmpty()) {
    		ChangeScene.showInformationAlert("None of the items amount changed");
    		return;
    	}
    	
    	if(addedAmountChanged.size() != itemInDeviceList.size()) {
    		
    		ChangeScene.showInformationAlert("You've to update added amount for all items");
    		return;
    		
    	}
    	
   
    		
    	msg.setData(inventoryCall);
		msg.setTask("Close Inventory Call");
    	ClientUI.chat.accept(msg);
    	
    		
		msg.setData(addedAmountChanged);
		msg.setTask("Update Inventory");
    	ClientUI.chat.accept(msg);
    	
    	
    	
    	String returnMessage = ChatClient.getReturnMessage();
    	
    	if(returnMessage.equals("Falied"))
    		ChangeScene.showInformationAlert("Inventory Update Failed");
    	else 
    		ChangeScene.showInformationAlert("Inventory Update Succeeded");
    	
    	ChangeScene scene=new ChangeScene();
		scene.changeScreen(new Stage(),"/clientGUI/LogisticsWorkerMenu.fxml");
		((Node)event.getSource()).getScene().getWindow().hide(); //hiding primary window
    		
    		
    	
    	
    	
    	
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

    /**
     * Go back to Logistics Worker View Calls screen.
     *
     * @param event MouseEvent
     */
    @FXML
    void goBack(MouseEvent event) {
    	ChangeScene scene=new ChangeScene();
		scene.changeScreen(new Stage(),"/clientGUI/LogisticsWorkerViewCalls.fxml");
		((Node)event.getSource()).getScene().getWindow().hide(); //hiding primary window
    }

}
