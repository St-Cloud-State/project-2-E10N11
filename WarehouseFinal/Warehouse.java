import java.util.*;
import java.io.*;
public class Warehouse implements Serializable {
    private static final long serialVersionUID = 1L;
    public static final int CUSTOMER_NOT_FOUND  = 1;
    public static final int PRODUCT_NOT_FOUND  = 2;
    private CustomersList customersList;
    private ProductList productList;
    private static Warehouse warehouse;

    private Warehouse() {
        customersList = CustomersList.getInstance();
        productList = ProductList.getInstance();
      }
      public static Warehouse instance() {
        if (warehouse == null) {
          CustomerIdServer.getInstance(); // instantiate all singletons
          ProductIdServer.getInstance();
          return (warehouse = new Warehouse());
        } else {
          return warehouse;
        }
      }
      public Customer addCustomer(String name, String address, double balance) {
        Customer customer = new Customer(name, address, balance);
        if (customersList.addCustomer(customer)) {
          return (customer);
        }
        return null;
      }
      public Product addProduct(String name, double price, int quantity) {
        Product product = new Product(name, quantity, price);
        if (productList.addProduct(product)) {
          return (product);
        }
        return null;
      }
    
      public Iterator getCustomers() {
          return customersList.getCustomers();
      }
    
      public Iterator getProducts() {
          return productList.getProducts();
      }

      public WishlistItem addToWishlist(int cid, int pid, int quantity)
      {
        Wishlist userWishlist = customersList.find(cid).getWishlist();
        WishlistItem item = new WishlistItem(productList.find(pid), quantity); 
        userWishlist.add(item);
        return item;
      }
      public Iterator getWishlist(int cid)
      {
        Wishlist userWishlist = customersList.find(cid).getWishlist();
        return userWishlist.getItems();
      }
      public Iterator getInvoices(int cid)
      {
        return customersList.find(cid).getInvoices();
      }
      public Iterator getWaitlist(int pid)
      {
        Waitlist productWaitlist = productList.find(pid).getWaitlist();
        return productWaitlist.getItems();
      }

      public Product addStock(int pId, int quantity)
      {
        Product p = productList.find(pId);
        int remaining = p.fulfillWaitlist(quantity);
        if(remaining > 0)
        {
          p.setQuantity(p.getStock() + remaining);
        }
        return p;
      }
      public Customer addPayment(int cId, double a)
      {
        Customer c = customersList.find(cId);
        c.setBalance(c.getBalance() + a);
        return c;
      }
      public void updateWishlistItem(int cid, int pid, int quantity)
      {
        Wishlist userWishlist = customersList.find(cid).getWishlist();
        if(quantity > 0)
        {
          WishlistItem wi = new WishlistItem(productList.find(pid), quantity);
          if(!userWishlist.updateWishlistItem(wi))
          {
            System.out.println("Item was unable to be updated.");
          }
        }
        else
        {
          userWishlist.remove(userWishlist.find(pid));
        }
      }

      public Invoice createOrder(int cid)
      {
        return customersList.find(cid).ProcessOrder();
      }

      public static Warehouse retrieve() {
        try {
          FileInputStream file = new FileInputStream("WarehouseData");
          ObjectInputStream input = new ObjectInputStream(file);
          input.readObject();
          CustomerIdServer.retrieve(input);
          return warehouse;
        } catch(IOException ioe) {
          ioe.printStackTrace();
          return null;
        } catch(ClassNotFoundException cnfe) {
          cnfe.printStackTrace();
          return null;
        }
      }
      public static  boolean save() {
        try {
          FileOutputStream file = new FileOutputStream("WarehouseData");
          ObjectOutputStream output = new ObjectOutputStream(file);
          output.writeObject(warehouse);
          output.writeObject(CustomerIdServer.getInstance());
          output.writeObject(ProductIdServer.getInstance());
          return true;
        } catch(IOException ioe) {
          ioe.printStackTrace();
          return false;
        }
      }
      private void writeObject(java.io.ObjectOutputStream output) {
        try {
          output.defaultWriteObject();
          output.writeObject(warehouse);
        } catch(IOException ioe) {
          System.out.println(ioe);
        }
      }
      private void readObject(java.io.ObjectInputStream input) {
        try {
          input.defaultReadObject();
          if (warehouse == null) {
            warehouse = (Warehouse) input.readObject();
          } else {
            input.readObject();
          }
        } catch(IOException ioe) {
          ioe.printStackTrace();
        } catch(Exception e) {
          e.printStackTrace();
        }
      }
      public String toString() {
        return customersList + "\n" + productList;
      } 
}
