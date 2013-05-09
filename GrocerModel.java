/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Mushu117
 */
import java.util.*;
import java.sql.*;
import javax.sql.*;
import com.mysql.jdbc.Driver;

public class GrocerModel {
    
    HashMap<Integer, ArrayList<String>> theList;
    
    public GrocerModel(){
        theList = new HashMap<Integer, ArrayList<String>>();
    }
    
    public void addItem(int aisle, String item){
        ArrayList temp = theList.get(aisle);
        if(temp == null)
            temp = new ArrayList<String>();
        temp.add(item);
        theList.put(aisle, temp);
    }
    
    public void removeItem(String item){
        theList.remove(item);
    }
    
    public HashMap<Integer, ArrayList<String>> getList(){
        return theList;
    }
    
    public String stringList(){
        Set<Integer> temp = theList.keySet();
        //Integer[] iter = temp.toArray();
        int[] iter = new int[temp.size()];
        int index = 0;
        for (Integer i : temp)
            iter[index++] = i;
        String result="";
        ArrayList<String> temp2;
        for(int x =0;x<iter.length; x++){
            temp2 = theList.get(iter[x]);
            for(int y = 0; y<temp2.size();y++)
                result += temp2.get(y)+"\t\t\t" + iter[x]+ '\n';
            
        }
        return result;
    }
}
