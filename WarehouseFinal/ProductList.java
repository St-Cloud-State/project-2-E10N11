import java.io.*;
import java.util.*;

/**
 * Singleton class to manage a list of Product.
 */
public class ProductList implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<Product> products = new LinkedList();
    private static ProductList ProductList;

    private ProductList() {
    }

    public static ProductList getInstance() {
        if (ProductList == null) {
            ProductList = new ProductList();
        }
        return ProductList;
    }

    public boolean addProduct(Product product) {
        return products.add(product);
    }

    public Iterator getProducts() {
        return products.iterator();
    }

    public Product find(int pid)
    {
        for (int i=0; i < products.size(); i++)
        {
            if (products.get(i).getId() == pid)
            {
                return products.get(i);
            }
        }
        return null;
    }

    private void writeObject(ObjectOutputStream output) throws IOException {
        output.defaultWriteObject();
        output.writeObject(ProductList);
    }

    private void readObject(ObjectInputStream input) throws IOException, ClassNotFoundException {
        input.defaultReadObject();
        if (ProductList == null) {
            ProductList = (ProductList) input.readObject();
        } else {
            input.readObject();
        }
    }

    public String toString() {
        return products.toString();
    }
}