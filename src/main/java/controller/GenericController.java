package controller;

import domain.entities.User;
import javafx.fxml.FXML;
import javafx.stage.Stage;
import services.Services;

public abstract class GenericController {

    protected User loggedUser;
    protected Services services;
    @FXML
    protected Stage stage;

    public GenericController(Services services, Stage stage) {
        this.services = services;
        this.stage = stage;
    }

    public GenericController(User loggedUser, Services services, Stage stage) {
        this.loggedUser = loggedUser;
        this.services = services;
        this.stage = stage;
    }

    public void setLoggedUser(User loggedUser) {
        this.loggedUser = loggedUser;
    }
}
