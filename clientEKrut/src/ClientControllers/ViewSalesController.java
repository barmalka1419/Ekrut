package ClientControllers;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

import client.ClientUI;
import common.ChangeScene;
import common.Message;
import entities.Sale;
import entitiesController.SaleController;
import entitiesController.userLoginController;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class ViewSalesController {

	Message msg = new Message();
	
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
    private TableColumn<Sale, String> region;

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
	public void initialize() {
		
		
		msg.setData("");
		msg.setTask("View Sales");
    	ClientUI.chat.accept(msg);
    	
    	ObservableList<Sale> saleList = SaleController.getSaleList();
    	
    	
    	
    	salesTable.setItems(saleList);
    	saleID.setCellValueFactory(new PropertyValueFactory<>("saleID"));
    	discountSize.setCellValueFactory(new PropertyValueFactory<>("discountSize"));
    	startDate.setCellValueFactory(new PropertyValueFactory<>("startDate"));
    	endDate.setCellValueFactory(new PropertyValueFactory<>("endDate"));
    	startHour.setCellValueFactory(new PropertyValueFactory<>("startHour"));
    	endHour.setCellValueFactory(new PropertyValueFactory<>("endHour"));
    	region.setCellValueFactory(new PropertyValueFactory<>("region"));
    	days.setCellValueFactory(new PropertyValueFactory<>("days"));
    	saleType.setCellValueFactory(new PropertyValueFactory<>("saleType"));


    	isActive.setCellValueFactory(cellData -> cellData.getValue().isActiveProperty());
		
	}
    	
    @FXML
    void closeApp(MouseEvent event) {
    	userLoginController.logoutEKrut(event);
	  	Message connect = new Message();
		connect.setTask("Disconnect Server");
		ClientUI.chat.accept(connect);
		System.exit(0);
    }

    @FXML
    void goBack(MouseEvent event) {
     	ChangeScene scene=new ChangeScene();
    		scene.changeScreen(new Stage(),"/clientGUI/MarketingManagerMenu.fxml"); // load service worker menu screen
    		((Node)event.getSource()).getScene().getWindow().hide(); //hiding primary window
    }

}
