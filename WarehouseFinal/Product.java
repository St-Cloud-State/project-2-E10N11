import java.util.*;
import java.io.*;
import java.text.DecimalFormat;
public class Product implements Serializable{
    private static final DecimalFormat df = new DecimalFormat("0.00");
    private int id;
    private String name;
    private int quantity;
    private double price;
    private Waitlist waitlist;

    public Product(String p, int s, double v)
    {
        this.id = ProductIdServer.getInstance().getId();
        this.name = p;
        this.quantity = s;
        this.price = v;
        this.waitlist = new Waitlist(id);
    }

    public void setName(String name) {
        this.name = name;
    }
    public void setPrice(double price) {
        this.price = price;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public double getPrice() {
        return price;
    }
    public int getStock() {
        return quantity;
    }
    public Waitlist getWaitlist() {
        return waitlist;
    }

    public int fulfillWaitlist(int q)
    {
        Iterator items = this.waitlist.getItems();
        int remaining = q;
        while (items.hasNext()) 
        {
            WaitlistItem wli = (WaitlistItem)items.next();
            if(remaining > 0 && wli.getQuantity() < remaining)
            {
                OrderItem oi = new OrderItem(this, wli.getQuantity());
                Invoice in = new Invoice();
                in.addItem(oi);
                Customer c = wli.getCustomer();
                c.setBalance(Double.parseDouble(df.format(c.getBalance() - in.getPrice())));
                c.addInvoice(in);
                remaining = remaining - wli.getQuantity();
                waitlist.remove(wli);
            }
        }
        return remaining;
    }

    public boolean addItemToWaitlist(WaitlistItem wi)
    {
        waitlist.add(wi);
        return true;
    }
    public String toString()
    {
        String s =  "Id: " +this.id+ "\nName: " +this.name+ " \nPrice: " +this.price+ "\nStock: " +this.quantity;  
        return s;
    }
    public void display()
    {
        System.out.println(this.toString());
    }
}
