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

import javax.swing.text.DateFormatter;
import java.io.IOException;
import java.net.URL;
import java.time.format.DateTimeFormatter;

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

    @FXML
    public void initiatePlaceOrderProcedure() throws IOException {
        NewOrderController newOrderController = new NewOrderController(loggedUser, services, new Stage(), stage, this);
        newOrderController.initiatePlaceOrderProcedure();
        stage.hide();
    }

    @FXML
    public void handleCancelOrder() {
        if (!ordersTable.getSelectionModel().isEmpty()) {
            services.cancelOrder(ordersTable.getSelectionModel().getSelectedItem());
            refresh();
        }
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
        orderTimestampColumn.setCellValueFactory(param ->
                new SimpleStringProperty(param.getValue().getTimestamp().format(DateTimeFormatter.ofPattern("HH:mm yy-MM-dd"))));
        orderStatusColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getStatus().toString()));
    }
}
