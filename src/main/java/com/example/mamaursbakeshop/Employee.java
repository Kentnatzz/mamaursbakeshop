package com.example.mamaursbakeshop;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Employee {
    private final StringProperty id;
    private final StringProperty name;
    private final StringProperty position;
    private final StringProperty shift;
    private final StringProperty email;
    private final StringProperty rate;

    public Employee(String id, String name, String position, String shift, String email, String rate) {
        this.id = new SimpleStringProperty(id);
        this.name = new SimpleStringProperty(name);
        this.position = new SimpleStringProperty(position);
        this.shift = new SimpleStringProperty(shift);
        this.email = new SimpleStringProperty(email);
        this.rate = new SimpleStringProperty(rate);
    }

    public StringProperty idProperty() { return id; }
    public StringProperty nameProperty() { return name; }
    public StringProperty positionProperty() { return position; }
    public StringProperty shiftProperty() { return shift; }
    public StringProperty emailProperty() { return email; }
    public StringProperty rateProperty() { return rate; }
}
