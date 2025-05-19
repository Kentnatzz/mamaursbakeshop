package com.example.mamaursbakeshop;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.io.IOException;

public class EmployeesController {

    @FXML
    private TextArea employeeTextArea;

    @FXML
    private Button updateSalaryButton;


    @FXML
    private void handleBack(ActionEvent event) {
        try {

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Options.fxml"));
            Parent root = fxmlLoader.load();


            Stage stage = new Stage();
            stage.setTitle("Options");
            stage.setScene(new Scene(root));
            stage.show();


            Stage currentStage = (Stage) employeeTextArea.getScene().getWindow();
            currentStage.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void handleUpdateSalary(ActionEvent event) {
        String updatedText = employeeTextArea.getText();


        System.out.println("Updated Employee Info:\n" + updatedText);


    }
}
