import java.io.*;
import java.lang.*;

public class WishlistItem implements Serializable{

    private Product product;
    private int quantity;

    public WishlistItem(Product p, int q)
    {
        this.product = p;
        this.quantity = q;
    }
    public Product getProduct() {
        return product;
    }
    public int getProductId() {
        return product.getId();
    }
    public int getQuantity() {
        return quantity;
    }
    public String toString()
    {
        return "Product: " +product.getName()+ " Quantity: " +quantity; 
    }
}