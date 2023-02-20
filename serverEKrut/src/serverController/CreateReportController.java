package serverController;


import entities.ClientStatus;
import db.mysqlConnection;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import server.EchoServer;
import server.ServerUI;
import javafx.event.ActionEvent;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalDate;
import java.util.Timer;
import java.util.TimerTask; 

/**
 * The Class CreateReportController.
 * class for automatically generating monthly reports.
 */
public class CreateReportController {
	
	static int currentMonthInDB=0;
    
    /**
     * Main.
     */
    public static void main() {
        TimerTask task = new TimerTask() {
            public void run() {
                int year=LocalDate.now().getYear();
                int month=LocalDate.now().getMonth().getValue();
            	if(month==1){
        		  month=12;
        		  year--;
        		}
        	     else{
        		  month--;
        	    }
                if(month!=currentMonthInDB){
            	mysqlConnection.CreateOrderReport(year,month);
            	mysqlConnection.CreateCustomerReport(year,month);
            	mysqlConnection.CreateInventoryReport(year,month);
            	currentMonthInDB=month;
                }		  
            }
        };

        Timer timer = new Timer();
        long delay = 0;
        long intevalPeriod = 24 * 60 * 60 * 1000; // 24 hours
        // schedules the task to be run in an interval 
        timer.scheduleAtFixedRate(task, delay, intevalPeriod);
    }
}
