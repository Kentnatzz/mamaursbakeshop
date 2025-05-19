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
import java.time.format.DateTimeParseException;

public class StocksController {

    @FXML
    private TableView<Sale> breadSalesTable;

    @FXML
    private TableColumn<Sale, String> breadTypeColumn;

    @FXML
    private TableColumn<Sale, Double> priceColumn;

    @FXML
    private TableColumn<Sale, Integer> quantitySoldColumn;

    @FXML
    private TableColumn<Sale, Double> totalColumn;

    @FXML
    private TableColumn<Sale, String> salesDateColumn;

    @FXML
    private TextField breadTypeInput;

    @FXML
    private TextField priceInput;

    @FXML
    private TextField quantitySoldInput;

    @FXML
    private TextField salesDateInput;

    @FXML
    private Label totalQuantityLabel;

    @FXML
    private Label biMonthlyLabel;

    private ObservableList<Sale> salesData;

    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @FXML
    public void initialize() {
        // Initialize table columns
        breadTypeColumn.setCellValueFactory(new PropertyValueFactory<>("breadType"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        quantitySoldColumn.setCellValueFactory(new PropertyValueFactory<>("quantitySold"));
        totalColumn.setCellValueFactory(new PropertyValueFactory<>("total"));
        salesDateColumn.setCellValueFactory(new PropertyValueFactory<>("salesDate"));

        // Initialize sales data list
        salesData = FXCollections.observableArrayList();
        breadSalesTable.setItems(salesData);

        updateTotalQuantity();
        updateBiMonthlyPeriod();
    }

    @FXML
    private void handleAddSale(ActionEvent event) {
        String breadType = breadTypeInput.getText().trim();
        String priceText = priceInput.getText().trim();
        String quantityText = quantitySoldInput.getText().trim();
        String dateText = salesDateInput.getText().trim();

        if (breadType.isEmpty() || priceText.isEmpty() || quantityText.isEmpty() || dateText.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Input Error", "Please fill in all fields.");
            return;
        }

        double price;
        int quantity;
        LocalDate salesDate;

        try {
            price = Double.parseDouble(priceText);
            if (price < 0) throw new NumberFormatException();
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Input Error", "Invalid price. Enter a positive number.");
            return;
        }

        try {
            quantity = Integer.parseInt(quantityText);
            if (quantity < 0) throw new NumberFormatException();
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Input Error", "Invalid quantity. Enter a positive integer.");
            return;
        }

        try {
            salesDate = LocalDate.parse(dateText, dateFormatter);
        } catch (DateTimeParseException e) {
            showAlert(Alert.AlertType.ERROR, "Input Error", "Invalid date format. Use yyyy-MM-dd.");
            return;
        }

        Sale newSale = new Sale(breadType, price, quantity, salesDate.format(dateFormatter));
        salesData.add(newSale);

        clearInputs();
        updateTotalQuantity();
        updateBiMonthlyPeriod();
    }

    @FXML
    private void handleDeleteSale(ActionEvent event) {
        Sale selected = breadSalesTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            salesData.remove(selected);
            updateTotalQuantity();
            updateBiMonthlyPeriod();
        } else {
            showAlert(Alert.AlertType.WARNING, "No Selection", "Please select a sale to delete.");
        }
    }

    @FXML
    private void handleBack(ActionEvent event) throws IOException {
        Parent optionsRoot = FXMLLoader.load(getClass().getResource("/com/example/mamaursbakeshop/Options.fxml"));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(optionsRoot);
        stage.setScene(scene);
        stage.show();
    }

    private void updateTotalQuantity() {
        int totalQty = salesData.stream().mapToInt(Sale::getQuantitySold).sum();
        totalQuantityLabel.setText(String.valueOf(totalQty));
    }

    private void updateBiMonthlyPeriod() {
        if (salesData.isEmpty()) {
            biMonthlyLabel.setText("-");
            return;
        }

        LocalDate minDate = salesData.stream()
                .map(s -> LocalDate.parse(s.getSalesDate(), dateFormatter))
                .min(LocalDate::compareTo)
                .orElse(null);

        LocalDate maxDate = salesData.stream()
                .map(s -> LocalDate.parse(s.getSalesDate(), dateFormatter))
                .max(LocalDate::compareTo)
                .orElse(null);

        if (minDate == null || maxDate == null) {
            biMonthlyLabel.setText("-");
            return;
        }

        String period = minDate.format(dateFormatter) + " to " + maxDate.format(dateFormatter);
        biMonthlyLabel.setText(period);
    }

    private void clearInputs() {
        breadTypeInput.clear();
        priceInput.clear();
        quantitySoldInput.clear();
        salesDateInput.clear();
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Inner class to represent a Sale record
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

        public String getBreadType() {
            return breadType;
        }

        public double getPrice() {
            return price;
        }

        public int getQuantitySold() {
            return quantitySold;
        }

        public double getTotal() {
            return total;
        }

        public String getSalesDate() {
            return salesDate;
        }
    }
}
