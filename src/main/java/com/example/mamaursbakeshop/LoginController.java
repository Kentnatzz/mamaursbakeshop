package com.example.mamaursbakeshop;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    protected void onLoginButtonClick(ActionEvent event) {
        String username = usernameField.getText().trim();
        String password = passwordField.getText().trim();

        if (username.equals("admin") && password.equals("admin")) {
            loadFXML("/com/example/mamaursbakeshop/OPTIONS.fxml", event); // Full access
        } else if (username.equals("employee") && password.equals("employee")) {
            loadFXML("/com/example/mamaursbakeshop/STOCKS2.fxml", event); // Limited access
        } else {
            showError("Login Failed", "Invalid username or password.");
        }
    }

    private void loadFXML(String fxmlPath, ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showError("Navigation Error", "Could not load screen: " + fxmlPath);
        }
    }

    private void showError(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
