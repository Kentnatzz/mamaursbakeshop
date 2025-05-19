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

public class BreadStockController {

    @FXML private TextField breadNameField;
    @FXML private TextField quantityField;
    @FXML private TextField priceField;

    @FXML private TableView<BreadItem> tableView;
    @FXML private TableColumn<BreadItem, String> breadNameColumn;
    @FXML private TableColumn<BreadItem, Integer> quantityColumn;
    @FXML private TableColumn<BreadItem, Double> priceColumn;

    private final ObservableList<BreadItem> breadList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        breadNameColumn.setCellValueFactory(cellData -> cellData.getValue().breadNameProperty());
        quantityColumn.setCellValueFactory(cellData -> cellData.getValue().quantityProperty().asObject());
        priceColumn.setCellValueFactory(cellData -> cellData.getValue().priceProperty().asObject());

        tableView.setItems(breadList);

        tableView.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                breadNameField.setText(newVal.getBreadName());
                quantityField.setText(String.valueOf(newVal.getQuantity()));
                priceField.setText(String.valueOf(newVal.getPrice()));
            }
        });
    }

    @FXML
    private void handleAdd() {
        String breadName = breadNameField.getText();
        String quantityText = quantityField.getText();
        String priceText = priceField.getText();

        if (!breadName.isEmpty() && quantityText.matches("\\d+") && priceText.matches("\\d+(\\.\\d+)?")) {
            int quantity = Integer.parseInt(quantityText);
            double price = Double.parseDouble(priceText);

            breadList.add(new BreadItem(breadName, quantity, price));
            clearFields();
        }
    }

    @FXML
    private void handleDelete() {
        BreadItem selected = tableView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            breadList.remove(selected);
            clearFields();
        }
    }

    @FXML
    private void handleClear() {
        breadList.clear();
        clearFields();
    }

    private void clearFields() {
        breadNameField.clear();
        quantityField.clear();
        priceField.clear();
        tableView.getSelectionModel().clearSelection();
    }


    @FXML
    private void handleBack(javafx.event.ActionEvent event) throws IOException {
        Parent optionsRoot = FXMLLoader.load(getClass().getResource("/com/example/mamaursbakeshop/Options.fxml"));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(optionsRoot);
        stage.setScene(scene);
        stage.show();
    }
}
