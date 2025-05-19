package com.example.mamaursbakeshop;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

public class OperationalCostController {

    @FXML private TextField capitalField;
    @FXML private TextField profitField;
    @FXML private TextField laborCostField;
    @FXML private TextField utilityCostField;

    @FXML private TableView<OperationalCost> tableView;
    @FXML private TableColumn<OperationalCost, String> capitalColumn;
    @FXML private TableColumn<OperationalCost, String> profitColumn;
    @FXML private TableColumn<OperationalCost, String> laborCostColumn;
    @FXML private TableColumn<OperationalCost, String> utilityCostColumn;

    private final ObservableList<OperationalCost> data = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // Setup table columns
        capitalColumn.setCellValueFactory(cell -> cell.getValue().capitalProperty());
        profitColumn.setCellValueFactory(cell -> cell.getValue().profitProperty());
        laborCostColumn.setCellValueFactory(cell -> cell.getValue().laborCostProperty());
        utilityCostColumn.setCellValueFactory(cell -> cell.getValue().utilityCostProperty());

        tableView.setItems(data);
    }

    @FXML
    private void handleAdd() {
        if (capitalField.getText().isEmpty() || profitField.getText().isEmpty() ||
                laborCostField.getText().isEmpty() || utilityCostField.getText().isEmpty()) {
            showAlert("Please fill all fields.");
            return;
        }

        OperationalCost opCost = new OperationalCost(
                capitalField.getText(),
                profitField.getText(),
                laborCostField.getText(),
                utilityCostField.getText()
        );
        data.add(opCost);
        clearFields();
    }

    @FXML
    private void handleDelete() {
        OperationalCost selected = tableView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            data.remove(selected);
        } else {
            showAlert("No item selected to delete.");
        }
    }

    @FXML
    private void handleClear() {
        data.clear();
    }

    private void clearFields() {
        capitalField.clear();
        profitField.clear();
        laborCostField.clear();
        utilityCostField.clear();
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Back button handler to load Options.fxml scene
    @FXML
    private void handleBack(javafx.event.ActionEvent event) throws IOException {
        Parent optionsRoot = FXMLLoader.load(getClass().getResource("/com/example/mamaursbakeshop/Options.fxml"));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(optionsRoot);
        stage.setScene(scene);
        stage.show();
    }
}
