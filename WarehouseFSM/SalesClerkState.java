import java.util.*;
import java.text.*;
import java.io.*;
public class SalesClerkState extends WareState {
  private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
  private static Warehouse warehouse;
  private WareContext context;
  private static SalesClerkState instance;
  private static final int EXIT = 0;
  private static final int ADD_CLIENT = 1;
  private static final int LIST_PRODUCTS = 2;
  private static final int LIST_CLIENTS = 3;
  private static final int LIST_OUTSTANDING = 4;
  private static final int ADD_PAYMENT = 5;
  private static final int BECOME_CLIENT = 6;
  private static final int LOGOUT = 7;
  private static final int HELP = 8;
  private SalesClerkState() { 
      super();
      warehouse = Warehouse.instance();
      //context = WareContext.instance();
  }

  public static SalesClerkState instance() {
    if (instance == null) {
      instance = new SalesClerkState();
    }
    return instance;
  }

  public String getToken(String prompt) {
    do {
      try {
        System.out.println(prompt);
        String line = reader.readLine();
        StringTokenizer tokenizer = new StringTokenizer(line,"\n\r\f");
        if (tokenizer.hasMoreTokens()) {
          return tokenizer.nextToken();
        }
      } catch (IOException ioe) {
        System.exit(0);
      }
    } while (true);
  }
  private boolean yesOrNo(String prompt) {
    String more = getToken(prompt + " (Y|y)[es] or anything else for no");
    if (more.charAt(0) != 'y' && more.charAt(0) != 'Y') {
      return false;
    }
    return true;
  }
  public int getNumber(String prompt) {
    do {
      try {
        String item = getToken(prompt);
        Integer num = Integer.valueOf(item);
        return num.intValue();
      } catch (NumberFormatException nfe) {
        System.out.println("Please input a number ");
      }
    } while (true);
  }

  public int getCommand() {
    do {
      try {
        int value = Integer.parseInt(getToken("Enter command:" + HELP + " for help"));
        if (value >= EXIT && value <= HELP) {
          return value;
        }
      } catch (NumberFormatException nfe) {
        System.out.println("Enter a number");
      }
    } while (true);
  }

  public void help() {
    System.out.println("Enter a number between 0 and "+HELP+" as explained below:");
    System.out.println(EXIT + " to Exit\n");
    System.out.println(ADD_CLIENT + " to  add customer");
    System.out.println(LIST_PRODUCTS + " to  list all products");
    System.out.println(LIST_CLIENTS + " to  list all customers");
    System.out.println(LIST_OUTSTANDING + " to  list customers with outstanding balance");
    System.out.println(ADD_PAYMENT + " to  add payment to customer");
    System.out.println(BECOME_CLIENT + " to  become customer");
    System.out.println(LOGOUT + " to  logout");
    System.out.println(HELP + " for help");
  }

  public void listProducts() {
    Iterator result;
    result = warehouse.getProducts();
    if (result == null) {
      System.out.println("Invalid Member ID");
    } else {
      while (result.hasNext()){
        Product product = (Product)(result.next());
        System.out.println(product.toString());
      }
      System.out.println("\n  There are no more products \n" );
    }
  }
  public void addClient() {
    String name = getToken("Enter customer name");
    String address = getToken("Enter address");
    double balance = Double.parseDouble(getToken("Enter balance"));
    Customer result;
    result = warehouse.addCustomer(name, address, balance);
    if (result == null) {
      System.out.println("Could not add member");
    }
    System.out.println("Customer Added:");
    System.out.println(result);
  }
  public void listOutstanding() {
    Iterator result;
    result = warehouse.getCustomers();
    if (result == null) {
      System.out.println("Invalid Member ID");
    } else {
      while (result.hasNext()){
        Customer customer = (Customer)(result.next());
        if(customer.getBalance() < 0)
        {
          System.out.println(customer.toString());
        }
        
      }
      System.out.println("\n  There are no more customers \n" );
    }
  }
  public void listClients() {
    Iterator result;
    result = warehouse.getCustomers();
    if (result == null) {
      System.out.println("Invalid Member ID");
    } else {
      while (result.hasNext()){
        Customer customer = (Customer)(result.next());
        System.out.println(customer.toString());
      }
      System.out.println("\n  There are no more customers \n" );
    }
  }

  public void addPayment()
  {
    int cid = Integer.parseInt(getToken("Enter customer Id"));
    double amount = Double.parseDouble(getToken("Enter payment amount for customer"));
    System.out.println("Payment add:");
    System.out.println(warehouse.addPayment(cid, amount).toString());
  }
  public void becomeClient()
  {
    int userId = getNumber("Input userId");
    if (Warehouse.instance().searchCustomer(userId) != null){
      (WareContext.instance()).setUser(Integer.toString(userId));  
      (WareContext.instance()).changeState(0);
    }
    else 
      System.out.println("Invalid user id.");
  }
  public void logout()
  {
    var state = WareContext.instance().getLogin();
    if(state == 1)
    {
      (WareContext.instance()).changeState(3);
    }
    else
    {
      (WareContext.instance()).changeState(2);
    }
  }

  public void terminate(int exitcode)
  {
    (WareContext.instance()).changeState(exitcode); // exit with a code 
  }
 

  public void process() {
    int command, exitcode = -1;
    help();
    boolean done = false;
    while (!done) {
      switch (getCommand()) {
        case ADD_CLIENT:        addClient();
                                break;
        case LIST_PRODUCTS:     listProducts();
                                break;
        case LIST_CLIENTS:      listClients();
                                break;
        case LIST_OUTSTANDING:  listOutstanding();
                                break;
        case ADD_PAYMENT:       addPayment();
                                break;
        case BECOME_CLIENT:     becomeClient();
                                break;
        case LOGOUT:                logout();
                                break;
        case HELP:                  help();
                                break;
        case EXIT:              exitcode = 0;
                                done = true; break;
      }
    }
    terminate(exitcode);
  }
  public void run() {
    process();
  }
}
