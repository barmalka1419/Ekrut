	package ClientControllers;

	import common.Message;
	import entities.Report;
	import entities.User;
import entitiesController.userLoginController;

import java.util.ArrayList;
import java.util.Arrays;

import client.ChatClient;
	import client.ClientUI;
	import common.ChangeScene;
	import javafx.collections.FXCollections;
	import javafx.collections.ObservableList;
	import javafx.event.ActionEvent;
	import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.PieChart.Data;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.Button;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.CategoryAxis;
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
	public class ViewSpecificCustomerReportController {

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
		private Text activityLevelTXT; 

		@FXML
		private static userLoginController user1=new userLoginController();
		@FXML
		private Text orderAverageTXT; 
		@FXML
		private Text numberOfClientOrderedTXT; 

		@FXML
		private BarChart<?, ?> barCHART ;
		@FXML
		private static Report repo=new Report();
	    
    	/**
    	 * Initialize.
    	 */

	    @FXML
	    public void initialize() {
	    	String date;
	    	user1=ChatClient.getUser();
	    
            repo=ChatClient.getReport();
	    	regionTXT.setText(repo.getRegion());
	    	date=(repo.getMonth()+"/"+repo.getYear());
	    	dateTXT.setText(date);
	    	activityLevelTXT.setText(repo.getactivityLevel()+"");
	    	orderAverageTXT.setText(repo.getorderAverage()+"");
	    	numberOfClientOrderedTXT.setText(repo.getnumberOfClientOrdered()+"");
	    
	    	CategoryAxis xAxis = new CategoryAxis();
	        xAxis.setLabel("Devices");

	        NumberAxis yAxis = new NumberAxis();
	        yAxis.setLabel("Visits");

	        XYChart.Series dataSeries1 = new XYChart.Series();
	        XYChart.Series dataSeries2 = new XYChart.Series();
	        XYChart.Series dataSeries3 = new XYChart.Series();
	        XYChart.Series dataSeries4 = new XYChart.Series();
	        XYChart.Series dataSeries5 = new XYChart.Series();
	        
	        dataSeries1.setName("Below 3 Orders");
	        dataSeries2.setName("3 To 8 Orders");
	        dataSeries3.setName("8 To 12 Orders");
	        dataSeries4.setName("12 To 15 Orders");
	        dataSeries5.setName("16 Or Above Orders");
	        dataSeries1.getData().add(new XYChart.Data("  ", repo.getbelow3orders()));
	        dataSeries2.getData().add(new XYChart.Data("  "  , repo.get4to8orders()));
	        dataSeries3.getData().add(new XYChart.Data("  "  , repo.get9to12orders()));
	        dataSeries4.getData().add(new XYChart.Data("  ", repo.get13to15orders()));
	        dataSeries5.getData().add(new XYChart.Data("  "  , repo.get16oraboveorders()));
	        barCHART.getData().addAll(dataSeries1,dataSeries2,dataSeries3,dataSeries4,dataSeries5);

	        //VBox vbox = new VBox(barChart1);
	       // barCHART.setData(barCHART);
	          
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
	    		scene.changeScreen(new Stage(),"/clientGUI/CustomerReportOption.fxml");
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