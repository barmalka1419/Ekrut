package ClientControllers;

import client.ChatClient;
import entities.Item;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.Button;

/**
 * The Class CartPatternController.
 * controller for a cart row
 * holds information of the item in the row
 */ 
public class CartPatternController {
    
	
	private int i = 1 ;//canceled static to fix shared quantity bug
	

	private int MyIndex = 0;


	public Item cartItem;//added variable to keep item object
	
	
	private  OrderingController primaryScreen;//added variable
   
 
    @FXML
    private TextField AmountItemsCart;//disable editing with keyboard

   
    @FXML
    private ImageView ItemImageCart=new ImageView();

   
    @FXML
    private Label NameItemLable;

    
    @FXML
    private Label PriceLableItem;
    
 
    private ItemPatternController ItemScreen;
    
  
    @FXML
    private ImageView PlusBtn;

   
    @FXML
    private ImageView LessBtn;

  
    @FXML
    private ImageView RemoveBtn;
    

    @FXML
    private Button plus;

   


    @FXML
    private Button minus;


    @FXML
    private Button trash;

   
    int StartedAmount;

    /**
     * Sets the primary screen(OrderingController instance) of the row cart.
     *
     * @param primaryScreen the new primary screen
     */
    public void setPrimaryScreen(OrderingController primaryScreen) {
		this.primaryScreen = primaryScreen;
	}

	/**
	 * remove the cart row rom  the cart on clicking the remove button, 
	 * also updates the total price and the amount of the item in the catalog
	 * @param event the event
	 */
	@FXML
    void RemoveBtn(MouseEvent event) {

    	if(ItemScreen != null) {
    		ItemScreen.SetFlage(true);
    	}
    	cartItem.getItemsInDevice().get(0).setAmount(StartedAmount);
    	ItemScreen.SetVisibleText(false);
    	int priceQuantity=i;//calculated quantity for payment
    	if(ChatClient.saleController.getSalesInDevice()!=null) {
    		if(ChatClient.saleController.getSalesInDevice().getSaleType().equals("1+1")) {
        		priceQuantity= (i/2) + (i%2);
        	}
    	}
    
    	OrderingController.TotalPrice-=(cartItem.getPrice())*priceQuantity;//changed line
    	primaryScreen.SetPrice();
    	primaryScreen.deleteRow(MyIndex);
    	i=1;
    	
    }

    /**
     * increases the quantity of the item in the cart on clicking on the plus button.
     * also updates total price if necessary.
     * @param event the event
     */
    @FXML
    void addBtn(MouseEvent event) {//check if amount is okay+ make sure amount is updated in catalog
    	int lastAmount = cartItem.getItemsInDevice().get(0).getAmount();  		
	    	if((lastAmount>0) || ChatClient.MyOrder.getOrderType().equals("delivery")) {
	    		AmountItemsCart.setText(String.valueOf(++i));
	    		if(!ChatClient.MyOrder.getOrderType().equals("delivery")) {
	    			cartItem.getItemsInDevice().get(0).setAmount(lastAmount-1);
	    		}
	    		if(ChatClient.saleController.getSalesInDevice()==null||!ChatClient.saleController.getSalesInDevice().getSaleType().equals("1+1") || ((i%2)==1)){//added cond
	    		OrderingController.TotalPrice+=cartItem.getPrice();
	    		}
	    		
	    		primaryScreen.SetPrice();
	    	} 
	    	else {
	    		ItemScreen.SetVisibleText(true);
	    	}	
    }

