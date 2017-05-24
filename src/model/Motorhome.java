package model;

import databaseConnection.Fleet;
import javafx.beans.property.*;

/**
 * Created by ADMIN on 19-05-2017.
 */
public class Motorhome {

    private StringProperty brand = new SimpleStringProperty(this,"brand","unknown");
    private DoubleProperty price= new SimpleDoubleProperty(this, "price",0);
    private IntegerProperty nbrPersons = new SimpleIntegerProperty(this, "nbrPersons",0);
    private int id;

    public Motorhome(String brand, double price, int nbrPersons) {
        this.brand.setValue(brand);
        this.price.setValue(price);
        this.nbrPersons.setValue(nbrPersons);
        setListeners();
        this.id = 0;
    }

    public Motorhome(String brand, double price, int nbrPersons, int id) {
        this.brand.setValue(brand);
        this.price.setValue(price);
        this.nbrPersons.setValue(nbrPersons);
        this.id = id;
        setListeners();
    }
    public Motorhome() {
        setListeners();
    }

    public String getBrand() {
        return brand.get();
    }

    public StringProperty brandProperty() {
        return brand;
    }

    public double getPrice() {
        return price.get();
    }

    public DoubleProperty priceProperty() {
        return price;
    }

    public int getNbrPersons() {
        return nbrPersons.get();
    }

    public IntegerProperty nbrPersonsProperty() {
        return nbrPersons;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Motorhome{" +
                "brand=" + brand.getValue() +
                ", price=" + price.getValue() +
                ", nbrPersons=" + nbrPersons.getValue() +
                '}';
    }

    private void setListeners(){
        //first we take the property object that wraps the brand.
        //properties are objects that you can add listeners to.
        //check this page:
        brand.addListener(
                //this is a lambda expression,
                (v, oldValue, newValue)->{
                    Fleet.getInstance().updateMotorhome(this, "brand", newValue);
                });
        price.addListener(
                //this is a lambda expression,
                (v, oldValue, newValue)->{
                    String price = Double.toString(newValue.doubleValue());
                    Fleet.getInstance().updateMotorhome(this, "price", price);
                });
        nbrPersons.addListener(
                //this is a lambda expression,
                (v, oldValue, newValue)->{
                    String capacity = Integer.toString(newValue.intValue());
                    Fleet.getInstance().updateMotorhome(this, "capacity", capacity);
                });

    }

}