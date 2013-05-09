/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Mushu117
 */
import java.sql.*;
import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;

public class GrocerController implements ActionListener{
    GrocerModel list;
    GrocerView view;
    ArrayList shop;//?
    String dbUrl = "jdbc:mysql://localhost:3306/groceries";
    String dbClass = "com.mysql.jdbc.Driver";
    Statement stmt;
    ResultSet rs;
    Connection con=null;
    
    static String itemLimbo;
    String store;
    String zipCode;
    public GrocerController(){
        
    }
    
    public GrocerController(GrocerView view){
        this.view = view;
        list = new GrocerModel();
    }
    
    public void print(){
        
    }
    
    public ArrayList<String> allStores(){
        String q = "select * from allstores;";
        ArrayList<String> res = new ArrayList<String>();
        res.add("storeName");
        res.add("zip");
        return runQuery(q, res);        
    }
    
    public ArrayList<String> getStores(String sName){
        String q = "select * from allstores where storeName ='"+sName+
                "';";
        return runQuery(q,"storeName");
    }
    
    public ArrayList<String> possibleZips(String sName){
        String q = "select * from allstores where storeName = '"+ sName+
                "';";
        return runQuery(q, "zip");
    }
    
    public ArrayList<String> needZip(String zip, String sName){
        String q = "select * from  allstores where storeName ='"+sName+
                "' and zip ='"+zip+"';";
        zipCode = zip;
        store = sName;
        return runQuery(q, "storeName");
    }
    
    public void chooseStore(String st){
        store = st;
    }
    
    public String storeInfo(){
        return store + " " + zipCode;
    }
    
    public ArrayList<String> getStoreCategories(){
        String q = "select * from store where storeName ='"+store+
                "';";
        return runQuery(q,"category");                
    }
    
    public boolean addItem(String st){
        String q = "select * from inventory where item ='"+st+
                "';";
        ArrayList<String> cat = runQuery(q,"category");
        boolean itemInDB = (cat != null);
        if(itemInDB){
            q = "select * from store where storeName = '"+store+
                    "' and category = '"+cat.get(0) +"';";
            ArrayList<String> temp = runQuery(q,"aisleNumber");
            int aisle = Integer.parseInt(temp.get(0).trim());
            list.addItem(aisle, st);
            return true;
        }
        else
        {
            String message = "Could not locate "+ st+". Would you like"
                    + "to categorize " + st+"?";
            int resp = JOptionPane.showConfirmDialog(null,"Categorize?",message, JOptionPane.YES_NO_OPTION);
            if(resp == 0)
            {    
                itemLimbo = st;
                selectCategory(st);
                return true;
            }
            else
                return false;
        }
    }
    
    public String selectCategory(String st){
        String q = "select category from store where storeName = '"
                + store + "' and zipcode = "+zipCode+";";
        ArrayList<String> temp = runQuery(q,"category");
        Collections.sort(temp);
        JFrame frame = new JFrame("Select Category of " + st);
        String result;
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //3. Create components and put them in the frame.
        //...create emptyLabel...
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(0,5, 5, 10));
        for(int x=0; x< temp.size(); x++)
        {
            JButton button = new JButton(temp.get(x));
            button.setActionCommand(temp.get(x));
            button.addActionListener(new GrocerController());
            buttonPanel.add(button);
        }
        frame.getContentPane().add(buttonPanel, BorderLayout.CENTER);
            
        //4. Size the frame.
        frame.pack();

        //5. Show it.
        frame.setVisible(true);
        return "fish";
    }
    public void removeItem(String st){
        list.removeItem(st);
        System.out.println(st);
    }
    
    public HashMap<Integer, ArrayList<String>> getList(){
        return list.getList();
    }
    public String getStringList(){
        return list.stringList();
    }
    
    public void actionPerformed(ActionEvent e){
        String result = e.getActionCommand();
        JButton t1 = (JButton) e.getSource();
        JPanel t2 = (JPanel) t1.getParent().getParent();
        JFrame t3 = (JFrame) t2.getParent().getParent().getParent();
        t3.dispose();
        String q = "insert into inventory"
                 + " values ('"+itemLimbo+"', '"+result+"');";
        runQuery(q, "update");
        JOptionPane.showMessageDialog(null,"DB Updated Successfully");
        //addItem(itemLimbo);
    }
    
    private ArrayList<String> runQuery(String q, String result){
        ArrayList<String> qResults = new ArrayList<String>();
        try{
            com.mysql.jdbc.Driver d = null;            
        }
        catch(Exception e){
            e.printStackTrace();
        }
        try{
            //here too
            con = DriverManager.getConnection(dbUrl,"cchest01","mushu117");
            stmt = con.createStatement();
            if(!result.equals("update"))
            {
                rs = stmt.executeQuery(q);
            
                while(rs.next())
                {
                    qResults.add(rs.getString(result));     
                }
            }
            else
                stmt.executeUpdate(q);
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        if(qResults.size() > 0)
            return qResults;
        else
            return null;
    }
    
    private ArrayList<String> runQuery(String q, ArrayList<String> result){
        ArrayList<String> qResults = new ArrayList<String>();
        try{
            com.mysql.jdbc.Driver d = null;            
        }
        catch(Exception e){
            e.printStackTrace();
        }
        try{
            //temp change due to using other computer
            con = DriverManager.getConnection(dbUrl,"cchest01","mushu117");
            stmt = con.createStatement();
            rs = stmt.executeQuery(q);
            while(rs.next())
            {
                String piece = "";
                for(int x =0; x<result.size(); x++)
                {
                    piece += rs.getString(result.get(x));
                } 
                qResults.add(piece);
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        if(qResults.size() > 0)
            return qResults;
        else
            return null;
    }
}
