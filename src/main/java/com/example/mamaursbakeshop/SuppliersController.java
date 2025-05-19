package com.example.mamaursbakeshop;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SuppliersController implements Initializable {

    @FXML
    private TextField supplierNameField;

    @FXML
    private TextField supplierAddressField;

    @FXML
    private ListView<String> supplierListView;

    @FXML
    private void handleAddSupplier() {
        String name = supplierNameField.getText().trim();
        String address = supplierAddressField.getText().trim();

        if (!name.isEmpty() && !address.isEmpty()) {
            String supplierInfo = name + " - " + address;
            supplierListView.getItems().add(supplierInfo);


            supplierNameField.clear();
            supplierAddressField.clear();
        } else {
            System.out.println("Please enter both name and address.");
        }
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


            Stage currentStage = (Stage) supplierListView.getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        supplierListView.getItems().addAll(
                "Golden Grains Co. - Manila, Philippines",
                "FreshBake Supplies - Cebu City",
                "Dough Masters Inc. - Davao City",
                "Yeast & Flour Distributors - Quezon City",
                "BakeSource PH - Bacolod",
                "Supreme Baking Goods - Iloilo",
                "Tasty Trade Corp. - Baguio City",
                "Crusty Ingredients Ltd. - General Santos",
                "Flourish Supply Chain - Tagaytay",
                "Panadero Partners - Laguna"
        );
    }
}
