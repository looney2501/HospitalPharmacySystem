package controller;

import domain.entities.User;
import domain.enums.UserType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import services.Services;
import services.exceptions.ServicesException;

import java.io.IOException;
import java.net.URL;

/**
 * 
 */
public class LoginController extends GenericController {

    @FXML
    private TextField usernameTextField;
    @FXML
    private TextField passwordTextField;

    public LoginController(Services services, Stage stage) {
        super(services, stage);
    }

    public LoginController(User loggedUser, Services services, Stage stage) {
        super(loggedUser, services, stage);
    }

    /**
     * 
     */
    public void initiateLoginProcedure() throws IOException {
        URL path = this.getClass().getResource("../fxml/login.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(path);
        fxmlLoader.setController(this);

        Parent root = fxmlLoader.load();
        stage.setScene(new Scene(root));
        stage.setTitle("Login");
        stage.show();
    }

    /**
     *
     */
    public void handleLogin() {
        String username = usernameTextField.getText();
        String password = passwordTextField.getText();
        if (username.isEmpty()) {
            MessageAlert.showErrorMessage(null, "Username cannot be empty!");
        }
        else if (password.isEmpty()) {
            MessageAlert.showErrorMessage(null, "Password cannot be empty!");
        }
        else {
            try {
//                MainController mainController = new MainController();
//                mainController.setService(service);
//                mainController.setLoggedUsername(username);
//                mainController.setLoginStage(stage);
//
                UserType userType = services.login(username, password);
                MessageAlert.showMessage(null, Alert.AlertType.CONFIRMATION, "ok", userType.toString());
//
//                FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("../client/fxml/main-view.fxml"));
//                fxmlLoader.setController(mainController);
//                Parent root = fxmlLoader.load();
//
//                Stage mainStage = new Stage();
//                mainStage.setTitle("Swimming races administration");
//                mainStage.setScene(new Scene(root));
//                mainController.setStage(mainStage);
//                mainStage.show();
//
//                resetTextFields();
//                stage.hide();
            } catch (ServicesException e) {
                MessageAlert.showErrorMessage(null, e.getMessage());
            }
        }
    }

    private void resetTextFields() {
        usernameTextField.clear();
        passwordTextField.clear();
    }

}