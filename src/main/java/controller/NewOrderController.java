package controller;

import domain.dtos.MedicationDTO;
import domain.entities.Medication;
import domain.entities.User;
import javafx.beans.binding.Bindings;
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
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import services.Services;

import java.awt.*;
import java.io.IOException;
import java.net.URL;

public class NewOrderController extends GenericController {

    @FXML
    private TableView<Medication> medicationsTable;
    @FXML
    private TableColumn<Medication, String> medicationNameColumn;
    @FXML
    private TableColumn<Medication, String> medicationProducerColumn;
    @FXML
    private TableColumn<Medication, String> medicationStockColumn;
    @FXML
    private TableView<MedicationDTO> basketTable;
    @FXML
    private TableColumn<MedicationDTO, String> basketMedicationNameColumn;
    @FXML
    private TableColumn<MedicationDTO, String> basketMedicationProducerColumn;
    @FXML
    private TableColumn<MedicationDTO, String> basketMedicationQuantityColumn;

    @FXML
    private final Stage menuStage;
    private final ObservableList<Medication> medicationsModel = FXCollections.observableArrayList();
    private final ObservableList<MedicationDTO> basketModel = FXCollections.observableArrayList();
    private final GenericController parentController;

    public NewOrderController(User loggedUser, Services services, Stage stage, Stage menuStage, GenericController parentController) {
        super(loggedUser, services, stage);
        this.menuStage = menuStage;
        this.parentController = parentController;
    }

    public void refresh() {
        updateMedicationsModel();
    }

    public void initiatePlaceOrderProcedure() throws IOException {
        URL path = this.getClass().getResource("../fxml/place-order.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(path);
        fxmlLoader.setController(this);

        Parent root = fxmlLoader.load();
        stage.setScene(new Scene(root));
        stage.setTitle("Hospital Pharmacy System");
        stage.show();
        updateMedicationsModel();
        initializeMedicationsTable();
    }

    @FXML
    public void handleCancel() {
        stage.close();
        menuStage.show();
    }

    @FXML
    public void handleAddToBasket() {
        var selectionModel = medicationsTable.getSelectionModel();
        if (!selectionModel.isEmpty()) {
            MedicationDTO medicationDTO = new MedicationDTO(selectionModel.getSelectedItem());
            if (basketModel.contains(medicationDTO)) {
                MessageAlert.showErrorMessage(null, "Medication already added to basket!");
            } else if (medicationDTO.getMedication().getStock() == 0) {
                MessageAlert.showErrorMessage(null, "Medication is out of stock!");
            }
            else {
                basketModel.add(new MedicationDTO(selectionModel.getSelectedItem()));
            }
        }
    }

    @FXML
    public void handleClearBasket() {
        basketModel.clear();
    }

    @FXML
    public void handleRemoveMedicationFromBasket() {
        var selectionModel = basketTable.getSelectionModel();
        if (!selectionModel.isEmpty()) {
            basketModel.remove(selectionModel.getSelectedItem());
        }
    }

    @FXML
    public void initializeViewBasketProcedure() throws IOException {
        URL path = this.getClass().getResource("../fxml/basket.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(path);
        fxmlLoader.setController(this);

        Parent root = fxmlLoader.load();
        stage.setScene(new Scene(root));
        stage.setTitle("Hospital Pharmacy System");
        stage.show();
        initializeBasketTable();
    }

    @FXML
    public void handleGoBack() throws IOException {
        initiatePlaceOrderProcedure();
    }

    @FXML
    public void handlePlaceOrder() {
        services.addOrder(loggedUser.getSection(), basketModel);
        parentController.refresh();
        menuStage.show();
        stage.close();
    }

    @FXML
    public void handleLowerQuantity() {
        if (!basketTable.getSelectionModel().isEmpty()) {
            MedicationDTO selectedBasketItem = basketTable.getSelectionModel().getSelectedItem();
            Integer currentQuantity = selectedBasketItem.getQuantity();
            if (currentQuantity > 1) {
                basketTable.getSelectionModel().getSelectedItem().setQuantity(currentQuantity - 1);
            }
            basketTable.refresh();
        }
    }

    @FXML
    public void handleIncreaseQuantity() {
        if (!basketTable.getSelectionModel().isEmpty()) {
            MedicationDTO selectedBasketItem = basketTable.getSelectionModel().getSelectedItem();
            Integer currentQuantity = selectedBasketItem.getQuantity();
            Integer stock = selectedBasketItem.getMedication().getStock();
            if (currentQuantity < stock) {
                selectedBasketItem.setQuantity(currentQuantity + 1);
            }
            basketTable.refresh();
        }
    }

    private void initializeMedicationsTable() {
        medicationsTable.setItems(medicationsModel);
        medicationsTable.setRowFactory(tv -> {
            TableRow<Medication> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty())) {
                    handleViewMedicationDetails(row.getItem());
                }
            });
            return row;
        });
        medicationNameColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getName()));
        medicationProducerColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getProducer()));
        medicationStockColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getStock().toString()));
    }

    private void initializeBasketTable() {
        basketTable.setItems(basketModel);
        basketTable.setRowFactory(tv -> {
            TableRow<MedicationDTO> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty())) {
                    handleViewMedicationDetails(row.getItem().getMedication());
                }
            });
            return row;
        });
        basketMedicationNameColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getMedication().getName()));
        basketMedicationProducerColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getMedication().getProducer()));
        basketMedicationQuantityColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getQuantity().toString()));
    }

    private void handleViewMedicationDetails(Medication medication) {
        MedicationDetailsController medicationDetailsController = new MedicationDetailsController(services, new Stage(), medication, this);
        try {
            medicationDetailsController.initiateViewMedicationDetailsProcedure();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void updateMedicationsModel() {
        medicationsModel.setAll(services.getAllMedications());
    }
}
