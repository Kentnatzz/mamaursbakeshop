package com.example.mamaursbakeshop;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class OptionsController {

    @FXML
    protected void handleStocks(ActionEvent event) throws IOException {
        loadScene(event, "/com/example/mamaursbakeshop/STOCKS.fxml");
    }

    @FXML
    protected void handleSales(ActionEvent event) throws IOException {
        loadScene(event, "/com/example/mamaursbakeshop/SALES.fxml");
    }

    @FXML
    protected void handleSuppliers(ActionEvent event) throws IOException {
        loadScene(event, "/com/example/mamaursbakeshop/SUPPLIERS.fxml");
    }

    @FXML
    protected void handleEmployees(ActionEvent event) throws IOException {
        loadScene(event, "/com/example/mamaursbakeshop/EMPLOYEES.fxml");
    }

    @FXML
    protected void handleOperationalCost(ActionEvent event) throws IOException {
        loadScene(event, "/com/example/mamaursbakeshop/OperationalCost.fxml");
    }

    @FXML
    protected void handleBreadStock(ActionEvent event) throws IOException {
        loadScene(event, "/com/example/mamaursbakeshop/BreadStock.fxml");
    }

    @FXML
    protected void handleLogout(ActionEvent event) {
        System.exit(0);
    }

    private void loadScene(ActionEvent event, String fxmlPath) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(fxmlPath));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }
}
