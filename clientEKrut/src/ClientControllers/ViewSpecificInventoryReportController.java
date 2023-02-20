	package ClientControllers;

	import common.Message;
	import entities.Report;
	import entities.User;
import entitiesController.userLoginController;

import java.util.ArrayList;

	import client.ChatClient;
	import client.ClientUI;
	import common.ChangeScene;
	import javafx.collections.FXCollections;
	import javafx.collections.ObservableList;
	import javafx.event.ActionEvent;
	import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.PieChart.Data;
import javafx.scene.control.Button;
	import javafx.scene.control.ComboBox;
	import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
	import javafx.stage.Stage;

	/**
	 * The Class ViewSpecificCustomerReportController is controller for the screen ViewSpecificCustomerReport.fxml.
	 * This class is setup the screen at first and than gives the CEO or to the Regional Manager the specific
	 * report that he choose to view this screen not allow do anything except back and close app only "view mode"
	 * for the report itself
	 */
	public class ViewSpecificInventoryReportController {
		Message connect = new Message();
		
	    @FXML
	    private Button getReportBTN;
	    @FXML
	    private Button backBTN;
		@FXML
		private Text regionTXT;   
		@FXML
		private Text dateTXT; 
		@FXML
		private Text inTXT; 

		@FXML
		private static userLoginController user1=new userLoginController();
		@FXML
		private Text notinTXT;  
		@FXML
		private Text deviceTXT; 
		@FXML
		private Text thresholdTXT; 
		@FXML
		private PieChart pieCHART;
		@FXML
		private static Report repo=new Report();
	    
    	/**
    	 * Initialize.
    	 */

	    @FXML
	    public void initialize() {
	    	String date,deviceid;
	    	int i=0;
	    	user1=ChatClient.getUser();
	    	
            repo=ChatClient.getReport();
	    	regionTXT.setText(repo.getRegion());
	    	date=(repo.getMonth()+"/"+repo.getYear());
	    	dateTXT.setText(date);
	    	deviceid=(repo.getDeviceID()+"");
	    	deviceTXT.setText(deviceid);
	    	thresholdTXT.setText(repo.getthreshold()+"");
	    	inTXT.setText(repo.getitemsInShortage()+"");
	    	notinTXT.setText(repo.getitemsNotInShortage()+"");
	    	ObservableList<PieChart.Data> pieChartData=FXCollections.observableArrayList();
	    	while(repo.getreportList().size()>i)
	    	{
	    	pieChartData.add(new PieChart.Data(repo.getreportList().get(i).getReportName()+"-"+repo.getreportList().get(i).gettotalSales(),repo.getreportList().get(i).gettotalSales()));
	    	i++;
	    	}
	    	pieCHART.setData(pieChartData);
	    

	    }
	    
	    /**
	     * Click on Go back change screen for the user to the previous screen he was in.
	     *
	     * @param event the ActionEvent
	     */
	    @FXML
	    void goBack(ActionEvent event) {
	        	ChangeScene scene=new ChangeScene();
	    		((Node)event.getSource()).getScene().getWindow().hide(); //hiding primary window
	    		scene.changeScreen(new Stage(),"/clientGUI/InventoryReportOption.fxml");
	    }
	    
	    /**
	     * This function is to Click exit and logout and terminate the app completely.
	     *
	     * @param event the ActionEvent
	     */
    	@FXML
	    void clickLogout(ActionEvent event) {
	    	userLoginController.logoutEKrut(event);
		  	Message connect = new Message();
			connect.setTask("Disconnect Server");
			ClientUI.chat.accept(connect);
			System.exit(0);
	    }
	}
