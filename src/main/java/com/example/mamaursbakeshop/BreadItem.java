package com.example.mamaursbakeshop;

import javafx.beans.property.*;

public class BreadItem {
    private final StringProperty breadName = new SimpleStringProperty();
    private final IntegerProperty quantity = new SimpleIntegerProperty();
    private final DoubleProperty price = new SimpleDoubleProperty();

    public BreadItem(String breadName, int quantity, double price) {
        this.breadName.set(breadName);
        this.quantity.set(quantity);
        this.price.set(price);
    }

    public String getBreadName() {
        return breadName.get();
    }

    public void setBreadName(String breadName) {
        this.breadName.set(breadName);
    }

    public StringProperty breadNameProperty() {
        return breadName;
    }

    public int getQuantity() {
        return quantity.get();
    }

    public void setQuantity(int quantity) {
        this.quantity.set(quantity);
    }

    public IntegerProperty quantityProperty() {
        return quantity;
    }

    public double getPrice() {
        return price.get();
    }

    public void setPrice(double price) {
        this.price.set(price);
    }

    public DoubleProperty priceProperty() {
        return price;
    }
}
