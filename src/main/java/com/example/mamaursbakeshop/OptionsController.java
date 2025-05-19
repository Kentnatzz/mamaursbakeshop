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

    private Stage stage;
    private Scene scene;


    @FXML
    protected void handleSales(ActionEvent event) throws IOException {
        loadScene(event, "SALES.fxml");
    }


    @FXML
    protected void handleStocks(ActionEvent event) throws IOException {
        loadScene(event, "STOCKS.fxml");
    }


    @FXML
    protected void handleSuppliers(ActionEvent event) throws IOException {
        loadScene(event, "SUPPLIERS.fxml");
    }


    @FXML
    protected void handleReports(ActionEvent event) throws IOException {
        loadScene(event, "REPORTS.fxml");
    }


    @FXML
    protected void handleEmployees(ActionEvent event) throws IOException {
        loadScene(event, "EMPLOYEES.fxml");
    }


    @FXML
    protected void handleLogout(ActionEvent event) {
        System.exit(0); // Close the application
    }


    @FXML
    protected void handleBack(ActionEvent event) throws IOException {
        loadScene(event, "LOGIN.fxml"); // Go back to the login screen
    }


    private void loadScene(ActionEvent event, String fxmlFile) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
        Parent root = loader.load();

        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
