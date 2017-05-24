package databaseConnection;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Customers;
import model.Motorhome;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Antonia on 19-05-2017.
 */

public class CustomerList {


    private static CustomerList ourInstance = new CustomerList();
    private ObservableList<Customers> theCustomerList = FXCollections.observableArrayList();

    public ObservableList<Customers> getTheCustomerList() {
        return theCustomerList;
    }

    public static CustomerList getInstance() {
        return ourInstance;
    }

    private CustomerList(){
        DBConnector db = new DBConnector();
        try {
            ResultSet result = db.makeQuery("select * from customer");
            while(result.next()){
                Customers toAdd= new Customers(result.getString("title"),result.getString("name"),result.getString("email");
                result.getInt(""),result.getInt("id")):
                final boolean add = theCustomerList.add(toAdd);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        db.closeConnection();
        //TODO remove, this is just for debugging
        for(Customers c: theCustomerList){
            System.out.println(c);
        }}

    public void updateCustomers(Customers toUpdate, String column, String newValue){
        DBConnector db = new DBConnector();
        try {
            db.makeUpdate("UPDATE customer SET "+column+"='"+newValue+"' WHERE id="+toUpdate.getId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}