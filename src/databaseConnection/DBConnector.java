package databaseConnection;

import javafx.collections.ObservableList;
import model.Customers;
import model.Motorhome;

import java.sql.*;

/**
 * This class can be used whenever we need a connection to the Database
 * We can make two types of interactions with the database,
 * queries that return a result set
 * and updates that return an integer
 * more information on the methods description
 * Created by Stefanos on 19/05/2017.
 */
public class DBConnector {

    private Connection conn = null;

    /**
     * this is the generic constructor of the class. its empty because we don't need it to do anything yet.
     */
    public DBConnector() {

    }

    /**
     * Use this method whenever you wanna get data from the database.
     * @param query an SQL command as a string ex. "SELECT * FROM fleet"
     * @return a resultset object, have a look at this to understand how to use it:
     * http://docs.oracle.com/javase/7/docs/api/java/sql/ResultSet.html
     * REMEMBER you have to close the connection after reading the ResultSet,
     * when you close the connection the ResultSet disappears
     * @throws SQLException handle this appropriately informing the user when necessary
     */
    public ResultSet makeQuery(String query) throws SQLException {
        Connection conn = getConnection();
        Statement st = conn.createStatement();
        ResultSet res = st.executeQuery(query);
        return res;
    }

    /**
     * Use this method after every query in order to close the connection and avoid problems
     * with other parts of the application trying to connect with the database.
     */
    public void closeConnection(){
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /**
     *Use this method whenever you want to insert data to the database
     * or delete data. You cannot get data with this method.
     * The connection is closed automatically.
     * @param s an SQL command as a string ex. "DELETE * FROM fleet"
     * @return an integer, if "1" then everything went OK.
     * @throws SQLException handle this appropriately informing the user when necessary
     */
    public int makeUpdate(String s) throws SQLException {
        Connection conn = getConnection();
        int val =conn.createStatement().executeUpdate(s);
        if(val==1)
            System.out.println("Successfully inserted value");
        conn.close();
        return val;
    }

    /**
     * this method can be used only be the DBConnector object,
     * it is called everytime we call the makeUpdate() and makeQuery()
     * as it consumes resources we should close it after using it.
     * @return a Connection object to our database
     * have a look at this for more info : https://docs.oracle.com/javase/7/docs/api/java/sql/Connection.html
     */
    private Connection getConnection(){
        String url = "jdbc:mysql://localhost:3306/";
        String dbName = "nordicmotorhomes";
        String driver = "com.mysql.jdbc.Driver";
        String userName = "root";
        String password = "";
        try {
            Class.forName(driver).newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            conn = DriverManager.getConnection(url+dbName,userName,password);
            System.out.println("We got the connection"); //TODO remove, this was used for debugging
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    public boolean addMotorhome(Fleet fleet, String brand, int capacity, double price) {
        int res = 0;
        try {
            ResultSet getId=makeQuery("select max(id) from motorhome");
            getId.next();
            int id=getId.getInt(1)+1;
            System.out.println(id);
            Motorhome newMotorhome= new Motorhome(brand,price,capacity);
            newMotorhome.setId(id);
            res = makeUpdate("INSERT INTO motorhome (id, capacity, price, brand) VALUES ('"+id+"','"+capacity+"','"+price+"','"+brand+"')");
            ObservableList<Motorhome> motorhomeList = fleet.getTheFleetList();
            if(res==1)motorhomeList.add(newMotorhome);
        } catch (Exception e ) {
            e.printStackTrace();
        }
        return res==1;
    }

    public boolean deleteMotorhome(Fleet fleet, Motorhome byeMotorhome) {
        try {
            boolean flag= makeUpdate("DELETE FROM motorhome WHERE ID="+byeMotorhome.getId())==1;
            ObservableList<Motorhome> motorhomeList = fleet.getTheFleetList();
            if(flag) motorhomeList.remove(byeMotorhome);
            return flag;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        closeConnection();
        return false;
    }

// **************************************    Customers        ************************************* /*/

    public boolean addCustomers(CustomerList customerList, String title, String name, String email) {
        int res = 0;
        try {
            ResultSet getId=makeQuery("select max(id) from customers");
            getId.next();
            int id=getId.getInt(1)+1;
            System.out.println(id);
            Customers newCustomers= new Customers(title,name,email);
            newCustomers.setId(id);
            res = makeUpdate("INSERT INTO customers (id, title, name, email) VALUES ('"+id+"','"+title+"','"+name+"','"+email+"')");
            ObservableList<Customers> customerList1 = customerList.getTheCustomerList();
            if(res==1)customerList1.add(newCustomers);
        } catch (Exception e ) {
            e.printStackTrace();
        }
        return res==1;
    }

    public boolean deleteCustomers(CustomerList customerList, Customers byeCustomers) {
        try {
            boolean flag= makeUpdate("DELETE FROM customers WHERE ID="+byeCustomers.getId())==1;
            ObservableList<Customers> customerList1 = customerList.getTheCustomerList();
            if(flag) customerList1.remove(byeCustomers);
            return flag;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        closeConnection();
        return false;
    }
}
