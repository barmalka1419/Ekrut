package ClientControllers;

import java.net.URL;
import java.text.Format;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.scene.control.Button;
import client.ChatClient;
import client.ClientUI;
import common.Message;
import entities.Item;
import entities.Sale;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;


/**
 * The Class ItemPatternController.
 * Controller for item view in the catalog in Ordering screen
 * shows the item's details and lets the user add the item to the cart 
 */
public class ItemPatternController implements Initializable {

	private Item item;
	
	private boolean flag =true;//flag=true if item is not in the cart
	
	private int amount;
    
    @FXML
    private Label TextForNamE;

    @FXML
    private Label TextForPricE;
    
    @FXML
    private Button addToCartBtn;
    
    @FXML
    public  Text outOfStockText;
    
    @FXML
    private Text PriceForDiscount;

    
    private OrderingController mainScreen;
    
    @FXML
    private ImageView onePlusOneImage;
    
    private Sale sale ;


    /**
     * Click on add to cart btn.
     * adds the item to the cart (if there is enough in inventory)
     * updates the amount in the item object accordingly
     * @param event the event
     */
    @FXML
    void ClickOnAddToCartBtn(ActionEvent event) {//act like cart + button
    	int LastAmount= (item.getItemsInDevice()).get(0).getAmount();
    	
    	if(item.getItemsInDevice().get(0).getAmount()==0 && !ChatClient.MyOrder.getOrderType().equals("delivery")) {
    		outOfStockText.setVisible(true);//if amount return to positive set visible false
    	}
    	else {
    		mainScreen.SetcartIsEmptyLable(false);
    		if(flag==true) {
    			flag=false;
    			
    			ClientControllers.OrderingController.AddItemToCart(this.item ,mainScreen,!flag,this);
    			if(!ChatClient.MyOrder.getOrderType().equals("delivery")) {
    				item.getItemsInDevice().get(0).setAmount(LastAmount-1);
    			}

    		}	
    		else {
    			
    			ClientControllers.OrderingController.AddItemToCart(this.item ,mainScreen,flag,this);	
    		}
    	}
    	
    	 
    }
    
    /**
     * Sets the visible text.
     * set out of stock text visible when an item is out of stock 
     * and can't be added to the cart anymore.
     * @param isVisible the is visible
     */
    public void SetVisibleText(boolean isVisible) {
    	outOfStockText.setVisible(isVisible);
    }
    
    /**
     * Sets the add to cart button Enablement.
     *
     * @param isNotClickable the is not clickable
     */
    public void SetAddToCartButton(boolean isNotClickable) {//maybe remove
    	addToCartBtn.setDisable(isNotClickable);
    }
  
    /**
     * Sets the flag that indicates if adding the item to the cart
     * needs to include creating a new row in the cart
     * @param flag the flag
     */
    public void SetFlage(boolean flag) {
    	this.flag=flag;
    }
    
    
	
 	@FXML
	 private ImageView imageView = new ImageView();

	 
	 /**
 	 * Organize pattern.
 	 *	Organizes the item view and applies a sale(if there's one)
 	 * @param item the item
 	 * @param mainScreen the main screen
 	 */
 	// check sale here
    public void OrganizePattern(Item item ,OrderingController mainScreen) {
    	this.mainScreen=mainScreen;
    	outOfStockText.setVisible(false);
    	this.item=item;
    	this.amount = (item.getItemsInDevice()).get(0).getAmount();
    	
    	
    	if(sale != null) {
    		float discount = ((100-sale.getDiscountSize())*item.getPrice())/100;
    		if(sale.getSaleType().equals("discountSize")) {
    			PriceForDiscount.setText((String.format("%.2f", item.getPrice()))+"$");
    			PriceForDiscount.setVisible(true);
    			TextForPricE.setText((String.format("%.2f",discount))+"$");
        		item.setPrice(discount);
        	}
    		else if (sale.getSaleType().equals("1+1")) {
    			onePlusOneImage.setVisible(true);
        		TextForPricE.setText(String.valueOf(String.format("%.2f",item.getPrice()))+"$");
    		}
    		javafx.scene.image.Image i = new javafx.scene.image.Image(item.getPhoto());
        	imageView.setImage(i);
    		
    	}
    	else {

    	if(amount>0) {	
    		TextForNamE.setText(item.getName());
        	TextForPricE.setText(String.valueOf(String.format("%.2f", item.getPrice()))+"$");
        	javafx.scene.image.Image i = new javafx.scene.image.Image(item.getPhoto());
        	imageView.setImage(i);
    	}
    	

    }
    	TextForNamE.setText(item.getName());
  }
    
    /**
     * Sets the main screen.
     * @param ordering the new main screen
     */
    public void setMainScreen(OrderingController ordering) {
    	mainScreen=ordering;
    }
	
	/**
	 * Initialize- sets sales view invisible and check if there is a sale.
	 * @param arg0 the arg 0
	 * @param arg1 the arg 1
	 */
	public void initialize(URL arg0, ResourceBundle arg1) {
		PriceForDiscount.setVisible(false);
		onePlusOneImage.setVisible(false);
		
		try {
			
		sale = ChatClient.saleController.getSalesInDevice();
		}
		catch (Exception e ) { //there is no sales 
			e.printStackTrace();
		}	
		
	}


    
    

}

