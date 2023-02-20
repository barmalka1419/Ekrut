package ClientControllers;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

import client.ChatClient;
import client.ClientUI;
import common.ChangeScene;
import common.Message;
import entities.Sale;
import entitiesController.userLoginController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import util.FormUtil;

/**
 * The Class DefineSaleController.
 * Let the Marketing Employee define a new sale pattern
 * 
 */
public class DefineSaleController {

    @FXML
    private ComboBox<String> region;

    @FXML
    private CheckBox hourlySale;

    @FXML
    private DatePicker startDate;

    @FXML
    private DatePicker endDate;

    @FXML
    private ComboBox<String> saleType;
    
    @FXML
    private TextField startTime;

    @FXML
    private TextField endTime;

    @FXML
    private TextField discount;

    @FXML
    private CheckBox sun;

    @FXML
    private CheckBox mon;

    @FXML
    private CheckBox tue;

    @FXML
    private CheckBox wed;

    @FXML
    private CheckBox thu;

    @FXML
    private CheckBox fri;

    @FXML
    private CheckBox sat;
    
    @FXML
    private Text errorMessage;

    boolean hourly = false,isDiscount = false;
    Message connect = new Message();
    Sale sale;

    /**
     * Initialize.
     * 
     * Set the regions and sale types
     * 
     */
    @FXML
	public void initialize() {
    	ObservableList<String> regionData = FXCollections.observableArrayList("UAE", "south", "north");
    	ObservableList<String> saleTypeData = FXCollections.observableArrayList("1+1", "discountSize");
    	
    	region.setItems(regionData);
    	saleType.setItems(saleTypeData);
    }
    
    /**
     * Select sale type: discountSize or 1+1
     *
     * @param event ActionEvent
     */
    @FXML
    void selectType(ActionEvent event) {
    		if(saleType.getValue().equals("discountSize")) {
    			isDiscount = true;
    			discount.setDisable(false);
    		}
    			
    		else  {
    			isDiscount = false;
    			discount.setDisable(true);
    		}
    		
    }
    
    /**
     * Sets the error for wrong input.
     *
     * @param msgError error message
     */
    void setError(String msgError) {
    	
    	errorMessage.setText(msgError);
    }
    
    /**
     * Go back to Marketing Manager Menu.
     *
     * @param event the event
     */
    @FXML
    void goBack(MouseEvent event) {
    	
    	ChangeScene scene=new ChangeScene();
		scene.changeScreen(new Stage(),"/clientGUI/MarketingManagerMenu.fxml"); // load service worker menu screen
		((Node)event.getSource()).getScene().getWindow().hide(); //hiding primary window

    }
    
    
    
    /**
     * Click define sale.
     * Save all sale parameters and check form validation
     *
     * @param event ActionEvent
     */
    @FXML
    void clickDefineSale(ActionEvent event) {
    	

    
    	Time startTimeD = Time.valueOf(LocalTime.of(0, 0));
    	Time endTimeD = Time.valueOf(LocalTime.of(23, 59, 59));
    	
    	Date now = Date.valueOf(LocalDate.now());

    	if(startDate.getValue() == null || endDate.getValue() == null) {
    		errorMessage.setText("Sale must contain start and end date");
    		return;
    		
    	}
    	
    	Date  startDate_s = Date.valueOf(startDate.getValue());
    	Date  endDate_s = Date.valueOf(endDate.getValue());
    	
    	
    	if(startDate_s.after(endDate_s)) {
    		errorMessage.setText("Start date must be before end date");
    		return;
    	}
    	if(now.after(startDate_s)){
    		errorMessage.setText("Start date is in the past");
    		return;
    	}
    	if(hourly){
    			
    		if(startTime.getText().isEmpty() || endTime.getText().isEmpty()) {
    			errorMessage.setText("Start time and end time can't be empty");
    			return;
    		}
    		else {
    			
    		    try {
        	    	
    		    	if(startTime.getText().length() != 5 || endTime.getText().length() != 5)
    		    		throw new NullPointerException();
    		    		
    		    		startTimeD = Time.valueOf(startTime.getText() + ":00");
    		    		endTimeD =  Time.valueOf(endTime.getText() + ":00");
    		    		
    		    		if(startTimeD.after(endTimeD)) {
    		    			errorMessage.setText("Start time should be before end time");
    		    			return;
    		    		}
    		    	
        	        
        	        
        	    } catch (Exception e) {
        	    	
        	    	errorMessage.setText("Start time and end time format should be hh:mm");
        	    	return;
        	    }	
        	 
    		}
    		
    		
    	}
    	if(saleType.getValue() == null) {
    		
    		errorMessage.setText("You've to select sale type.");
    		return;
    		
    	}
    	
    	if(isDiscount) {
    		
    		if(discount.getText().isEmpty()) {
    			errorMessage.setText("Discount can't be empty"); 
    			return;
    		}
    		else if(!FormUtil.CheckContainOnlyDigit(discount.getText())) {
    			errorMessage.setText("Discount must contain only digits"); 
    			return;
    		}
    		else {
    			
    			int discountNum = Integer.parseInt(discount.getText());
    			
    					if(!(discountNum > 0 && discountNum < 100)) {
    						errorMessage.setText("Discount should be in this range: 1-99");
    						return;
    					}
    					
    				
    		}
    		
    	
    	}
    	
    
    		
    		
    		Sale sale = new Sale();
    		
    		sale.setStartDate(startDate_s);
			sale.setEndDate(endDate_s);
    		sale.setStartHour(startTimeD);
    	    sale.setEndHour(endTimeD);		
    		sale.setRegion(region.getValue());
    		
    		if(!isDiscount) {
    			sale.setDiscountSize(0);
    		}
    		else 
    			sale.setDiscountSize(Integer.parseInt(discount.getText()));
    		
    		
    		ArrayList<String> days= new ArrayList<String>();
    		
    		if(sun.isSelected())
    			days.add("sun");
    		if(mon.isSelected())
    			days.add("mon");
    		if(tue.isSelected())
    			days.add("tue");
    		if(wed.isSelected())
    			days.add("wed");
    		if(thu.isSelected())
    			days.add("thu");
    		if(fri.isSelected())
    			days.add("fri");
    		if(sat.isSelected())
    			days.add("sat");
    		
    	
    		if(days.isEmpty()) {
    			
    			errorMessage.setText("You've to select at least one day");
				return;
    			
    			
    		}
    		
    		sale.setDays(days);
    		sale.setSaleType(saleType.getValue());//change enum to public in entities package
    		

        	connect.setTask("Define Sale");
        	connect.setData(sale);
        	ClientUI.chat.accept(connect); // send the message to server
    		
    	
        	String returnMessage = ChatClient.getReturnMessage();
        	
        	if(returnMessage.equals("Falied"))
        		ChangeScene.showInformationAlert("Define Sale Failed");
        	else 
        		ChangeScene.confirmationAlert("MarketingManagerMenu",event);

    		
        	
    	
    	
    
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
    	connect.setTask("Disconnect Server"); // set disconnect Task
    	ClientUI.chat.accept(connect); // send message to server
    	System.exit(0); // exit the app
    }

    /**
     * toggle hourly sale
     *
     * @param event ActionEvent
     */
    @FXML
    void clickedHourly(ActionEvent event) {
    	hourly = !hourly;
    	startTime.setDisable(!hourly);
    	endTime.setDisable(!hourly);
    }
    
}
