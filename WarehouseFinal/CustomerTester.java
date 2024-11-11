import java.io.*;
import java.util.*;
/**
 * Class to test the functionality of Customer, CustomerIdServer, and CustomersList classes.
 */
public class CustomerTester {
    public static void main(String[] args) {
        // Create a CustomersList instance
        CustomersList customersList = CustomersList.getInstance();

        // Create some customers
        Customer customer1 = new Customer("Alice", "123 Fake St", 100.0);
        Customer customer2 = new Customer("Bob", "456 Blank Ave", 200.0);
        Customer customer3 = new Customer("Charlie", "789 Whatever Rd", 300.0);

        // Add customers to the list
        customersList.addCustomer(customer1);
        customersList.addCustomer(customer2);
        customersList.addCustomer(customer3);

        // Display all customers
        System.out.println("Customer List:");
        Iterator c = customersList.getCustomers();
        while (c.hasNext()) {
            System.out.println(c.next().toString());
        }
    }
}