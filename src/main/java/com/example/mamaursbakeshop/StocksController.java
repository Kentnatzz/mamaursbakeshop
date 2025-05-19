package com.example.mamaursbakeshop;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

public class StocksController {

    @FXML
    private TableView<BreadSale> breadSalesTable;
    @FXML
    private TableColumn<BreadSale, String> breadTypeColumn;
    @FXML
    private TableColumn<BreadSale, Integer> quantitySoldColumn;
    @FXML
    private TableColumn<BreadSale, String> salesDateColumn;

    @FXML
    private TextField breadTypeInput;
    @FXML
    private TextField quantitySoldInput;
    @FXML
    private TextField salesDateInput;

    private final ObservableList<BreadSale> breadSalesData = FXCollections.observableArrayList();

    @FXML
    public void initialize() {

        breadTypeColumn.setCellValueFactory(cellData -> cellData.getValue().breadTypeProperty());
        quantitySoldColumn.setCellValueFactory(cellData -> cellData.getValue().quantitySoldProperty().asObject());
        salesDateColumn.setCellValueFactory(cellData -> cellData.getValue().salesDateProperty());


        breadSalesData.addAll(
                new BreadSale("Sourdough", 15, "2025-05-01"),
                new BreadSale("Baguette", 10, "2025-05-02"),
                new BreadSale("Croissant", 20, "2025-05-03"),
                new BreadSale("Ciabatta", 12, "2025-05-04"),
                new BreadSale("Focaccia", 9, "2025-05-05"),
                new BreadSale("Rye Bread", 7, "2025-05-06"),
                new BreadSale("Multigrain", 18, "2025-05-07"),
                new BreadSale("Brioche", 14, "2025-05-08"),
                new BreadSale("Whole Wheat", 11, "2025-05-09"),
                new BreadSale("Banana Bread", 16, "2025-05-10")
        );

        breadSalesTable.setItems(breadSalesData);
    }

    @FXML
    private void handleAddSale(ActionEvent event) {
        String breadType = breadTypeInput.getText();
        String quantityText = quantitySoldInput.getText();
        String salesDate = salesDateInput.getText();


        if (breadType.isEmpty() || quantityText.isEmpty() || salesDate.isEmpty()) {
            showAlert("Invalid Input", "Please provide all fields.");
            return;
        }

        try {
            int quantitySold = Integer.parseInt(quantityText);
            breadSalesData.add(new BreadSale(breadType, quantitySold, salesDate));
            clearInputs(); // Clear input fields after adding the sale
        } catch (NumberFormatException e) {
            showAlert("Invalid Input", "Quantity must be a valid number.");
        }
    }

    @FXML
    private void handleDeleteSale(ActionEvent event) {
        BreadSale selectedSale = breadSalesTable.getSelectionModel().getSelectedItem();
        if (selectedSale != null) {
            breadSalesData.remove(selectedSale); // Remove the selected sale
        } else {
            showAlert("No Selection", "Please select a sale to delete.");
        }
    }

    @FXML
    private void handleBack(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Options.fxml"));
            Stage stage = (Stage) breadSalesTable.getScene().getWindow();
            stage.setScene(new Scene(loader.load()));
            stage.show();
        } catch (IOException e) {
            showAlert("Error", "Failed to load the options page.");
        }
    }

    private void clearInputs() {
        breadTypeInput.clear();
        quantitySoldInput.clear();
        salesDateInput.clear();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
