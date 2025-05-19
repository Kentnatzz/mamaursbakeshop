package com.example.mamaursbakeshop;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class BreadSale {

    private final SimpleStringProperty breadType;
    private final SimpleIntegerProperty quantitySold;
    private final SimpleStringProperty salesDate;

    public BreadSale(String breadType, int quantitySold, String salesDate) {
        this.breadType = new SimpleStringProperty(breadType);
        this.quantitySold = new SimpleIntegerProperty(quantitySold);
        this.salesDate = new SimpleStringProperty(salesDate);
    }

    public String getBreadType() {
        return breadType.get();
    }

    public void setBreadType(String breadType) {
        this.breadType.set(breadType);
    }

    public SimpleStringProperty breadTypeProperty() {
        return breadType;
    }

    public int getQuantitySold() {
        return quantitySold.get();
    }

    public void setQuantitySold(int quantitySold) {
        this.quantitySold.set(quantitySold);
    }

    public SimpleIntegerProperty quantitySoldProperty() {
        return quantitySold;
    }

    public String getSalesDate() {
        return salesDate.get();
    }

    public void setSalesDate(String salesDate) {
        this.salesDate.set(salesDate);
    }

    public SimpleStringProperty salesDateProperty() {
        return salesDate;
    }
}
