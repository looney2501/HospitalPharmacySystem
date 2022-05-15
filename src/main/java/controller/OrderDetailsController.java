package controller;

import domain.dtos.MedicationDTO;
import domain.entities.Medication;
import domain.entities.Order;
import domain.entities.OrderMedication;
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
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import services.Services;

import java.io.IOException;
import java.net.URL;
import java.time.format.DateTimeFormatter;

public class OrderDetailsController extends GenericController {

    @FXML
    private TableView<MedicationDTO> medicationsTable;
    @FXML
    private TableColumn<MedicationDTO, String> medicationNameColumn;
    @FXML
    private TableColumn<MedicationDTO, String> medicationProducerColumn;
    @FXML
    private TableColumn<MedicationDTO, String> medicationQuantityColumn;
    @FXML
    private Label sectionLabel;
    @FXML
    private Label timestampLabel;
    @FXML
    private Label statusLabel;
    private final ObservableList<MedicationDTO> medicationsModel = FXCollections.observableArrayList();
    private final Order order;

    public OrderDetailsController(User loggedUser, Services services, Stage stage, Order order) {
        super(loggedUser, services, stage);
        this.order = order;
    }

    public void initialize() {
        updateMedicationsModel();
        initializeMedicationsTable();
        initializeLabels();
    }

    public void initiateViewOrderProcedure() throws IOException {
        URL path = this.getClass().getResource("../fxml/order-details.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(path);
        fxmlLoader.setController(this);

        Parent root = fxmlLoader.load();
        stage.setScene(new Scene(root));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Medication Details");
        stage.show();
    }

    private void initializeMedicationsTable() {
        medicationsTable.setItems(medicationsModel);
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

    public void refresh() {
        updateMedicationsModel();
    }

    private void updateMedicationsModel() {
        medicationsModel.setAll(
                order.getMedications().stream()
                        .map(om -> new MedicationDTO(om.getMedication(), om.getQuantity()))
                        .toList()
        );
    }

    private void initializeLabels() {
        sectionLabel.setText(order.getMedicalSection());
        timestampLabel.setText(order.getTimestamp().format(DateTimeFormatter.ofPattern("HH:mm yy-MM-dd")));
        statusLabel.setText(order.getStatus().toString());
    }
}
