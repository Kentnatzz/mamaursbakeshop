package com.example.mamaursbakeshop;

import javafx.beans.property.*;
import javafx.collections.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class OperationalCostController {

    @FXML private TextField profitField;
    @FXML private TextField laborCostField;
    @FXML private TextField utilityCostField;

    @FXML private TableView<OperationalCost> tableView;
    @FXML private TableColumn<OperationalCost, Number> utilityCostColumn;
    @FXML private TableColumn<OperationalCost, Number> laborCostColumn;
    @FXML private TableColumn<OperationalCost, Number> profitColumn;
    @FXML private TableColumn<OperationalCost, Number> totalColumn;

    private ObservableList<OperationalCost> data;

    public static class OperationalCost {
        private final DoubleProperty profit;
        private final DoubleProperty laborCost;
        private final DoubleProperty utilityCost;
        private final DoubleProperty total;

        public OperationalCost(double profit, double laborCost, double utilityCost) {
            this.profit = new SimpleDoubleProperty(profit);
            this.laborCost = new SimpleDoubleProperty(laborCost);
            this.utilityCost = new SimpleDoubleProperty(utilityCost);
            this.total = new SimpleDoubleProperty(profit + laborCost + utilityCost);
        }

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

        profitColumn.setCellValueFactory(cell -> cell.getValue().profitProperty());
        laborCostColumn.setCellValueFactory(cell -> cell.getValue().laborCostProperty());
        utilityCostColumn.setCellValueFactory(cell -> cell.getValue().utilityCostProperty());
        totalColumn.setCellValueFactory(cell -> cell.getValue().totalProperty());

        tableView.setItems(data);


        data.addAll(
                new OperationalCost(5000.0, 2000.0, 1500.0),
                new OperationalCost(4800.0, 2100.0, 1400.0),
                new OperationalCost(5300.0, 1900.0, 1600.0),
                new OperationalCost(5100.0, 2000.0, 1550.0),
                new OperationalCost(4950.0, 2050.0, 1450.0)
        );
    }

    @FXML
    private void handleAdd() {
        try {
            double profit = Double.parseDouble(profitField.getText());
            double laborCost = Double.parseDouble(laborCostField.getText());
            double utilityCost = Double.parseDouble(utilityCostField.getText());

            OperationalCost newCost = new OperationalCost(profit, laborCost, utilityCost);
            data.add(newCost);

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
    private void handleBack(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Options.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Options");
            stage.setScene(new Scene(root));
            stage.show();

            Stage currentStage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            currentStage.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
