
package model;

import databaseConnection.CustomerList;
import javafx.beans.property.*;

/**
 * Created by Antonia on 19-05-2017.
 */

public class Customers {

    private StringProperty title = new SimpleStringProperty(this, "title", "unknown");
    private StringProperty name = new SimpleStringProperty(this, "name", "unknown");
    private StringProperty email = new SimpleStringProperty(this, "email", "unknown");
    private int id;

    public Customers(String title, String name, String email) {
        this.title.setValue(title);
        this.name.setValue(name);
        this.email.setValue(email);
        setListeners();
        this.id = 0;
    }

    public Customers(String title, String name, String email, int id) {
        this.title.setValue(title);
        this.name.setValue(name);
        this.email.setValue(email);

        this.id = id;
        setListeners();
    }

    public String getTitle() {
        return title.get();
    }

    public StringProperty titleProperty() {
        return title;
    }

    public String getname() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public String getEmail() {
        return email.get();
    }

    public StringProperty emailProperty() {
        return email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Customers{" +
                "Title=" + title.getValue() +
                ", Name" + name.getValue() +
                ", Email=" + email.getValue() +
                '}';
    }

    private void setListeners() {
        title.addListener(
                (v, oldValue, newValue) -> {
                    CustomerList.getInstance().updateCustomers(this, "title", newValue);
                });
        name.addListener(
                (v, oldValue, newValue) -> {
                    CustomerList.getInstance().updateCustomers(this, "name", newValue);
                });
        email.addListener(
                (v, oldValue, newValue) -> {
                    CustomerList.getInstance().updateCustomers(this, "email", newValue);
                });

    }
}