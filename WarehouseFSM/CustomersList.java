import java.io.*;
import java.util.*;

/**
 * Singleton class to manage a list of customers.
 */
public class CustomersList implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<Customer> customers = new LinkedList();
    private static CustomersList customersList;

    // Private constructor to prevent instantiation
    private CustomersList() {
    }

    // Method to get the singleton instance of CustomersList
    public static CustomersList getInstance() {
        if (customersList == null) {
            customersList = new CustomersList();
        }
        return customersList;
    }

    // Method to add a customer to the list
    public boolean addCustomer(Customer customer) {
        return customers.add(customer);
    }

    // Method to get an iterable list of customers
    public Iterator getCustomers() {
        return customers.iterator();
    }
    public Customer find(int cid)
    {
        for (int i=0; i < customers.size(); i++)
        {
            if (customers.get(i).getId() == cid) 
            {
                return customers.get(i);
            }
        }
        return null;
    }

    // Method to serialize the object
    private void writeObject(ObjectOutputStream output) throws IOException {
        output.defaultWriteObject();
        output.writeObject(customersList);
    }

    // Method to deserialize the object
    private void readObject(ObjectInputStream input) throws IOException, ClassNotFoundException {
        input.defaultReadObject();
        if (customersList == null) {
            customersList = (CustomersList) input.readObject();
        } else {
            input.readObject();
        }
    }

    // Method to return a string representation of the customers list
    public String toString() {
        return customers.toString();
    }
}