    /**
     * Reduce the item quantity in the cart by 1 on clicking the reduce button(minus).
     * if there  is only one unit of the item also removes the cart row.
     * also updates the total price and item quantity in the catalog accordingly
     * @param event the event
     */
    @FXML
    void reduce(MouseEvent event) {
    	int lastAmount = cartItem.getItemsInDevice().get(0).getAmount();
    	ItemScreen.SetVisibleText(false);
    	if(i==1) {
    		
    		if(ItemScreen != null) {	
    			
    			ItemScreen.SetFlage(true);
        	}
    		
    		cartItem.getItemsInDevice().get(0).setAmount(StartedAmount);
    		OrderingController.TotalPrice-=cartItem.getPrice();
    		primaryScreen.deleteRow(MyIndex);
    		
    	}
    	else {
    		
    		i-=1;
        	AmountItemsCart.setText(String.valueOf(i));
        	if(!ChatClient.MyOrder.getOrderType().equals("delivery")) {
        		cartItem.getItemsInDevice().get(0).setAmount(lastAmount+1);
        	}
        	if(ChatClient.saleController.getSalesInDevice()==null||!ChatClient.saleController.getSalesInDevice().getSaleType().equals("1+1") || ((i%2)==0)) {//added cond
        	OrderingController.TotalPrice-=cartItem.getPrice();
        	}       	
    	}
    	primaryScreen.SetPrice();
    }
    
    /**
     * update the cart row index on the deletion of another cart row(if necessary).
     */
    public void moveRowUp() {//added function to move row up in a row deletion
    	MyIndex--;
    }
    
    /**
     * increase amount of item in cart on clicking on the items' "add to cart" button in the catalog. 
     * also updtaes total price of the order and the item's inventory in the catalog 
     */
    public void addToCartFromCatalog() {
    	int lastAmount = cartItem.getItemsInDevice().get(0).getAmount();
    	i++;
    	AmountItemsCart.setText(String.valueOf(i));   
    	if(!ChatClient.MyOrder.getOrderType().equals("delivery")) {
    		cartItem.getItemsInDevice().get(0).setAmount(lastAmount-1);
    	}
    	if(ChatClient.saleController.getSalesInDevice()==null||!ChatClient.saleController.getSalesInDevice().getSaleType().equals("1+1") || ((i%2)==1)) {//added cond
    		OrderingController.TotalPrice+=cartItem.getPrice();
    	}	
    	primaryScreen.SetPrice();
    }

   /**
    * returns the row cart item object.
    *
    * @return the cart item
    */
   public Item getCartItem() {
	   return cartItem;
   }
   
   /**
    * returns the cart item quantity.
    *
    * @return the cart item quantity
    */
   public int getCartItemQuantity() {
	   return i;
   }
    
    /**
     * organizing the view and information of a newly created cart row.
     *
     * @param item the item
     * @param primarySCreen the primary Screen
     * @param ItemScreen the item screen
     */
    public void OrginaizeScreen(Item item, OrderingController primarySCreen,ItemPatternController ItemScreen) {//added parameters
    	AmountItemsCart.setEditable(false);//amount field disabled so user can't type in it
    	this.ItemScreen = ItemScreen;
    	MyIndex = OrderingController.RowIndex;
    	OrderingController.RowIndex++;
    	javafx.scene.image.Image i = new javafx.scene.image.Image(item.getPhoto());
    	NameItemLable.setText(item.getName());
    	
    	ItemImageCart.setImage(i);
    	cartItem=item;
    	StartedAmount = cartItem.getItemsInDevice().get(0).getAmount();
    	PriceLableItem.setText(String.valueOf(String.format("%.2f",cartItem.getPrice()))+"$");
    	this.primaryScreen=primarySCreen;
    }
    
    /**
     * disables cart row buttons.
     */
    public void SetDisableImages() {
    	RemoveBtn.setVisible(false);
    	PlusBtn.setVisible(false);
    	LessBtn.setVisible(false);
    	trash.setDisable(true);
    	plus.setDisable(true);
    	minus.setDisable(true);
    	AmountItemsCart.setEditable(false);
    }
    
    /**
     * Enables cart row buttons.
     */
    public void SetEnableImages(){//make cart buttons appear again {
    	RemoveBtn.setVisible(true);
    	PlusBtn.setVisible(true);
    	LessBtn.setVisible(true);
    	trash.setDisable(false);
    	plus.setDisable(false);
    	minus.setDisable(false);
    }

}