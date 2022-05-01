package controller;

import domain.entities.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
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

    public LoginController(Services services, javafx.stage.Stage stage) {
        super(services, stage);
    }

    public LoginController(User loggedUser, Services services, javafx.stage.Stage stage) {
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
    @FXML
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
                User loggedUser = services.login(username, password);
                switch (loggedUser.getUserType()) {
                    case Admin -> initiateAdminLoginProcedure(loggedUser);
                    case Pharmacist -> {
                    }
                    case MedicalPersonnel -> {
                    }
                }
                stage.hide();
            } catch (ServicesException e) {
                MessageAlert.showErrorMessage(null, e.getMessage());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void resetTextFields() {
        usernameTextField.clear();
        passwordTextField.clear();
    }

    private void initiateAdminLoginProcedure(User admin) throws IOException {
        AdminController adminController = new AdminController(admin, services, new javafx.stage.Stage(), this.stage);
        adminController.initiateViewMenuProcedure();
    }
}