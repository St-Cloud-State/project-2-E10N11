import java.io.*;
import java.lang.*;

public class WaitlistItem implements Serializable{

    private Customer customer;
    private int quantity;

    public WaitlistItem(Customer c, int q)
    {
        this.customer = c;
        this.quantity = q;
    }
    public Customer getCustomer() {
        return customer;
    }
    public int getCustomerId() {
        return customer.getId();
    }
    public int getQuantity() {
        return quantity;
    }
    public String toString()
    {
        return "Customer: (ID:"+customer.getId()+")" +customer.getName()+ " Quantity: " +quantity; 
    }
}