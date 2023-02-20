package serverController;


import entities.ClientStatus;
import entities.Device;
import db.mysqlConnection;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import server.EchoServer;
import server.ServerUI;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import common.Message; 


/**
 * The Class ClientStatusController.
 */
public class ClientStatusController {

 static String port = "5555";
 
 static String DB_PASSWORD ="B0548316212ö!";
 
 static String DBPath="jdbc:mysql://localhost/ekrut?serverTimezone=Asia/Jerusalem";
 
 static String UserName="root";
	
   @FXML
   private Text connectedMessage;
 
   @FXML
   private PasswordField DB_Password;

   @FXML
   private TextField DBpath;

   @FXML
   private TextField Db_UserName;

   @FXML
   private TextField IPaddress;

    @FXML
   private TextField Port;

    @FXML
    private TableView<ClientStatus> clientTable;
    
    @FXML
    private TableColumn<ClientStatus, String> ipColumn;

    @FXML
    private TableColumn<ClientStatus, String> hostColumn;
    
    @FXML
    private ComboBox<String>smartPhoneSimulationComboBox;

    /*
     * @param <S> The type of the TableView generic type
     * @param <T> The type of the content in all cells in this TableColumn
     */
     
    /** The status column. */
    @FXML
    private TableColumn<ClientStatus, String> statusColumn;
    
    /** The installation type. */
    @FXML
    private ComboBox<String> installationType;

    /** The device ID. */
    @FXML
    private ComboBox<String> deviceID;
    
    /** The install btn. */
    @FXML
    private Button installBtn;
    
	/** The install types. */
	ObservableList<String> installTypes = FXCollections.observableArrayList("machine", "remote","merged");
    
	/** The devices. */
	ObservableList<String> devices = FXCollections.observableArrayList();
    
    /**
     * Click install.
     *
     * @param event the event
     */
    @FXML
    void clickInstall(ActionEvent event) {
    	
    	
    	
    	mysqlConnection.updateInstallation(installationType.getValue(),Integer.valueOf(deviceID.getValue()));
    	

    	

    }
    
   
    /**
     * Disconnect.
     *
     * @param event the event
     */
    @FXML
    void Disconnect(ActionEvent event) {
    }
 
    
    /**
     * Close app.
     *
     * @param event the event
     * @throws IOException Signals that an I/O exception has occurred.
     */
    @FXML
    void closeApp(MouseEvent event) throws IOException {
    	System.exit(0);
    }
    
    
    /**
     * Initialize.
     */
    @FXML
    void initialize() {
    	String ip = null;
    	
    	installationType.setItems(installTypes);
    	
    	installationType.getSelectionModel().select(0);
    	
    	
    	
    	
    	try {
    	ip = InetAddress.getLocalHost().getHostAddress();
    	}
    	catch(UnknownHostException e){
    		e.printStackTrace();    		
    	}
    	
    	DBpath.setText(DBPath);
    	Db_UserName.setText(UserName);
    	IPaddress.setText(ip);
    	Port.setText(port);
    	DB_Password.setText(DB_PASSWORD);   
    	
    	
    	
    }
  
    
    /**
     * Gets the devices.
     *
     */
    void getDevices() {
    	
    	
    	
    	Message msg = mysqlConnection.getPickUpDeviceList(null);
    	
    	ArrayList<Device> deviceList = (ArrayList<Device>)msg.getData();
    	
    	for(Device d : deviceList) {
    		
    		
    		devices.add(String.valueOf(d.getDeviceID()));
    		
    		
    	}
    	
    	deviceID.setItems(devices);
    	deviceID.getSelectionModel().select(0);

    	
  
    	
    	
    	
    }
    
    
    /**
     * Connect server.
     *
     * @param event the event
     */
    @FXML
    void connectServer(MouseEvent event) {
    	
    	 ServerUI.startServer();
    	 if(ServerUI.isConnected) {
    		 connectedMessage.setText("Server Started");	
    	 }
    	 else
    		 connectedMessage.setText("Unable to start server");
    	 
    	 clientTable.setItems(EchoServer.getClientsList()); 		 
    	 ipColumn.setCellValueFactory(new PropertyValueFactory<>("ip"));
    	 hostColumn.setCellValueFactory(new PropertyValueFactory<>("host"));
    	 statusColumn.setCellValueFactory(new PropertyValueFactory<>("status")); 
    	 mysqlConnection.connectionDb(DBpath.getText(),Db_UserName.getText(),(DB_Password.getText()));
    	 try {
			DisplayUserList();
			 getDevices();
		} catch (Exception e) {
			System.out.println("Error display user list/ get devices");
		}
		 installBtn.setDisable(false);    	

    }


    /**
     * Import users.
     *
     * @param event the event
     */
    @FXML
    void importUsers(ActionEvent event) {

    	try {
			mysqlConnection.importUsers();
			DisplayUserList();
		} catch (Exception e) {
			System.out.println("Error display user list/ import user");
		}
    	
    }
     
     /**
      * Click onsmart phone simulation combo box.
      *
      * @param event the event
      */
     @FXML
    void clickOnsmartPhoneSimulationComboBox(ActionEvent event) {
    	
    	if(EchoServer.Customer.size() !=0) {
    		EchoServer.CustomerPhoneSimulation.setUserName((smartPhoneSimulationComboBox.getSelectionModel()).getSelectedItem());
    	}
    }
    
   
    
    
    /**
     * Display user list for smartphone login simulation combobox.
     */
    public void DisplayUserList() {

    	smartPhoneSimulationComboBox.setItems(EchoServer.Customer);
    	if(EchoServer.Customer.size() !=0) {
        	smartPhoneSimulationComboBox.setValue(EchoServer.Customer.get(0));
        	EchoServer.CustomerPhoneSimulation.setUserName(EchoServer.Customer.get(0));
    	}
    	else {
    		smartPhoneSimulationComboBox.setValue("No Approved Customers in DB");
    	}
    	
    }
}
