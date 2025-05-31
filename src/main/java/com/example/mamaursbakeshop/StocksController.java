package com.example.mamaursbakeshop;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class StocksController {

    @FXML private TableView<Sale> breadSalesTable;
    @FXML private TableColumn<Sale, String> breadTypeColumn;
    @FXML private TableColumn<Sale, Double> priceColumn;
    @FXML private TableColumn<Sale, Integer> quantitySoldColumn;
    @FXML private TableColumn<Sale, Double> totalColumn;
    @FXML private TableColumn<Sale, String> salesDateColumn;

    @FXML private TableView<BreadStockController.BreadItem> breadAvailableTable;
    @FXML private TableColumn<BreadStockController.BreadItem, String> breadAvailableNameColumn;
    @FXML private TableColumn<BreadStockController.BreadItem, Integer> breadAvailableQuantityColumn;
    @FXML private TableColumn<BreadStockController.BreadItem, Double> breadAvailablePriceColumn;

    @FXML private DatePicker salesDatePicker;
    @FXML private ComboBox<String> breadTypeComboBox;
    @FXML private TextField priceInput;
    @FXML private TextField quantitySoldInput;

    private ObservableList<Sale> salesData;
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private static final ObservableList<Sale> persistentSalesData = FXCollections.observableArrayList(
            new Sale("Pandesal", 2.50, 50, "2025-05-10"),
            new Sale("Ensaymada", 10.00, 20, "2025-05-12"),
            new Sale("Spanish Bread", 5.00, 30, "2025-05-15"),
            new Sale("Cheese Roll", 8.00, 15, "2025-05-18")
    );

    @FXML
    public void initialize() {
        breadTypeColumn.setCellValueFactory(new PropertyValueFactory<>("breadType"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        quantitySoldColumn.setCellValueFactory(new PropertyValueFactory<>("quantitySold"));
        totalColumn.setCellValueFactory(new PropertyValueFactory<>("total"));
        salesDateColumn.setCellValueFactory(new PropertyValueFactory<>("salesDate"));

        breadAvailableNameColumn.setCellValueFactory(cellData -> cellData.getValue().breadNameProperty());
        breadAvailableQuantityColumn.setCellValueFactory(cellData -> cellData.getValue().quantityProperty().asObject());
        breadAvailablePriceColumn.setCellValueFactory(cellData -> cellData.getValue().priceProperty().asObject());

        salesData = persistentSalesData;

        breadSalesTable.setItems(salesData);
        breadAvailableTable.setItems(BreadStockController.persistentBreadList);

        updateBreadTypeComboBox();
    }

    @FXML
    private void handleAddSale(ActionEvent event) {
        String breadType = breadTypeComboBox.getValue();
        String priceText = priceInput.getText().trim();
        String quantityText = quantitySoldInput.getText().trim();
        LocalDate salesDate = salesDatePicker.getValue();

        if (breadType == null || priceText.isEmpty() || quantityText.isEmpty() || salesDate == null) {
            showAlert(Alert.AlertType.ERROR, "Input Error", "Please fill in all fields.");
            return;
        }

        double price;
        int quantity;

        try {
            price = Double.parseDouble(priceText);
            if (price < 0) throw new NumberFormatException();
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Input Error", "Invalid price. Enter a positive number.");
            return;
        }

        try {
            quantity = Integer.parseInt(quantityText);
            if (quantity <= 0) throw new NumberFormatException();
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Input Error", "Invalid quantity. Enter a positive integer.");
            return;
        }

        BreadStockController.BreadItem availableBread = findBreadByName(breadType);
        if (availableBread == null) {
            showAlert(Alert.AlertType.ERROR, "Inventory Error", "Selected bread not found in inventory.");
            return;
        }

        if (availableBread.getQuantity() < quantity) {
            showAlert(Alert.AlertType.ERROR, "Inventory Error",
                    "Not enough quantity available. Current stock: " + availableBread.getQuantity());
            return;
        }

        String formattedDate = salesDate.format(dateFormatter);
        Sale newSale = new Sale(breadType, price, quantity, formattedDate);
        salesData.add(newSale);

        availableBread.setQuantity(availableBread.getQuantity() - quantity);
        breadAvailableTable.refresh();

        // Optional: Update graph in SalesController
        String chartDate = salesDate.format(DateTimeFormatter.ofPattern("MMM d"));
        SalesController.addSaleToChart(chartDate, price * quantity);

        clearInputs();
    }

    @FXML
    private void handleDeleteSale(ActionEvent event) {
        Sale selected = breadSalesTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            BreadStockController.BreadItem bread = findBreadByName(selected.getBreadType());
            if (bread != null) bread.setQuantity(bread.getQuantity() + selected.getQuantitySold());

            salesData.remove(selected);
            breadAvailableTable.refresh();
        } else {
            showAlert(Alert.AlertType.WARNING, "No Selection", "Please select a sale to delete.");
        }
    }

    @FXML
    private void handleBack(ActionEvent event) throws IOException {
        Parent optionsRoot = FXMLLoader.load(getClass().getResource("/com/example/mamaursbakeshop/Options.fxml"));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(optionsRoot));
        stage.show();
    }

    private void clearInputs() {
        breadTypeComboBox.setValue(null);
        priceInput.clear();
        quantitySoldInput.clear();
        salesDatePicker.setValue(null);
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private BreadStockController.BreadItem findBreadByName(String breadType) {
        for (BreadStockController.BreadItem item : BreadStockController.persistentBreadList) {
            if (item.getBreadName().equals(breadType)) return item;
        }
        return null;
    }

    private void updateBreadTypeComboBox() {
        breadTypeComboBox.setItems(FXCollections.observableArrayList(
                BreadStockController.persistentBreadList.stream()
                        .map(BreadStockController.BreadItem::getBreadName)
                        .toList()
        ));
    }

    // Sales model (kept same)
    public static class Sale {
        private final String breadType;
        private final double price;
        private final int quantitySold;
        private final double total;
        private final String salesDate;

        public Sale(String breadType, double price, int quantitySold, String salesDate) {
            this.breadType = breadType;
            this.price = price;
            this.quantitySold = quantitySold;
            this.total = price * quantitySold;
            this.salesDate = salesDate;
        }

        public String getBreadType() { return breadType; }
        public double getPrice() { return price; }
        public int getQuantitySold() { return quantitySold; }
        public double getTotal() { return total; }
        public String getSalesDate() { return salesDate; }
    }
}
