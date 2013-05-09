/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Mushu117
 */
import java.util.Scanner;
import java.util.ArrayList;
public class GrocerViewTextBased implements GrocerView{
    
    public GrocerViewTextBased(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Welcome to GrocerEZ!"+'\n');
        
        GrocerController cont = new GrocerController(this);
        System.out.println("");
        System.out.print("Enter Store Name: ");
        String storeInput = sc.nextLine();
        ArrayList<String> temp = cont.getStores(storeInput);
        
        if(temp == null){
            System.out.println("Sorry, store not yet in DB");
        }
        else if(temp.size() == 1){
            System.out.println(temp.get(0) + " is selected store from DB");
            cont.chooseStore(temp.get(0));
        }
        else{
            System.out.println("Possible Zip Codes: ");
            temp = cont.possibleZips(storeInput);
            for(int x =0; x < temp.size(); x++){
                System.out.println(temp.get(x));
            }
            System.out.print('\n'+"Enter Zip Code: ");
            temp = cont.needZip(sc.nextLine(), storeInput);
            System.out.println(cont.storeInfo() + " is selected store from DB");
            cont.chooseStore(temp.get(0));
        }
        System.out.println("->Enter an item to add it to the inventory list");
        System.out.println("->Enter RM! then an item name to remove it from the list");
        System.out.println("->Enter L to display shopping list");
        System.out.println("->Enter P to print list and quit");
        System.out.println("->Enter Q to quit");
        boolean addingItems = true;
        while(addingItems){
            System.out.print("Enter Command: ");
            String res = sc.nextLine();
            if(res.equals("L"))
            {
                System.out.println(cont.getStringList());
            }
            else if(res.contains("RM!"))
            {
                cont.removeItem(res);
            }
            else if(res.equals("Q"))
            {
                addingItems = false;
            }
            else if(res.equals("P")){
                //print
            }
            else{
                boolean test =cont.addItem(res);
                if(!test){
                    System.out.println("Give this item from a given category: ");
                    ArrayList<String> cats = cont.getStoreCategories();
                            
                }
            }
           
        }
    }
}
