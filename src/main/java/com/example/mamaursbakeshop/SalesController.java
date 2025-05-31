package com.example.mamaursbakeshop;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.Parent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class SalesController {

    @FXML private AreaChart<String, Number> salesChart;
    @FXML private CategoryAxis xAxis;
    @FXML private NumberAxis yAxis;
    @FXML private TextField dateInput;
    @FXML private TextField salesInput;


    private static XYChart.Series<String, Number> salesSeries = new XYChart.Series<>();

    @FXML
    public void initialize() {
        salesSeries.setName("Daily Sales");
        salesChart.getData().add(salesSeries);
    }


    public static void addSaleToChart(String date, double amount) {
        salesSeries.getData().add(new XYChart.Data<>(date, amount));
    }

    @FXML
    private void handleAddSales(ActionEvent event) {
        String date = dateInput.getText();
        String salesText = salesInput.getText();

        if (date.isEmpty() || salesText.isEmpty()) {
            showAlert("Error", "Please enter both date and sales amount.");
            return;
        }

        try {
            double salesAmount = Double.parseDouble(salesText);
            salesSeries.getData().add(new XYChart.Data<>(date, salesAmount));
            dateInput.clear();
            salesInput.clear();
        } catch (NumberFormatException e) {
            showAlert("Error", "Invalid sales amount. Please enter a valid number.");
        }
    }

    @FXML
    private void handleRefresh(ActionEvent event) {
        salesSeries.getData().clear();
        showAlert("Success", "Sales chart refreshed.");
    }

    @FXML
    private void handleExport(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));
        File file = fileChooser.showSaveDialog(null);

        if (file != null) {
            try (FileWriter writer = new FileWriter(file)) {
                writer.write("Date,Total Sales ($)\n");
                for (XYChart.Data<String, Number> data : salesSeries.getData()) {
                    writer.write(data.getXValue() + "," + data.getYValue() + "\n");
                }
                showAlert("Success", "Sales data exported successfully.");
            } catch (IOException e) {
                showAlert("Error", "An error occurred while exporting the data.");
            }
        }
    }

    @FXML
    private void handleBack(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Options.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) salesChart.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            showAlert("Error", "Failed to load the options page.");
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
