package ClientControllers;

import java.util.ArrayList;

import client.ChatClient;
import client.ClientUI;
import common.ChangeScene;
import common.Message;
import entities.Employee;
import entities.User;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 * The Class RegistrationRequestManagement.
 * Controller for Registration Request Management screen
 * Let the regional manager option to approve users requests to be a customers
 */
public class RegistrationRequestManagement {

	
	Message msg = new Message();
	
	@FXML
    private TableView<User> userTable;

    @FXML
    private TableColumn<User, String> ID;

    @FXML
    private TableColumn<User, String> firstName;

    @FXML
    private TableColumn<User, String> lastName;

    @FXML
    private TableColumn<User, String> email;

    @FXML
    private TableColumn<User, String> phoneNumber;
    
    @FXML
    private TableColumn<User, String> manage;
    

    @FXML
    private Text txtRegion;

    
    ObservableList<String> pending = FXCollections.observableArrayList("pending customer", "customer");
    ObservableList<User> userList;
    
    

	/**
	 * Initialize.
	 * Gets all pending customer list and sets to table
	 */
	@FXML
	public void initialize() {
		
		Employee employee = (Employee)ChatClient.userloginController.getUser();
		txtRegion.setText("Region: " + employee.getRegion());
		
		msg.setData("");
		msg.setTask("Get Pending Customer");
    	ClientUI.chat.accept(msg);
    	
    	userList = UserEntityController.getUserList();
    	userTable.setItems(userList);
    	
    	ID.setCellValueFactory(new PropertyValueFactory<>("IDNumber"));
    	firstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
    	lastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
    	email.setCellValueFactory(new PropertyValueFactory<>("email"));
    	phoneNumber.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
    	manage.setCellValueFactory(cellData -> new SimpleStringProperty("Click to edit"));

    	
    	manage.setCellFactory(new Callback<TableColumn<User, String>, TableCell<User, String>>() {
			@Override
			public TableCell<User, String> call(TableColumn<User, String> column) {
				return new TableCell<User, String>() {

					@Override
					protected void updateItem(String item, boolean empty) {
						super.updateItem(item, empty);
						if (empty) {
							setGraphic(null);
						} else {

							ComboBox<String> comboBox = new ComboBox<>();
							comboBox.setMaxWidth(Double.MAX_VALUE);
							comboBox.setPrefWidth(Double.MAX_VALUE);		
							
							comboBox.setItems(pending);
							comboBox.setValue("pending customer");
							setGraphic(comboBox);

							comboBox.setOnAction(event -> {

								
									getTableRow().getItem().setUserPermission(comboBox.getValue());
							

							});

						}
					}

				};
			}
		});
    	

    	userTable.setEditable(true);
    	
    	userTable.refresh();
    	   
	}
    

    /**
     * Click save: check which customers status changed to send it to the DB,
     * if no status changed, a message showing to the manager.
     *
     * @param event MouseEvent
     */
    @FXML
    void clickSave(ActionEvent event) {

    	ArrayList<User> customerApprove = new ArrayList<>();
    	
    	
    	userList.forEach((user) -> { 
    		if(user.getUserPermission() != null && user.getUserPermission().equals("customer")) {
    			customerApprove.add(user);
    		}
    				
    	});
    	
    	if(customerApprove.isEmpty()) {
    		ChangeScene.showInformationAlert("No customer selected for approval");
    	}
    	else {
    		
    		msg.setData(customerApprove);
    		msg.setTask("Registration Mangement");
        	ClientUI.chat.accept(msg);
        	
        	
        	
        	String returnMessage = ChatClient.getReturnMessage();
        	
        	if(returnMessage.equals("Falied"))
        		ChangeScene.showInformationAlert("Customer Approval Failed");
        	else 
        		ChangeScene.showInformationAlert("Customer Approval Succeeded");
        	
        	ChangeScene scene=new ChangeScene();
    		scene.changeScreen(new Stage(),"/clientGUI/RegionalManagerMenu.fxml");
    		((Node)event.getSource()).getScene().getWindow().hide(); //hiding primary window
    		
    	}
    	
	
    	
    	
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
