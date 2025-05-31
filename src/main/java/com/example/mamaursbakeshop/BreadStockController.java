package com.example.mamaursbakeshop;

import javafx.beans.property.*;
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


    public static final ObservableList<BreadItem> persistentBreadList = FXCollections.observableArrayList(
            new BreadItem("Pandesal", 100, 2.50),
            new BreadItem("Ensaymada", 50, 10.00),
            new BreadItem("Spanish Bread", 80, 5.00),
            new BreadItem("Cheese Roll", 40, 8.00),
            new BreadItem("Monay", 60, 3.00),
            new BreadItem("Tasty Bread", 30, 4.00)
    );

    @FXML private TextField breadNameField;
    @FXML private TextField quantityField;
    @FXML private TextField priceField;

    @FXML private TableView<BreadItem> tableView;
    @FXML private TableColumn<BreadItem, String> breadNameColumn;
    @FXML private TableColumn<BreadItem, Integer> quantityColumn;
    @FXML private TableColumn<BreadItem, Double> priceColumn;

    @FXML
    public void initialize() {
        breadNameColumn.setCellValueFactory(cellData -> cellData.getValue().breadNameProperty());
        quantityColumn.setCellValueFactory(cellData -> cellData.getValue().quantityProperty().asObject());
        priceColumn.setCellValueFactory(cellData -> cellData.getValue().priceProperty().asObject());

        tableView.setItems(persistentBreadList);

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
        String breadName = breadNameField.getText().trim();
        String quantityText = quantityField.getText().trim();
        String priceText = priceField.getText().trim();

        if (!breadName.isEmpty() && quantityText.matches("\\d+") && priceText.matches("\\d+(\\.\\d+)?")) {
            int quantity = Integer.parseInt(quantityText);
            double price = Double.parseDouble(priceText);

            BreadItem existing = findBreadByName(breadName);
            if (existing != null) {
                existing.setQuantity(existing.getQuantity() + quantity);
                existing.setPrice(price);
                tableView.refresh();
            } else {
                persistentBreadList.add(new BreadItem(breadName, quantity, price));
            }

            clearFields();
        } else {
            showAlert(Alert.AlertType.ERROR, "Invalid Input", "Please enter valid bread name, quantity and price.");
        }
    }

    @FXML
    private void handleDelete() {
        BreadItem selected = tableView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            persistentBreadList.remove(selected);
            clearFields();
        }
    }

    @FXML
    private void handleClear() {
        persistentBreadList.clear();
        clearFields();
    }

    private void clearFields() {
        breadNameField.clear();
        quantityField.clear();
        priceField.clear();
        tableView.getSelectionModel().clearSelection();
    }

    private BreadItem findBreadByName(String name) {
        for (BreadItem item : persistentBreadList) {
            if (item.getBreadName().equalsIgnoreCase(name)) {
                return item;
            }
        }
        return null;
    }

    @FXML
    private void handleBack(javafx.event.ActionEvent event) throws IOException {
        Parent optionsRoot = FXMLLoader.load(getClass().getResource("/com/example/mamaursbakeshop/Options.fxml"));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(optionsRoot);
        stage.setScene(scene);
        stage.show();
    }

    public static ObservableList<String> getBreadNames() {
        ObservableList<String> names = FXCollections.observableArrayList();
        for (BreadItem item : persistentBreadList) {
            names.add(item.getBreadName());
        }
        return names;
    }

    public static void decrementBreadQuantity(String breadName, int quantity) {
        for (BreadItem item : persistentBreadList) {
            if (item.getBreadName().equalsIgnoreCase(breadName)) {
                item.setQuantity(item.getQuantity() - quantity);
                break;
            }
        }
    }

    // ✅ Nested BreadItem class (used by TableView)
    public static class BreadItem {
        private final StringProperty breadName;
        private final IntegerProperty quantity;
        private final DoubleProperty price;

        public BreadItem(String breadName, int quantity, double price) {
            this.breadName = new SimpleStringProperty(breadName);
            this.quantity = new SimpleIntegerProperty(quantity);
            this.price = new SimpleDoubleProperty(price);
        }

        public String getBreadName() { return breadName.get(); }
        public void setBreadName(String breadName) { this.breadName.set(breadName); }
        public StringProperty breadNameProperty() { return breadName; }

        public int getQuantity() { return quantity.get(); }
        public void setQuantity(int quantity) { this.quantity.set(quantity); }
        public IntegerProperty quantityProperty() { return quantity; }

        public double getPrice() { return price.get(); }
        public void setPrice(double price) { this.price.set(price); }
        public DoubleProperty priceProperty() { return price; }
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
