package controller;

import domain.entities.Order;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import services.Services;

import java.io.IOException;
import java.net.URL;
import java.time.format.DateTimeFormatter;

public class PharmacistController extends GenericController {

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
    private final ObservableList<Order> ordersModel = FXCollections.observableArrayList();

    public PharmacistController(Services services, Stage stage, Stage loginStage) {
        super(services, stage);
        this.loginStage = loginStage;
    }

    public void initialize() {
        updateOrdersModel();
        initializeOrdersTable();
    }

    @FXML
    public void handleLogout() {
        stage.close();
        loginStage.show();
    }

    @FXML
    public void handleHonorOrder() {
        if (!ordersTable.getSelectionModel().isEmpty()) {
            Order order = ordersTable.getSelectionModel().getSelectedItem();
            services.honorOrder(order);
            refresh();
        }
    }

    public void refresh() {
        updateOrdersModel();
    }

    public void initializeViewMenuProcedure() throws IOException {
        URL path = this.getClass().getResource("../fxml/pharmacist-menu.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(path);
        fxmlLoader.setController(this);

        Parent root = fxmlLoader.load();
        stage.setScene(new Scene(root));
        stage.setTitle("Hospital Pharmacy System");
        stage.show();
    }

    private void updateOrdersModel() {
        ordersModel.setAll(services.getAllOrders());
    }

    private void initializeOrdersTable() {
        ordersTable.setItems(ordersModel);
        ordersTable.setRowFactory(tv -> {
            TableRow<Order> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2) {
                    initiateViewOrderDetailsProcedure(row.getItem());
                }
            });
            return row;
        });
        orderIdColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getId().toString()));
        orderTimestampColumn.setCellValueFactory(param ->
                new SimpleStringProperty(param.getValue().getTimestamp().format(DateTimeFormatter.ofPattern("HH:mm yy-MM-dd"))));
        orderStatusColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getStatus().toString()));
    }

    private void initiateViewOrderDetailsProcedure(Order order) {
        OrderDetailsController orderDetailsController = new OrderDetailsController(services, new Stage(), order);
        try {
            orderDetailsController.initiateViewOrderDetailsProcedure();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
