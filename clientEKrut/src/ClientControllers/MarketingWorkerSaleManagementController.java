package ClientControllers;

import java.util.ArrayList;

import client.ChatClient;
import client.ClientUI;
import common.ChangeScene;
import common.Message;
import entities.Employee;
import entities.InventoryCall;
import entities.ItemInDevice;
import entities.Sale;
import entitiesController.InventoryCallEntityController;
import entitiesController.SaleController;
import entitiesController.userLoginController;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 * The Class MarketingWorkerSaleManagementController.
 * Controller for Marketing Worker Sale Management screen
 * Let the marketing worker active and deactivate sales
 */
public class MarketingWorkerSaleManagementController {

    @FXML
    private TableView<Sale> salesTable;

    @FXML
    private TableColumn<Sale, Integer> saleID;

    @FXML
    private TableColumn<Sale, String> startDate;

    @FXML
    private TableColumn<Sale, String> endDate;

    @FXML
    private TableColumn<Sale, String> discountSize;

    @FXML
    private TableColumn<Sale, String> days;

    @FXML
    private TableColumn<Sale, String> startHour;

    @FXML
    private TableColumn<Sale, String> endHour;

    @FXML
    private TableColumn<Sale, String> saleType;

    @FXML
    private TableColumn<Sale, String> isActive;

    @FXML
    private Text txtRegion;


    
    
    Message msg = new Message();
    ArrayList<Sale> changedSaleList = new ArrayList<>();
    Employee employee = (Employee)ChatClient.userloginController.getUser();
    
	/**
	 * Initialize.
	 * Sets region, get sales by employee's region and sets it to table  
	 */
	@FXML
	public void initialize() {
		
    	txtRegion.setText("Region: " + employee.getRegion());
		
	
		msg.setData(employee);
		msg.setTask("View Sales By Region");
    	ClientUI.chat.accept(msg);
    	
    	ObservableList<Sale> saleList = SaleController.getSaleList();
    	
    	
    	
    	salesTable.setItems(saleList);
    	saleID.setCellValueFactory(new PropertyValueFactory<>("saleID"));
    	discountSize.setCellValueFactory(new PropertyValueFactory<>("discountSize"));
    	startDate.setCellValueFactory(new PropertyValueFactory<>("startDate"));
    	endDate.setCellValueFactory(new PropertyValueFactory<>("endDate"));
    	startHour.setCellValueFactory(new PropertyValueFactory<>("startHour"));
    	endHour.setCellValueFactory(new PropertyValueFactory<>("endHour"));
    	days.setCellValueFactory(new PropertyValueFactory<>("days"));
    	saleType.setCellValueFactory(new PropertyValueFactory<>("saleType"));

  
    	
    	
    	isActive.setCellFactory(new Callback<TableColumn<Sale, String>, TableCell<Sale, String>>() {
		    @Override
		    public TableCell<Sale, String> call(TableColumn<Sale, String> column) {
		        return new TableCell<Sale, String>() {
		            // Create a button for the cell
		            Button on = new Button("On");
		            Button off = new Button("Off");
	
		            
		            

		            {
		            	//on.setBackground(background);
		            	/*
		            	 * 
		            	 * 	 Turn off sale
		            	 * 
		            	 */
		            	
		            	on.setStyle("-fx-background-color: green; -fx-text-fill: white;");
		            	on.setMaxWidth(Double.MAX_VALUE);
		                // Add an action event for the button
		            	on.setOnAction((event) -> {
		                	
		
		            		
		            		if(!changedSaleList.contains(getTableRow().getItem())) {
		            			
		            			getTableRow().getItem().setActive(false);
		            			changedSaleList.add(getTableRow().getItem());
		            		}
		            		else {
		            			
		            			
		            			changedSaleList.get(changedSaleList.indexOf(getTableRow().getItem())).setActive(false);
		            			
		            		}

		            		
		            	    	on.setVisible(false);
		            	    	off.setVisible(true);
		                
		            
		                });
		            	
		             	/*
		            	 * 
		            	 * 	 Turn on sale
		            	 * 
		            	 */
		            	
		            	
		            	
		            	off.setStyle("-fx-background-image: url('/GuiAssets/switch-off.png')");
		            	off.setStyle("-fx-background-color: red; -fx-text-fill: white;");
		            	off.setMaxWidth(Double.MAX_VALUE);
		                // Add an action event for the button
		            	off.setOnAction((event) -> {
		                	
		            		
		            		
		              		
		            		if(!changedSaleList.contains(getTableRow().getItem())) {
		            			
		            			getTableRow().getItem().setActive(true);
		            			changedSaleList.add(getTableRow().getItem());
		            		}
		            		else {
		            			
		            			
		            			changedSaleList.get(changedSaleList.indexOf(getTableRow().getItem())).setActive(true);
		            			
		            		}
		            		
		            		

		                     on.setVisible(true);
		                     off.setVisible(false);
		             
		          	
		            			
		            
		                });
		            	
		            	
		            	
		            	
		            }

		            @Override
		            protected void updateItem(String item, boolean empty) {
		                super.updateItem(item, empty);
		                if (empty) {
		                    setGraphic(null);
		                } else {
		                    // Set the button as the graphic for the cell
		                	
		                	boolean isActiveFlag = this.getTableRow().getItem().isActive();
		                	
		               

		                     StackPane buttonContainer = new StackPane(on, off);
		                     
		             
		                     on.setVisible(isActiveFlag);
		                     off.setVisible(!isActiveFlag);
		                     
		                     
		                     setGraphic(buttonContainer);
		                	
		                    
		                }
		            }
		        };
		    }
		});
    	
    	
    	
    	
    	salesTable.setEditable(true);
    	
    	
    	
    	
		
	}
    

    /**
     * Clicking save, check if at least one sale status changed, then send it to DB,
     * otherwise show the employee message
     *
     * @param event ActionEvent
     */
    @FXML
    void clickSave(ActionEvent event) {

    	
    	if(changedSaleList.isEmpty()) {
    		ChangeScene.showInformationAlert("None of the sales status changed");
    	}
    	else {
    		
    		msg.setData(changedSaleList);
    		msg.setTask("Set Sale Status");
        	ClientUI.chat.accept(msg);
        	
        	
        	
        	String returnMessage = ChatClient.getReturnMessage();
        	
        	if(returnMessage.equals("Falied"))
        		ChangeScene.showInformationAlert("Sale Set Status Failed");
        	else 
        		ChangeScene.showInformationAlert("Sale Set Status Succeeded");
        	
        	ChangeScene scene=new ChangeScene();
    		scene.changeScreen(new Stage(),"/clientGUI/MarketingWorkerMenu.fxml");
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
	 * Go back to Marketing Worker Menu.
	 *
	 * @param event the event
	 */
	@FXML
	void goBack(MouseEvent event) {
	 	ChangeScene scene=new ChangeScene();
			scene.changeScreen(new Stage(),"/clientGUI/MarketingWorkerMenu.fxml"); // load service worker menu screen
			((Node)event.getSource()).getScene().getWindow().hide(); //hiding primary window
	}

}
