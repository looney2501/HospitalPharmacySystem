package controller;

import domain.entities.Order;
import domain.entities.User;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import services.Services;

import java.io.IOException;
import java.net.URL;

public class MedicalPersonnelController extends GenericController {

    @FXML
    private TableView<Order> ordersTable;
    @FXML
    private TableColumn<Order, String> orderIdColumn;
    @FXML
    private TableColumn<Order, String> orderTimestampColumn;
    @FXML
    private TableColumn<Order, String> orderStatusColumn;
    @FXML
    private final Stage loginStage;
    @FXML
    private Label medicalSectionLabel;
    private final ObservableList<Order> ordersModel = FXCollections.observableArrayList();

    public MedicalPersonnelController(User loggedUser, Services services, Stage stage, Stage loginStage) {
        super(loggedUser, services, stage);
        this.loginStage = loginStage;
    }

    public void initialize() {
        updateOrdersModel();
        initializeOrdersTable();
        medicalSectionLabel.setText(loggedUser.getSection());
    }

    @FXML
    public void handleLogout() {
        stage.close();
        loginStage.show();
    }

    public void initializeViewMenuProcedure() throws IOException {
        URL path = this.getClass().getResource("../fxml/medical-personnel-menu.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(path);
        fxmlLoader.setController(this);

        Parent root = fxmlLoader.load();
        stage.setScene(new Scene(root));
        stage.setTitle("Hospital Pharmacy System");
        stage.show();
    }

    public void refresh() {
        updateOrdersModel();
    }

    public void updateOrdersModel() {
        ordersModel.setAll(services.getAllOrdersBySection(loggedUser.getSection()));
    }

    public void initializeOrdersTable() {
        ordersTable.setItems(ordersModel);
        orderIdColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getId().toString()));
        orderTimestampColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getTimestamp().toString()));
        orderStatusColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getStatus().toString()));
    }
}
