package controller;

import domain.dtos.MedicationDTO;
import domain.entities.Medication;
import domain.entities.Order;
import domain.entities.User;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import services.Services;

import java.io.IOException;
import java.net.URL;
import java.util.List;

public class ConfirmOrderController extends GenericController {

    @FXML
    private TableView<MedicationDTO> medicationsTable;
    @FXML
    private TableColumn<MedicationDTO, String> medicationNameColumn;
    @FXML
    private TableColumn<MedicationDTO, String> medicationProducerColumn;
    @FXML
    private TableColumn<MedicationDTO, String> medicationQuantityColumn;
    private final GenericController parentController;
    private final Order order;
    private final ObservableList<MedicationDTO> medicationsModel = FXCollections.observableArrayList();

    public ConfirmOrderController(User loggedUser, Services services, Stage stage, GenericController parentController, Order order) {
        super(loggedUser, services, stage);
        this.parentController = parentController;
        this.order = order;
    }

    public void initialize() {
        updateMedicationsModel();
        initializeMedicationsTable();
    }

    public void initiateConfirmOrderProcedure() throws IOException {
        URL path = this.getClass().getResource("../fxml/confirm-order.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(path);
        fxmlLoader.setController(this);

        Parent root = fxmlLoader.load();
        stage.setScene(new Scene(root));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Medication Details");
        stage.show();
    }

    @FXML
    public void handleConfirmOrder() {
        services.confirmOrder(order);
        parentController.refresh();
        stage.close();
    }

    @FXML
    public void handleIncompleteOrder() {
        if (!medicationsTable.getSelectionModel().isEmpty()) {
            List<MedicationDTO> medicationDTOs = medicationsTable.getSelectionModel().getSelectedItems();
            services.placeIncompleteOrder(order, medicationDTOs);
            parentController.refresh();
            stage.close();
        }
    }

    public void refresh() {
        updateMedicationsModel();
    }

    private void initializeMedicationsTable() {
        medicationsTable.setItems(medicationsModel);
        medicationsTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        medicationsTable.setRowFactory(tv -> {
            TableRow<MedicationDTO> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty())) {
                    handleViewMedicationDetails(row.getItem().getMedication());
                }
            });
            return row;
        });
        medicationNameColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getMedication().getName()));
        medicationProducerColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getMedication().getProducer()));
        medicationQuantityColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getQuantity().toString()));
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
        medicationsModel.setAll(
                order.getMedications().stream()
                        .map(om -> new MedicationDTO(om.getMedication(), om.getQuantity()))
                        .toList()
        );
    }
}
