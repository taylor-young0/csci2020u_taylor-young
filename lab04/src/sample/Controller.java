package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

public class Controller {

    @FXML private TextField usernameField;
    @FXML private TextField passwordField;
    @FXML private TextField fullNameField;
    @FXML private TextField emailField;
    @FXML private TextField phoneNumberField;
    @FXML private DatePicker dateOfBirthField;

    @FXML
    public void registerButtonPressed(ActionEvent actionEvent) {
        System.out.println("Username: " + usernameField.getText());
        System.out.println("Password: " + passwordField.getText());
        System.out.println("Full name: " + fullNameField.getText());
        System.out.println("Email: " + emailField.getText());
        System.out.println("Phone number: " + phoneNumberField.getText());
        System.out.println("Date of birth: " + dateOfBirthField.getValue().toString());
    }
}
