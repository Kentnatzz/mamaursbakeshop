package com.example.mamaursbakeshop;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class OperationalCost {

    private final StringProperty capital;
    private final StringProperty profit;
    private final StringProperty laborCost;
    private final StringProperty utilityCost;

    public OperationalCost(String capital, String profit, String laborCost, String utilityCost) {
        this.capital = new SimpleStringProperty(capital);
        this.profit = new SimpleStringProperty(profit);
        this.laborCost = new SimpleStringProperty(laborCost);
        this.utilityCost = new SimpleStringProperty(utilityCost);
    }

    public StringProperty capitalProperty() { return capital; }
    public StringProperty profitProperty() { return profit; }
    public StringProperty laborCostProperty() { return laborCost; }
    public StringProperty utilityCostProperty() { return utilityCost; }
}
