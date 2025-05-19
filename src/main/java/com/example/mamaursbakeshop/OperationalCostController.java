package com.example.mamaursbakeshop;

import javafx.beans.property.*;
import javafx.collections.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class OperationalCostController {

    @FXML private TextField capitalField;
    @FXML private TextField profitField;
    @FXML private TextField laborCostField;
    @FXML private TextField utilityCostField;

    @FXML private TableView<OperationalCost> tableView;
    @FXML private TableColumn<OperationalCost, Number> capitalColumn;
    @FXML private TableColumn<OperationalCost, Number> utilityCostColumn;
    @FXML private TableColumn<OperationalCost, Number> laborCostColumn;
    @FXML private TableColumn<OperationalCost, Number> profitColumn;
    @FXML private TableColumn<OperationalCost, Number> totalColumn;

    private ObservableList<OperationalCost> data;

    // Data class inside controller
    public static class OperationalCost {
        private final DoubleProperty capital;
        private final DoubleProperty profit;
        private final DoubleProperty laborCost;
        private final DoubleProperty utilityCost;
        private final DoubleProperty total;

        public OperationalCost(double capital, double profit, double laborCost, double utilityCost) {
            this.capital = new SimpleDoubleProperty(capital);
            this.profit = new SimpleDoubleProperty(profit);
            this.laborCost = new SimpleDoubleProperty(laborCost);
            this.utilityCost = new SimpleDoubleProperty(utilityCost);
            this.total = new SimpleDoubleProperty(capital + profit + laborCost + utilityCost);
        }

        public double getCapital() { return capital.get(); }
        public DoubleProperty capitalProperty() { return capital; }

        public double getProfit() { return profit.get(); }
        public DoubleProperty profitProperty() { return profit; }

        public double getLaborCost() { return laborCost.get(); }
        public DoubleProperty laborCostProperty() { return laborCost; }

        public double getUtilityCost() { return utilityCost.get(); }
        public DoubleProperty utilityCostProperty() { return utilityCost; }

        public double getTotal() { return total.get(); }
        public DoubleProperty totalProperty() { return total; }
    }

    @FXML
    public void initialize() {
        data = FXCollections.observableArrayList();

        capitalColumn.setCellValueFactory(cell -> cell.getValue().capitalProperty());
        profitColumn.setCellValueFactory(cell -> cell.getValue().profitProperty());
        laborCostColumn.setCellValueFactory(cell -> cell.getValue().laborCostProperty());
        utilityCostColumn.setCellValueFactory(cell -> cell.getValue().utilityCostProperty());
        totalColumn.setCellValueFactory(cell -> cell.getValue().totalProperty());

        tableView.setItems(data);
    }

    @FXML
    private void handleAdd() {
        try {
            double capital = Double.parseDouble(capitalField.getText());
            double profit = Double.parseDouble(profitField.getText());
            double laborCost = Double.parseDouble(laborCostField.getText());
            double utilityCost = Double.parseDouble(utilityCostField.getText());

            OperationalCost newCost = new OperationalCost(capital, profit, laborCost, utilityCost);
            data.add(newCost);

            // Clear inputs after add
            capitalField.clear();
            profitField.clear();
            laborCostField.clear();
            utilityCostField.clear();

        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Input Error");
            alert.setHeaderText("Invalid number input");
            alert.setContentText("Please enter valid numbers for all fields.");
            alert.showAndWait();
        }
    }

    @FXML
    private void handleDelete() {
        OperationalCost selected = tableView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            data.remove(selected);
        }
    }

    @FXML
    private void handleClear() {
        data.clear();
    }

    @FXML
    private void handleBack() {
        // Implement navigation back logic here
        System.out.println("Back button clicked");
    }
}
