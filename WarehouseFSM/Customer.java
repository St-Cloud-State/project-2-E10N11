import java.io.*;
import java.text.DecimalFormat;
import java.util.*;

/**
 * Class representing a customer.
 */
public class Customer implements Serializable {
    private static final DecimalFormat df = new DecimalFormat("0.00");
    private static final long serialVersionUID = 1L;
    private int id;
    private String name;
    private String address;
    private double balance;
    private Wishlist wishlist;
    private List<Invoice> invoices = new LinkedList();
    private List<Transaction> transactions = new LinkedList();

    // Constructor to initialize a new customer
    public Customer(String name, String address, double balance) {
        this.id = CustomerIdServer.getInstance().getNextId();
        this.name = name;
        this.address = address;
        this.balance = balance;
        this.wishlist = new Wishlist(id);
    }

    // Getter for ID
    public int getId() {
        return id;
    }

    public Wishlist getWishlist() {
        return wishlist;
    }
    // Getter for name
    public String getName() {
        return name;
    }

    // Setter for name
    public void setName(String name) {
        this.name = name;
    }

    // Getter for address
    public String getAddress() {
        return address;
    }

    // Setter for address
    public void setAddress(String address) {
        this.address = address;
    }

    // Getter for balance
    public double getBalance() {
        return balance;
    }

    // Setter for balance
    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void addInvoice(Invoice I)
    {
        invoices.add(I);
    }

    public Iterator getInvoices()
    {
        return invoices.iterator();
    }
    
    public Invoice ProcessOrder()
    {
        Invoice invoice = new Invoice();
        Product p;
        Wishlist userWishlist = getWishlist();
        List<WishlistItem> wishlistItems = userWishlist.getListOfItems();
        int refSize = wishlistItems.size();
        for(int i = 0; i < refSize; i++)
        {
            OrderItem newOI;
            WishlistItem w = wishlistItems.get(i);
            p = w.getProduct();
            if(p.getStock() > w.getQuantity())
            {
                int remaining = p.getStock() - w.getQuantity();
                newOI = new OrderItem(w.getProduct(), w.getQuantity());
                p.setQuantity(remaining);
            }
            else
            {
                int overflow = w.getQuantity() - p.getStock();
                WaitlistItem wli = new WaitlistItem(this, overflow);
                p.addItemToWaitlist(wli);
                newOI = new OrderItem(w.getProduct(), p.getStock());
                p.setQuantity(0);
            }
            invoice.addItem(newOI);
        }
        setBalance(Double.parseDouble(df.format(balance - invoice.getPrice())));
        addInvoice(invoice);
        userWishlist.clear();
        return invoice;
    }

    public String toString()
    {
        return "Customer ID: " + id + "\nName: " + name + "\nAddress: " + address +"\nBalance: " + balance;
    }
    public void display() {
        System.out.println(this.toString());
    }
}
