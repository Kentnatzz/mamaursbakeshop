package com.example.mamaursbakeshop;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

import java.io.IOException;

public class EmployeesController {

    public static class Employee {
        private final StringProperty id = new SimpleStringProperty();
        private final StringProperty name = new SimpleStringProperty();
        private final StringProperty position = new SimpleStringProperty();
        private final StringProperty shift = new SimpleStringProperty();
        private final StringProperty email = new SimpleStringProperty();
        private final StringProperty rate = new SimpleStringProperty();

        public Employee(String id, String name, String position, String shift, String email, String rate) {
            this.id.set(id);
            this.name.set(name);
            this.position.set(position);
            this.shift.set(shift);
            this.email.set(email);
            this.rate.set(rate);
        }

        public StringProperty idProperty() { return id; }
        public StringProperty nameProperty() { return name; }
        public StringProperty positionProperty() { return position; }
        public StringProperty shiftProperty() { return shift; }
        public StringProperty emailProperty() { return email; }
        public StringProperty rateProperty() { return rate; }
    }

    @FXML
    private TableView<Employee> employeeTable;

    @FXML
    private TableColumn<Employee, String> idColumn, nameColumn, positionColumn, shiftColumn, emailColumn, rateColumn;

    @FXML
    private Button updateSalaryButton, backButton;

    @FXML
    private static ObservableList<Employee> persistentEmployees = FXCollections.observableArrayList(
        new Employee("BK001", "Alex Rivera", "Baker", "Morning", "alex.rivera@bakeryhouse.com", "10$/hr"),
        new Employee("BK002", "Jamie Cruz", "Cashier", "Afternoon", "jamie.cruz@bakeryhouse.com", "12$/hr"),
        new Employee("BK003", "Leo Santos", "Pastry Chef", "Full-Time", "leo.santos@bakeryhouse.com", "16$/hr"),
        new Employee("BK004", "Carla Dizon", "Supervisor", "Morning", "carla.dizon@bakeryhouse.com", "18$/hr"),
        new Employee("BK005", "Miguel Reyes", "Bread Packager", "Afternoon", "miguel.reyes@bakeryhouse.com", "10$/hr"),
        new Employee("BK006", "Nina Flores", "Customer Service Rep", "Full-Time", "nina.flores@bakeryhouse.com", "15$/hr"),
        new Employee("BK007", "Bryan Tan", "Oven Technician", "Morning", "bryan.tan@bakeryhouse.com", "10$/hr"),
        new Employee("BK008", "Rose Lim", "Cleaning Staff", "Night Shift", "rose.lim@bakeryhouse.com", "16$/hr")
    );
    @FXML
    public void initialize() {
        employeeTable.setEditable(true);

        setupColumn(idColumn, Employee::idProperty);
        setupColumn(nameColumn, Employee::nameProperty);
        setupColumn(positionColumn, Employee::positionProperty);
        setupColumn(shiftColumn, Employee::shiftProperty);
        setupColumn(emailColumn, Employee::emailProperty);
        setupColumn(rateColumn, Employee::rateProperty);

        employeeTable.setItems(persistentEmployees);

        updateSalaryButton.setOnAction(e -> {
            System.out.println("Update Salary clicked");
        });

        backButton.setOnAction(this::handleBackButton);
    }

    private void setupColumn(TableColumn<Employee, String> col, java.util.function.Function<Employee, StringProperty> prop) {
        col.setCellValueFactory(cellData -> prop.apply(cellData.getValue()));
        col.setCellFactory(TextFieldTableCell.forTableColumn());
        col.setOnEditCommit(event -> {
            Employee employee = event.getRowValue();
            prop.apply(employee).set(event.getNewValue());
        });
    }


    private void handleBackButton(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/mamaursbakeshop/Options.fxml"));
            Parent root = loader.load();


            Stage stage = (Stage) backButton.getScene().getWindow();

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
