import java.util.*;
import java.lang.*;
import java.io.*;
import java.util.LinkedList;
public class Waitlist implements Serializable{
    private int productId;
    private List<WaitlistItem> items  = new LinkedList<WaitlistItem>();
    
    //constructor
    public Waitlist(int pId)
    {
        this.productId = pId;
    }
    //get operations
    public int getProductId() {
        return productId;
    }
    public Iterator getItems() {
        return items.iterator();
    }
    public void setProductId(int pId) {
        this.productId = pId;
    }
    public boolean add(WaitlistItem w)
    {
        items.add(w);
        return true;
    }

    public boolean remove(WaitlistItem w)
    {
        items.remove(w);
        return true;
    }

    public WaitlistItem find(int cid)
    {
        for (int i=0; i < items.size(); i++)
        {
            if (items.get(i).getCustomerId() == cid) 
            {
                return items.get(i);
            }
        }
        return null;
    }

    public boolean updateWaitlistItem(WaitlistItem newWI)
    {
        WaitlistItem oldItem = find(newWI.getCustomerId());
        if(remove(oldItem) && add(newWI))
        {
            return true;
        }
        return false;
    }

    public String toString()
    {
        return items.toString();
    }
}
