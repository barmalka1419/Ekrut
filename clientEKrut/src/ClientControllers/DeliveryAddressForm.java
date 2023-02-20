package ClientControllers;


import common.Message;
import entities.Delivery;
import client.ChatClient;
import client.ClientUI;
import common.ChangeScene;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import util.FormUtil;


/**
 * The Class DeliveryAddressForm.
 * controller for the delivery address form screen
 * ;ets the user enter delivery address for a new delivery order
 */
public class DeliveryAddressForm {

    
    @FXML
    private TextField city;

    @FXML
    private TextField house;

  
    @FXML
    private TextField floor;

   
    @FXML
    private TextField apartment;


    @FXML
    private TextField street;

    @FXML
    private TextArea notes;
    
    @FXML
    private Text errorMessage;
    
    @FXML
    private ComboBox<String> country;
    
    private String enteredNotes, enteredAddress;

    /**
     * Initialize- loads list of countries to cuntry combobox.
     */
    @FXML
    public void initialize() {
    ObservableList<String> Country=FXCollections.observableArrayList("Israel", "UAE");
    country.setItems(Country);
    }


    /**
     * Check the validity of the address entered by the user in the form.
     *
     * @return true, if successful
     */
    public boolean checkForm() {
    	
    	String s_city,s_street,s_houseNumber,s_notes,s_country;
    	
    	s_city = city.getText();
    	s_street = street.getText(); 
    	s_houseNumber = house.getText();
    	s_notes=notes.getText();
    	s_country=country.getValue();
    	
    	
    	
    	if(FormUtil.isBlank(s_city)|| FormUtil.isBlank(s_street) || FormUtil.isBlank(s_houseNumber) || s_country==(null) ) {
    		
    		errorMessage.setText("City,Street,House No. and Country are mandatory fields");
    		return false;
    	}
    	
    	//need to fix, city/street names may contain something other than letters and in some cases even numbers
    	//maybe check that there is at least one character that is not a space/tab etc
    	
    	else if (!FormUtil.CheckValidCityName(s_city)) {
    		errorMessage.setText("City must contain only letters");
    		return false;
		}
    		
  
    	
    	else if(s_street.charAt(0)>='0'&& s_street.charAt(0)<= '9') {
    		errorMessage.setText("street can't start with a digit!"); 
    		return false;
    	}
    
    	
    	else if(!FormUtil.CheckContainOnlyDigit(s_houseNumber)) {
    		errorMessage.setText("House number must contain only digits");
    		return false;
    	}
    	enteredNotes=s_notes;
    	enteredAddress= s_street+" "+s_houseNumber+", "+ s_city+ ", "+s_country; 
    	return true;

    	
    	
    }
    
    
    /**
     * triggered by Clicking on Start Order button.
     *	calls checkform to check that the input is valid.
     * if the address is valid saves the address and transfers the user to ordering screen
     * @param event the event
     */
    @FXML
    void clickStartOrder(ActionEvent event) {
    	if(checkForm()) {
    	Delivery del=new Delivery();
    	del.setAddress(enteredAddress);
    	del.setNotes(enteredNotes);
    	ChatClient.deliveryCont.setDelivery(del);
    	((Node)event.getSource()).getScene().getWindow().hide(); 
		ChatClient.currentScreen.changeScreen(new Stage(),"/clientGUI/Ordering.fxml");
    	}
    }

    /**
     * Close app.
     * closes the app and disconnects the client from the server
     * @param event the event
     */
    @FXML
    void closeApp(MouseEvent event) {
    	Message connect = new Message();
    	connect.setTask("Disconnect Server"); // set disconnect Task
    	ClientUI.chat.accept(connect); // send message to server
    	System.exit(0); // exit the app
    }

    /**
     * Go back.
     *	triggered by clicking on back button
     *	moves to remote order menu screen
     * @param event the event
     */
    @FXML
    void goBack(MouseEvent event) {
    	
    	ChangeScene scene=new ChangeScene();
		scene.changeScreen(new Stage(),"/clientGUI/RemoteOrderMenu.fxml"); // load service worker menu screen
		((Node)event.getSource()).getScene().getWindow().hide(); //hiding primary window

    }

}
